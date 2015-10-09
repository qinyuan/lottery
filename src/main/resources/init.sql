
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
    detail_image varchar(800) not null,
    back_image varchar(800),
    visible boolean not null default true,
    ranking int unique not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `user` (
    `id` int(11) NOT NULL primary key AUTO_INCREMENT,
    `username` char(50) UNIQUE NOT NULL,
    `password` char(50) NOT NULL,
    `role` char(100) DEFAULT NULL,
    email char(200) unique,
    tel char(50),
    active boolean not null default false,
    liveness int,
    serial_key char(200) unique,
    spread_user_id int,
    spread_way char(50),
    real_name char(50),
    gender enum('男','女'),
    birthday date,
    constellation char(10),
    hometown char(200),
    residence char(200),
    lunar_birthday boolean,
    receive_mail boolean not null default true
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table virtual_user (
  id int primary key auto_increment,
  username char(50) unique not null,
  tel_prefix char(11) not null,
  tel_suffix int not null,
  mail_prefix char(10) not null,
  mail_suffix char(50) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table pre_user (
  id int primary key auto_increment,
  email char(200) not null,
  spread_user_id int,
  spread_way char(50),
  activity_id int,
  serial_key char(200) unique not null
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
  term int unique not null,
  commodity_id int not null,
  start_time datetime not null,
  expect_end_time datetime,
  close_time datetime not null,
  end_time datetime,
  continuous_serial_limit int,
  expire boolean not null,
  expect_participant_count int,
  announcement varchar(2000),
  virtual_participants int not null default 0,
  virtual_liveness int,
  virtual_liveness_users char(200),
  max_serial_number int not null default 0,
  min_serial_number int not null default 0,
  dual_colored_ball_term int not null,
  winners char(100),
  description varchar(2000),
  min_livness_to_participate int not null,
  closed boolean not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table lottery_lot (
  id int primary key auto_increment,
  activity_id int not null,
  user_id int not null,
  lot_time datetime not null,
  serial_number int not null,
  win boolean,
  unique(activity_id, user_id, serial_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table seckill_activity (
  id int primary key auto_increment,
  term int unique not null,
  commodity_id int not null,
  description varchar(2000),
  start_time datetime not null,
  expire boolean not null,
  winners char(100),
  announcement varchar(2000),
  expect_participant_count int
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table seckill_lot (
  id int primary key auto_increment,
  activity_id int not null,
  user_id int not null,
  lot_time datetime not null,
  win boolean,
  unique(activity_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table lottery_liveness (
  id int primary key auto_increment,
  activity_id int not null,
  spread_user_id int not null,
  receive_user_id int not null,
  liveness int not null,
  spread_way char(50) not null,
  register_before boolean not null,
  unique(activity_id, spread_user_id, receive_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table dual_colored_ball_record (
  id int primary key auto_increment,
  year int not null,
  term int not null,
  publish_date date not null,
  result char(100),
  unique(year, term)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table mail_send_record (
  id int primary key auto_increment,
  mail_account_id int not null,
  user_id int not null,
  mail_id int not null,
  send_time datetime not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table system_info (
  id int primary key auto_increment,
  build_time datetime not null,
  content text not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table system_info_send_record (
  id int primary key auto_increment,
  user_id int not null,
  info_id int not null,
  unread boolean not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



/************** test data *******************/
insert into user(username, password, serial_key) values('user1', 'password', 'abdafdipsuap');
insert into user(username, password, role, email) values('admin-user1', 'password', 'ROLE_ADMIN', '12345@qq.com');
insert into user(username, password, role, active) values('normal-user1', 'password', 'ROLE_NORMAL', true);
insert into user(username, password, role, active) values('normal-user2', 'password', 'ROLE_NORMAL', false);
insert into user(username, password, role, active, spread_user_id) values('normal-user3', 'password', 'ROLE_NORMAL', true, 2);

insert into virtual_user values(1, 'virtual_user1', 15, 00, 'ai', 'qq.com'), (2, 'virtual_user2', 15, 00, 'ai', 'qq.com');

insert into commodity values(1, 'name1', 11.0, true, true, 'snapshot1', 'detail_image1', 'back_image1', false, 10);
insert into commodity values(2, 'name2', 12.0, true, true, 'snapshot2', 'detail_image2', 'back_image2', true, 11);
insert into commodity values(3, 'name3', 13.0, true, true, 'snapshot3', 'detail_image3', 'back_image3', true, 14);

insert into lottery_activity(commodity_id, term, start_time, expire, virtual_participants, max_serial_number,
  dual_colored_ball_term, min_livness_to_participate, min_serial_number, close_time, closed) values
  (1, 1, '2015-01-01 12:12:12' , false, 1000, 10000, 2015081, 2, 10, '2015-01-02 12:12:12', false);
