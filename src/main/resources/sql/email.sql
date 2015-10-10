
CREATE TABLE `email` (
    `id` int(11) primary key AUTO_INCREMENT,
    `subject` varchar(1000) NOT NULL,
    `content` text not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
