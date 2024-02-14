SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `thiscode_db` DEFAULT CHARACTER SET utf8 ;
USE `thiscode_db` ;

-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thiscode_db`.`user` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(45) NULL,
    `password` CHAR(68) NULL,
    `nickname` VARCHAR(15) NULL,
    `user_code` VARCHAR(10) NULL,
    `introduction` VARCHAR(255) NULL,
    `created_at` DATETIME NULL,
    `modified_at` DATETIME NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `user_code_UNIQUE` (`user_code` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`friend`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thiscode_db`.`friend` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `from_user_id` INT NOT NULL,
    `to_user_id` INT NOT NULL,
    `state` VARCHAR(20) NOT NULL,
    `created_at` DATETIME NULL,
    `modified_at` DATETIME NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_friend_user_idx` (`from_user_id` ASC) VISIBLE,
    INDEX `fk_friend_user1_idx` (`to_user_id` ASC) VISIBLE)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `thiscode_db`.`profile`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thiscode_db`.`profile` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `fcm_token` VARCHAR(255) NULL,
    `last_access_at` DATETIME NULL,
    `created_at` DATETIME NULL,
    `modified_at` DATETIME NULL,
    PRIMARY KEY (`id`),
    INDEX `profile_user1_idx` (`user_id` ASC) VISIBLE)
    ENGINE = InnoDB;
-- it might be better to add Foreign Key constraints about user table

-- -----------------------------------------------------
-- Table `mydb`.`blocked_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thiscode_db`.`blocked_user` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `blocked_user_id` INT NOT NULL,
    `created_at` DATETIME NULL,
    `modified_at` DATETIME NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_blocked_user_user1_idx` (`user_id` ASC) VISIBLE,
    INDEX `fk_blocked_user_user2_idx` (`blocked_user_id` ASC) VISIBLE)
    ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `thiscode_db`.`room` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_count` INT NULL,
    `type` VARCHAR(10) NULL,
    `name` VARCHAR(45) NULL,
    `created_at` DATETIME NULL,
    `modified_at` DATETIME NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `thiscode_db`.`room_user` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `room_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `state` VARCHAR(10) NULL,
    `joined_at` DATETIME NULL,
    `last_read_at` DATETIME NULL,
    `created_at` DATETIME NULL,
    `modified_at` DATETIME NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_user_room_user1_idx` (`user_id` ASC) VISIBLE,
    INDEX `fk_user_room_room1_idx` (`room_id` ASC) VISIBLE
    )ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
