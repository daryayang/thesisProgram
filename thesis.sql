/*
 Navicat Premium Data Transfer

 Source Server         : yangyang
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : thesis

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 14/01/2024 20:49:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `Summary` text,
  `publish_time` datetime DEFAULT NULL,
  `Source` varchar(255) DEFAULT NULL,
  `Link` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf16;

-- ----------------------------
-- Records of news
-- ----------------------------
BEGIN;
INSERT INTO `news` (`id`, `title`, `Summary`, `publish_time`, `Source`, `Link`) VALUES (1, '新科技公司推出革命性产品', '一家新科技公司近日推出了一款革命性的产品，引起了广泛关注。这款产品被认为将改变行业格局，带来巨大的创新和便利。', '2024-01-13 09:00:00', '科技新闻网', 'https://example.com/tech-news');
INSERT INTO `news` (`id`, `title`, `Summary`, `publish_time`, `Source`, `Link`) VALUES (2, '医学界取得重大突破', '研究人员宣布医学领域取得了一项重大突破，有望为治疗多种疾病提供新的可能性。这一发现将为未来的医学研究和治疗带来深远影响。', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf16 COLLATE utf16_general_ci NOT NULL DEFAULT '新增系统通知',
  `content` text CHARACTER SET utf16 COLLATE utf16_general_ci NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('未读','已读','回收站') CHARACTER SET utf16 COLLATE utf16_general_ci NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`notification_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf16;

-- ----------------------------
-- Records of notification
-- ----------------------------
BEGIN;
INSERT INTO `notification` (`notification_id`, `title`, `content`, `timestamp`, `status`, `user_id`) VALUES (1, '系统通知1', '测试用系统通知', '2024-01-09 22:16:20', '未读', 1);
INSERT INTO `notification` (`notification_id`, `title`, `content`, `timestamp`, `status`, `user_id`) VALUES (2, '系统通知2', '测试用系统通知2', '2024-01-09 22:17:02', '已读', 1);
INSERT INTO `notification` (`notification_id`, `title`, `content`, `timestamp`, `status`, `user_id`) VALUES (3, '系统通知3', '测试用系统通知', '2024-01-09 22:17:17', '回收站', 1);
COMMIT;

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
  `reportid` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `fileid` int DEFAULT NULL,
  `report_title` varchar(255) NOT NULL,
  `report_path` varchar(255) NOT NULL,
  `generate_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`reportid`),
  KEY `user` (`userid`),
  KEY `fileid` (`fileid`),
  CONSTRAINT `fileid` FOREIGN KEY (`fileid`) REFERENCES `uploaddoc` (`fileid`),
  CONSTRAINT `user` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

-- ----------------------------
-- Records of report
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for uploaddoc
-- ----------------------------
DROP TABLE IF EXISTS `uploaddoc`;
CREATE TABLE `uploaddoc` (
  `userid` int NOT NULL,
  `fileid` int NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) NOT NULL,
  `file_path` varchar(255) NOT NULL,
  `upload_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`fileid`) USING BTREE,
  KEY `userid` (`userid`),
  CONSTRAINT `userid` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

-- ----------------------------
-- Records of uploaddoc
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `description` varchar(255) CHARACTER SET utf16 COLLATE utf16_general_ci DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf16 COLLATE utf16_general_ci DEFAULT '/Users/yangyang/Desktop/logo.png',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf16;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `username`, `password`, `description`, `avatar`) VALUES (1, 'admin', '123456', '要疯了', '/Users/yangyang/Desktop/logo.png');
INSERT INTO `user` (`id`, `username`, `password`, `description`, `avatar`) VALUES (2, 'yangyang', '199868', '要疯了', '/Users/yangyang/Desktop/logo.png');
INSERT INTO `user` (`id`, `username`, `password`, `description`, `avatar`) VALUES (3, 'test', '12345678', '要疯了', '/Users/yangyang/Desktop/logo.png');
COMMIT;

-- ----------------------------
-- Table structure for UserInfo
-- ----------------------------
DROP TABLE IF EXISTS `UserInfo`;
CREATE TABLE `UserInfo` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `description` text,
  `avatar:` varchar(255) CHARACTER SET utf16 COLLATE utf16_general_ci DEFAULT '/Users/yangyang/Desktop/logo.png',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf16;

-- ----------------------------
-- Records of UserInfo
-- ----------------------------
BEGIN;
INSERT INTO `UserInfo` (`user_id`, `username`, `description`, `avatar:`) VALUES (1, 'yangyang', '杨洋加油', '/Users/yangyang/Desktop/logo.png');
INSERT INTO `UserInfo` (`user_id`, `username`, `description`, `avatar:`) VALUES (2, 'admin', '管理员大人', '/Users/yangyang/Desktop/logo.png');
COMMIT;

-- ----------------------------
-- View structure for notibyid
-- ----------------------------
DROP VIEW IF EXISTS `notibyid`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `notibyid` AS select `a`.`timestamp` AS `timestamp`,`a`.`title` AS `title`,`a`.`content` AS `content`,`a`.`status` AS `status` from (`notification` `a` join `user` `b`) where (`a`.`user_id` = `b`.`id`);

SET FOREIGN_KEY_CHECKS = 1;
