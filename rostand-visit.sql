DROP DATABASE IF EXISTS rostand-visit;

CREATE DATABASE IF NOT EXISTS rostand-visit;
USE rostand-visit;
# -----------------------------------------------------------------------------
#       TABLE : SPECIALITÉ
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS SPECIALITÉ
 (
   SPE_ID INT 32 NOT NULL  ,
   SPE_VIS VARCHAR 0 NOT NULL  ,
   SPE_LIBELLE VARCHAR 32 NULL
   , PRIMARY KEY (SPE_ID)
 )
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE SPECIALITÉ
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_SPECIALITÉ_VISITE
     ON SPECIALITÉ (SPE_VIS ASC);

# -----------------------------------------------------------------------------
#       TABLE : ETUDIANT
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ETUDIANT
 (
   ETU_ID INT, 32 NOT NULL  ,
   ETU_SEC VARCHAR, 0 NOT NULL  ,
   ETU_NOM VARCHAR, 50 NULL  ,
   ETU_PRENOM VARCHAR, 32 NULL  ,
   ETU_MAIL VARCHAR, 32 NULL  ,
   ETU_ETAB VARCHAR, 32 NULL
   , PRIMARY KEY (ETU_ID)
 )
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE ETUDIANT
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_ETUDIANT_SECTION
     ON ETUDIANT (ETU_SEC ASC);

# -----------------------------------------------------------------------------
#       TABLE : OPTION
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS OPTION
 (
   OPT_ID INT, 32 NOT NULL  ,
   OPT_SEC VARCHAR, 0 NOT NULL  ,
   OPT_LIBELLE VARCHAR, 32 NULL
   , PRIMARY KEY (OPT_ID)
 )
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE OPTION
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_OPTION_SECTION
     ON OPTION (OPT_SEC ASC);

# -----------------------------------------------------------------------------
#       TABLE : SECTION
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS SECTION
 (
   SEC_ID INT, 0 NOT NULL  ,
   SEC_LIBELLE VARCHAR, 32 NULL
   , PRIMARY KEY (SEC_ID)
 )
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : VISITE
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS VISITE
 (
   VIS_ID DB_TEXT, 0 NOT NULL  ,
   VIS_LIEU DB_TEXT, 32 NULL
   , PRIMARY KEY (VIS_ID)
 )
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : ENSEIGNANT
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ENSEIGNANT
 (
   ENS_ID DB_TEXT, 32 NOT NULL  ,
   ENS_NOM DB_TEXT, 32 NULL  ,
   ENS_PRENOM DB_TEXT, 32 NULL  ,
   ENS_NOMUTI DB_TEXT, 32 NULL  ,
   ENS_MDP DB_TEXT, 32 NULL
   , PRIMARY KEY (ENS_ID)
 )
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : EFFECTUER
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS EFFECTUER
 (
   ETU_ID DB_TEXT, 32 NOT NULL  ,
   VIS_ID DB_TEXT, 0 NOT NULL  ,
   EFF_DATE DB_DATE(8) NULL
   , PRIMARY KEY (ETU_ID,VIS_ID)
 )
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE EFFECTUER
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_EFFECTUER_ETUDIANT
     ON EFFECTUER (ETU_ID ASC);

CREATE  INDEX I_FK_EFFECTUER_VISITE
     ON EFFECTUER (VIS_ID ASC);


# -----------------------------------------------------------------------------
#       CREATION DES REFERENCES DE TABLE
# -----------------------------------------------------------------------------


ALTER TABLE SPECIALITÉ
  ADD FOREIGN KEY FK_SPECIALITÉ_VISITE (SPE_VIS)
      REFERENCES VISITE (VIS_ID) ;


ALTER TABLE ETUDIANT
  ADD FOREIGN KEY FK_ETUDIANT_SECTION (ETU_SEC)
      REFERENCES SECTION (SEC_ID) ;


ALTER TABLE OPTION
  ADD FOREIGN KEY FK_OPTION_SECTION (OPT_SEC)
      REFERENCES SECTION (SEC_ID) ;


ALTER TABLE EFFECTUER
  ADD FOREIGN KEY FK_EFFECTUER_ETUDIANT (ETU_ID)
      REFERENCES ETUDIANT (ETU_ID) ;


ALTER TABLE EFFECTUER
  ADD FOREIGN KEY FK_EFFECTUER_VISITE (VIS_ID)
      REFERENCES VISITE (VIS_ID) ;
