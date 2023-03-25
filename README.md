![GitHub top language](https://img.shields.io/github/languages/top/Gaurav000001/Tender-Management-API?color=yellow)
![GitHub forks](https://img.shields.io/github/forks/Gaurav000001/Tender-Management-API?style=social)
![GitHub Repo stars](https://img.shields.io/github/stars/Gaurav000001/Tender-Management-API?style=social)

<img alt="coding banner" width="100%" src="https://github.com/Gaurav000001/Tender-Management-API/blob/main/Tender%20Cover%20Image.jpg">

## Flow Chart

<img alt="coding banner" width="100%" src="https://github.com/Gaurav000001/Tender-Management-API/blob/main/Flow%20Chart.png">

## Tender Management System

#### C.L.I BASED APPLICATION

Tender Management System is designed to streamline the process of floating **Tenders** and managing **Bids** for a **Company**.

The system allows for the `Creation` and `Management` of an empaneled list of **Vendors** who are eligible to **Bid** on **Tenders**.

The application will be used by the two categories of users:

- **Administrator (Admin)**

- **Vendor (Bidder / User)**

## Tech Stack

- Java
- J.D.B.C.
- MySql

## System Structure

The **Administrator** is responsible for managing the system, including creating a new **Vendor**, Deleting a **Vendor**, Create new **Tender**, View all Tenders / Vendors, etc.

The **Vendor** can view all current **Tenders** for which he/she got **Selected**, place **Bids**, View status of his/her Bid, View Bid **History**, `Update` Profile and `Change Password`.

> A **Vendor** can **Bid** for a **Tender** only once.

- ### Administrator

  - Register new Vendor
  - View all Vendors
  - Create new Tender
  - View all Tenders
  - View Bids of Tender
  - Assign Tender to Vendor
  - Delete Vendor
  - Delete Tender
  
 - ### Vendor / Bidder

    - Show Current Tenders
    - Place Bid for Tender
    - Show Bid History
    - Delete Bid
    - Update Profile
    - Change Password

## Setting and Installation

Install the Spring Tool Suite
```bash
https://spring.io/tools
```

Install MySQL Community Server
```bash
https://dev.mysql.com/downloads/mysql/
```

Clone the Project
```bash
https://github.com/Gaurav000001/Tender-Management-API.git
```


> ### Import the database attached above


Open MySQL Server
```bash
Create a New Database in SQL: tms
```

Go to the Project Directory & Open SQL Folder > SQL Tables
```bash
Create The Same Tables in Your tms Database 
```

## Run Locally

Go to the Project Directory
```Bash
Open the `Tender Management System/tms` Folder With S.T.S 
```

Go to Build Path > Libraries > Class_Path
```bash
Add the Jar File Present in JDBC Driver As External Jar
```

Go to utility Package > dbDetails.properties
```bash
url jdbc:mysql://localhost:3306/tms
username your-username
password your-password
```

Go to UI Package
```bash
NOTE : Insert Data Into Database Either Through Application OR Through MySQL By Entering the Queries From SQL Queries  
```

```bash
Run as Java Application !
```

## Contributions

Contributions are always **Welcome** !

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are Greatly Appreciated.

If you have a suggestion that would make this application better, Please fork the repo and create a pull request. You can also connect with me for further development of this application !

Don't forget to give the project a star ! Thank You !

## Authors

- [Gaurav Ganguly](https://github.com/Gaurav000001)

## Contact Me

[![portfolio](https://img.shields.io/badge/my_portfolio-A020F0?style=for-the-badge&logo=ko-fi&logoColor=white)](https://Gaurav000001.github.io/)

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/gaurav-ganguly-bb7ba9246/)
