# TODO List for Menu Visibility Enhancement

## Task: Ensure menu visible to admin is also visible to waiter, and changes are reflected.

### Steps:
- [x] Modify WaiterUI.java to add a "View Menu" button in the bottom panel.
- [x] Add action listener to the "View Menu" button to open a new MenuViewUI() instance.
- [x] Test the changes to ensure the menu view opens correctly and reflects updates.

### Notes:
- The menu data is already shared via the database through MenuService.
- Admin can manage menu via MenuManagementUI, and Waiter can view via the new button.
- Refresh functionality in WaiterUI already allows updating the order-taking menu.
