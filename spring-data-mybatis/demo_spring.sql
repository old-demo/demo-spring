/*
 Navicat MySQL Data Transfer

 Source Server         : hq_test
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : demo_spring

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 03/08/2020 14:35:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for people
-- ----------------------------
DROP TABLE IF EXISTS `people`;
CREATE TABLE `people`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名字',
  `age` int(11) NOT NULL DEFAULT 0 COMMENT '年龄',
  `gender` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'M' COMMENT '性别：F-女 M-男',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of people
-- ----------------------------
INSERT INTO `people` VALUES (1, '测试1-1', 0, 'M', '2020-07-28 20:00:52');
INSERT INTO `people` VALUES (2, '测试1-2', 0, 'M', '2020-07-28 20:00:52');
INSERT INTO `people` VALUES (38, '测试2', 2, 'M', '2020-07-31 17:23:55');

-- ----------------------------
-- Table structure for school_class
-- ----------------------------
DROP TABLE IF EXISTS `school_class`;
CREATE TABLE `school_class`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '班级名',
  `head_teacher_id` bigint(20) NOT NULL COMMENT '班主任id',
  `class_director_id` bigint(20) NOT NULL COMMENT '年级教导主任id',
  `class_director_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKbbvitugxcrv06a8pk0pw41k26`(`head_teacher_id`) USING BTREE,
  INDEX `FKotva6x8xyvqkp6scvjxpttj8i`(`class_director_id`) USING BTREE,
  CONSTRAINT `FKbbvitugxcrv06a8pk0pw41k26` FOREIGN KEY (`head_teacher_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKotva6x8xyvqkp6scvjxpttj8i` FOREIGN KEY (`class_director_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKr74w0bib1cumvwmr99hocljcm` FOREIGN KEY (`head_teacher_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of school_class
-- ----------------------------
INSERT INTO `school_class` VALUES (1, '高一1班', 2, 1, '贺小白');
INSERT INTO `school_class` VALUES (2, '高一2班', 3, 1, '贺小白');
INSERT INTO `school_class` VALUES (3, '高二1班', 1, 3, '贺小灰');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, '贺小白');
INSERT INTO `teacher` VALUES (2, '贺小黑');
INSERT INTO `teacher` VALUES (3, '贺小灰');

-- ----------------------------
-- Table structure for teacher_class
-- ----------------------------
DROP TABLE IF EXISTS `teacher_class`;
CREATE TABLE `teacher_class`  (
  `teacher_id` bigint(20) NOT NULL COMMENT '教师id',
  `class_id` bigint(20) NOT NULL COMMENT '班级id',
  PRIMARY KEY (`teacher_id`, `class_id`) USING BTREE,
  INDEX `FK9054ocslygwpxb6b3m95mhf6k`(`class_id`) USING BTREE,
  CONSTRAINT `FK9054ocslygwpxb6b3m95mhf6k` FOREIGN KEY (`class_id`) REFERENCES `school_class` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKcys37idxlb3esi22jegr7j7lc` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_class
-- ----------------------------
INSERT INTO `teacher_class` VALUES (1, 1);
INSERT INTO `teacher_class` VALUES (2, 1);
INSERT INTO `teacher_class` VALUES (2, 2);
INSERT INTO `teacher_class` VALUES (3, 2);
INSERT INTO `teacher_class` VALUES (1, 3);
INSERT INTO `teacher_class` VALUES (2, 3);

SET FOREIGN_KEY_CHECKS = 1;
