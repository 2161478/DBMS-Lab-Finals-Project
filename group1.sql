CREATE DATABASE  IF NOT EXISTS `group1` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `group1`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: group1
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `deptid` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(45) NOT NULL,
  PRIMARY KEY (`deptid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'myDepartment');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `empid` int(11) NOT NULL,
  `ename` varchar(45) NOT NULL,
  `job` varchar(45) NOT NULL,
  `hiredate` date NOT NULL,
  `bday` date NOT NULL,
  `mgrid` int(11) DEFAULT NULL,
  `deptid` int(11) DEFAULT NULL,
  PRIMARY KEY (`empid`),
  KEY `mgrid_idx` (`mgrid`),
  KEY `deptid_idx` (`deptid`),
  CONSTRAINT `deptid` FOREIGN KEY (`deptid`) REFERENCES `department` (`deptid`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `mgrid` FOREIGN KEY (`mgrid`) REFERENCES `employee` (`empid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Gaspar','Programmer','2017-12-02','1998-08-25',NULL,NULL);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empproj`
--

DROP TABLE IF EXISTS `empproj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empproj` (
  `empid` int(11) NOT NULL,
  `projid` int(11) NOT NULL,
  `startdate` date NOT NULL,
  `hourlyrate` double DEFAULT NULL,
  PRIMARY KEY (`empid`,`projid`),
  KEY `projid_idx` (`projid`),
  CONSTRAINT `empid` FOREIGN KEY (`empid`) REFERENCES `employee` (`empid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `projid` FOREIGN KEY (`projid`) REFERENCES `project` (`projid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empproj`
--

LOCK TABLES `empproj` WRITE;
/*!40000 ALTER TABLE `empproj` DISABLE KEYS */;
INSERT INTO `empproj` VALUES (1,1,'2017-12-02',150);
/*!40000 ALTER TABLE `empproj` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `projid` int(11) NOT NULL,
  `projname` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `dept` int(11) DEFAULT NULL,
  `projhead` int(11) DEFAULT NULL,
  PRIMARY KEY (`projid`),
  KEY `projhead_idx` (`projhead`),
  KEY `dept_idx` (`dept`),
  CONSTRAINT `dept` FOREIGN KEY (`dept`) REFERENCES `department` (`deptid`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `projhead` FOREIGN KEY (`projhead`) REFERENCES `employee` (`empid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'project','pending',1,1);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-09 18:32:08
