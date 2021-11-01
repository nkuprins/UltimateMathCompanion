-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema calculator
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema calculator
-- -----------------------------------------------------

DROP SCHEMA IF EXISTS calculator;
CREATE SCHEMA `calculator` DEFAULT CHARACTER SET utf8 ;
USE `calculator` ;

-- -----------------------------------------------------
-- Table `calculator`.`complexities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `calculator`.`complexities` (
  `complexity_id` INT NOT NULL AUTO_INCREMENT,
  `complexity_name` VARCHAR(50) NOT NULL,
  `description` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`complexity_id`),
  UNIQUE INDEX `complexity_UNIQUE` (`complexity_name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `calculator`.`types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `calculator`.`types` (
  `type_id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(50) NOT NULL,
  `complexity` INT NOT NULL,
  PRIMARY KEY (`type_id`),
  UNIQUE INDEX `type_UNIQUE` (`type` ASC) VISIBLE,
  INDEX `fk_types_complexities1_idx` (`complexity` ASC) VISIBLE,
  CONSTRAINT `fk_types_complexities`
    FOREIGN KEY (`complexity`)
    REFERENCES `calculator`.`complexities` (`complexity_id`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `calculator`.`expressions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `calculator`.`expressions` (
  `expression_id` INT NOT NULL AUTO_INCREMENT,
  `expression` VARCHAR(255) NOT NULL,
  `answer` DECIMAL(50,0) NOT NULL,
  `date` DATE NOT NULL,
  `type` INT NOT NULL,
  PRIMARY KEY (`expression_id`),
  INDEX `fk_expressions_types1_idx` (`type` ASC) VISIBLE,
  CONSTRAINT `fk_expressions_types1`
    FOREIGN KEY (`type`)
    REFERENCES `calculator`.`types` (`type_id`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 39
DEFAULT CHARACTER SET = utf8;

USE `calculator` ;

-- -----------------------------------------------------
-- Placeholder table for view `calculator`.`expression_list`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `calculator`.`expression_list` (`expression_id` INT, `expression` INT, `answer` INT, `date` INT, `type` INT, `complexity_name` INT, `description` INT);

-- -----------------------------------------------------
-- procedure delete_expression
-- -----------------------------------------------------

DELIMITER $$
USE `calculator`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_expression`(
	expression_id INT
)
BEGIN
	DELETE e FROM expressions e
    WHERE e.expression_id = expression_id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_expression
-- -----------------------------------------------------

DELIMITER $$
USE `calculator`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_expression`(
	expression VARCHAR(255),
    answer DECIMAL(50),
    date DATE,
    type_id INT
)
BEGIN
	INSERT INTO expressions VALUES
    (DEFAULT, expression, answer, date, type_id);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure update_expression
-- -----------------------------------------------------

DELIMITER $$
USE `calculator`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_expression`(
	expression VARCHAR(250),
    answer DECIMAL(50),
    expression_id INT
)
BEGIN
	UPDATE expressions e
    SET e.expression = expression, e.answer = answer
    WHERE e.expression_id = expression_id;
END$$

DELIMITER ;

INSERT INTO expressions (expression, answer, date, type) 
VALUES 
("2 + 7 + 9", 18, "2020-08-04", 1),
("7 - 2", 5, "2020-08-04", 1),
("2 * 7", 14, "2020-08-04", 2),
("120 / 2", 80, "2020-08-04", 2),
("70 / 115 + 138 - 104 - 164 + 183", 53.6087, "2020-08-04", 3),
("175 * 106 / 52 / 49 * 27 - 76", 120.5654, "2020-08-04", 3),
("27 + 13 / 131 * 173 / 48 + 144 - 158", 13.3575, "2020-08-04", 3),
("60 * 75 * 10 * 135 / 93 - 185 + 33", 65170.5806, "2020-08-04", 3),
("133 - 149 - 175 + 94 - 184 - 167", -448, "2020-08-04", 3),
("17 * 8 - 189 - 197 - 79 + 9 / 162", -328.9444, "2020-08-04", 3);

ALTER TABLE types AUTO_INCREMENT = 1;

INSERT INTO types (type, complexity)
VALUES
("+-", 1),
("/*", 2),
("+-/*", 3),
("++--//**", 4);

ALTER TABLE complexities AUTO_INCREMENT = 1;

INSERT INTO complexities (complexity_name, description)
VALUES
("Easy", "Only plus and/or minus"),
("Medium", "Only division and/or multiplication"),
("Hard", "All or almost all math signs"),
("Very Hard", "Too long and all or almost all math signs");

-- -----------------------------------------------------
-- View `calculator`.`expression_list`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calculator`.`expression_list`;
USE `calculator`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `calculator`.`expression_list` AS select `calculator`.`expressions`.`expression_id` AS `expression_id`,`calculator`.`expressions`.`expression` AS `expression`,`calculator`.`expressions`.`answer` AS `answer`,`calculator`.`expressions`.`date` AS `date`,`calculator`.`types`.`type` AS `type`,`calculator`.`complexities`.`complexity_name` AS `complexity_name`,`calculator`.`complexities`.`description` AS `description` from ((`calculator`.`expressions` join `calculator`.`types` on((`calculator`.`types`.`type_id` = `calculator`.`expressions`.`type`))) join `calculator`.`complexities` on((`calculator`.`types`.`complexity` = `calculator`.`complexities`.`complexity_id`))) order by `calculator`.`expressions`.`expression_id`;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


