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
  `poll_id` BIGINT(11) NULL,
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
ALTER TABLE `meeting` ADD FOREIGN KEY (poll_id) REFERENCES `poll` (`id`);
ALTER TABLE `vote` ADD FOREIGN KEY (poll_id) REFERENCES `poll` (`id`);
ALTER TABLE `vote` ADD FOREIGN KEY (vote_option_id) REFERENCES `vote_option` (`id`);
ALTER TABLE `vote` ADD FOREIGN KEY (user_id) REFERENCES `user` (`id`);

-- ---
-- Table Properties
-- ---

ALTER TABLE `user` ADD CONSTRAINT USER_DOCUMENT_UNIQUE UNIQUE(DOCUMENT);
ALTER TABLE `vote` ADD CONSTRAINT VOTE_USER_ID_POLL_ID_UNIQUE UNIQUE(USER_ID, POLL_ID);