-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: tms
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bidder`
--

DROP TABLE IF EXISTS `bidder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bidder` (
  `Bid_id` int NOT NULL AUTO_INCREMENT,
  `Vendor_id` int NOT NULL,
  `Tender_id` int NOT NULL,
  `Bid_Amount` int NOT NULL,
  `Bid_Date` date NOT NULL,
  `Bid_status` varchar(10) DEFAULT (_cp850'Pending'),
  PRIMARY KEY (`Bid_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bidder`
--

LOCK TABLES `bidder` WRITE;
/*!40000 ALTER TABLE `bidder` DISABLE KEYS */;
INSERT INTO `bidder` VALUES (1,6,1,393940,'2023-02-22','Selected'),(2,6,2,49383,'2023-02-22','Rejected'),(3,7,1,393940,'2023-02-23','Rejected'),(4,7,2,400000,'2023-02-22','Selected');
/*!40000 ALTER TABLE `bidder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tender`
--

DROP TABLE IF EXISTS `tender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tender` (
  `Tid` int NOT NULL AUTO_INCREMENT,
  `tender_name` varchar(30) NOT NULL,
  `tender_type` varchar(50) NOT NULL,
  `tender_desc` varchar(100) DEFAULT NULL,
  `tender_price` int NOT NULL,
  `tender_deadline` date NOT NULL,
  `tender_location` varchar(30) NOT NULL,
  `tender_status` varchar(10) DEFAULT (_cp850'Open'),
  PRIMARY KEY (`Tid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tender`
--

LOCK TABLES `tender` WRITE;
/*!40000 ALTER TABLE `tender` DISABLE KEYS */;
INSERT INTO `tender` VALUES (1,'bridge','construction',NULL,393930,'2023-04-05','Sikkim','Open'),(2,'damp','maintainance',NULL,393838,'2024-05-10','Uttar Pradesh','Open'),(3,'Savage','Construction','Create savage system',393039,'2023-12-01','Chennai','Open'),(4,'Savage','Ccnstruction','create savage System',399840,'2023-12-01','Chennai','Open');
/*!40000 ALTER TABLE `tender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendor`
--

DROP TABLE IF EXISTS `vendor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendor` (
  `Vid` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `mobileNumber` varchar(10) NOT NULL,
  `address` varchar(30) NOT NULL,
  `companyName` varchar(30) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`Vid`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `mobileNumber` (`mobileNumber`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendor`
--

LOCK TABLES `vendor` WRITE;
/*!40000 ALTER TABLE `vendor` DISABLE KEYS */;
INSERT INTO `vendor` VALUES (6,'Gaurav444','gangulygaurav444@gmail.com','7066457141','Maharashtra','Ganguly Limited','Ga123@'),(7,'Saurav333','saurav4321@yahoo.in','9657307790','Indore','Saurav Limited','Sg345@'),(8,'Gaurav166@ganguly','gangulygaurav1666@gmail.com','9545995527','shankar nagar, chandrapur','','1234@Ganguly'),(9,'Roshni123','roshniKapoor@masaischool.in','8444048445','shankar nagar','kapoor enterprises','1234@Ganguly'),(10,'Ganguly001','gauravganguly514@gmail.com','7878909797','New York','York Enterprises','5678@Pass');
/*!40000 ALTER TABLE `vendor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-26 14:30:41
