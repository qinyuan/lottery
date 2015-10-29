
create table mail_serial_key (
  id int primary key auto_increment,
  user_id int not null,
  serial_key char(200) unique not null,
  send_time datetime not null,
  response_time datetime,
  mail_type char(50) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;