delete from products;
delete from categories;

insert into categories(id, name) values
(1, 'Category');

insert into products(id, name, description, cost, amount, categories_id, status) values
(1, 'Test', 'Description', 25, 10, 1, true);

