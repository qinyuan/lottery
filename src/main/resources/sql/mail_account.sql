
create table mail_account(
  id int primary key auto_increment,
  host char(200) not null,
  username char(100) unique not null,
  password char(100) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
