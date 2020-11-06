-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: proyectoserver
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contacts` (
  `idContact` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `server` varchar(45) NOT NULL,
  PRIMARY KEY (`idContact`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES (1,'laura','45'),(2,'pablo','23'),(3,'martin','789.654');
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ips`
--

DROP TABLE IF EXISTS `ips`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ips` (
  `idIPs` int NOT NULL AUTO_INCREMENT,
  `serverName` varchar(45) NOT NULL,
  `ip` varchar(45) NOT NULL,
  PRIMARY KEY (`idIPs`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ips`
--

LOCK TABLES `ips` WRITE;
/*!40000 ALTER TABLE `ips` DISABLE KEYS */;
INSERT INTO `ips` VALUES (1,'42','1583.14.12.65'),(2,'78','483.486.48.14');
/*!40000 ALTER TABLE `ips` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mails`
--

DROP TABLE IF EXISTS `mails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mails` (
  `idMail` int NOT NULL AUTO_INCREMENT,
  `author` varchar(50) NOT NULL,
  `server` varchar(25) NOT NULL,
  `matter` varchar(60) NOT NULL,
  `body` varchar(300) NOT NULL,
  PRIMARY KEY (`idMail`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mails`
--

LOCK TABLES `mails` WRITE;
/*!40000 ALTER TABLE `mails` DISABLE KEYS */;
INSERT INTO `mails` VALUES (1,'juan','90','kdfj','laksdfjlsdfj'),(2,'juan ','32','sldfjl','lksadjfl'),(3,'pedro','31','fjk','sdlkfjsdlfkjdsf');
/*!40000 ALTER TABLE `mails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `idUser` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'andrea','97','off'),(2,'brandon','654','off');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_contacts`
--

DROP TABLE IF EXISTS `users_contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_contacts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idUser` int NOT NULL,
  `idContact` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_idx` (`idUser`),
  KEY `fk_contact_idx` (`idContact`),
  CONSTRAINT `fk_contact` FOREIGN KEY (`idContact`) REFERENCES `contacts` (`idContact`),
  CONSTRAINT `fk_userC` FOREIGN KEY (`idUser`) REFERENCES `users` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_contacts`
--

LOCK TABLES `users_contacts` WRITE;
/*!40000 ALTER TABLE `users_contacts` DISABLE KEYS */;
INSERT INTO `users_contacts` VALUES (1,1,2),(2,1,3),(3,1,1),(4,2,2);
/*!40000 ALTER TABLE `users_contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_mails`
--

DROP TABLE IF EXISTS `users_mails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_mails` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idUser` int NOT NULL,
  `idMail` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_idx` (`idUser`),
  KEY `fk_mail_idx` (`idMail`),
  CONSTRAINT `fk_mail` FOREIGN KEY (`idMail`) REFERENCES `mails` (`idMail`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_userM` FOREIGN KEY (`idUser`) REFERENCES `users` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_mails`
--

LOCK TABLES `users_mails` WRITE;
/*!40000 ALTER TABLE `users_mails` DISABLE KEYS */;
INSERT INTO `users_mails` VALUES (1,1,1),(2,1,2),(3,1,3),(4,2,2),(5,2,3);
/*!40000 ALTER TABLE `users_mails` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-06 17:02:32
