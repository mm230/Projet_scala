# Todo schema
 
# --- !Ups
CREATE TABLE Etudiant (
  `INEEtudiant` VARCHAR(50) NOT NULL ,
  `Nom` VARCHAR(45) NOT NULL,
  `Prenom` TINYINT(4) NOT NULL,
   `Telephone` Decimal(9,0) NULL DEFAULT NULL,
  PRIMARY KEY (`INEEtudiant`));
