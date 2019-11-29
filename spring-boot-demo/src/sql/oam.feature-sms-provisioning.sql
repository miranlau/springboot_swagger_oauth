DROP TABLE IF EXISTS `sms_screening_rule`;

CREATE TABLE `sms_screening_rule` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`prio` int(11) NOT NULL,
`type` enum('mo','mt') DEFAULT NULL,
`json` longblob NOT NULL,
`ts` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `sms_trusted_entity`;

CREATE TABLE `sms_trusted_entity` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`address` varchar(255) NOT NULL,
`type` enum('subscriber','network', 'smsc') DEFAULT NULL,
`json` longblob NOT NULL,
`ts` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
PRIMARY KEY (`id`),
UNIQUE KEY address_type(`address`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `sms_content_screening`;

CREATE TABLE `sms_content_screening` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`json` longblob NOT NULL,
`ts` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- The application needs to enforce that the `username` is unique regardless of `transport` type. 
DROP TABLE IF EXISTS `sms_northbound_account`;
CREATE TABLE `sms_northbound_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) UNIQUE NOT NULL,
  `transport` enum('SMPP','HTTP') NOT NULL,
  `password` varchar(20) NOT NULL,
  `user_data` longblob NOT NULL,
  `active` BOOLEAN NOT NULL, 
  `ts` timestamp(0) NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) 
); 

DROP TABLE IF EXISTS `sms_northbound_serving_address`;  
CREATE TABLE `sms_northbound_serving_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ton` tinyint NOT NULL, 
  `npi` tinyint NOT NULL, 
  `address` varchar(255) NOT NULL, 
  `ts` timestamp(0) NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY unique_address(`ton`,`npi`,`address`) 
); 

DROP TABLE IF EXISTS `sms_northbound_account_serving_address`;  
CREATE TABLE `sms_northbound_account_serving_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_sms_northbound_account` int NOT NULL,
  `id_sms_northbound_serving_address` int NOT NULL,
  `ts` timestamp(0) NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`), 
  UNIQUE KEY unique_account_and_address(`id_sms_northbound_account`,`id_sms_northbound_serving_address`) ,
  FOREIGN KEY (`id_sms_northbound_account`) REFERENCES sms_northbound_account(`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  FOREIGN KEY (`id_sms_northbound_serving_address`) REFERENCES sms_northbound_serving_address(`id`) ON DELETE CASCADE ON UPDATE RESTRICT
); 

DROP TABLE IF EXISTS `sms_southbound_account`;
CREATE TABLE `sms_southbound_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `transport` enum('SMPP','SIP') NOT NULL,
  `password` varchar(20) NOT NULL,
  `options` longblob NOT NULL,
  `active` BOOLEAN NOT NULL, 
  `ts` timestamp(0) NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY unique_username_and_transport(`username`,`transport`) 
); 

DROP TABLE IF EXISTS `sms_southbound_group`;
CREATE TABLE `sms_southbound_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) UNIQUE NOT NULL,
  `ts` timestamp(0) NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) 
); 

DROP TABLE IF EXISTS `sms_southbound_account_group`;  
CREATE TABLE `sms_southbound_account_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_sms_southbound_group` int NOT NULL,
  `id_sms_southbound_account` int NOT NULL,
  `ts` timestamp(0) NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY unique_account_and_group(`id_sms_southbound_group`,`id_sms_southbound_account`), 
  FOREIGN KEY (`id_sms_southbound_group`) REFERENCES sms_southbound_group(`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  FOREIGN KEY (`id_sms_southbound_account`) REFERENCES sms_southbound_account(`id`) ON DELETE CASCADE ON UPDATE RESTRICT
); 

DROP TABLE IF EXISTS `sms_data_rule`;
CREATE TABLE `sms_data_rule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rule` longblob NOT NULL,
  `ts` timestamp(0) NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) 
); 

DROP TABLE IF EXISTS `sms_time_rule`;
CREATE TABLE `sms_time_rule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rule` longblob NOT NULL,
  `ts` timestamp(0) NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) 
); 

DROP TABLE IF EXISTS `sms_route_rule`;  
CREATE TABLE `sms_route_rule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) UNIQUE,
  `id_sms_northbound_account` int,
  `id_sms_time_rule` int , 
  `id_sms_data_rule` int , 
  `priority` int NOT NULL,
  `action` enum('NONE','FDA')  NOT NULL, 
  `id_sms_southbound_group` int NOT NULL,
  `active` BOOLEAN NOT NULL, 
  `ts` timestamp(0) NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`), 
  UNIQUE KEY unique_rule(`id_sms_northbound_account`,`id_sms_time_rule`,`id_sms_data_rule`), 
  FOREIGN KEY (`id_sms_northbound_account`) REFERENCES sms_northbound_account(`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  FOREIGN KEY (`id_sms_time_rule`) REFERENCES sms_time_rule(`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  FOREIGN KEY (`id_sms_data_rule`) REFERENCES sms_data_rule(`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  FOREIGN KEY (`id_sms_southbound_group`) REFERENCES sms_southbound_group(`id`) ON DELETE RESTRICT ON UPDATE RESTRICT 
); 

