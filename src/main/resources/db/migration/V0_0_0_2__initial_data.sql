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

INSERT INTO `privilege` (`id`,`name`) VALUES (1 ,'Access all System');
INSERT INTO `role` (`id`,`name`) VALUES (1, 'Basic User');
INSERT INTO `roles_privileges` (`role_id`,`privilege_id`) VALUES (1, 1);