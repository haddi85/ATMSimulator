# Project Description
ATM Interaction Simulator Command Line Based.

# How to configure
`Pre-requsiite`
- Maven
- java 8

`To compile fresh JAR run the below Command`
- mvn clean package

# How to run
java -jar target/ATM-1.0-SNAPSHOT.jar
# Example
```bash
login maa
Hello, maa!
Your balance is $0
despoit 45
Unrecognized command!
deposit 50
Your balance is $50
withdraw 20
Your balance is $30
transfer maa 20
Cannot transfer to same account!
transfer shub 30
Transferred account not found!
logout
Goodbye, maa!
login shub
Hello, shub!
Your balance is $0
deposit 40
Your balance is $40
transfer maa 60
Transferred $40 to maa
Your balance is $0
Owed $20 to maa
logout
Goodbye, shub!
login maa
Hello, maa!
Your balance is $70
Owed $20 from shub
transfer shub 10
Your balance is $70
Owed $10 from shub
logout
Goodbye, maa!
login shub
Hello, shub!
Your balance is $0
Owed $10 to maa
logout
Goodbye, shub!
login maa
Hello, maa!
Your balance is $70
Owed $10 from shub
transfer shub 20
Transferred $10 to shub
Your balance is $60
logout
Goodbye, maa!
login shub
Hello, shub!
Your balance is $10
exit


# Available command
- login [name] -
This displays account owner name and balance.
- deposit [amount] -
This command deposit amount to the logged in account.
- withdraw [amount] -
Withdraws given amount from account.
- transfer [target_name] [amount] -
Transfer given amount to a particular named owner
- logout -
Logout from oner's profile
- exit -
Exit from execution of jar/program
