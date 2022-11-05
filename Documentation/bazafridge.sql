-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema heroku_d1561a1a0615483
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema heroku_d1561a1a0615483
-- -----------------------------------------------------

USE `heroku_d1561a1a0615483` ;

-- -----------------------------------------------------
-- Table `heroku_d1561a1a0615483`.`recept`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heroku_d1561a1a0615483`.`recept` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(100) NOT NULL,
  `opis` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `heroku_d1561a1a0615483`.`mjerna_jedinica`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heroku_d1561a1a0615483`.`mjerna_jedinica` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `heroku_d1561a1a0615483`.`namirnica`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heroku_d1561a1a0615483`.`namirnica` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(100) NOT NULL,
  `kolicina_hladnjak` INT NOT NULL,
  `mjerna_jedinica_id` INT NOT NULL,
  `kolicina_kupovina` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_namirnica_mjerna_jedinica1_idx` (`mjerna_jedinica_id` ASC),
  CONSTRAINT `fk_namirnica_mjerna_jedinica1`
    FOREIGN KEY (`mjerna_jedinica_id`)
    REFERENCES `heroku_d1561a1a0615483`.`mjerna_jedinica` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `heroku_d1561a1a0615483`.`namirnica_recepta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heroku_d1561a1a0615483`.`namirnica_recepta` (
  `recept_id` INT NOT NULL,
  `namirnica_id` INT NOT NULL,
  `kolicina` INT NOT NULL,
  PRIMARY KEY (`recept_id`, `namirnica_id`),
  INDEX `fk_recept_has_namirnica_namirnica1_idx` (`namirnica_id` ASC),
  INDEX `fk_recept_has_namirnica_recept_idx` (`recept_id` ASC),
  CONSTRAINT `fk_recept_has_namirnica_recept`
    FOREIGN KEY (`recept_id`)
    REFERENCES `heroku_d1561a1a0615483`.`recept` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_recept_has_namirnica_namirnica1`
    FOREIGN KEY (`namirnica_id`)
    REFERENCES `heroku_d1561a1a0615483`.`namirnica` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
