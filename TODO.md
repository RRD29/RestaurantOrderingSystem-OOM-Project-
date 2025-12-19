# TODO List for Waiter Panel Enhancement

## Overview
Modify the WaiterUI to display a list of customers with active orders upon opening, include a "New Customer" button, and allow viewing/adding items to existing customer orders.

## Steps
- [x] Update OrderService.java: Add method to get active customers (with pending orders).
- [x] Update OrderService.java: Add method to add items to an existing order.
- [x] Modify WaiterUI.java: Restructure to have customer selection panel first.
- [x] Modify WaiterUI.java: Add "New Customer" button to switch to new order mode.
- [x] Modify WaiterUI.java: Handle customer selection to load existing order and allow adding items.
- [x] Modify WaiterUI.java: Update placeOrder method to handle adding to existing orders.
- [x] Test the changes by running the application.
