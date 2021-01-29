-- ---
-- Globals
-- ---

-- SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
-- SET FOREIGN_KEY_CHECKS=0;

-- ---
-- Table 'user'
-- 
-- ---

DROP TABLE IF EXISTS `user`;
        
CREATE TABLE `user` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `enabled` bit NOT NULL DEFAULT true,
  `document` VARCHAR(11) NOT NULL,
  `password` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`id`)
);

-- ---
-- Table 'role'
-- 
-- ---

DROP TABLE IF EXISTS `role`;
        
CREATE TABLE `role` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`)
);

-- ---
-- Table 'privilege'
-- 
-- ---

DROP TABLE IF EXISTS `privilege`;
        
CREATE TABLE `privilege` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`)
);

-- ---
-- Table 'roles_privileges'
-- 
-- ---

DROP TABLE IF EXISTS `roles_privileges`;
        
CREATE TABLE `roles_privileges` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `role_id` BIGINT NOT NULL,
  `privilege_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`id`)
);

-- ---
-- Table 'users_roles'
-- 
-- ---

DROP TABLE IF EXISTS `users_roles`;
        
CREATE TABLE `users_roles` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `role_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`id`)
);

-- ---
-- Table 'meeting'
-- 
-- ---

DROP TABLE IF EXISTS `meeting`;
        
CREATE TABLE `meeting` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `subject` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`id`)
);

-- ---
-- Table 'poll'
-- 
-- ---

DROP TABLE IF EXISTS `poll`;
        
CREATE TABLE `poll` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `open_date` DATETIME NOT NULL,
  `close_date` DATETIME NOT NULL,
  `enabled` bit NOT NULL DEFAULT true,
  `meeting_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`id`)
);

-- ---
-- Table 'vote'
-- 
-- ---

DROP TABLE IF EXISTS `vote`;
        
CREATE TABLE `vote` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `poll_id` BIGINT(11) NOT NULL,
  `vote_option_id` BIGINT(11) NOT NULL,
  `user_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`id`)
);

-- ---
-- Table 'vote_option'
-- 
-- ---

DROP TABLE IF EXISTS `vote_option`;
        
CREATE TABLE `vote_option` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `label` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`)
);

-- ---
-- Foreign Keys 
-- ---

ALTER TABLE `roles_privileges` ADD FOREIGN KEY (role_id) REFERENCES `role` (`id`);
ALTER TABLE `roles_privileges` ADD FOREIGN KEY (privilege_id) REFERENCES `privilege` (`id`);
ALTER TABLE `users_roles` ADD FOREIGN KEY (user_id) REFERENCES `user` (`id`);
ALTER TABLE `users_roles` ADD FOREIGN KEY (role_id) REFERENCES `role` (`id`);
ALTER TABLE `poll` ADD FOREIGN KEY (meeting_id) REFERENCES `meeting` (`id`);
ALTER TABLE `vote` ADD FOREIGN KEY (poll_id) REFERENCES `poll` (`id`);
ALTER TABLE `vote` ADD FOREIGN KEY (vote_option_id) REFERENCES `vote_option` (`id`);
ALTER TABLE `vote` ADD FOREIGN KEY (user_id) REFERENCES `user` (`id`);

-- ---
-- Table Properties
-- ---

ALTER TABLE `user` ADD CONSTRAINT USER_DOCUMENT_UNIQUE UNIQUE(DOCUMENT);

-- ALTER TABLE `user` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `role` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `privilege` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `roles_privileges` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `users_roles` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `meeting` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `poll` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `vote` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `vote_option` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ---
-- Test Data
-- ---

-- INSERT INTO `user` (`id`,`name`,`enabled`,`document`,`password`) VALUES
-- ('','','','','');
-- INSERT INTO `role` (`id`,`name`) VALUES
-- ('','');
-- INSERT INTO `privilege` (`id`,`name`) VALUES
-- ('','');
-- INSERT INTO `roles_privileges` (`id`,`role_id`,`privilege_id`) VALUES
-- ('','','');
-- INSERT INTO `users_roles` (`id`,`user_id`,`role_id`) VALUES
-- ('','','');
-- INSERT INTO `meeting` (`id`,`subject`) VALUES
-- ('','');
-- INSERT INTO `poll` (`id`,`open_date`,`close_date`,`enabled`,`meeting_id`) VALUES
-- ('','','','','');
-- INSERT INTO `vote` (`id`,`poll_id`,`vote_option_id`,`user_id`) VALUES
-- ('','','','');
-- INSERT INTO `vote_option` (`id`,`label`) VALUES
-- ('','');