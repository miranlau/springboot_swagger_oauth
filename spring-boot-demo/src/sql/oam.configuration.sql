-- MySQL dump 10.16  Distrib 10.1.14-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: smsgw_configuration
-- ------------------------------------------------------
-- Server version	10.1.14-MariaDB

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
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module` varchar(255) NOT NULL,
  `instance` int(11) DEFAULT NULL,
  `feature` varchar(255) NOT NULL,
  `json` longblob NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` VALUES (1,'map-sms-gw',NULL,'common','{\"userId\":\"USER01_ID\",\"numberOfInstances\":2,\"tcapInstanceIds\":[1,2],\"queue\":{\"size\":10000,\"maxThreads\":4,\"keepAlive\":10},\"statistics\":{\"active\":true,\"writeInterval\":60000},\"map\":{\"operationTimerS\":3,\"operationTimerM\":15,\"operationTimerML\":60},\"ss7\":{\"networkStandard\":\"ITU\",\"localAddressSignals\":{\"first\":\"3352149149\"},\"localSSN\":{\"hlr\":6,\"msc\":8,\"sgsn\":149},\"remoteSSN\":{\"hlr\":6,\"msc\":8,\"sgsn\":149}}}','2018-04-24 07:25:20');
INSERT INTO `configuration` VALUES (2,'map-sms-gw',NULL,'screening','{\"active\":false,\"discardBehavior\":\"reject\",\"trafficDataStorage\":{\"method\":\"memory\"},\"addressInformation\":{\"active\":false},\"messageThreshold\":{\"active\":false,\"rate\":1000000000,\"screeningAction\":{\"discard\":true,\"notify\":false,\"modify\":{\"method\":\"none\",\"text\":\"REPLACEMENT_PLACE_HOLDER\"}}},\"identicalMessageThreshold\":{\"active\":false,\"rate\":1000000000,\"screeningAction\":{\"discard\":true,\"notify\":false,\"modify\":{\"method\":\"none\",\"text\":\"REPLACEMENT_PLACE_HOLDER\"}}},\"spoofing\":{\"active\":false,\"screeningAction\":{\"discard\":true,\"notify\":false,\"modify\":{\"method\":\"none\",\"text\":\"REPLACEMENT_PLACE_HOLDER\"}}},\"content\":{\"active\":false,\"keywordSearch\":{\"active\":false,\"screeningAction\":{\"discard\":true,\"notify\":false,\"modify\":{\"method\":\"none\",\"text\":\"REPLACEMENT_PLACE_HOLDER\"}}},\"digitStringThreshold\":{\"active\":false,\"rate\":1000000000,\"digitStringRegex\":\"\\\\+?[0-9]{8,12}\",\"screeningAction\":{\"discard\":true,\"notify\":false,\"modify\":{\"method\":\"none\",\"text\":\"REPLACEMENT_PLACE_HOLDER\"}}}}}','2018-04-24 07:30:20');
INSERT INTO `configuration` VALUES (3,'map-sms-gw',NULL,'home','{\"active\":false,\"correlationIdPrefix\":\"00101\",\"smscValidation\":{\"active\":false,\"significantDigits\":5}}','2018-04-25 05:08:58');
INSERT INTO `configuration` (module, feature, json) VALUES ('tni', 'mgmt-collector', '{\"host\" : \"om\", \"username\" : \"tni-mgmt\", \"password\" : \"tesc\", \"refreshInterval\":1,\"deliveryRetryTimes\":5,\"initialRetryDelay\":1,\"retryDelayIncreasingStep\":1,\"throttling\" :{\"alarmThrottling\":true,\"notificationThrottling\":60,\"discardAllTrap\":false}}');
INSERT INTO `configuration` (module, feature, json) VALUES ('tni', 'rest-api', '{\"jwt\" : {\"secret\" : \"thisshouldbearandomsceuritykey\"}}');
INSERT INTO `configuration` (module, feature, json) VALUES ('eir', 'eir', '{\"version\" : 1,\"defaultColor\" : \"white\",\"equipmentDatabase\" : {\"type\" : \"mariadb\",\"name\" : \"eir\",\"host\" : \"localhost\",\"port\" : 3306,\"username\" : \"eir\",\"password\" : \"tstp\",\"connectRetryTimer\" : 1000,\"noMatchColor\" : \"white\"},\"dda\" : {\"bindRetryTimer\" : 1000,\"bindTimeout\" : 1000,\"connectRetryTimer\" : 1000},\"transactionLog\" : {\"active\" : false,\"type\" : \"syslog/udp\",\"level\" : 1},\"statistics\" : {\"active\" : true,\"interval\" : 5000},\"imsiFilter\" : {\"active\" : true,\"prefix\" : [ \"123456\" , \"111222\" ],\"noMatchColor\" : \"grey\"},\"extendedGrey\" : {\"active\" : true,\"defaultColor\" : \"black\"},\"associatedAnotherImsi\" : {\"active\" : true,\"matchColor\" : \"grey\"},\"matchingRules\" : {\"order\" : [ \"priorityWhite\", \"priorityBlack\", \"imeiAndImsi\", \"imeiAndAnyImsi\", \"anyImeiAndImsi\", \"anyImeiAndAnyImsi\" ]}}');
INSERT INTO `configuration` (module, feature, json) VALUES ('statistics','report','{\"autoReportEnabled\":1,\"autoReportPeriod\":1,\"archiveEntryExpiration\":12,\"csvFileExpiration\":12,\"archiveEntrySize\":100000,\"csvFileSize\":1024,\"csvFileCount\":365}');
INSERT INTO `configuration` (module, feature, json) VALUES ('mrvt','common','{\"version\":\"MOD1001279V1\",\"localSsn\":4,\"traceOn\":0,\"timerT1\":8,\"maxNumMrvtTests\":50}');
INSERT INTO `configuration` (module, feature, json) VALUES ('sniff','common','{\"version\":\"MOD1001266V1\",\"sniffSsn\":17,\"traceLev\":6,\"suaInst\":1,\"disconnReason\":2, \"screeningVersion\": 1}');
INSERT INTO `configuration` (module, feature, json) VALUES ('system', 'log', '{\"storageTime\":1,\"numberOfFiles\":100,\"fileSize\":1024}');
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-26 10:31:13
