
CREATE TABLE `login_record` (
    `id` int(11) primary key AUTO_INCREMENT,
    `user_id` int not null,
    `login_time`  datetime not null,
    `ip` char(50) not null,
    `location` char(100) not null,
    `platform` char(20) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
