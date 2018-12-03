# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.24)
# Database: collaborative_filtering
# Generation Time: 2018-12-03 02:33:18 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table cf_theta_feature
# ------------------------------------------------------------
CREATE DATABASE collaborative_filtering;

USE collaborative_filtering;

DROP TABLE IF EXISTS `cf_theta_feature`;

CREATE TABLE `cf_theta_feature` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'theta_id',
  `feature0` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature1` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature2` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature3` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature4` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature5` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature6` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature7` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature8` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature9` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature10` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature11` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature12` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature13` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature14` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature15` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature16` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature17` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature18` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature19` double NOT NULL DEFAULT '0' COMMENT '特征值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table cf_x_feature
# ------------------------------------------------------------

DROP TABLE IF EXISTS `cf_x_feature`;

CREATE TABLE `cf_x_feature` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'x_id',
  `feature0` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature1` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature2` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature3` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature4` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature5` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature6` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature7` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature8` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature9` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature10` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature11` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature12` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature13` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature14` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature15` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature16` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature17` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature18` double NOT NULL DEFAULT '0' COMMENT '特征值',
  `feature19` double NOT NULL DEFAULT '0' COMMENT '特征值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table cf_x_theta_score
# ------------------------------------------------------------

DROP TABLE IF EXISTS `cf_x_theta_score`;

CREATE TABLE `cf_x_theta_score` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `x_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '商品、电影、演员、主播等id',
  `theta_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户、评价主体等id',
  `score` double NOT NULL DEFAULT '0' COMMENT '评分',
  PRIMARY KEY (`id`),
  KEY `idx_x_theta` (`x_id`,`theta_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
