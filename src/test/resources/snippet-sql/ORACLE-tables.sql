-- Create Table AFORISMI
Create Table AFORISMI
      (IDAFORISMA NUMBER(38,0),
       AFORISMA VARCHAR2(1000),
       AUTORE VARCHAR2(100),
       DATAULTIMAPUBLICAZIONE DATE)
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Dec_Funzioni
Create Table SEC_Dec_Funzioni
      (CODFUNZIONE VARCHAR2(100),
       DESCRIZIONEBREVE VARCHAR2(20),
       DESCRIZIONELUNGA VARCHAR2(100),
       POSIZIONE NUMBER(38,0),
       FLAGVALIDO VARCHAR2(1))
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Dec_Menu
Create Table SEC_Dec_Menu
      (CODMENU VARCHAR2(100),
       DESCRIZIONEBREVE VARCHAR2(20),
       DESCRIZIONELUNGA VARCHAR2(100),
       POSIZIONE NUMBER(38,0),
       FLAGVALIDO VARCHAR2(1),
       CODTIPOVOCE VARCHAR2(10),
       LIVELLO NUMBER(38,0),
       IMGHTML VARCHAR2(100),
       LINK VARCHAR2(200))
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Dec_Menuutente
Create Table SEC_Dec_Menuutente
      (CODMENUUTENTE VARCHAR2(100),
       DESCRIZIONEBREVE VARCHAR2(20),
       DESCRIZIONELUNGA VARCHAR2(100),
       POSIZIONE NUMBER(38,0),
       FLAGVALIDO VARCHAR2(1),
       CODTIPOVOCE VARCHAR2(10),
       IMGHTML VARCHAR2(100),
       LINK VARCHAR2(200))
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Dec_Ruoli
Create Table SEC_Dec_Ruoli
      (CODRUOLO VARCHAR2(10),
       DESCRIZIONEBREVE VARCHAR2(20),
       DESCRIZIONELUNGA VARCHAR2(100),
       POSIZIONE NUMBER(38,0),
       FLAGVALIDO VARCHAR2(1))
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Dec_Tipivoce
Create Table SEC_Dec_Tipivoce
      (CODTIPOVOCE VARCHAR2(10),
       DESCRIZIONEBREVE VARCHAR2(20),
       DESCRIZIONELUNGA VARCHAR2(100),
       POSIZIONE NUMBER(38,0),
       FLAGVALIDO VARCHAR2(1),
       FLAGMENUUTENTE VARCHAR2(1))
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Funzioniruoli
Create Table SEC_Funzioniruoli
      (CODFUNZIONE VARCHAR2(100),
       CODRUOLO VARCHAR2(10),
       FLAGACCESSO VARCHAR2(1))
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Menuruoli
Create Table SEC_Menuruoli
      (CODMENU VARCHAR2(100),
       CODRUOLO VARCHAR2(10),
       FLAGACCESSO VARCHAR2(1))
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Menuutenteruoli
Create Table SEC_Menuutenteruoli
      (CODMENUUTENTE VARCHAR2(100),
       CODRUOLO VARCHAR2(10),
       FLAGACCESSO VARCHAR2(1))
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Profili
Create Table SEC_Profili
      (IDPROFILO NUMBER(38,0),
       IDUTENTE NUMBER(38,0),
       CODRUOLO VARCHAR2(10),
       DATAINIZIO DATE,
       DATAFINE DATE)
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Utenti
Create Table SEC_Utenti
      (IDUTENTE NUMBER(38,0),
       NOME VARCHAR2(100),
       COGNOME VARCHAR2(100),
       USERID VARCHAR2(10),
       PASSWORD VARCHAR2(10),
       EMAIL VARCHAR2(100),
       EMAILPASSWORD VARCHAR2(100),
       LOCALE VARCHAR2(5),
       FOTO BLOB)
Tablespace USERS
Storage (Initial 64K);

