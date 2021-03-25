SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
SHOW WARNINGS;
CREATE SCHEMA IF NOT EXISTS `schools` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `mydb` ;
USE `schools` ;

-- -----------------------------------------------------
-- Table `schools`.`subscription`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `schools`.`subscription` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `schools`.`subscription` (
  `subscriptionId` INT(11) NOT NULL AUTO_INCREMENT ,
  `subscriptionMode` VARCHAR(255) NOT NULL ,
  `subscribedFrom` DATE NOT NULL ,
  `subscribedTo` DATE NOT NULL ,
  PRIMARY KEY (`subscriptionId`) )
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;
CREATE UNIQUE INDEX `subscriptionId_UNIQUE` ON `schools`.`subscription` (`subscriptionId` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `schools`.`school`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `schools`.`school` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `schools`.`school` (
  `schoolId` INT(11) NOT NULL AUTO_INCREMENT ,
  `schoolName` VARCHAR(255) NOT NULL ,
  `domainForLogin` VARCHAR(255) NOT NULL ,
  `subscriptionId` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`schoolId`) ,
  CONSTRAINT `subscribed_plan`
    FOREIGN KEY (`subscriptionId` )
    REFERENCES `schools`.`subscription` (`subscriptionId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;
CREATE INDEX `subscribed_plan_idx` ON `schools`.`school` (`subscriptionId` ASC) ;

SHOW WARNINGS;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
