INSERT INTO `restapi`.`users` (`user_name`, `password`) VALUES ('admin','$2a$10$F1bLCTvd1veRvFxsI3A6getQYQ7XZbF/lSxHrXAbduqo1JqIN3QqK');

INSERT INTO `restapi`.`roles` (`role_name`, `role_id`) VALUES ('ROLE_ADMIN',2);
INSERT INTO `restapi`.`roles` (`role_name`, `role_id`) VALUES ('ROLE_STANDARD',1);

INSERT INTO `restapi`.`user_role` (`user_id`, `role_id`) VALUES (1,1);
INSERT INTO `restapi`.`user_role` (`user_id`, `role_id`) VALUES (1,2);