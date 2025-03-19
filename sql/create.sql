CREATE DATABASE `demo` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

-- demo.tbl_demo definition

CREATE TABLE `tbl_demo` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `name` varchar(100) NOT NULL,
                            `age` int(11) NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- demo.tbl_student definition

CREATE TABLE `tbl_student` (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `name` varchar(100) NOT NULL COMMENT '姓名',
                               `sex` tinyint(4) NOT NULL COMMENT '性别',
                               `birthday` datetime NOT NULL COMMENT '出生日期',
                               `address` varchar(200) NOT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4024 DEFAULT CHARSET=utf8mb4;