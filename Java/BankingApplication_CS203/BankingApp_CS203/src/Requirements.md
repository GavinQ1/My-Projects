Final Project: Banking System
Due 12/16/2016 at the latest.

This is meant to be done by yourself, not in a team.
The project will be graded on quality of code, adherence to the requirements, and quality of the testing framework.
Please upload your project to your git repository.
A word to the wise: Remember that the last 10% of your project takes 90% of the time.
Do not wait until the last day or two to create this application. Start as early as possible.
Requirements:

Create a Banking application with an Account class, and several subclasses, SavingsAccount and CheckingAccount, that inherit from Account.
Create a ListOfAccounts class that uses a single Arraylist<Account> of Savings and Checking accounts.
An Account has a name and balance.
A Checking account has a transaction fee of $.10 per check (withdrawal), deposits are free.
A Savings account earns interest of 0.01% daily and has no fees.
Create a BankApplication class with a main() function.
The BankApplication should create a ListOfAccounts object and populate it with accounts of various types.
BankApplication should start a GUI (BankAppGUI), and use the MVC pattern.
The user should be able to type in the amount to deposit or withdraw in the GUI, and have a 'submit' button next to the input field or fields.
The user should be able to create new accounts through the GUI.
The user should be able to deposit and withdraw from each account with appropriate fees and interest.
The GUI should be able to display a single account, found by name.
The GUI should be able to display all the savings accounts in the list.
The GUI should be able to display all the checking accounts in the list.
The GUI should be able to display the entire account list.
