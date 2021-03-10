delete from user_role;
delete from users;
delete from role;

insert into users(id, name, surname, login, password, phone_number, is_active) values
(1, 'Victor', 'Vasilkou', 'root', '$2a$10$AjngY5lA12PagnEWjjKqBezX6qu9lX./5CuKSVJaxbdVHu1JFUkxG', '291571601', true);

insert into role(id, role_name) values
(1, 'ADMIN');

insert into user_role(user_id, role_id) values
(1, 1)