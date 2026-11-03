# Test Case Inventory

| ID | Module | Test scenario | Expected result | Automation method |
|---|---|---|---|---|
| TC-LOGIN-001 | Login | Sign in with standard user credentials | Inventory page titled `Products` is displayed | `validUserCanLogin` |
| TC-LOGIN-002 | Login | Sign in with an invalid username | Authentication error is displayed | `invalidCredentialsShowValidationMessage` |
| TC-LOGIN-003 | Login | Sign in with an invalid password | Authentication error is displayed | `invalidCredentialsShowValidationMessage` |
| TC-LOGIN-004 | Login | Submit with username blank | Username-required error is displayed | `invalidCredentialsShowValidationMessage` |
| TC-LOGIN-005 | Login | Submit with password blank | Password-required error is displayed | `invalidCredentialsShowValidationMessage` |
| TC-LOGIN-006 | Login | Sign in with locked-out user | Locked-out error is displayed | `invalidCredentialsShowValidationMessage` |
| TC-PROD-001 | Inventory | Load the inventory page | Six products are displayed | `inventoryDisplaysSixProducts` |
| TC-PROD-002 | Inventory | Sort products by price low to high | Prices are in ascending order | `productsCanBeSortedByPriceAscending` |
| TC-CART-001 | Cart | Add two products | Badge and cart contents show two products | `userCanAddAndRemoveProducts` |
| TC-CART-002 | Cart | Remove a product | Removed product is absent and count is updated | `userCanAddAndRemoveProducts` |
| TC-CART-003 | Cart | Open cart without adding products | Cart contains no products | `emptyCartDisplaysNoProducts` |
| TC-CHK-001 | Checkout | Complete checkout with valid customer information | Order confirmation is displayed | `userCanCompleteCheckout` |
| TC-CHK-002 | Checkout | Continue checkout without first name | First-name validation message is displayed | `checkoutRequiresFirstName` |
