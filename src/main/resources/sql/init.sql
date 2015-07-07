
CREATE TABLE `navigation_link` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `title` char(50) NOT NULL,
    `href` varchar(800) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `index_image` (
    `id` int(11) primary key auto_increment,
    `path` varchar(800) not null,
    `row_index` int not null,
    `back_path` varchar(800) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table commodity (
    id int primary key auto_increment,
    name char(200) not null,
    price double not null,
    in_lottery boolean not null,
    own boolean not null,
    snapshot varchar(800) not null,
    detail_image varchar(800) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

alter table user add column email char(200) unique;
alter table user add column tel char(50);
alter table user add column active boolean not null default false;

create table activate_request (
  id int primary key auto_increment,
  user_id int not null,
  serial_key char(200) unique not null,
  send_time datetime not null,
  response_time datetime
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table help_group (
  id int primary key auto_increment,
  title char(100) not null,
  ranking int unique not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table help_item (
  id int primary key auto_increment,
  group_id int not null,
  icon char(200),
  title char(200) not null,
  content text not null,
  ranking int unique not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
