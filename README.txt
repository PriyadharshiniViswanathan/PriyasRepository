Run the project as java application.
Mention the operation in first arguement and input parameter in second arguement.

Input to the application has to be in below format.
1)List

args[0] = "list"
args[1] can be the searching string.

2)add
args[0] ="add"
args[1],book fields should be semi-colon seperated.
Eg:Desired1;Rich Bloke1;564.50;0

3)buy
args[0]="buy"
args[1],books should be # seperated and the book fields should be semi-colon seperated.
Eg:"Desired1;Rich Bloke1;564.50;0#Desired;Rich Bloke;564.50;0#Random Sales;Cunning Bastard;499.50;3"