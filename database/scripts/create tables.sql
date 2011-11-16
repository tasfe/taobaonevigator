
use askleave;
CREATE TABLE `catagory`  
(
  `id` int(11) auto_increment NOT NULL,
  `name` varchar(45) NOT NULL,
  `parent_catagory` int(11),
  `remark` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `catagory_id` int(11) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(120) DEFAULT NULL,
  `price` double NOT NULL,
  `picture` varchar(120) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

CREATE TABLE `promotion` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(120) DEFAULT NULL,
  `picture` varchar(120) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `catagory_id` int(11) DEFAULT NULL,
  `enabled` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

CREATE TABLE `click_count` (
  `id` int(11) NOT NULL DEFAULT '0',
  `product_id` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `first_click_date` date DEFAULT NULL,
  `last_click_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;



