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