# CanalPlus ** Test Technique 

 

## Technologies 

l'application utilise une base de données en mémoire qui est initialisée 
au moment de lancement de l'application afin de faciliter les tests ( data.sql ) 

*Java 11
*Spring Boot 2.4.1 
*H2
*Maven 3.8.4 

## Utilisation 

git clone -> mvn clean install -> run Application ! et c'est partii ! 

## Services 


#### /api/reunions/create  
un POST avec l'objet Reunion comme parametre 
sert a créer une réunion en fonction de nombre de personnes , des salles et des équipements disponibles. 

Exemple : 
name : "Reunion 1"
start : "2022-02-11T10:00:00"
end : "2022-02-11T10:00:00"
numberOfPersons; 8
reunionType; "VC"

 #### /api/reservations 
un GET avec aucun parametre 

sert a récupérer toutes les réservations dans le systéme 


#### /api/reunions
un GET avec aucun parametre 

sert a récupérer tous les reunions dans le systéme 


#### /api/salles
un GET avec aucun parametre 

sert a récupérer toutes les sallesdans le systéme 

#### /api/reunions/delete

un POST avec l'id de reunion en parametre 

sert a supprimer une réunion déja planifiée 
