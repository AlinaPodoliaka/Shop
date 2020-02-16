CREATE SCHEMA `internetshop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internetshop`.`items` (
    `item_id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `price` DECIMAL(12,2) NOT NULL,
    PRIMARY KEY (`item_id`));

CREATE TABLE `internetshop`.`orders` (
    `order_id` INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`order_id`));

CREATE TABLE `internetshop`.`order_items` (
    `order_items_id` INT NOT NULL AUTO_INCREMENT,
    `order_id` INT NOT NULL,
    `item_id` INT NOT NULL,
    PRIMARY KEY (`order_items_id`),
    INDEX `order_items_order_fk_idx` (`order_id` ASC) VISIBLE,
    INDEX `order_items_item_fk_idx` (`item_id` ASC) VISIBLE,
    CONSTRAINT `order_items_order_fk`
        FOREIGN KEY (`order_id`)
        REFERENCES `internetshop`.`orders` (`order_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT `order_items_item_fk`
        FOREIGN KEY (`item_id`)
        REFERENCES `internetshop`.`items` (`item_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION);

CREATE TABLE `internetshop`.`users` (
    `user_id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NOT NULL,
    `password` VARCHAR(128) NOT NULL,
    `first_name` VARCHAR(45) NULL,
    `second_name` VARCHAR(45) NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE);

ALTER TABLE `internetshop`.`users`
    ADD COLUMN `salt` BLOB NOT NULL AFTER `second_name`;

CREATE TABLE `internetshop`.`roles` (
    `role_id` INT NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`role_id`));

CREATE TABLE `internetshop`.`user_roles` (
    `user_role_id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `role_id` INT NOT NULL,
    PRIMARY KEY (`user_role_id`),
    INDEX `user_roles_user_fk_idx` (`user_id` ASC) VISIBLE,
    INDEX `user_roles_role_fk_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `user_roles_user_fk`
        FOREIGN KEY (`user_id`)
        REFERENCES `internetshop`.`users` (`user_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT `user_roles_role_fk`
        FOREIGN KEY (`role_id`)
        REFERENCES `internetshop`.`roles` (`role_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION);

ALTER TABLE `internetshop`.`orders`
    ADD COLUMN `user_id` INT NOT NULL AFTER `order_id`,
    ADD INDEX `orders_user_fk_idx` (`user_id` ASC) VISIBLE;

ALTER TABLE `internetshop`.`orders`
    ADD CONSTRAINT `orders_user_fk`
        FOREIGN KEY (`user_id`)
        REFERENCES `internetshop`.`users` (`user_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION;

CREATE TABLE `internetshop`.`buckets` (
    `bucket_id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    PRIMARY KEY (`bucket_id`),
    INDEX `bucket_user_fk_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `bucket_user_fk`
        FOREIGN KEY (`user_id`)
        REFERENCES `internetshop`.`users` (`user_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION);

CREATE TABLE `internetshop`.`bucket_items` (
    `bucket_items_id` INT NOT NULL AUTO_INCREMENT,
    `bucket_id` INT NOT NULL,
    `item_id` INT NOT NULL,
    PRIMARY KEY (`bucket_items_id`),
    INDEX `bucket_items_bucket_fk_idx` (`bucket_id` ASC) VISIBLE,
    INDEX `bucket_items_item_fk_idx` (`item_id` ASC) VISIBLE,
    CONSTRAINT `bucket_items_bucket_fk`
        FOREIGN KEY (`bucket_id`)
        REFERENCES `internetshop`.`buckets` (`bucket_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT `bucket_items_item_fk`
        FOREIGN KEY (`item_id`)
        REFERENCES `internetshop`.`items` (`item_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION);

INSERT INTO `internetshop`.`roles` (`role_name`) VALUES ('USER');
INSERT INTO `internetshop`.`roles` (`role_name`) VALUES ('ADMIN');
