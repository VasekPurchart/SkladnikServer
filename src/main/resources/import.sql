-- You can use this file to load seed data into the database using SQL statements
INSERT INTO `Person` (`id`, `name`, `phoneId`) VALUES (1, 'Technician', '');
INSERT INTO `Technician` (`id`) VALUES (1);

INSERT INTO `Person` (`id`, `name`, `phoneId`) VALUES (2, 'Auditor', '');
INSERT INTO `Auditor` (`id`) VALUES (2);

-- Warehouse
INSERT INTO `Warehouse` (`id`) VALUES ('1');

-- VendingMachine
INSERT INTO `VendingMachine` (`id`, `address`, `number`) VALUES (1, 'Vodičkova 12', 1);
INSERT INTO `VendingMachine` (`id`, `address`, `number`) VALUES (2, 'Pelzova 1514', 3);

-- ProductType
INSERT INTO `ProductType` (`id`, `barcode`, `name`) VALUES (1, "1234", "Tatrkanka");
INSERT INTO `ProductType` (`id`, `barcode`, `name`) VALUES (2, "555", "Fidorka");
INSERT INTO `ProductType` (`id`, `barcode`, `name`) VALUES (3, "999", "Delisa");