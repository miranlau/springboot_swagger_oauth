DROP TABLE IF EXISTS `mo_threshold_info`;

CREATE TABLE `mo_threshold_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` varchar(20) NOT NULL,
  `last_allowance` decimal(20,7) UNSIGNED NOT NULL,
  `last_checked_time` timestamp(3) NOT NULL DEFAULT current_timestamp(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `sender_id` (`sender_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `mt_threshold_info`;

CREATE TABLE `mt_threshold_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` varchar(20) NOT NULL,
  `last_allowance` decimal(20,7) UNSIGNED NOT NULL,
  `last_checked_time` timestamp(3) NOT NULL DEFAULT current_timestamp(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `sender_id` (`sender_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `mo_identical_threshold_info`;

CREATE TABLE `mo_identical_threshold_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_hash` varchar(64) NOT NULL,
  `last_allowance` decimal(20,7) UNSIGNED NOT NULL,
  `last_checked_time` timestamp(3) NOT NULL DEFAULT current_timestamp(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `message_hash` (`message_hash`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `mt_identical_threshold_info`;

CREATE TABLE `mt_identical_threshold_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_hash` varchar(64) NOT NULL,
  `last_allowance` decimal(20,7) UNSIGNED NOT NULL,
  `last_checked_time` timestamp(3) NOT NULL DEFAULT current_timestamp(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `message_hash` (`message_hash`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `sms_home_correlation_data`;

CREATE TABLE `sms_home_correlation_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `correlation_id` varchar(16) NOT NULL,
  `correlation_data` longblob NOT NULL,
  `created_time` timestamp(3) NOT NULL DEFAULT current_timestamp(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `correlation_id` (`correlation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `mo_digits_threshold_info`;

CREATE TABLE `mo_digits_threshold_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `digits` varchar(64) NOT NULL,
  `last_allowance` decimal(20,7) UNSIGNED NOT NULL,
  `last_checked_time` timestamp(3) NOT NULL DEFAULT current_timestamp(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `digits` (`digits`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `mt_digits_threshold_info`;

CREATE TABLE `mt_digits_threshold_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `digits` varchar(64) NOT NULL,
  `last_allowance` decimal(20,7) UNSIGNED NOT NULL,
  `last_checked_time` timestamp(3) NOT NULL DEFAULT current_timestamp(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `digits` (`digits`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_clean_traffic_data(
age INT /*time in seconds,threshold info record whose last_checked_time+age > NOW() will be deleted*/
)
BEGIN
 DELETE FROM mo_threshold_info where TIMESTAMPDIFF(SECOND,last_checked_time,NOW()) >= age;
 DELETE FROM mt_threshold_info where TIMESTAMPDIFF(SECOND,last_checked_time,NOW()) >= age;
 DELETE FROM mo_identical_threshold_info where TIMESTAMPDIFF(SECOND,last_checked_time,NOW()) >= age;
 DELETE FROM mt_identical_threshold_info where TIMESTAMPDIFF(SECOND,last_checked_time,NOW()) >= age;
 DELETE FROM sms_home_correlation_data where TIMESTAMPDIFF(SECOND,created_time,NOW()) >= age;
END;
//

DELIMITER ;

SET GLOBAL event_scheduler = ON;
DROP EVENT IF EXISTS event_clean_traffic_data;
CREATE EVENT event_clean_traffic_data ON SCHEDULE EVERY 600 SECOND DO CALL sp_clean_traffic_data(3600);

