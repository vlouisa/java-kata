### LOW-HANGING FRUIT ### 
- includeHeader seems obsolete
- state of item is stored in two separate lists
- item is represented as a String
- price is represented as a double
- taxRate looks like a constant
- taxRate represented as a double (percentage/fraction)
- total, grandTotal tax is represented as double

### DESIGN ISSUES ###
- printInvoice     -> presentation and business logic
- sendInvoiceEmail -> presentation and business logic
- duplicate calculation in printInvoice and sendInvoiceEmail
- class has multiple responsibilities

### DESIGN ISSUES - BREAKING CHANGES ###
- customer info is passed in separate parameters
- addItem accepts separate parameters
- customer info is injected via constructor?
