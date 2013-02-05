-- You can use this file to load seed data into the database using SQL statements
INSERT INTO `Person` (`id`, `name`, `phoneId`) VALUES (1, 'Technician', '');
INSERT INTO `Technician` (`id`) VALUES (1);

INSERT INTO `Person` (`id`, `name`, `phoneId`) VALUES (2, 'Auditor', '');
INSERT INTO `Auditor` (`id`) VALUES (2);

-- Warehouse
INSERT INTO `Warehouse` (`id`) VALUES ('1');

-- VendingMachine
INSERT INTO `VendingMachine` (`id`, `adress`, `number`) VALUES (1, 'Vodičkova 12', 1);