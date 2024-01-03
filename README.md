According to requirments and analisys of the problem, i decided use mongo for  storing information, for the the next reasons:
1.- There ins´t a relational structure for save information 
2.- Just needed a simple collection for save currency change details
3.- using mongo can improve reading and writing performance

Persistence Layer
I use "org.springframework.boot:spring-boot-starter-data-mongodb" which is a dependency used by spring boot,  for manage conection whit mongo.

For manage interections whit mongo we use the MongoRepository interface seting up a Document called CurrencyExchange. 
CurrencyExchange is a java class what let us to maping a mongo document its fields and structure.

I use Mongo Template for make specific queries. Therefore i adding criteria filters for getting information precisely.
