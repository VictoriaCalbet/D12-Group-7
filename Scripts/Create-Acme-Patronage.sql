start transaction;

-- DB creation
drop database if exists `Acme-Patronage`;
create database `Acme-Patronage`;

use `Acme-Patronage`;

-- Create users.
create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

-- Grant privilages.
grant select, insert, update, delete on `Acme-Patronage`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter, create temporary tables, lock tables, create view, create routine, alter routine, execute, trigger, show view on `Acme-Patronage`.* to 'acme-manager'@'%';

-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Patronage
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
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (364,0,'This is a bio of an admin.','admin@admins.com','Admin McAdmin','619619617',356);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `announcement`
--

DROP TABLE IF EXISTS `announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `announcement` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `creationMoment` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `project_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_2ok0q9kohf7pw2x5ijegr7b6` (`creationMoment`),
  KEY `FK_lwsgq1xtu7bbuq1inbm7dhynt` (`project_id`),
  KEY `FK_frbasihc59fobfwh11mlco2qn` (`user_id`),
  CONSTRAINT `FK_frbasihc59fobfwh11mlco2qn` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_lwsgq1xtu7bbuq1inbm7dhynt` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement`
--

LOCK TABLES `announcement` WRITE;
/*!40000 ALTER TABLE `announcement` DISABLE KEYS */;
INSERT INTO `announcement` VALUES (387,0,'2017-12-11 11:00:00','This is Announcement1.','Announcement1',378,365),(388,0,'2018-01-11 12:00:00','This is Announcement2.','Announcement2',379,366),(389,0,'2018-02-11 13:00:00','This is Announcement3.','Announcement3',380,366);
/*!40000 ALTER TABLE `announcement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `announcementcomment`
--

DROP TABLE IF EXISTS `announcementcomment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `announcementcomment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `creationMoment` datetime DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `announcement_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_iqdkagt26vek745ih2h3h9l4c` (`announcement_id`),
  KEY `FK_hs1qnchpvrs58g4veh59lrkmq` (`user_id`),
  CONSTRAINT `FK_hs1qnchpvrs58g4veh59lrkmq` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_iqdkagt26vek745ih2h3h9l4c` FOREIGN KEY (`announcement_id`) REFERENCES `announcement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcementcomment`
--

LOCK TABLES `announcementcomment` WRITE;
/*!40000 ALTER TABLE `announcementcomment` DISABLE KEYS */;
INSERT INTO `announcementcomment` VALUES (390,0,'2018-03-12 14:00:00',1,'Comment in an announcement',365,388),(391,0,'2018-04-12 14:00:00',2,'Comment in another different announcement',365,389),(392,0,'2018-05-12 14:00:00',4,'Look! Another comment in an announcement',366,389);
/*!40000 ALTER TABLE `announcementcomment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `award`
--

DROP TABLE IF EXISTS `award`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `award` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `moneyGoal` double NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `project_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_jjm32n821qbmkoi23seah44kh` (`moneyGoal`),
  KEY `FK_9uq76sw938ciokot3hbkyse0m` (`project_id`),
  CONSTRAINT `FK_9uq76sw938ciokot3hbkyse0m` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `award`
--

