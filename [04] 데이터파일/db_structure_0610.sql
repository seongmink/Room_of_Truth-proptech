-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: k02b2031.p.ssafy.io    Database: rot
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `address_id` bigint NOT NULL AUTO_INCREMENT,
  `road_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  UNIQUE KEY `address_UNIQUE` (`road_address`)
) ENGINE=InnoDB AUTO_INCREMENT=168014 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `agent`
--

DROP TABLE IF EXISTS `agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agent` (
  `agent_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `count` int NOT NULL,
  `license` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `point` int NOT NULL,
  `representative` varchar(255) DEFAULT NULL,
  `user_num` bigint DEFAULT NULL,
  `rnk` int NOT NULL,
  PRIMARY KEY (`agent_id`),
  KEY `FKirp4a2lkvje9j93g2d9g2ccku` (`user_num`),
  CONSTRAINT `FKirp4a2lkvje9j93g2d9g2ccku` FOREIGN KEY (`user_num`) REFERENCES `user` (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `around`
--

DROP TABLE IF EXISTS `around`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `around` (
  `around_id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(100) DEFAULT NULL,
  `trans` int DEFAULT NULL,
  `comforts` int DEFAULT NULL,
  `education` int DEFAULT NULL,
  `medical` int DEFAULT NULL,
  `eatery` int DEFAULT NULL,
  `culture` int DEFAULT NULL,
  PRIMARY KEY (`around_id`),
  UNIQUE KEY `address_UNIQUE` (`address`)
) ENGINE=InnoDB AUTO_INCREMENT=94352 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contract` (
  `contract_id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `longitude` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `latitude` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `exclusive` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `floor` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ho` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `kind` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `detail` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `cost` bigint DEFAULT NULL,
  `monthly` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `license` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `image` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `contract_date` date DEFAULT NULL,
  PRIMARY KEY (`contract_id`)
) ENGINE=InnoDB AUTO_INCREMENT=162835 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `favorite`
--

DROP TABLE IF EXISTS `favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite` (
  `favorite_id` int NOT NULL AUTO_INCREMENT,
  `around_around_id` bigint NOT NULL,
  `user_num` bigint NOT NULL,
  `score` int NOT NULL,
  PRIMARY KEY (`favorite_id`),
  KEY `around_idx` (`around_around_id`),
  KEY `user_idx` (`user_num`),
  CONSTRAINT `around_around_id` FOREIGN KEY (`around_around_id`) REFERENCES `around` (`around_id`) ON DELETE CASCADE,
  CONSTRAINT `user_num` FOREIGN KEY (`user_num`) REFERENCES `user` (`num`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=319085 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `find`
--

DROP TABLE IF EXISTS `find`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `find` (
  `address` varchar(255) NOT NULL,
  `floor` varchar(255) DEFAULT NULL,
  `ho` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `interest`
--

DROP TABLE IF EXISTS `interest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interest` (
  `interest_id` bigint NOT NULL AUTO_INCREMENT,
  `birth` int DEFAULT NULL,
  `first` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `sd` varchar(255) DEFAULT NULL,
  `second` varchar(255) DEFAULT NULL,
  `sgg` varchar(255) DEFAULT NULL,
  `third` varchar(255) DEFAULT NULL,
  `user_num` bigint DEFAULT NULL,
  PRIMARY KEY (`interest_id`),
  KEY `FKf8wn298xgcdc24qo7odexlbdi` (`user_num`),
  CONSTRAINT `FKf8wn298xgcdc24qo7odexlbdi` FOREIGN KEY (`user_num`) REFERENCES `user` (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=4010 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ranking`
--

DROP TABLE IF EXISTS `ranking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ranking` (
  `ranking_id` bigint NOT NULL AUTO_INCREMENT,
  `agent_name` varchar(255) DEFAULT NULL,
  `agent_picture` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `point` int NOT NULL,
  `rnk` int NOT NULL,
  `user_num` bigint NOT NULL,
  `user_picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ranking_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `search`
--

DROP TABLE IF EXISTS `search`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `search` (
  `search_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `user_num` bigint DEFAULT NULL,
  PRIMARY KEY (`search_id`),
  KEY `FKebsofd9polqkj9k3rp9quxtks` (`user_num`),
  CONSTRAINT `FKebsofd9polqkj9k3rp9quxtks` FOREIGN KEY (`user_num`) REFERENCES `user` (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status` (
  `status_id` bigint NOT NULL,
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `sd` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `sgg` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `emd` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `longitude` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `latitude` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `exclusive` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `floor` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ho` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `category` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `detail` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `cost` bigint DEFAULT NULL,
  `license` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `image` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `num` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `auth` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-10 14:48:12
