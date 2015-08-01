
CREATE TABLE `app_config` (
    `id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `property_name` char(50) UNIQUE NOT NULL,
    `property_value` varchar(2000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
