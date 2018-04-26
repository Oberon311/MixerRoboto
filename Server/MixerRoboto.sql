-- MySQL Script generated by MySQL Workbench
-- Thu Apr 26 16:08:50 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema BarDB
-- -----------------------------------------------------
-- Contains menu items, transactions, custom drinks, bar locations, etc

-- -----------------------------------------------------
-- Schema BarDB
--
-- Contains menu items, transactions, custom drinks, bar locations, etc
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `BarDB` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema UserDB
-- -----------------------------------------------------
-- Will contain all user information

-- -----------------------------------------------------
-- Schema UserDB
--
-- Will contain all user information
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `UserDB` ;
USE `BarDB` ;

-- -----------------------------------------------------
-- Table `UserDB`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UserDB`.`user` (
  `userID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `sex` ENUM('M', 'F', 'X') NOT NULL,
  `height` VARCHAR(45) NOT NULL,
  `weight` INT NOT NULL,
  `dob` DATE NOT NULL,
  `address` VARCHAR(60) NOT NULL,
  `adminCode` VARCHAR(45) NULL,
  `email` VARCHAR(100) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `expdate` DATE NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE INDEX `userID_UNIQUE` (`userID` ASC),
  UNIQUE INDEX `adminCode_UNIQUE` (`adminCode` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BarDB`.`Locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BarDB`.`Locations` (
  `ipAddress` VARCHAR(15) NOT NULL COMMENT 'IPv4',
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(60) NOT NULL,
  `phone` VARCHAR(11) NOT NULL,
  `adminID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ipAddress`),
  UNIQUE INDEX `ipAddress_UNIQUE` (`ipAddress` ASC),
  INDEX `adminID_idx` (`adminID` ASC),
  CONSTRAINT `adminID`
    FOREIGN KEY (`adminID`)
    REFERENCES `UserDB`.`user` (`userID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BarDB`.`Recipe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BarDB`.`Recipe` (
  `recipeID` INT UNSIGNED NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(13,2) UNSIGNED NOT NULL,
  `description` VARCHAR(150) NULL,
  PRIMARY KEY (`recipeID`),
  UNIQUE INDEX `recipeID_UNIQUE` (`recipeID` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BarDB`.`Transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BarDB`.`Transactions` (
  `transactionID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user` INT UNSIGNED NOT NULL,
  `date` DATE NOT NULL,
  `time` TIME NOT NULL,
  `price` DECIMAL(13,2) UNSIGNED NOT NULL COMMENT 'amount is in USD',
  `recipe` INT UNSIGNED NOT NULL,
  `ip` VARCHAR(45) NOT NULL,
  `drinkDone` TINYINT NOT NULL,
  PRIMARY KEY (`transactionID`),
  UNIQUE INDEX `transactionID_UNIQUE` (`transactionID` ASC),
  INDEX `ipAddress_idx` (`ip` ASC),
  INDEX `recipeID_idx` (`recipe` ASC),
  INDEX `userID_idx` (`user` ASC),
  CONSTRAINT `ipAddress`
    FOREIGN KEY (`ip`)
    REFERENCES `BarDB`.`Locations` (`ipAddress`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `recipeID`
    FOREIGN KEY (`recipe`)
    REFERENCES `BarDB`.`Recipe` (`recipeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `userID`
    FOREIGN KEY (`user`)
    REFERENCES `UserDB`.`user` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BarDB`.`Ingredients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BarDB`.`Ingredients` (
  `ingredientID` INT UNSIGNED NOT NULL,
  `name` VARCHAR(25) NOT NULL,
  `price` DECIMAL(13,2) UNSIGNED NOT NULL,
  `nozzle` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ingredientID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BarDB`.`RecipeIngredients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BarDB`.`RecipeIngredients` (
  `recID` INT UNSIGNED NOT NULL,
  `ingID` INT UNSIGNED NOT NULL,
  `amount` DECIMAL(5,2) UNSIGNED NOT NULL,
  `RIid` INT NOT NULL,
  INDEX `ingredientID_idx` (`ingID` ASC),
  PRIMARY KEY (`RIid`),
  UNIQUE INDEX `RIid_UNIQUE` (`RIid` ASC),
  INDEX `recID_idx` (`recID` ASC),
  CONSTRAINT `recID`
    FOREIGN KEY (`recID`)
    REFERENCES `BarDB`.`Recipe` (`recipeID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `ingID`
    FOREIGN KEY (`ingID`)
    REFERENCES `BarDB`.`Ingredients` (`ingredientID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BarDB`.`CustomRecipe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BarDB`.`CustomRecipe` (
  `CustomRecipeID` INT UNSIGNED NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(13,2) UNSIGNED NOT NULL,
  `description` VARCHAR(150) NULL,
  `orderCount` INT NULL,
  PRIMARY KEY (`CustomRecipeID`),
  UNIQUE INDEX `recipeID_UNIQUE` (`CustomRecipeID` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BarDB`.`CustomRecipeIngredients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BarDB`.`CustomRecipeIngredients` (
  `rID` INT UNSIGNED NOT NULL,
  `inID` INT UNSIGNED NOT NULL,
  `amount` DECIMAL(5,2) UNSIGNED NOT NULL,
  `CustomRIid` INT UNSIGNED NOT NULL,
  INDEX `recipeID_idx` (`rID` ASC),
  INDEX `ingredientID_idx` (`inID` ASC),
  PRIMARY KEY (`CustomRIid`),
  UNIQUE INDEX `CustomRIid_UNIQUE` (`CustomRIid` ASC),
  CONSTRAINT `rID`
    FOREIGN KEY (`rID`)
    REFERENCES `BarDB`.`Recipe` (`recipeID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `inID`
    FOREIGN KEY (`inID`)
    REFERENCES `BarDB`.`Ingredients` (`ingredientID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BarDB`.`HistoricalTransactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BarDB`.`HistoricalTransactions` (
  `HistoricalTransactionID` INT UNSIGNED NOT NULL,
  `user` INT UNSIGNED NOT NULL,
  `date` DATE NOT NULL,
  `time` TIME NOT NULL,
  `price` DECIMAL(13,2) UNSIGNED NOT NULL COMMENT 'amount is in USD',
  `recipeID` INT UNSIGNED NOT NULL,
  `ip` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`HistoricalTransactionID`),
  UNIQUE INDEX `transactionID_UNIQUE` (`HistoricalTransactionID` ASC),
  INDEX `ipAddress_idx` (`ip` ASC),
  INDEX `recipeID_idx` (`recipeID` ASC),
  INDEX `userID_idx` (`user` ASC),
  CONSTRAINT `ipAdd`
    FOREIGN KEY (`ip`)
    REFERENCES `BarDB`.`Locations` (`ipAddress`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `reciID`
    FOREIGN KEY (`recipeID`)
    REFERENCES `BarDB`.`Recipe` (`recipeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user`
    FOREIGN KEY (`user`)
    REFERENCES `UserDB`.`user` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `UserDB` ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
