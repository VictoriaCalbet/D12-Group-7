start transaction;


create database `Acme-Battle`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete 
	on `Sample`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter, 
       create temporary tables, lock tables, create view, create routine, 
     alter routine, execute, trigger, show view
    on `Acme-Battle`.* to 'acme-manager'@'%';


-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: acme-battle
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `confirmMoment` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `hasConfirmedTerms` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cgls5lrufx91ufsyh467spwa3` (`userAccount_id`),
  UNIQUE KEY `UK_6cwpy57cri0qhnc28qj0qybsn` (`nickname`),
  CONSTRAINT `FK_cgls5lrufx91ufsyh467spwa3` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `confirmMoment` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `hasConfirmedTerms` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  UNIQUE KEY `UK_7pcwodeblkp8s3tyeniqcx333` (`nickname`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (1142,0,'https://i.pinimg.com/236x/e9/21/9f/e9219f8ef2a76ed643bc2ade7c64c929--dearly-beloved-tv-anime.jpg','2017-08-02 00:00:00','admin@gmail.com','','Administrador','admin','549592823','Sistema',1133);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banned`
--

DROP TABLE IF EXISTS `banned`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `banned` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banDate` datetime DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `isValid` bit(1) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `actor_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_cwrt71g0g5637qojimrx3j036` (`isValid`),
  KEY `UK_iev03is47q8hmtailognobf6o` (`actor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banned`
--

LOCK TABLES `banned` WRITE;
/*!40000 ALTER TABLE `banned` DISABLE KEYS */;
INSERT INTO `banned` VALUES (1208,0,'2018-01-04 12:05:00',99999999,'','Cheats',1183);
/*!40000 ALTER TABLE `banned` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `battle`
--

DROP TABLE IF EXISTS `battle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `battle` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attackerOwner` bit(1) DEFAULT NULL,
  `balance` varchar(255) DEFAULT NULL,
  `isWon` bit(1) DEFAULT NULL,
  `luckAttacker` double DEFAULT NULL,
  `luckDeffender` double DEFAULT NULL,
  `gummiCoal` int(11) DEFAULT NULL,
  `munny` int(11) DEFAULT NULL,
  `mytrhil` int(11) DEFAULT NULL,
  `attacker_id` int(11) NOT NULL,
  `deffender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_es2fkuwxqq8msuwpdhq96e3ds` (`attacker_id`),
  KEY `FK_hmquk2ueg3x8tmhmv1wsr4n7m` (`deffender_id`),
  CONSTRAINT `FK_es2fkuwxqq8msuwpdhq96e3ds` FOREIGN KEY (`attacker_id`) REFERENCES `keybladewielder` (`id`),
  CONSTRAINT `FK_hmquk2ueg3x8tmhmv1wsr4n7m` FOREIGN KEY (`deffender_id`) REFERENCES `keybladewielder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `battle`
--

