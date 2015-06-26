/*
CREATE TABLE `navigation_link` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `title` char(50) NOT NULL,
    `href` varchar(800) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `index_image` (
    `id` int(11) primary key auto_increment,
    `path` varchar(800) not null,
    `row_index` int not null,
    `back_path` varchar(800) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table commodity (
    id int primary key auto_increment,
    name char(200) not null,
    price double not null,
    in_lottery boolean not null,
    own boolean not null,
    snapshot varchar(800) not null,
    detail_image varchar(800) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

/*create table commodity_map (
  id int primary key auto_increment,
  commodity_id int not null,
  x_start int not null,
  y_start int not null,
  x_end int not null,
  y_end int not null,
  href varchar(800) not null,
  comment char(200) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `index_image_map` (
    `id` int(11) primary key auto_increment,
    `image_id` int(11) not null,
    `x_start` int(11) not null,
    `y_start` int(11) not null,
    `x_end` int(11) not null,
    `y_end` int(11) not null,
    `href` varchar(800) not null,
    `comment` char(200)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/
