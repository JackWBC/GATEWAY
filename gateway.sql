/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 127.0.0.1
 Source Database       : gateway

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : utf-8

 Date: 04/03/2019 11:00:14 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `gw_predicate`
-- ----------------------------
DROP TABLE IF EXISTS `gw_predicate`;
CREATE TABLE `gw_predicate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `route_id` varchar(255) NOT NULL COMMENT '路由id',
  `predicate_name` varchar(255) NOT NULL COMMENT '断言名称',
  `predicate_text` varchar(10000) NOT NULL COMMENT '断言规则',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(255) DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT '' COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `ix_route_id` (`route_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `gw_predicate`
-- ----------------------------
BEGIN;
INSERT INTO `gw_predicate` VALUES ('1', 'get_route', 'Path', 'Path=/get', '0', '2019-04-02 09:39:40', '', '2019-04-02 09:39:40', ''), ('2', 'headers_route', 'Path', 'Path=/headers', '1', '2019-04-02 09:49:23', '', '2019-04-02 09:58:32', ''), ('3', 'headers_route', 'Path', 'Path=/headers', '1', '2019-04-02 13:48:12', '', '2019-04-02 13:49:09', ''), ('4', 'headers_route', 'Path', 'Path=/headers', '0', '2019-04-03 02:08:30', '', '2019-04-03 02:08:30', ''), ('5', 'hystrix_route', 'Host', 'Host=*.hystrix.com', '0', '2019-04-03 02:20:26', '', '2019-04-03 02:20:26', '');
COMMIT;

-- ----------------------------
--  Table structure for `gw_filter`
-- ----------------------------
DROP TABLE IF EXISTS `gw_filter`;
CREATE TABLE `gw_filter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `route_id` varchar(255) NOT NULL COMMENT '路由id',
  `filter_name` varchar(255) NOT NULL COMMENT '过滤器名称',
  `filter_text` varchar(10000) NOT NULL COMMENT '过滤器规则',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(255) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT '' COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `ix_route_id` (`route_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `gw_filter`
-- ----------------------------
BEGIN;
INSERT INTO `gw_filter` VALUES ('1', 'get_route', 'AddRequestHeader', 'AddRequestHeader=Hello,World', '0', '2019-04-02 09:39:40', '', '2019-04-02 09:39:40', ''), ('2', 'headers_route', 'AddRequestHeader', 'AddRequestHeader=Hello,World_1', '1', '2019-04-02 09:49:23', '', '2019-04-02 09:58:32', ''), ('3', 'headers_route', 'AddRequestHeader', 'AddRequestHeader=Hello,World_1', '1', '2019-04-02 13:48:12', '', '2019-04-02 13:49:09', ''), ('4', 'headers_route', 'AddRequestHeader', 'AddRequestHeader=Hello,World_1', '0', '2019-04-03 02:08:30', '', '2019-04-03 02:08:30', ''), ('5', 'headers_route', 'Hystrix', 'Hystrix=name~fallbackcmd,fallbackUri~forward:/fallback/', '0', '2019-04-03 02:08:30', '', '2019-04-03 02:08:30', ''), ('6', 'hystrix_route', 'AddRequestHeader', 'AddRequestHeader=Hello,World_1', '0', '2019-04-03 02:20:26', '', '2019-04-03 02:20:26', ''), ('7', 'hystrix_route', 'Hystrix', 'Hystrix=name~myCmd,fallbackUri~forward:/fallback/', '0', '2019-04-03 02:20:26', '', '2019-04-03 02:20:26', '');
COMMIT;

-- ----------------------------
--  Table structure for `gw_route`
-- ----------------------------
DROP TABLE IF EXISTS `gw_route`;
CREATE TABLE `gw_route` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `route_id` varchar(255) NOT NULL,
  `route_order` int(11) NOT NULL DEFAULT '0' COMMENT '执行顺序',
  `route_uri` varchar(255) NOT NULL COMMENT '路由规则转发目标uri',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新人',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `ix_route_id` (`route_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `gw_route`
-- ----------------------------
BEGIN;
INSERT INTO `gw_route` VALUES ('2', 'get_route', '0', 'http://localhost:8888', '0', '2019-04-02 09:39:40', null, '2019-04-02 09:39:40', null), ('3', 'headers_route', '0', 'http://localhost:8888', '1', '2019-04-02 09:49:23', null, '2019-04-02 09:58:32', null), ('4', 'headers_route', '0', 'http://localhost:8888', '1', '2019-04-02 13:48:12', null, '2019-04-02 13:49:09', null), ('5', 'headers_route', '0', 'http://localhost:8888', '0', '2019-04-03 02:08:30', null, '2019-04-03 02:08:30', null), ('6', 'hystrix_route', '0', 'http://localhost:8888', '0', '2019-04-03 02:20:26', null, '2019-04-03 02:20:26', null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
