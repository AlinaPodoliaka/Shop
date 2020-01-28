CREATE SCHEMA `test` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `test`.`items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`item_id`));

INSERT INTO `test`.`items` (`name`, `price`) VALUES ('iPhone', '1000');
INSERT INTO `test`.`items` (`name`, `price`) VALUES ('Sumsung', '500');

CREATE TABLE `test`.`orders` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`order_id`));

CREATE TABLE `test`.`orders_items` (
  `idorders_items_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `item_id` INT NOT NULL,
  PRIMARY KEY (`idorders_items_id`),
  INDEX `orders_items_orders_fk_idx` (`order_id` ASC) VISIBLE,
  INDEX `orders_items_items_fk_idx` (`item_id` ASC) VISIBLE,
  CONSTRAINT `orders_items_orders_fk`
    FOREIGN KEY (`order_id`)
    REFERENCES `test`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `orders_items_items_fk`
    FOREIGN KEY (`item_id`)
    REFERENCES `test`.`items` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

    CREATE TABLE `test`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `token` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`));

  ALTER TABLE `test`.`orders`
ADD COLUMN `user_id` INT NOT NULL AFTER `order_id`,
ADD INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE;
;
ALTER TABLE `test`.`orders`
ADD CONSTRAINT `orders_users_fk`
  FOREIGN KEY (`user_id`)
  REFERENCES `test`.`users` (`user_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  ALTER TABLE `test`.`users`
CHANGE COLUMN `token` `token` VARCHAR(45) NULL ;

INSERT INTO test.users (name, surname, login, password)
VALUES ('Scarlet', 'Ohara', 'wind', '1');
INSERT INTO test.users (name, surname, login, password)
VALUES ('Ret', 'Butler', 'bad_guy', '2');

INSERT INTO test.orders (user_id) VALUES ('1');
INSERT INTO test.orders (user_id) VALUES ('1');
INSERT INTO test.orders (user_id) VALUES ('2');

INSERT INTO test.items (name, price) VALUES ('Huawai', '800');
INSERT INTO test.items (name, price) VALUES ('Lenovo', '250');
INSERT INTO test.items (name, price) VALUES ('LG', '150');
INSERT INTO test.items (name, price) VALUES ('Motorolla', '790');

INSERT INTO test.orders_items (order_id, item_id) VALUES ('1','1');
INSERT INTO test.orders_items (order_id, item_id) VALUES ('1','2');
INSERT INTO test.orders_items (order_id, item_id) VALUES ('2','4');
INSERT INTO test.orders_items (order_id, item_id) VALUES ('3','2');
INSERT INTO test.orders_items (order_id, item_id) VALUES ('3','5');

SELECT orders.order_id, orders.user_id, items.item_id, items.name, items.price
FROM orders
INNER JOIN orders_items
ON orders.order_id=orders_items.order_id
INNER JOIN items
ON orders_items.item_id=items.item_id
WHERE orders.user_id=1
ORDER BY orders.order_id;

CREATE TABLE `test`.`role` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`));

 INSERT INTO `test`.`role` (`role_name`) VALUES ('ADMIN');
INSERT INTO `test`.`role` (`role_name`) VALUES ('USER');

CREATE TABLE `test`.`user_roles` (
  `user_roles_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL DEFAULT 2,
  PRIMARY KEY (`user_roles_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `role_id_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `test`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `test`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `test`.`orders_items`
CHANGE COLUMN `idorders_items_id` `orders_items_id` INT NOT NULL AUTO_INCREMENT ;

CREATE TABLE `test`.`buckets` (
    `bucket_id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    PRIMARY KEY (`bucket_id`),
    INDEX `bucket_user_fk_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `bucket_user_fk`
        FOREIGN KEY (`user_id`)
        REFERENCES `test`.`users` (`user_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION);

CREATE TABLE `test`.`bucket_items` (
    `bucket_items_id` INT NOT NULL AUTO_INCREMENT,
    `bucket_id` INT NOT NULL,
    `item_id` INT NOT NULL,
    PRIMARY KEY (`bucket_items_id`),
    INDEX `bucket_items_bucket_fk_idx` (`bucket_id` ASC) VISIBLE,
    INDEX `bucket_items_item_fk_idx` (`item_id` ASC) VISIBLE,
    CONSTRAINT `bucket_items_bucket_fk`
        FOREIGN KEY (`bucket_id`)
        REFERENCES `test`.`buckets` (`bucket_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT `bucket_items_item_fk`
        FOREIGN KEY (`item_id`)
        REFERENCES `test`.`items` (`item_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION);






