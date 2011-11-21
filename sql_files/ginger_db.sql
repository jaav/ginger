-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (i486)
--
-- Host: localhost    Database: ginger_db_1
-- ------------------------------------------------------
-- Server version	5.1.41-3ubuntu12.10

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Activity`
--

DROP TABLE IF EXISTS `Activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Activity_date` datetime DEFAULT NULL,
  `beschrijving` longtext,
  `Duur` int(11) DEFAULT NULL,
  `Evaluvated` bit(1) DEFAULT NULL,
  `InternalActivity` bit(1) DEFAULT NULL,
  `IsActive` int(11) NOT NULL,
  `Reported` bit(1) DEFAULT NULL,
  `TotalParticipants` int(11) DEFAULT NULL,
  `centrumId_id` bigint(20) DEFAULT NULL,
  `locationId_id` bigint(20) DEFAULT NULL,
  `organizationId_id` bigint(20) DEFAULT NULL,
  `userId_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA126572F21F92CE4` (`locationId_id`),
  KEY `FKA126572FFCC5691C` (`userId_id`),
  KEY `FKA126572F76A64812` (`centrumId_id`),
  KEY `FKA126572F9A54B639` (`organizationId_id`)
) ENGINE=MyISAM AUTO_INCREMENT=16888 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ActivityEvaluvators`
--

