
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
alter table user add column liveness int;

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

create table lottery_activity(
  id int primary key auto_increment,
  commodity_id int not null,
  start_time datetime not null,
  expect_end_time datetime,
  end_time datetime,
  continuous_serial_limit int,
  expire boolean not null,
  expect_participant_count int,
  announcement varchar(2000),
  virtual_participants int not null default 0,
  virtual_liveness int,
  virtual_liveness_users char(200),
  max_serial_number int not null default 0;
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table lottery_lot (
  id int primary key auto_increment,
  activity_id int not null,
  user_id int not null,
  lot_time datetime not null,
  serial_number int not null,
  win boolean
  unique(activity_id, user_id, serial_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
