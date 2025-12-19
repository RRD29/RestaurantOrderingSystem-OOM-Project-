package com.restaurant.service;

import com.restaurant.model.Order;
import com.restaurant.model.OrderItem;
import com.restaurant.util.DBUtil;
import java.sql.*;
import java.util.*;

public class OrderService {

    public static int createOrder(Order order) throws SQLException{
        String insertOrder="INSERT INTO orders(customer_name,table_number,total) VALUES(?,?,?)";
        String insertItem="INSERT INTO order_items(order_id,menu_id,item_name,price,quantity) VALUES(?,?,?,?,?)";
        try(Connection c=DBUtil.getConnection();
            PreparedStatement psOrder=c.prepareStatement(insertOrder,Statement.RETURN_GENERATED_KEYS)){
            psOrder.setString(1, order.getCustomerName());
            psOrder.setInt(2, order.getTableNumber());
            psOrder.setDouble(3, order.getTotal());
            psOrder.executeUpdate();
            try(ResultSet keys=psOrder.getGeneratedKeys()){
                if(keys.next()){
                    int orderId=keys.getInt(1);
                    try(PreparedStatement psItem=c.prepareStatement(insertItem)){
                        for(OrderItem it: order.getItems()){
                            psItem.setInt(1, orderId);
                            psItem.setInt(2, it.getId() != null ? it.getId() : 0); // Use getId() for menu_id
                            psItem.setString(3,it.getItemName());
                            psItem.setDouble(4,it.getPrice());
                            psItem.setInt(5,it.getQuantity());
                            psItem.addBatch();
                        }
                        psItem.executeBatch();
                    }
                    return orderId;
                }
            }
        }
        return -1;
    }

    public static List<Order> getAllOrders(){
        List<Order> list=new ArrayList<>();
        String sql="SELECT id,customer_name,table_number,total,status FROM orders ORDER BY created_at DESC";
        try(Connection c=DBUtil.getConnection();
            PreparedStatement ps=c.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                list.add(new Order(rs.getInt("id"),rs.getString("customer_name"),rs.getInt("table_number"),rs.getDouble("total"),rs.getString("status")));
            }
        }catch(SQLException e){e.printStackTrace();}
        return list;
    }

    public static List<OrderItem> getItemsForOrder(int orderId){
        List<OrderItem> items=new ArrayList<>();
        String sql="SELECT menu_id,item_name,price,quantity FROM order_items WHERE order_id=?";
        try(Connection c=DBUtil.getConnection();
            PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,orderId);
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                    Integer menuId=rs.getObject("menu_id")==null?null:rs.getInt("menu_id");
                    items.add(new OrderItem(menuId,rs.getString("item_name"),rs.getDouble("price"),rs.getInt("quantity")));
                }
            }
        }catch(SQLException e){e.printStackTrace();}
        return items;
    }

    public static List<Order> getActiveOrders(){
        List<Order> list=new ArrayList<>();
        String sql="SELECT id,customer_name,table_number,total,status FROM orders WHERE status IN ('PENDING','IN_PROGRESS') ORDER BY created_at DESC";
        try(Connection c=DBUtil.getConnection();
            PreparedStatement ps=c.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                list.add(new Order(rs.getInt("id"),rs.getString("customer_name"),rs.getInt("table_number"),rs.getDouble("total"),rs.getString("status")));
            }
        }catch(SQLException e){e.printStackTrace();}
        return list;
    }

    public static boolean addItemsToOrder(int orderId, List<OrderItem> newItems) throws SQLException{
        String insertItem="INSERT INTO order_items(order_id,menu_id,item_name,price,quantity) VALUES(?,?,?,?,?)";
        String updateTotal="UPDATE orders SET total = total + ? WHERE id = ?";
        try(Connection c=DBUtil.getConnection()){
            c.setAutoCommit(false);
            double additionalTotal = newItems.stream().mapToDouble(i -> i.getPrice()*i.getQuantity()).sum();
            try(PreparedStatement psItem=c.prepareStatement(insertItem)){
                for(OrderItem it: newItems){
                    psItem.setInt(1, orderId);
                    psItem.setInt(2, it.getId() != null ? it.getId() : 0);
                    psItem.setString(3,it.getItemName());
                    psItem.setDouble(4,it.getPrice());
                    psItem.setInt(5,it.getQuantity());
                    psItem.addBatch();
                }
                psItem.executeBatch();
            }
            try(PreparedStatement psTotal=c.prepareStatement(updateTotal)){
                psTotal.setDouble(1, additionalTotal);
                psTotal.setInt(2, orderId);
                psTotal.executeUpdate();
            }
            c.commit();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }
    }
}
