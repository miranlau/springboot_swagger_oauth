DROP TABLE IF EXISTS `alarm`;

CREATE TABLE `alarm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(200) NOT NULL,
  `module` varchar(100) DEFAULT NULL,
  `instance_id` int(11) DEFAULT NULL,
  `auid` varchar(100) NOT NULL,
  `json` blob NOT NULL,
  `status` char(8) DEFAULT NULL,
  `severity` varchar(20) NOT NULL DEFAULT 'INFO',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `alarm_archive`;

CREATE TABLE `alarm_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `alarm_id` int(11) NOT NULL,
  `action` char(20) NOT NULL,
  `details` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

