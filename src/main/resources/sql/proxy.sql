
CREATE TABLE `proxy` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `host` char(50) NOT NULL,
    `port` int(11) NOT NULL,
    `type` char(20) NOT NULL,
    `speed` int(11) NOT NULL,
    `speed_update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `host` (`host`,`port`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `proxy_rejection` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `proxy_id` int(11) NOT NULL,
    `host` char(200) NOT NULL,
    `url` char(200) NOT NULL,
    `reject_time` datetime NOT NULL,
    `speed` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `proxy_id` (`proxy_id`,`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
