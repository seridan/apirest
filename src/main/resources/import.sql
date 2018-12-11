INSERT INTO `restapi`.`users` (`user_name`, `password`) VALUES ('admin','$2a$10$F1bLCTvd1veRvFxsI3A6getQYQ7XZbF/lSxHrXAbduqo1JqIN3QqK');
INSERT INTO `restapi`.`users` (`user_name`, `password`) VALUES ('sergio','$2a$10$vW6g64HX.NzaaHsFR8e37uImR1JyQqwoSoKdv0ieu9BkHMeY.mQaK');

INSERT INTO `restapi`.`roles` (`role_name`, `user_id`) VALUES ('ROLE_ADMIN','1');
INSERT INTO `restapi`.`roles` (`role_name`, `user_id`) VALUES ('ROLE_STANDARD','1');
INSERT INTO `restapi`.`roles` (`role_name`, `user_id`) VALUES ('ROLE_STANDARD','2');