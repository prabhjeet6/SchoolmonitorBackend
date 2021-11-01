SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
SHOW WARNINGS;
CREATE SCHEMA IF NOT EXISTS `schoolmonitor` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `mydb` ;
USE `schoolmonitor` ;

-- -----------------------------------------------------
-- Table `schoolmonitor`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `schoolmonitor`.`address` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `schoolmonitor`.`address` (
  `addressId` INT(11) NOT NULL AUTO_INCREMENT ,
  `landmark` VARCHAR(255) NULL DEFAULT NULL ,
  `city` VARCHAR(255) NULL DEFAULT NULL ,
  `pincode` VARCHAR(255) NULL DEFAULT NULL ,
  `linkedStudentId` VARCHAR(45) NULL DEFAULT NULL ,
  `linkedTeacherId` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`addressId`) )
ENGINE = InnoDB
AUTO_INCREMENT = 32
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;
CREATE UNIQUE INDEX `linkedStudentId_UNIQUE` ON `schoolmonitor`.`address` (`linkedStudentId` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `linkedTeacherId_UNIQUE` ON `schoolmonitor`.`address` (`linkedTeacherId` ASC) ;

SHOW WARNINGS;
CREATE INDEX `LinkedStudentId_idx` ON `schoolmonitor`.`address` (`linkedStudentId` ASC) ;

SHOW WARNINGS;
CREATE INDEX `LinkedTeacherId_idx` ON `schoolmonitor`.`address` (`linkedTeacherId` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `schoolmonitor`.`schoolspecifics`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `schoolmonitor`.`schoolspecifics` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `schoolmonitor`.`schoolspecifics` (
  `schoolSpecificsId` INT(11) NOT NULL AUTO_INCREMENT ,
  `branchName` VARCHAR(255) NULL DEFAULT NULL ,
  `district` VARCHAR(255) NOT NULL ,
  `schoolAddress` VARCHAR(255) NOT NULL ,
  `pincode` VARCHAR(255) NOT NULL ,
  `schoolEmailId` VARCHAR(255) NULL DEFAULT NULL ,
  `schoolContactNumber` INT(11) NOT NULL ,
  PRIMARY KEY (`schoolSpecificsId`) )
ENGINE = InnoDB
AUTO_INCREMENT = 73
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `schoolmonitor`.`student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `schoolmonitor`.`student` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `schoolmonitor`.`student` (
  `studentId` VARCHAR(255) NOT NULL ,
  `firstName` VARCHAR(255) NOT NULL ,
  `lastName` VARCHAR(255) NOT NULL ,
  `schoolId` INT(11) NOT NULL ,
  `stream` VARCHAR(255) NULL DEFAULT NULL ,
  `fatherName` VARCHAR(255) NOT NULL ,
  `motherName` VARCHAR(255) NOT NULL ,
  `bloodGroup` VARCHAR(4) NOT NULL ,
  `contactNumber` INT(11) NULL DEFAULT NULL ,
  `linkedAddressId` INT(11) NOT NULL ,
  `dateOfBirth` DATE NOT NULL ,
  `schoolSpecificsId` INT(11) NOT NULL ,
  `classRollnumberSectionInformation` VARCHAR(255) NULL DEFAULT NULL ,
  `gender` VARCHAR(1) NOT NULL ,
  PRIMARY KEY (`studentId`) ,
  CONSTRAINT `LinkedSchoolspecificsId`
    FOREIGN KEY (`schoolSpecificsId` )
    REFERENCES `schoolmonitor`.`schoolspecifics` (`schoolSpecificsId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `student_ibfk_1`
    FOREIGN KEY (`linkedAddressId` )
    REFERENCES `schoolmonitor`.`address` (`addressId` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;
CREATE UNIQUE INDEX `linkedAddressId_UNIQUE` ON `schoolmonitor`.`student` (`linkedAddressId` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `classRoleSectionInformation_UNIQUE` ON `schoolmonitor`.`student` (`classRollnumberSectionInformation` ASC) ;

SHOW WARNINGS;
CREATE INDEX `LinkedSchoolspecificsId_idx` ON `schoolmonitor`.`student` (`schoolSpecificsId` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `schoolmonitor`.`teacher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `schoolmonitor`.`teacher` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `schoolmonitor`.`teacher` (
  `teacherId` VARCHAR(255) NOT NULL ,
  `firstName` VARCHAR(255) NOT NULL ,
  `lastName` VARCHAR(255) NOT NULL ,
  `bloodGroup` VARCHAR(255) NOT NULL ,
  `schoolSpecificsId` INT(11) NOT NULL ,
  `designation` VARCHAR(45) NULL DEFAULT NULL ,
  `department` VARCHAR(45) NULL DEFAULT NULL ,
  `linkedAddressId` INT(11) NULL DEFAULT NULL ,
  `gender` VARCHAR(1) NOT NULL ,
  PRIMARY KEY (`teacherId`) ,
  CONSTRAINT `linkedAddress`
    FOREIGN KEY (`linkedAddressId` )
    REFERENCES `schoolmonitor`.`address` (`addressId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `teacher_ibfk_1`
    FOREIGN KEY (`schoolSpecificsId` )
    REFERENCES `schoolmonitor`.`schoolspecifics` (`schoolSpecificsId` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;
CREATE UNIQUE INDEX `linkedAddressId_UNIQUE` ON `schoolmonitor`.`teacher` (`linkedAddressId` ASC) ;

SHOW WARNINGS;
CREATE INDEX `LinkedSchoolspecificsId` ON `schoolmonitor`.`teacher` (`schoolSpecificsId` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `schoolmonitor`.`credentials`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `schoolmonitor`.`credentials` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `schoolmonitor`.`credentials` (
  `userId` INT(11) NOT NULL AUTO_INCREMENT ,
  `password` VARCHAR(255) NOT NULL ,
  `userName` VARCHAR(255) NOT NULL ,
  `linkedStudentId` VARCHAR(255) NULL DEFAULT NULL ,
  `linkedTeacherId` VARCHAR(255) NULL DEFAULT NULL ,
  `isAdmin` BIT(1) NOT NULL ,
  `accountCreationDate` DATE NOT NULL ,
  `passwordLastChangedDate` DATE NULL DEFAULT NULL ,
  `numberOfRetry` INT(11) NULL DEFAULT NULL ,
  `isActive` BIT(1) NOT NULL ,
  `emailId` VARCHAR(225) NOT NULL ,
  PRIMARY KEY (`userId`) ,
  CONSTRAINT `credentials_ibfk_1`
    FOREIGN KEY (`linkedStudentId` )
    REFERENCES `schoolmonitor`.`student` (`studentId` ),
  CONSTRAINT `credentials_ibfk_2`
    FOREIGN KEY (`linkedTeacherId` )
    REFERENCES `schoolmonitor`.`teacher` (`teacherId` ))
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;
CREATE UNIQUE INDEX `userName_UNIQUE` ON `schoolmonitor`.`credentials` (`userName` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `UK_n6vtl5ouhk1l9metn5xdauoc2` ON `schoolmonitor`.`credentials` (`userName` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `linkedStudentId_UNIQUE` ON `schoolmonitor`.`credentials` (`linkedStudentId` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `linkedTeacherId_UNIQUE` ON `schoolmonitor`.`credentials` (`linkedTeacherId` ASC) ;

SHOW WARNINGS;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
