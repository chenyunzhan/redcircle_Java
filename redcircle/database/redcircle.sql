-- MySQL dump 10.13  Distrib 5.7.11, for osx10.9 (x86_64)
--
-- Host: localhost    Database: redcircle
-- ------------------------------------------------------
-- Server version	5.7.11

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
-- Table structure for table `t_red_message`
--

DROP TABLE IF EXISTS `t_red_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_red_message` (
  `appId` varchar(45) DEFAULT NULL,
  `fromUserId` varchar(45) DEFAULT NULL,
  `targetId` varchar(45) DEFAULT NULL,
  `targetType` varchar(45) DEFAULT NULL,
  `GroupId` varchar(45) DEFAULT NULL,
  `classname` varchar(45) DEFAULT NULL,
  `content` varchar(10000) DEFAULT NULL,
  `dateTime` varchar(45) DEFAULT NULL,
  `msgUID` varchar(45) NOT NULL,
  PRIMARY KEY (`msgUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_red_message`
--

LOCK TABLES `t_red_message` WRITE;
/*!40000 ALTER TABLE `t_red_message` DISABLE KEYS */;
INSERT INTO `t_red_message` VALUES ('qf3d5gbj3ufqh','18829218257','18706734109','1.0','','RC:TxtMsg','{content=  你是谁？}','2016-04-13 11:00:50.843','5A0R-43I3-C771-AKKO'),('qf3d5gbj3ufqh','18706734109','18829218257','1.0','','RC:TxtMsg','{content=我是陈云展的男朋友}','2016-04-13 11:02:09.858','5A0R-4D6O-8742-OE6J'),('qf3d5gbj3ufqh','18829218257','18706734109','1.0','','RC:TxtMsg','{content=哦哦}','2016-04-13 11:02:16.572','5A0R-4E0V-G771-AKKO'),('qf3d5gbj3ufqh','18706734109','18829218257','1.0','','RC:TxtMsg','{content=你在学校吗？}','2016-04-13 11:02:30.876','5A0R-4FOR-G742-OE6J'),('qf3d5gbj3ufqh','18829218257','18706734109','1.0','','RC:TxtMsg','{content=是啊还要上课呢}','2016-04-13 11:02:54.008','5A0R-4IJ7-0771-AKKO');
/*!40000 ALTER TABLE `t_red_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_red_user`
--

DROP TABLE IF EXISTS `t_red_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_red_user` (
  `me_phone` varchar(45) NOT NULL,
  `friend_phone` varchar(45) NOT NULL,
  `sex` varchar(45) DEFAULT '',
  `name` varchar(45) DEFAULT '',
  PRIMARY KEY (`me_phone`,`friend_phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_red_user`
--

LOCK TABLES `t_red_user` WRITE;
/*!40000 ALTER TABLE `t_red_user` DISABLE KEYS */;
INSERT INTO `t_red_user` VALUES ('15891739884','123456','','陈蕊'),('15891739884','15891739884','','陈蕊'),('15891739884','654321','','陈蕊'),('18611206039','18611206039','','黄亚莉'),('18611206039','18829218257','','黄亚莉'),('18611206039','18829221518','','黄亚莉'),('18706734109','15891739884','','陈云展'),('18706734109','18611206039','','陈云展'),('18706734109','18706734109','','陈云展'),('18829218257','147','','张琴'),('18829218257','18829218257','','张琴'),('18829218257','258','','张琴');
/*!40000 ALTER TABLE `t_red_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-15 13:19:33




DROP TABLE IF EXISTS `t_red_report_user`;
CREATE TABLE `t_red_report_user` (
  `report_id` int NOT NULL AUTO_INCREMENT,
  `report_type` varchar(45) DEFAULT NULL,
  `user_phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  