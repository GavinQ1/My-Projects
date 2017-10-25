# Bank Application

## Features

### Settle Policy
The program will perform a settlement action for all saving accounts when it is initialized.<br/>
The program will perform a settlement action for all saving accounts when it is 00:00:00 everyday.<br/>
### Deposite/Withdraw
The program will invoke warning if input is not a valid number.<br/>
The program will invoke warning if trying to withdraw an amount greater than the current balance. <br/>
For checking accounts, a withdraw is valid iff balance >= amount + transaction fee. <br/>
### New Account
The program will invoke warning if name is consist of only spaces or is empty.<br/>
Same name under different account type is allowed. (Case sensitive) <br/>
Same name under same account type is not allowed and the program will invoke warning. (Case sensitive) <br/>