LOCK TABLES `award` WRITE;
/*!40000 ALTER TABLE `award` DISABLE KEYS */;
INSERT INTO `award` VALUES (393,0,'You get a prize, you get a prize, EVERYBODY GETS A PRIZE!!',50,'Award1',378),(394,0,'I will write more original examples for testing purposes rather than \'Award2\'.',1600,'Award2',379),(395,0,'I will try to do the Ice Bucket Challenge.',200,'Award3',380),(396,0,'I will make a large amount of examples.',2000,'Award4',382),(397,0,'Montgomery Burns Award for Outstanding Achievement in the Field of Excellence',2000,'Award5',382);
/*!40000 ALTER TABLE `award` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `awardcomment`
--

DROP TABLE IF EXISTS `awardcomment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `awardcomment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `creationMoment` datetime DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `award_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_e2m9v0yqktip9rp1x19bc2io` (`award_id`),
  KEY `FK_ges1vgk3cvf85wapp64cmuhy2` (`user_id`),
  CONSTRAINT `FK_ges1vgk3cvf85wapp64cmuhy2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_e2m9v0yqktip9rp1x19bc2io` FOREIGN KEY (`award_id`) REFERENCES `award` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `awardcomment`
--

LOCK TABLES `awardcomment` WRITE;
/*!40000 ALTER TABLE `awardcomment` DISABLE KEYS */;
INSERT INTO `awardcomment` VALUES (398,0,'2018-04-12 14:00:00',2,'Comment in an award',365,394),(399,0,'2018-04-12 15:00:00',3,'Comment in another award',365,395),(400,0,'2018-04-12 15:00:00',4,'Comment in a brand new different award',365,395);
/*!40000 ALTER TABLE `awardcomment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (372,0,'This category is for games which need funding.','Games'),(373,0,'This category is for artists who need funding.','Art'),(374,0,'This category is for handmade crafters who need funding.','Artisan Crafts'),(375,0,'This category is for projects that don\'t match the other categories.','Others');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `creationMoment` datetime DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jhvt6d9ap8gxv67ftrmshdfhj` (`user_id`),
  CONSTRAINT `FK_jhvt6d9ap8gxv67ftrmshdfhj` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commentform`
--

DROP TABLE IF EXISTS `commentform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `commentform` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `creationMoment` datetime DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commentform`
--

LOCK TABLES `commentform` WRITE;
/*!40000 ALTER TABLE `commentform` DISABLE KEYS */;
/*!40000 ALTER TABLE `commentform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `corporation`
--

DROP TABLE IF EXISTS `corporation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `corporation` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sftvla3vhja6cwaakkd196nw0` (`userAccount_id`),
  CONSTRAINT `FK_sftvla3vhja6cwaakkd196nw0` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `corporation`
--

LOCK TABLES `corporation` WRITE;
/*!40000 ALTER TABLE `corporation` DISABLE KEYS */;
INSERT INTO `corporation` VALUES (370,0,'This is the bio of corporation1.','corporation1@corporations.com','Corporation1 McCorporation1','61961999',362),(371,0,'This is the bio of corporation2.','corporation2@corporations.com','Corporation2 McCorporation2','61961990',363);
/*!40000 ALTER TABLE `corporation` ENABLE KEYS */;
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
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachmentLink` varchar(255) DEFAULT NULL,
  `body` varchar(255) DEFAULT NULL,
  `header` varchar(255) DEFAULT NULL,
  `recipient_id` int(11) NOT NULL,
  `sender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (376,0,NULL,'This is a message for you!','Header of message1',368,365),(377,0,NULL,'A message for you!','Header of message2',370,369);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moderator`
--

DROP TABLE IF EXISTS `moderator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `moderator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dvoe3msx5ofgj5qm5tyj6hnm9` (`userAccount_id`),
  CONSTRAINT `FK_dvoe3msx5ofgj5qm5tyj6hnm9` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moderator`
--

LOCK TABLES `moderator` WRITE;
/*!40000 ALTER TABLE `moderator` DISABLE KEYS */;
INSERT INTO `moderator` VALUES (368,0,'This is the bio of moderator1.','moderator1@moderators.com','Moderator1 McModerator1','619619622',360),(369,0,'This is the bio of moderator2.','moderator2@moderators.com','Moderator2 McModerator2','619619625',361);
/*!40000 ALTER TABLE `moderator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patronage`
--

DROP TABLE IF EXISTS `patronage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patronage` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `amount` double NOT NULL,
  `creationDate` datetime DEFAULT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `cvv` int(11) NOT NULL,
  `expirationMonth` int(11) NOT NULL,
  `expirationYear` int(11) NOT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `isCancelled` bit(1) NOT NULL,
  `project_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_scktxqo6skck718k320afbg4o` (`amount`),
  KEY `FK_b6dd2cqonb7sbqkqabvtnxxq9` (`project_id`),
  KEY `FK_emll0fthd5u4vuanor96syrbe` (`user_id`),
  CONSTRAINT `FK_emll0fthd5u4vuanor96syrbe` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_b6dd2cqonb7sbqkqabvtnxxq9` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patronage`
--

LOCK TABLES `patronage` WRITE;
/*!40000 ALTER TABLE `patronage` DISABLE KEYS */;
INSERT INTO `patronage` VALUES (401,0,20,'2017-01-12 12:00:00','VISA',673,6,2019,'user2','4485750721419113','\0',378,366),(402,0,30,'2017-02-12 12:00:00','VISA',673,6,2019,'user1','4485750721419113','\0',379,365),(403,0,2,'2017-03-12 12:00:00','VISA',673,6,2019,'user1','4485750721419113','\0',380,365);
/*!40000 ALTER TABLE `patronage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `creationMoment` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dueDate` datetime DEFAULT NULL,
  `economicGoal` double NOT NULL,
  `isCancelled` bit(1) NOT NULL,
  `isDraft` bit(1) NOT NULL,
  `minimumPatronageAmount` double NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `category_id` int(11) NOT NULL,
  `creator_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_ranfrqyr3dj6cf7o71u62jc8i` (`dueDate`),
  KEY `UK_evnf8purejj5avimgtomep8fa` (`economicGoal`),
  KEY `UK_im6pf7k8e3dek6h0mt7o8tik1` (`isCancelled`),
  KEY `UK_3yv30h967o9he0h3gnokw5xha` (`isDraft`),
  KEY `UK_nh2tf1haiyej2guc7hufipomc` (`creationMoment`),
  KEY `UK_9ytiqf3wdk0iv4jcttcngwr9a` (`title`),
  KEY `UK_369mtlmwx25rddj29ug591fp0` (`description`),
  KEY `FK_3like0l3x1tm6ihavq2ud562f` (`category_id`),
  KEY `FK_1u71rhtd2wynf8cpw03h3cn3p` (`creator_id`),
  CONSTRAINT `FK_1u71rhtd2wynf8cpw03h3cn3p` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_3like0l3x1tm6ihavq2ud562f` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (378,0,'2016-08-11 15:30:00','Project1 is about a game.','2019-12-02 15:30:00',5050,'\0','\0',5,'Project1',372,365),(379,0,'2016-08-11 16:30:00','Project2 is about art.','2017-01-01 15:30:00',10050,'\0','\0',10,'Project2',373,366),(380,0,'2016-04-11 16:30:00','Project3 is about crafting.','2017-01-03 15:30:00',12470,'\0','\0',15,'Project3',374,367),(381,0,'2016-08-11 16:30:00','Project4 is about something else.','2019-01-01 15:30:00',620050,'','\0',30,'Project4',375,367),(382,0,'2016-08-11 16:30:00','Project5 is a draft.','2019-01-04 15:30:00',720050,'\0','',40,'Project5',375,367);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projectcomment`
--

DROP TABLE IF EXISTS `projectcomment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projectcomment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `creationMoment` datetime DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bjnpi2h3jm8bx56jm9omfoptn` (`project_id`),
  KEY `FK_43mtrd34k29a4c332rc8j2hdp` (`user_id`),
  CONSTRAINT `FK_43mtrd34k29a4c332rc8j2hdp` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_bjnpi2h3jm8bx56jm9omfoptn` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projectcomment`
--

LOCK TABLES `projectcomment` WRITE;
/*!40000 ALTER TABLE `projectcomment` DISABLE KEYS */;
INSERT INTO `projectcomment` VALUES (383,0,'2018-03-12 14:00:00',5,'Comment in a project',366,378);
/*!40000 ALTER TABLE `projectcomment` ENABLE KEYS */;
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
  `complaint` varchar(255) DEFAULT NULL,
  `isLegit` bit(1) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `project_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_q61exfy99xxrht9wr6t80kbs7` (`isLegit`),
  KEY `FK_6pcvccx7kkkah06hmyxawguls` (`project_id`),
  KEY `FK_q8xq6jgl38r39cogrph09w76m` (`user_id`),
  CONSTRAINT `FK_q8xq6jgl38r39cogrph09w76m` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_6pcvccx7kkkah06hmyxawguls` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (384,0,'I hate this user.','\0','It\'s childish',379,367),(385,0,'This project is inappropriate because reasons.','','The reasons are so goods, I\'m happy.',381,365);
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsorship`
--

DROP TABLE IF EXISTS `sponsorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsorship` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `bannerURL` varchar(255) DEFAULT NULL,
  `pageURL` varchar(255) DEFAULT NULL,
  `corporation_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tmua5b0ahh5v301pxb2rc2l1i` (`corporation_id`),
  KEY `FK_ra7amorl96hdwghb5v3uqgvjp` (`project_id`),
  CONSTRAINT `FK_ra7amorl96hdwghb5v3uqgvjp` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `FK_tmua5b0ahh5v301pxb2rc2l1i` FOREIGN KEY (`corporation_id`) REFERENCES `corporation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsorship`
--

LOCK TABLES `sponsorship` WRITE;
/*!40000 ALTER TABLE `sponsorship` DISABLE KEYS */;
INSERT INTO `sponsorship` VALUES (386,0,'https://image.freepik.com/free-icon/facebook-logo_318-49940.jpg','https://facebook.com',370,378);
/*!40000 ALTER TABLE `sponsorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o6s94d43co03sx067ili5760c` (`userAccount_id`),
  CONSTRAINT `FK_o6s94d43co03sx067ili5760c` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (365,0,'This is the bio of user1.','user1@users.com','User1 McUser1','619619619',357),(366,0,'This is the bio of user2.','user2@users.com','User2 McUser2','619619618',358),(367,0,'This is the bio of user3.','user3@users.com','User3 McUser3','619619718',359);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
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
  `isEnabled` bit(1) NOT NULL,
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
INSERT INTO `useraccount` VALUES (356,0,'','21232f297a57a5a743894a0e4a801fc3','admin'),(357,0,'','24c9e15e52afc47c225b757e7bee1f9d','user1'),(358,0,'','7e58d63b60197ceb55a1c487989a3720','user2'),(359,0,'','92877af70a45fd6a2ed7fe81e1236b78','user3'),(360,0,'','38caf4a470117125b995f7ce53e6e6b9','moderator1'),(361,0,'','95d88ad73653fc7ad4fec3bc56677c3c','moderator2'),(362,0,'','6ebb9282d889012f86ad6c73cf6e706b','corporation1'),(363,0,'','29ce5983dce114d6e5b06e4fef1694e2','corporation2');
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
INSERT INTO `useraccount_authorities` VALUES (356,'ADMIN'),(357,'USER'),(358,'USER'),(359,'USER'),(360,'MODERATOR'),(361,'MODERATOR'),(362,'CORPORATION'),(363,'CORPORATION');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-30 16:58:44

commit;