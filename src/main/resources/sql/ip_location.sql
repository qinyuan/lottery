
CREATE TABLE `ip_location` (
    `id` int(11) primary key AUTO_INCREMENT,
    `ip` char(50) NOT NULL unique,
    `location` char(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

