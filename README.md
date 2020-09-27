# Web programming project for university (2020)
## Technologies used:
* Backend:
    * Jersey JAX-RS
    * Built on top of [jersey-rest-template](https://github.com/nikolagudelj/jersey-rest-template)
    * Eclipse IDE
* Frontend
    * Vue.js
    * HTML/CSS
## Features: 
* AirBNB-inspired website for apartment rental. 
* Supports Guest, Host and Administrator accounts
* Guest can make and cancel reservations for available apartments
* Guests can review apartments at which they stayed, or have been denied
* Hosts can Accept/Deny reservations
* Hosts can create and edit their own apartments
* Administrators have access to everything
* Administrator creates host accounts, Guests can create an account by themselves

## Technicalities
Data storage consists of a series of files which store data in .txt files as JSON strings. Upon server start, JSON files are read and deserialized into ```Map<String, T>``` objects and stored in a Map variable inside of their respective ```DAO``` objects. No database has been used for this project (as per the project specification). Every change (CRUD operation) in the ```Map``` object, fires a ```Storage.writeAll()``` method, which serializes the ```Map``` into a JSON, and overwrites the existing data in the respective .txt file.
<br><br>
HTML is hosted and distributed using the ```HtmlService``` class, which caches all the .html files in local memory upon their first use. JS scripts are stored in the same way, and can be accessed through ```localhost:8080/WebProject/scripts/scriptname``` in the browser.
<br><br>
Classes for objects which needed to have their own .txt json files (Accounts, Apartments,...) are extending ```DatabaseEntity```.<br>
Each one of those classes has its own ```DAO``` class, which extends ```BeanDAO``` (provides basic CRUD), with some additional methods built on top of it. <br>
REST service classes have been implemented on top of the ```CRUDService<T extends DatabaseEntity>``` class. Each REST service class is tied to 1 ```DatabaseEntity```. It creates and holds a ```ServletContext``` reference to the ```T-dao extends BeanDAO``` which is used for accessing and manipulating data inside of the ```T-dao``` object.
<br><br>
Identification was kept simple, with 2 helper interfaces with default methods (```SessionTracker```, ```SessionHandler```). Each client ```HttpSession``` is assigned an attribute upon login. Attribute contains a ```SessionToken``` object, which is read every time a client wants to access or modify something. Upon log out, ```SessionToken``` is removed from the ```HttpSession```. <br>
Each REST service class implements 1 or both of the aforementioned interfaces, in order to control who can access or modify its data.
<br>

## Screenshots of important pages
* Login screen <br>
![Login screen](https://i.imgur.com/XVksC2x.png)
<br><br>
* Apartment browse for guests
![Apartment browse](https://i.imgur.com/GESduuv.png)
<br><br>
* Apartment details for guests
![Apartment details](https://i.imgur.com/vHOQDXi.png)
<br><br>
* Apartment filters
![Apartment filters](https://i.imgur.com/tCautQH.png)
<br><br>
* Reservations overview for host
![Reservations overview](https://i.imgur.com/S0zgyKn.png)
<br><br>
* Edit apartment for host and administrator
![Edit apartment](https://i.imgur.com/B3qIwcD.png)
<br><br>
* Reviews for an apartment
![Reviews](https://i.imgur.com/WwbFdpO.png)
<br><br>