DROP TABLE IF EXISTS `ActivityEvaluvators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ActivityEvaluvators` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activityId_id` bigint(20) DEFAULT NULL,
  `evalTypeId_id` bigint(20) DEFAULT NULL,
  `evaluvatorsId_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKCC7835119CCF4337` (`evalTypeId_id`),
  KEY `FKCC783511AB03DD1B` (`evaluvatorsId_id`),
  KEY `FKCC783511FB8A1F43` (`activityId_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4922 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ActivitySectors`
--

DROP TABLE IF EXISTS `ActivitySectors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ActivitySectors` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activityId_id` bigint(20) DEFAULT NULL,
  `sectorId_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5959339EE73D8142` (`sectorId_id`),
  KEY `FK5959339EFB8A1F43` (`activityId_id`)
) ENGINE=MyISAM AUTO_INCREMENT=20816 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ActivityTargets`
--

DROP TABLE IF EXISTS `ActivityTargets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ActivityTargets` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activityId_id` bigint(20) DEFAULT NULL,
  `attendantTypeId_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK88395ED320AFD69B` (`attendantTypeId_id`),
  KEY `FK88395ED3FB8A1F43` (`activityId_id`)
) ENGINE=MyISAM AUTO_INCREMENT=16221 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ActivityType`
--

DROP TABLE IF EXISTS `ActivityType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ActivityType` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Beschrijving` longtext,
  `IsActive` int(11) NOT NULL,
  `Naam` varchar(100) DEFAULT NULL,
  `ouder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6AE12B09DB97597C` (`ouder_id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ActivityTypeJunction`
--

DROP TABLE IF EXISTS `ActivityTypeJunction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ActivityTypeJunction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activityId_id` bigint(20) DEFAULT NULL,
  `activityTypeId_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA49C9D7D409025A3` (`activityTypeId_id`),
  KEY `FKA49C9D7DFB8A1F43` (`activityId_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12906 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `AttendantType`
--

DROP TABLE IF EXISTS `AttendantType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AttendantType` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Beschrijving` longtext,
  `Naam` varchar(100) DEFAULT NULL,
  `targetTypeId_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKFB37A1C7F00C3FE3` (`targetTypeId_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Centrum`
--

DROP TABLE IF EXISTS `Centrum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Centrum` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Beschrijving` longtext,
  `IsActive` int(11) NOT NULL,
  `Naam` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Evaluvation_Type`
--

DROP TABLE IF EXISTS `Evaluvation_Type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Evaluvation_Type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `EvalType` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Evaluvators`
--

DROP TABLE IF EXISTS `Evaluvators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Evaluvators` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `IsActive` int(11) NOT NULL,
  `Naam` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Items`
--

DROP TABLE IF EXISTS `Items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Beschrijving` longtext,
  `IsActive` int(11) NOT NULL,
  `Naam` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ItemsInActivity`
--

DROP TABLE IF EXISTS `ItemsInActivity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ItemsInActivity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activityId_id` bigint(20) DEFAULT NULL,
  `itemId_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3AA13B142B885AA8` (`itemId_id`),
  KEY `FK3AA13B14FB8A1F43` (`activityId_id`)
) ENGINE=MyISAM AUTO_INCREMENT=24412 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Locations`
--

DROP TABLE IF EXISTS `Locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Locations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Beschrijving` longtext,
  `IsActive` int(11) NOT NULL,
  `IsCluster` int(11) NOT NULL,
  `Naam` varchar(100) DEFAULT NULL,
  `centrumId_id` bigint(20) DEFAULT NULL,
  `ouder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3016773E74CE6A29` (`ouder_id`),
  KEY `FK3016773E76A64812` (`centrumId_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3736 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Materials`
--

DROP TABLE IF EXISTS `Materials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Materials` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Beschrijving` longtext,
  `IsActive` int(11) NOT NULL,
  `Naam` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MaterialsInActivity`
--

DROP TABLE IF EXISTS `MaterialsInActivity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MaterialsInActivity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activityId_id` bigint(20) DEFAULT NULL,
  `materialId_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB8B0C80B885E780` (`materialId_id`),
  KEY `FKB8B0C80FB8A1F43` (`activityId_id`)
) ENGINE=MyISAM AUTO_INCREMENT=24087 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Organisaties`
--

DROP TABLE IF EXISTS `Organisaties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Organisaties` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Adres` varchar(50) DEFAULT NULL,
  `Gemeente` varchar(50) DEFAULT NULL,
  `IsActive` int(11) NOT NULL,
  `Land` varchar(20) DEFAULT NULL,
  `Naam` varchar(50) DEFAULT NULL,
  `OrganisatieNetwerk` varchar(3) DEFAULT NULL,
  `Postcode` varchar(10) DEFAULT NULL,
  `centrumId_id` bigint(20) DEFAULT NULL,
  `ouder_id` bigint(20) DEFAULT NULL,
  `userId_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK441E53C9B4D4823C` (`ouder_id`),
  KEY `FK441E53C9FCC5691C` (`userId_id`),
  KEY `FK441E53C976A64812` (`centrumId_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3763 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SectorActivityJunction`
--

DROP TABLE IF EXISTS `SectorActivityJunction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SectorActivityJunction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activityId_id` bigint(20) DEFAULT NULL,
  `sectorId_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC82BB29E73D8142` (`sectorId_id`),
  KEY `FKC82BB29FB8A1F43` (`activityId_id`)
) ENGINE=MyISAM AUTO_INCREMENT=20957 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Sectors`
--

DROP TABLE IF EXISTS `Sectors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sectors` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `beschrijving` longtext,
  `IsActive` int(11) NOT NULL,
  `Naam` varchar(200) DEFAULT NULL,
  `ouder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD8A82DAD6AFA4F98` (`ouder_id`)
) ENGINE=MyISAM AUTO_INCREMENT=98 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TargetType`
--

DROP TABLE IF EXISTS `TargetType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TargetType` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Beschrijving` longtext,
  `IsActive` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `emailAddress` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `loginCount` int(11) NOT NULL,
  `passwordHash` varchar(255) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `userID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `VadGingerUser`
--

DROP TABLE IF EXISTS `VadGingerUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VadGingerUser` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `emailAddress` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `IsActive` int(11) NOT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `loginCount` int(11) NOT NULL,
  `passwordHash` varchar(255) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `userID` varchar(255) DEFAULT NULL,
  `centrumId_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6244FFCC76A64812` (`centrumId_id`)
) ENGINE=MyISAM AUTO_INCREMENT=152 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-11-18  5:14:26
