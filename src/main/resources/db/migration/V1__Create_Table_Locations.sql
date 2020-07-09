CREATE TABLE IF NOT EXISTS `locations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `street` varchar(150) NOT NULL,
  `number` bigint NOT NULL,
  `complement` varchar(80),
  `neighbourhood` varchar(80) NOT NULL,
  `city` varchar(80) NOT NULL,
  `state` varchar(80) NOT NULL,
  `country` varchar(80) NOT NULL,
  `zipcode` varchar(8) NOT NULL,
  `latitude` varchar(20),
  `longitude` varchar(20),
  PRIMARY KEY (`id`)
);