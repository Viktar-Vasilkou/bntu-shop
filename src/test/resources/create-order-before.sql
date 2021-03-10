delete from order_items;
delete from orders;
delete from categories;
delete from products;
delete from user_role;
delete from users;
delete from role;

insert into users(id, name, surname, login, password, phone_number, is_active) values
(1, 'Victor', 'Vasilkou', 'root', '$2a$10$AjngY5lA12PagnEWjjKqBezX6qu9lX./5CuKSVJaxbdVHu1JFUkxG', '291571601', true);

insert into role(id, role_name) values
(1, 'ADMIN');

insert into user_role(user_id, role_id) values
(1, 1);

insert into orders(id, user_id, date, total_price, status, address) values
(1, 1, '2020-12-11', 25, false, 'address'),
(2, 1, '2020-12-11', 50, true, 'address');

insert into categories(id, name) values
(1, 'Category');

insert into products(id, name, description, cost, amount, categories_id, status) values
(1, 'Test', 'Description', 25, 10, 1, true);

insert into order_items(id, product_id, amount, order_id) values
(1, 1, 1, 1),
(2, 1, 2, 2);