LOCK TABLES `battle` WRITE;
/*!40000 ALTER TABLE `battle` DISABLE KEYS */;
INSERT INTO `battle` VALUES (1184,0,'','Has ganado','',0.1,0,10,30,20,1179,1180),(1185,0,'\0','Has perdido','\0',0,0.2,15,40,30,1179,1180),(1186,0,'','Has ganado','',0.3,0,10,30,20,1180,1181),(1187,0,'\0','Has perdido','\0',0,0.2,15,40,30,1180,1181),(1188,0,'','Has ganado','',0.5,0,10,30,20,1183,1179),(1189,0,'\0','Has perdido','\0',0,0.1,15,40,30,1183,1179);
/*!40000 ALTER TABLE `battle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `building` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `gummiCoal` int(11) DEFAULT NULL,
  `munny` int(11) DEFAULT NULL,
  `mytrhil` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `extraCostPerLvl` double DEFAULT NULL,
  `isFinal` bit(1) DEFAULT NULL,
  `maxLvl` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `timeToConstruct` int(11) DEFAULT NULL,
  `contentManager_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_kafanifq4tl8429iw316qaiqh` (`isFinal`),
  KEY `FK_6rnrpwu52hmaj92tqtnmhbnvp` (`contentManager_id`),
  CONSTRAINT `FK_6rnrpwu52hmaj92tqtnmhbnvp` FOREIGN KEY (`contentManager_id`) REFERENCES `contentmanager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building`
--

LOCK TABLES `building` WRITE;
/*!40000 ALTER TABLE `building` DISABLE KEYS */;
/*!40000 ALTER TABLE `building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `built`
--

DROP TABLE IF EXISTS `built`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `built` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `activationDate` datetime DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `lvl` int(11) DEFAULT NULL,
  `building_id` int(11) NOT NULL,
  `gummiShip_id` int(11) DEFAULT NULL,
  `keybladeWielder_id` int(11) NOT NULL,
  `troop_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_bf8c7q592fpjrnukybtm3p4no` (`lvl`),
  KEY `FK_97junl37o3m1t1nhf6g5jaky3` (`gummiShip_id`),
  KEY `FK_ra1behx93p0phfoa56jkusmqn` (`keybladeWielder_id`),
  KEY `FK_biwn31vxymhnfsx82s2d4t8pl` (`troop_id`),
  CONSTRAINT `FK_97junl37o3m1t1nhf6g5jaky3` FOREIGN KEY (`gummiShip_id`) REFERENCES `gummiship` (`id`),
  CONSTRAINT `FK_biwn31vxymhnfsx82s2d4t8pl` FOREIGN KEY (`troop_id`) REFERENCES `troop` (`id`),
  CONSTRAINT `FK_ra1behx93p0phfoa56jkusmqn` FOREIGN KEY (`keybladeWielder_id`) REFERENCES `keybladewielder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `built`
--

LOCK TABLES `built` WRITE;
/*!40000 ALTER TABLE `built` DISABLE KEYS */;
INSERT INTO `built` VALUES (1190,0,NULL,'2018-05-04 00:00:00',10,1148,NULL,1179,NULL),(1191,0,NULL,'2018-05-04 00:00:00',1,1150,NULL,1179,NULL),(1192,0,NULL,'2018-05-04 00:00:00',1,1151,NULL,1179,NULL),(1193,0,'2018-05-04 12:08:00','2018-05-04 00:00:00',1,1153,NULL,1179,1155),(1194,0,'2018-05-04 12:08:00','2018-05-04 00:00:00',1,1152,NULL,1179,1155),(1195,0,'2018-05-04 12:08:00','2018-05-04 00:00:00',1,1162,NULL,1179,NULL),(1196,0,NULL,'2018-05-04 00:00:00',1,1156,NULL,1180,NULL),(1197,0,NULL,'2018-05-04 00:00:00',1,1157,NULL,1180,NULL),(1198,0,NULL,'2018-05-04 00:00:00',1,1158,NULL,1180,NULL),(1199,0,'2018-05-04 12:08:00','2018-05-04 00:00:00',1,1159,NULL,1180,1161);
/*!40000 ALTER TABLE `built` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatty`
--

DROP TABLE IF EXISTS `chatty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chatty` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `invitation_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_1vxysb3m4saa2icosl029m11y` (`date`),
  KEY `FK_dran7x58nypw2v8hm2g37a9r5` (`invitation_id`),
  CONSTRAINT `FK_dran7x58nypw2v8hm2g37a9r5` FOREIGN KEY (`invitation_id`) REFERENCES `invitation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatty`
--

LOCK TABLES `chatty` WRITE;
/*!40000 ALTER TABLE `chatty` DISABLE KEYS */;
/*!40000 ALTER TABLE `chatty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `baseGummiCoal` int(11) DEFAULT NULL,
  `baseMunny` int(11) DEFAULT NULL,
  `baseMytrhil` int(11) DEFAULT NULL,
  `dailyGummiCoal` int(11) DEFAULT NULL,
  `dailyMunny` int(11) DEFAULT NULL,
  `dailyMytrhil` int(11) DEFAULT NULL,
  `lostLvlsDeffender` int(11) DEFAULT NULL,
  `orgMessages` int(11) DEFAULT NULL,
  `percentageWinAttacker` double DEFAULT NULL,
  `percentageWinDefender` double DEFAULT NULL,
  `worldSlots` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` VALUES (1164,0,300,300,300,10,30,20,1,50,0.6,0.5,7);
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contentmanager`
--

DROP TABLE IF EXISTS `contentmanager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contentmanager` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `confirmMoment` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `hasConfirmedTerms` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jkls9gyrwigpi8unmm6p6gv51` (`userAccount_id`),
  UNIQUE KEY `UK_id5ivndau3qraqav3gv1glxpm` (`nickname`),
  CONSTRAINT `FK_jkls9gyrwigpi8unmm6p6gv51` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contentmanager`
--

LOCK TABLES `contentmanager` WRITE;
/*!40000 ALTER TABLE `contentmanager` DISABLE KEYS */;
INSERT INTO `contentmanager` VALUES (1143,0,NULL,'2017-08-02 00:00:00','manager1@gmail.com','','Content','manager','549592823','Manager',1135),(1144,0,NULL,'2017-08-02 00:00:00','manager2@gmail.com','','Content','manager2','549592823','Manager 2',1141);
/*!40000 ALTER TABLE `contentmanager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `defense`
--

DROP TABLE IF EXISTS `defense`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `defense` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `gummiCoal` int(11) DEFAULT NULL,
  `munny` int(11) DEFAULT NULL,
  `mytrhil` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `extraCostPerLvl` double DEFAULT NULL,
  `isFinal` bit(1) DEFAULT NULL,
  `maxLvl` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `timeToConstruct` int(11) DEFAULT NULL,
  `contentManager_id` int(11) NOT NULL,
  `defense` int(11) DEFAULT NULL,
  `extraDefensePerLvl` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `DefenseUK_kafanifq4tl8429iw316qaiqh` (`isFinal`),
  KEY `FK_7ucegt7vmim0tht04r9ogkt2y` (`contentManager_id`),
  CONSTRAINT `FK_7ucegt7vmim0tht04r9ogkt2y` FOREIGN KEY (`contentManager_id`) REFERENCES `contentmanager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `defense`
--

LOCK TABLES `defense` WRITE;
/*!40000 ALTER TABLE `defense` DISABLE KEYS */;
INSERT INTO `defense` VALUES (1148,0,15,40,30,'Defensive towers',0.3,'',10,'Defense Towers','./images/buildings/defense.png',5,1143,100,0.25),(1149,0,15,40,30,'Defensive towers',0.3,'\0',10,'Defense 3','./images/buildings/defense.png',5,1143,100,0.25),(1156,0,15,40,30,'Defensive towers 2',0.3,'',10,'Defense Towers 2','./images/buildings/defense.png',5,1143,100,0.25);
/*!40000 ALTER TABLE `defense` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faction`
--

DROP TABLE IF EXISTS `faction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `faction` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `extraAttack` double DEFAULT NULL,
  `extraDefense` double DEFAULT NULL,
  `extraResources` double DEFAULT NULL,
  `galaxy` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `powerUpDescription` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faction`
--

LOCK TABLES `faction` WRITE;
/*!40000 ALTER TABLE `faction` DISABLE KEYS */;
INSERT INTO `faction` VALUES (1146,0,0,0,0.1,0,'Light','You will collect 10% more of materials'),(1147,0,0.05,0,0,0,'Darkness','Your troops will have 5% more of attack');
/*!40000 ALTER TABLE `faction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gamemaster`
--

DROP TABLE IF EXISTS `gamemaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gamemaster` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `confirmMoment` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `hasConfirmedTerms` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lw3n1at3slipc8cee8r9awhg` (`userAccount_id`),
  UNIQUE KEY `UK_36gfwf718ymlamy7e1io39de3` (`nickname`),
  CONSTRAINT `FK_lw3n1at3slipc8cee8r9awhg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gamemaster`
--

LOCK TABLES `gamemaster` WRITE;
/*!40000 ALTER TABLE `gamemaster` DISABLE KEYS */;
INSERT INTO `gamemaster` VALUES (1145,0,NULL,'2017-08-02 00:00:00','gm1@gmail.com','','Game','master','549592823','Master',1134);
/*!40000 ALTER TABLE `gamemaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gummiship`
--

DROP TABLE IF EXISTS `gummiship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gummiship` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `gummiCoal` int(11) DEFAULT NULL,
  `munny` int(11) DEFAULT NULL,
  `mytrhil` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `recruiterRequiredLvl` int(11) DEFAULT NULL,
  `slots` int(11) DEFAULT NULL,
  `timeToRecruit` int(11) DEFAULT NULL,
  `recruiter_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dcyrv2d4x2cc5owo9il8901c6` (`name`),
  KEY `UK_m0pk55jkxql6mtbqm8umak4om` (`recruiterRequiredLvl`),
  KEY `FK_bpe54i7i0udsn49gfpgqkh8qs` (`recruiter_id`),
  CONSTRAINT `FK_bpe54i7i0udsn49gfpgqkh8qs` FOREIGN KEY (`recruiter_id`) REFERENCES `recruiter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gummiship`
--

LOCK TABLES `gummiship` WRITE;
/*!40000 ALTER TABLE `gummiship` DISABLE KEYS */;
INSERT INTO `gummiship` VALUES (1154,0,70,150,80,'Interstellar',1,150,50,1153),(1160,0,300,300,300,'Platon',1,220,50,1159);
/*!40000 ALTER TABLE `gummiship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invitation`
--

DROP TABLE IF EXISTS `invitation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invitation` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `invitationStatus` varchar(255) DEFAULT NULL,
  `orgRange` varchar(255) DEFAULT NULL,
  `keybladeWielder_id` int(11) NOT NULL,
  `organization_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_4ui8dt5qj8ffrh428ojgjt49w` (`date`,`invitationStatus`,`orgRange`),
  KEY `FK_mnp1cm2xsssclt44jm5j8o7wt` (`organization_id`),
  CONSTRAINT `FK_mnp1cm2xsssclt44jm5j8o7wt` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invitation`
--

LOCK TABLES `invitation` WRITE;
/*!40000 ALTER TABLE `invitation` DISABLE KEYS */;
INSERT INTO `invitation` VALUES (1217,0,'¿Quieres unirte a mi organización?','2015-01-02','ACCEPTED','MASTER',1179,1174),(1218,0,'¿Quieres unirte a mi organización?','2016-08-02','ACCEPTED','OFFICER',1181,1174),(1219,0,'¿Quieres unirte a mi organización?','2016-08-02','ACCEPTED','GUEST',1182,1174);
/*!40000 ALTER TABLE `invitation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `expiration` int(11) DEFAULT NULL,
  `extra` double DEFAULT NULL,
  `munnyCost` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `onSell` bit(1) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `contentManager_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_a5hxmfx1a4325hg0uds5ulqdp` (`name`),
  KEY `FK_rgx5h3wyhrg3fy5k4iepacrh6` (`contentManager_id`),
  CONSTRAINT `FK_rgx5h3wyhrg3fy5k4iepacrh6` FOREIGN KEY (`contentManager_id`) REFERENCES `contentmanager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1165,0,'With this potion You will increase your atack 20%',500,15,0.2,60,'Atack Potion','','ATTACKBOOST',1143),(1166,0,'With this potion You will increase your defense 20%',500,15,0.2,60,'Defense Potion','','DEFENSEBOOST',1143),(1167,0,'With this potion You will increase your atack 40%',500,15,0.4,200,'Atack Potion XL','','ATTACKBOOST',1143),(1168,0,'With this potion You will increase your defense 40%',500,15,0.4,250,'Defense Potion XL','','DEFENSEBOOST',1143),(1169,0,'With this shield You will not be atacked',500,15,0.4,250,'Protective Shield','','SHIELD',1143),(1170,0,'With this boost You will have resources 30% faster',800,20,0.3,210,'Resource Boost','','RESOURCEBOOST',1143);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `keybladewielder`
--

DROP TABLE IF EXISTS `keybladewielder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keybladewielder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `confirmMoment` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `hasConfirmedTerms` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  `lastConnection` datetime DEFAULT NULL,
  `loses` int(11) DEFAULT NULL,
  `gummiCoal` int(11) DEFAULT NULL,
  `munny` int(11) DEFAULT NULL,
  `mytrhil` int(11) DEFAULT NULL,
  `wins` int(11) DEFAULT NULL,
  `x` int(11) DEFAULT NULL,
  `y` int(11) DEFAULT NULL,
  `z` int(11) DEFAULT NULL,
  `worldName` varchar(255) DEFAULT NULL,
  `faction_id` int(11) NOT NULL,
  `shield_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2p7covt8b041f18p9rky9n2a9` (`userAccount_id`),
  UNIQUE KEY `UK_mn080mjc05f31iq71yhijr4eb` (`worldName`),
  UNIQUE KEY `UK_49p0qgqd62xyulwm4lh2j9pwm` (`nickname`),
  KEY `FK_fxww30xulx45d0xlek9qbcqvd` (`faction_id`),
  KEY `FK_lq6c56d23q5dmybsgvgsmvau8` (`shield_id`),
  CONSTRAINT `FK_2p7covt8b041f18p9rky9n2a9` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`),
  CONSTRAINT `FK_fxww30xulx45d0xlek9qbcqvd` FOREIGN KEY (`faction_id`) REFERENCES `faction` (`id`),
  CONSTRAINT `FK_lq6c56d23q5dmybsgvgsmvau8` FOREIGN KEY (`shield_id`) REFERENCES `shield` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keybladewielder`
--

LOCK TABLES `keybladewielder` WRITE;
/*!40000 ALTER TABLE `keybladewielder` DISABLE KEYS */;
INSERT INTO `keybladewielder` VALUES (1179,0,NULL,'2017-08-02 00:00:00','keybladewielder@gmail.com','','Keyblade','Sora','549592823','Wielder',1136,'2017-02-07 08:32:00',7,300,300,300,13,1,1,1,'Mundo de Sora',1146,NULL),(1180,0,NULL,'2017-08-02 00:00:00','starzorro@gmail.com','','Star','StarFox','84959295233','Fox',1137,'2017-05-04 11:05:00',20,300,300,300,79,2,2,2,'Fox World',1147,NULL),(1181,0,NULL,'2017-08-02 00:00:00','julitomambo@gmail.com','','Julio','Julito','660450342','Mambo',1138,'2017-07-06 11:05:00',9,0,0,0,10,3,4,3,'Tron world',1146,NULL),(1182,0,NULL,'2017-08-02 00:00:00','peter@hotmail.com','','Peter','Peter','62458723','Garcia',1139,'2017-07-06 11:05:00',12,300,300,300,30,4,5,3,'Winnieh world',1146,NULL),(1183,0,NULL,'2017-08-02 00:00:00','luke@hotmail.com','','Luke','Luke','694852941','Smith',1140,'2017-07-06 11:05:00',26,300,300,300,40,5,1,4,'Riku world',1147,NULL);
/*!40000 ALTER TABLE `keybladewielder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `legaltext`
--

DROP TABLE IF EXISTS `legaltext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `legaltext` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` longtext,
  `codeLanguage` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `legaltext`
--

LOCK TABLES `legaltext` WRITE;
/*!40000 ALTER TABLE `legaltext` DISABLE KEYS */;
INSERT INTO `legaltext` VALUES (1175,0,'Este juego no puede contener palabras malsonantes','es','TERMS'),(1176,0,'This game can not have bad words','en','TERMS'),(1177,0,'Esta web usa cookies para su mejor funcionamiento','es','COOKIES'),(1178,0,'This web uses cookies in order to improve its funcionallity','en','COOKIES');
/*!40000 ALTER TABLE `legaltext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `livelihood`
--

DROP TABLE IF EXISTS `livelihood`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `livelihood` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `gummiCoal` int(11) DEFAULT NULL,
  `munny` int(11) DEFAULT NULL,
  `mytrhil` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `extraCostPerLvl` double DEFAULT NULL,
  `isFinal` bit(1) DEFAULT NULL,
  `maxLvl` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `timeToConstruct` int(11) DEFAULT NULL,
  `contentManager_id` int(11) NOT NULL,
  `extraMaterialsPerLvl` double DEFAULT NULL,
  `lessTimePerLvl` double DEFAULT NULL,
  `collectedGummiCoal` int(11) DEFAULT NULL,
  `collectedMunny` int(11) DEFAULT NULL,
  `collectedMytrhil` int(11) DEFAULT NULL,
  `timeToRecollect` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `LivelihoodUK_kafanifq4tl8429iw316qaiqh` (`isFinal`),
  KEY `FK_ackl04e3jgv0uk6n9encdd8vc` (`contentManager_id`),
  CONSTRAINT `FK_ackl04e3jgv0uk6n9encdd8vc` FOREIGN KEY (`contentManager_id`) REFERENCES `contentmanager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livelihood`
--

LOCK TABLES `livelihood` WRITE;
/*!40000 ALTER TABLE `livelihood` DISABLE KEYS */;
INSERT INTO `livelihood` VALUES (1150,0,15,40,30,'This is the Livehood number 1',0.4,'',10,'Livelihood Number 1','./images/buildings/livelihood.png',6,1143,0.3,0.5,10,30,20,40),(1157,0,15,40,30,'This is the Livehood number 2',0.4,'',10,'Livelihood Number 2','./images/buildings/livelihood.png',6,1143,0.3,0.5,10,30,20,40);
/*!40000 ALTER TABLE `livelihood` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization`
--

DROP TABLE IF EXISTS `organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organization` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `creationDate` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization`
--

LOCK TABLES `organization` WRITE;
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
INSERT INTO `organization` VALUES (1174,0,'2015-01-02 07:00:00','Asociacion de iluminados','Iluminati');
/*!40000 ALTER TABLE `organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prize`
--

DROP TABLE IF EXISTS `prize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prize` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `gummiCoal` int(11) DEFAULT NULL,
  `munny` int(11) DEFAULT NULL,
  `mytrhil` int(11) DEFAULT NULL,
  `keybladeWielder_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_n282oaqb6gj3684c2qlsqj8on` (`date`),
  KEY `FK_2u4vbgeqtq8yo5gf5xowvpi1v` (`keybladeWielder_id`),
  CONSTRAINT `FK_2u4vbgeqtq8yo5gf5xowvpi1v` FOREIGN KEY (`keybladeWielder_id`) REFERENCES `keybladewielder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prize`
--

LOCK TABLES `prize` WRITE;
/*!40000 ALTER TABLE `prize` DISABLE KEYS */;
INSERT INTO `prize` VALUES (1212,0,'2018-08-02 00:00:00','Premio por logearse una semana seguida',10,30,20,1179),(1213,0,'2018-08-03 00:00:00','Premio por logearse dos semana seguida',10,30,20,1179),(1214,0,'2018-08-02 00:00:00','Premio por logearse una semana seguida',10,30,20,1180),(1215,0,'2018-08-02 00:00:00','Premio por logearse una semana seguida',10,30,20,1181),(1216,0,'2018-08-02 00:00:00','Premio por logearse una semana seguida',10,30,20,1182);
/*!40000 ALTER TABLE `prize` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `activationDate` datetime DEFAULT NULL,
  `expirationDate` datetime DEFAULT NULL,
  `purchaseDate` date DEFAULT NULL,
  `item_id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_m8nebt839m2w55qk0dxcrnnnw` (`item_id`),
  KEY `FK_9jmud92x8qm28js0lloqod7bc` (`player_id`),
  CONSTRAINT `FK_9jmud92x8qm28js0lloqod7bc` FOREIGN KEY (`player_id`) REFERENCES `keybladewielder` (`id`),
  CONSTRAINT `FK_m8nebt839m2w55qk0dxcrnnnw` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recruited`
--

DROP TABLE IF EXISTS `recruited`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recruited` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `gummiShip_id` int(11) DEFAULT NULL,
  `storageBuilding_id` int(11) NOT NULL,
  `troop_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_i943aw5urm3iegjwt7c51iuxq` (`gummiShip_id`),
  KEY `FK_aj8fktrnyfhbljlgwu53yvnoe` (`storageBuilding_id`),
  KEY `FK_363v4589sr2895tlpm6va73n5` (`troop_id`),
  CONSTRAINT `FK_363v4589sr2895tlpm6va73n5` FOREIGN KEY (`troop_id`) REFERENCES `troop` (`id`),
  CONSTRAINT `FK_aj8fktrnyfhbljlgwu53yvnoe` FOREIGN KEY (`storageBuilding_id`) REFERENCES `built` (`id`),
  CONSTRAINT `FK_i943aw5urm3iegjwt7c51iuxq` FOREIGN KEY (`gummiShip_id`) REFERENCES `gummiship` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recruited`
--

LOCK TABLES `recruited` WRITE;
/*!40000 ALTER TABLE `recruited` DISABLE KEYS */;
INSERT INTO `recruited` VALUES (1202,0,1154,1192,NULL),(1203,0,NULL,1192,1155),(1204,0,1160,1198,NULL),(1205,0,NULL,1198,1161),(1206,0,NULL,1192,1161),(1207,0,1160,1192,NULL);
/*!40000 ALTER TABLE `recruited` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recruiter`
--

DROP TABLE IF EXISTS `recruiter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recruiter` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `gummiCoal` int(11) DEFAULT NULL,
  `munny` int(11) DEFAULT NULL,
  `mytrhil` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `extraCostPerLvl` double DEFAULT NULL,
  `isFinal` bit(1) DEFAULT NULL,
  `maxLvl` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `timeToConstruct` int(11) DEFAULT NULL,
  `contentManager_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `RecruiterUK_kafanifq4tl8429iw316qaiqh` (`isFinal`),
  KEY `FK_ev3n49ifl082qx37ap1qq4mbf` (`contentManager_id`),
  CONSTRAINT `FK_ev3n49ifl082qx37ap1qq4mbf` FOREIGN KEY (`contentManager_id`) REFERENCES `contentmanager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recruiter`
--

LOCK TABLES `recruiter` WRITE;
/*!40000 ALTER TABLE `recruiter` DISABLE KEYS */;
INSERT INTO `recruiter` VALUES (1153,0,15,40,30,'This is the Recruiter number 1',0.4,'',10,'Recruiter Number 1','./images/buildings/recruiter.png',8,1143),(1159,0,15,40,30,'This is the Recruiter number 2',0.4,'',10,'Recruiter Number 2','./images/buildings/recruiter.png',8,1143),(1162,0,15,40,30,'This is the Recruiter number 3',0.4,'\0',10,'Recruiter Number 3','./images/buildings/recruiter.png',8,1143);
/*!40000 ALTER TABLE `recruiter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `isBug` bit(1) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `keybladeWielder_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_oe0r6s550sr6cmgdstosytnpv` (`keybladeWielder_id`),
  CONSTRAINT `FK_oe0r6s550sr6cmgdstosytnpv` FOREIGN KEY (`keybladeWielder_id`) REFERENCES `keybladewielder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (1209,0,'contenido de prueba','2016-05-04','\0','RESOLVED','prueba',1179),(1210,0,'contenido de prueba','2016-05-04','\0','RESOLVED','prueba',1180),(1211,0,'contenido de prueba','2016-05-04','\0','ONHOLD','prueba',1180);
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report_photos`
--

DROP TABLE IF EXISTS `report_photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report_photos` (
  `Report_id` int(11) NOT NULL,
  `photos` varchar(255) DEFAULT NULL,
  KEY `FK_fd13a90x7k7j80d8xye1x2rfx` (`Report_id`),
  CONSTRAINT `FK_fd13a90x7k7j80d8xye1x2rfx` FOREIGN KEY (`Report_id`) REFERENCES `report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_photos`
--

LOCK TABLES `report_photos` WRITE;
/*!40000 ALTER TABLE `report_photos` DISABLE KEYS */;
/*!40000 ALTER TABLE `report_photos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report_reportupdate`
--

DROP TABLE IF EXISTS `report_reportupdate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report_reportupdate` (
  `Report_id` int(11) NOT NULL,
  `reportUpdates_id` int(11) NOT NULL,
  UNIQUE KEY `UK_43xicbjikgjminaei0arhats1` (`reportUpdates_id`),
  KEY `FK_srd13vb4jj76g5mjqllbug76j` (`Report_id`),
  CONSTRAINT `FK_43xicbjikgjminaei0arhats1` FOREIGN KEY (`reportUpdates_id`) REFERENCES `reportupdate` (`id`),
  CONSTRAINT `FK_srd13vb4jj76g5mjqllbug76j` FOREIGN KEY (`Report_id`) REFERENCES `report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_reportupdate`
--

LOCK TABLES `report_reportupdate` WRITE;
/*!40000 ALTER TABLE `report_reportupdate` DISABLE KEYS */;
INSERT INTO `report_reportupdate` VALUES (1209,1171),(1210,1172),(1211,1173);
/*!40000 ALTER TABLE `report_reportupdate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportupdate`
--

DROP TABLE IF EXISTS `reportupdate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reportupdate` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `isSuspicious` bit(1) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `administrator_id` int(11) DEFAULT NULL,
  `gameMaster_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6lbvciwghn3w9px3s8e92qp6v` (`administrator_id`),
  KEY `FK_mh33ijgwkf614b636i02jqrq5` (`gameMaster_id`),
  CONSTRAINT `FK_6lbvciwghn3w9px3s8e92qp6v` FOREIGN KEY (`administrator_id`) REFERENCES `administrator` (`id`),
  CONSTRAINT `FK_mh33ijgwkf614b636i02jqrq5` FOREIGN KEY (`gameMaster_id`) REFERENCES `gamemaster` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reportupdate`
--

LOCK TABLES `reportupdate` WRITE;
/*!40000 ALTER TABLE `reportupdate` DISABLE KEYS */;
INSERT INTO `reportupdate` VALUES (1171,0,'Imagen caida resubida','2016-01-05 05:05:00','\0','RESOLVED',NULL,1145),(1172,0,'Imagen caida resubida','2016-01-05 05:05:00','\0','RESOLVED',1142,NULL),(1173,0,'Imagen caida resubida','2016-01-05 05:05:00','\0','IRRESOLVABLE',NULL,1145);
/*!40000 ALTER TABLE `reportupdate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requirement`
--

DROP TABLE IF EXISTS `requirement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requirement` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `lvl` int(11) DEFAULT NULL,
  `mainBuilding_id` int(11) NOT NULL,
  `requiredBuilding_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_e3n6l17s6yisxwexn5wso8l52` (`lvl`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requirement`
--

LOCK TABLES `requirement` WRITE;
/*!40000 ALTER TABLE `requirement` DISABLE KEYS */;
INSERT INTO `requirement` VALUES (1200,0,1,1153,1151),(1201,0,1,1149,1156);
/*!40000 ALTER TABLE `requirement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shield`
--

DROP TABLE IF EXISTS `shield`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shield` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shield`
--

LOCK TABLES `shield` WRITE;
/*!40000 ALTER TABLE `shield` DISABLE KEYS */;
/*!40000 ALTER TABLE `shield` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `troop`
--

DROP TABLE IF EXISTS `troop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `troop` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attack` int(11) DEFAULT NULL,
  `gummiCoal` int(11) DEFAULT NULL,
  `munny` int(11) DEFAULT NULL,
  `mytrhil` int(11) DEFAULT NULL,
  `defense` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `recruiterRequiredLvl` int(11) DEFAULT NULL,
  `timeToRecruit` int(11) DEFAULT NULL,
  `recruiter_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_d63lj6ow3pqb1ke6npwi875fh` (`name`),
  KEY `UK_mh2ljjodpe0ncisfool5h16ey` (`recruiterRequiredLvl`),
  KEY `FK_1ry11t999tbcg2buhdgdal0m2` (`recruiter_id`),
  CONSTRAINT `FK_1ry11t999tbcg2buhdgdal0m2` FOREIGN KEY (`recruiter_id`) REFERENCES `recruiter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `troop`
--

LOCK TABLES `troop` WRITE;
/*!40000 ALTER TABLE `troop` DISABLE KEYS */;
INSERT INTO `troop` VALUES (1155,0,150,10,30,20,80,'Troopers',1,20,1153),(1161,0,190,15,40,30,50,'Soldiers',1,20,1159),(1163,0,170,15,40,30,60,'Explorers',1,20,1159);
/*!40000 ALTER TABLE `troop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (1133,0,'','21232f297a57a5a743894a0e4a801fc3','admin'),(1134,0,'','b1551983481358e829a23be83e85ccdc','gamemaster1'),(1135,0,'','c240642ddef994358c96da82c0361a58','manager1'),(1136,0,'','5d2bbc279b5ce75815849d5e3f0533ec','player1'),(1137,0,'','88e77ff74930f37010370c296d14737b','player2'),(1138,0,'','1aa3814dca32e4c0b79a2ca047ef1db0','player3'),(1139,0,'','12efaba7fd50f5c66bd295683c0ce2a7','player4'),(1140,0,'\0','c5aec8b7110bb97bf59ab1a06805ebdd','player5'),(1141,0,'','8df5127cd164b5bc2d2b78410a7eea0c','manager2');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (1133,'ADMIN'),(1134,'GM'),(1135,'MANAGER'),(1136,'PLAYER'),(1137,'PLAYER'),(1138,'PLAYER'),(1139,'PLAYER'),(1140,'PLAYER'),(1141,'MANAGER');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `warehouse` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `gummiCoal` int(11) DEFAULT NULL,
  `munny` int(11) DEFAULT NULL,
  `mytrhil` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `extraCostPerLvl` double DEFAULT NULL,
  `isFinal` bit(1) DEFAULT NULL,
  `maxLvl` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `timeToConstruct` int(11) DEFAULT NULL,
  `contentManager_id` int(11) NOT NULL,
  `extraSlotsPerLvl` double DEFAULT NULL,
  `gummiSlots` int(11) DEFAULT NULL,
  `gummiCoalSlots` int(11) DEFAULT NULL,
  `munnySlots` int(11) DEFAULT NULL,
  `mytrhilSlots` int(11) DEFAULT NULL,
  `troopSlots` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `WarehouseUK_kafanifq4tl8429iw316qaiqh` (`isFinal`),
  KEY `FK_mp4xkqm4jf2oxr9q7cdno2mna` (`contentManager_id`),
  CONSTRAINT `FK_mp4xkqm4jf2oxr9q7cdno2mna` FOREIGN KEY (`contentManager_id`) REFERENCES `contentmanager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES (1151,0,15,40,30,'This is the Warehouse number 1',0.6,'',10,'Warehouse Number 1','./images/buildings/warehouse.png',8,1143,0.5,2,15,40,30,10),(1152,0,15,40,30,'This is the Warehouse number 3',0.6,'\0',10,'Warehouse Number 3','./images/buildings/warehouse.png',8,1143,0.5,2,15,40,30,10),(1158,0,15,40,30,'This is the Warehouse number 2',0.6,'',10,'Warehouse Number 2','./images/buildings/warehouse.png',8,1143,0.5,2,15,40,30,10);
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-30 19:33:24

commit;
