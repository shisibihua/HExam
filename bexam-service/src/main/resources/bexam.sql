CREATE DATABASE IF NOT EXISTS bexam DEFAULT  CHARACTER SET utf8 COLLATE utf8_general_ci;
USE bexam;
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for nodebook  教材章节表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_nodebook` (
  `node_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '教材的章节id',
  `node_name` varchar(50) DEFAULT NULL COMMENT '章节名称',
  `parent_node_id` int(11) NOT NULL COMMENT '父级章节id',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`node_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for point  知识点表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_point` (
  `point_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '知识点id',
  `point_name` varchar(50) DEFAULT NULL COMMENT '科目id',
  `parent_point_id` int(11) DEFAULT NULL COMMENT '上级知识点id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`point_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for subject  学科表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_subject` (
  `subject_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '科目id',
  `subject_name` varchar(50) DEFAULT NULL COMMENT '科目名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`subject_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for textbook  教材册别表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_textbook` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '教材册别id',
  `book_name` varchar(50) DEFAULT NULL COMMENT '教材册别名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`book_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for version  教材版本表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_version` (
  `version_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '教材版本id',
  `version_name` varchar(100) DEFAULT NULL COMMENT '教材版本名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`version_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for subject2point  学科-知识点关系表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_subject2point` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学科-知识点关系id',
  `subject_id` int(11) DEFAULT NULL COMMENT '学科id',
  `point_id` int(11) DEFAULT NULL COMMENT '知识点id',
  PRIMARY KEY (`id`),
  KEY `subject_id` (`subject_id`),
  KEY `point_id` (`point_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for subject2version 学科-教材版本关系表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_subject2version` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '教材版本-学科关系id',
  `subject_id` int(11) DEFAULT NULL COMMENT '学科id',
  `version_id` int(11) DEFAULT NULL COMMENT '教材版本id',
  PRIMARY KEY (`id`),
  KEY `subject_id` (`subject_id`),
  KEY `version_id` (`version_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for textbook2nodebook  教材册别-教材章节关系表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_textbook2nodebook` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '教材册别-教材章节关系id',
  `book_id` int(11) DEFAULT NULL COMMENT '教材册别id',
  `node_id` int(11) DEFAULT NULL COMMENT '教材章节id',
  PRIMARY KEY (`id`),
  KEY `book_id` (`book_id`),
  KEY `node_id` (`node_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for version2textbook 教材版本-教材册别关系表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_version2textbook` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '教材版本-教材册别关系id',
  `version_id` int(11) DEFAULT NULL COMMENT '教材版本id',
  `book_id` int(11) DEFAULT NULL COMMENT '教材册别id',
  PRIMARY KEY (`id`),
  KEY `version_id` (`version_id`),
  KEY `book_id` (`book_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for exam_period
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_period` (
  `period_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学段id',
  `period_name` varchar(50) DEFAULT NULL COMMENT '学段名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`period_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for exam_class  班级表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_class` (
  `class_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '班级id',
  `class_name` varchar(50) DEFAULT NULL COMMENT '班级名称',
  `grade_id` int(11) DEFAULT NULL COMMENT '年级id',
  `period_id` int(11) DEFAULT NULL COMMENT '学段id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`class_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for exam_grade  年级表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_grade` (
  `grade_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '年级id',
  `grade_name` varchar(50) DEFAULT NULL COMMENT '年级名称',
  `period_id` int(11) DEFAULT NULL COMMENT '学段id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`grade_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for exam_user 用户表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_realname` varchar(50) DEFAULT NULL COMMENT '用户真实名称',
  `user_pwd` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `user_mobile` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `user_age` int(5) DEFAULT NULL COMMENT '年龄',
  `user_gender` int(5) DEFAULT '0' COMMENT '性别，0：男，1：女',
  `user_status` int(5) DEFAULT NULL COMMENT '用户状态，0：未删除，1：已删除',
  `user_type` int(5) DEFAULT NULL COMMENT '用户类型',
  `user_headpath` varchar(50) DEFAULT NULL COMMENT '头像url地址',
  `user_token` varchar(50) DEFAULT NULL COMMENT '用户token',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `subject_id` int(11) DEFAULT NULL COMMENT '科目id',
  `period_id` int(11) DEFAULT NULL COMMENT '学段id',
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for exam_class2user   班级与用户关系表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_class2user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '班级与用户的关系',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `class_id` int(11) DEFAULT NULL COMMENT '班级id',
  PRIMARY KEY (`id`),
  KEY `class_id` (`class_id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for exam_user_student  学生表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_user_student` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学生id',
  `student_realname` varchar(50) DEFAULT NULL COMMENT '学生真实名称',
  `student_code` varchar(50) DEFAULT NULL COMMENT '学生编号',
  `student_gender` int(5) DEFAULT NULL COMMENT '性别,0:男，1：女',
  `student_age` int(5) DEFAULT NULL COMMENT '年龄',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`student_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for exam_user_type  用户类型表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_user_type` (
  `type_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '用户类型id',
  `type_name` varchar(50) DEFAULT NULL COMMENT '类型名称',
  PRIMARY KEY (`type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `exam_user_type` VALUES ('1', '超级管理员');
INSERT INTO `exam_user_type` VALUES ('2', '管理员');
INSERT INTO `exam_user_type` VALUES ('3', '老师');

-- ----------------------------
-- Table structure for exam_class2student  班级与学生关系表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_class2student` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '班级与学生关系表',
  `class_id` int(11) DEFAULT NULL COMMENT '班级id',
  `student_id` int(11) DEFAULT NULL COMMENT '学生id',
  PRIMARY KEY (`id`),
  KEY `class_id` (`class_id`),
  KEY `student_id` (`student_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for exam_subject2questiontype  学科与试题类型表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_subject2questiontype` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学科与试题类型关系id',
  `subject_id` int(11) DEFAULT NULL COMMENT '科目id',
  `type_id` int(11) DEFAULT NULL COMMENT '试题类型id',
 PRIMARY KEY (`id`),
  KEY `subject_id` (`subject_id`),
  KEY `type_id` (`type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for exam_question_difficult   试题难度表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_question_difficult`  (
  `difficult_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `difficult_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`difficult_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `exam_question_difficult` VALUES ('1', '容易', '2019-01-02 20:00:00','2019-01-02 20:00:00');
INSERT INTO `exam_question_difficult` VALUES ('2', '较易', '2019-01-02 20:00:00','2019-01-02 20:00:00');
INSERT INTO `exam_question_difficult` VALUES ('3', '一般', '2019-01-02 20:00:00','2019-01-02 20:00:00');
INSERT INTO `exam_question_difficult` VALUES ('4', '较难', '2019-01-02 20:00:00','2019-01-02 20:00:00');
INSERT INTO `exam_question_difficult` VALUES ('5', '困难', '2019-01-02 20:00:00','2019-01-02 20:00:00');

-- ----------------------------
-- Table structure for exam_question_type  试题类型
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_question_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '试题类型id',
  `type_name` varchar(50) DEFAULT NULL COMMENT '试题类型名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for exam_question   试题表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_question`  (
  `question_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '试题ID',
  `subject_id` int(11) UNSIGNED NOT NULL COMMENT '科目ID',
  `question_type_id` int(5) UNSIGNED NULL DEFAULT NULL COMMENT '题型',
  `question_difficult` int(5) UNSIGNED NULL DEFAULT NULL COMMENT '难度',
  `question_comment` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '评价',
  `usage_count` int(11) UNSIGNED NULL DEFAULT 0 COMMENT '组卷次数',
  `question_point_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '一级知识点ID',
  `question_point2_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '二级知识点ID',
  `question_point3_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '三级知识点ID',
  `version_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '教材版本ID',
  `book_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '教材册别ID',
  `node_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '一级章节ID',
  `node2_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '二级章节ID',
  `node3_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '三级章节ID',
  `question_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源',
  `question_storage` int(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储试题来源，0：网络拉取，1：本地添加',
  `question_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '题干',
  `question_option` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '选项',
  `question_answer` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '答案',
  `question_analysis` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '解析',
  `question_public` int(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL  COMMENT '是否公开，0:不公开,1:公开',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`question_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for exam_paper_type  试卷类型表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_paper_type`  (
  `type_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '试卷类型id',
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试卷类型名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for exam_user2question  老师与试题的关系表(收藏试题/加入试题篮)
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_user2question` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '老师与试题的关系id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `question_id` int(11) DEFAULT NULL COMMENT '试题id',
  `type` int(5) DEFAULT NULL COMMENT '存储类型，0：收藏；1：试题篮',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `question_id` (`question_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for exam_paper   试卷表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_paper` (
  `paper_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '试卷id',
  `paper_title` varchar(150) DEFAULT NULL COMMENT '标题',
  `paper_sub_title` varchar(150) DEFAULT NULL COMMENT '副标题',
  `type_id` int(11) DEFAULT NULL COMMENT '试卷类型id',
  `subject_id` int(11) DEFAULT NULL COMMENT '科目id',
  `paper_time` varchar(5) DEFAULT NULL COMMENT '考试时长',
  `user_id` int(11) DEFAULT NULL COMMENT '出卷人id',
  `paper_extend` varchar(255) DEFAULT NULL COMMENT '试卷备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`paper_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for exam_paper_content  试卷试题表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_paper_content` (
  `paper_content_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '试题内容id',
  `content_type` int(11) DEFAULT NULL COMMENT '试卷内容类型，0：标题，1：试题',
  `score` int(11) DEFAULT NULL COMMENT '小题分数',
  `total_score` int(11) DEFAULT NULL COMMENT '总分数',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级试卷内容id',
  `question_id` int(11) DEFAULT NULL COMMENT '内容类型为0（标题）时为题型ID,内容类型为1（试题）时为试题ID',
  `paper_id` int(11) DEFAULT NULL COMMENT '试卷id',
  `height` int(11) DEFAULT NULL COMMENT '试题高度',
  PRIMARY KEY (`paper_content_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for exam_paper_score  学生得分表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_paper_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `paper_id` int(11) DEFAULT NULL COMMENT '试卷id',
  `student_id` int(11) DEFAULT NULL COMMENT '学生id',
  `paper_content_id` int(11) DEFAULT NULL COMMENT '试卷内容id',
  `score` int(11) DEFAULT NULL COMMENT '得分',
  `answer` text COMMENT '学生的答案',
  `class_id` int(10) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `paper_id` (`paper_id`),
  KEY `student_id` (`student_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for exam_score_student 学生得分排名
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_score_student`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `paper_id` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '试卷ID',
  `class_id` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '班级ID',
  `student_id` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '学生ID',
  `level` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '成绩段 1（0-20%）、2（20%-40%）、3（40%-60%）、4（60%-80%）、5（80%-100%）',
  `score` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '试卷得分',
  `ranking` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '排名',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Table structure for exam_score_class 班级得分
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_score_class`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `paper_id` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '试卷ID',
  `class_id` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '班级ID',
  `student_count` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '考生数量',
  `total_score` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '总分',
  `average_score` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '平均分',
  `highest_score` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '最高分',
  `lowest_score` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '最低分',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;


