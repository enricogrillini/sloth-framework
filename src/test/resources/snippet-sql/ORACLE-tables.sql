-- Create Table AFORISMI
Create Table AFORISMI
      (Idaforisma NUMBER(38,0) Not Null,
       Aforisma VARCHAR2(1000),
       Autore VARCHAR2(100),
       Dataultimapublicazione DATE)
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Dec_Funzioni
Create Table SEC_Dec_Funzioni
      (Codfunzione VARCHAR2(100) Not Null,
       Descrizionebreve VARCHAR2(20) Not Null,
       Descrizionelunga VARCHAR2(100) Not Null,
       Posizione NUMBER(38,0) Not Null,
       Flagvalido VARCHAR2(1) Not Null)
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Dec_Menu
Create Table SEC_Dec_Menu
      (Codmenu VARCHAR2(100) Not Null,
       Descrizionebreve VARCHAR2(20) Not Null,
       Descrizionelunga VARCHAR2(100) Not Null,
       Posizione NUMBER(38,0) Not Null,
       Flagvalido VARCHAR2(1) Not Null,
       Codtipovoce VARCHAR2(10) Not Null,
       Livello NUMBER(38,0) Not Null,
       Imghtml VARCHAR2(100),
       Link VARCHAR2(200))
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Dec_Menuutente
Create Table SEC_Dec_Menuutente
      (Codmenuutente VARCHAR2(100) Not Null,
       Descrizionebreve VARCHAR2(20) Not Null,
       Descrizionelunga VARCHAR2(100) Not Null,
       Posizione NUMBER(38,0) Not Null,
       Flagvalido VARCHAR2(1) Not Null,
       Codtipovoce VARCHAR2(10) Not Null,
       Imghtml VARCHAR2(100),
       Link VARCHAR2(200))
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Dec_Ruoli
Create Table SEC_Dec_Ruoli
      (Codruolo VARCHAR2(10) Not Null,
       Descrizionebreve VARCHAR2(20) Not Null,
       Descrizionelunga VARCHAR2(100) Not Null,
       Posizione NUMBER(38,0) Not Null,
       Flagvalido VARCHAR2(1) Not Null)
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Dec_Tipivoce
Create Table SEC_Dec_Tipivoce
      (Codtipovoce VARCHAR2(10) Not Null,
       Descrizionebreve VARCHAR2(20) Not Null,
       Descrizionelunga VARCHAR2(100) Not Null,
       Posizione NUMBER(38,0) Not Null,
       Flagvalido VARCHAR2(1) Not Null,
       Flagmenuutente VARCHAR2(1))
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Funzioniruoli
Create Table SEC_Funzioniruoli
      (Codfunzione VARCHAR2(100) Not Null,
       Codruolo VARCHAR2(10) Not Null,
       Flagaccesso VARCHAR2(1))
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Menuruoli
Create Table SEC_Menuruoli
      (Codmenu VARCHAR2(100) Not Null,
       Codruolo VARCHAR2(10) Not Null,
       Flagaccesso VARCHAR2(1))
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Menuutenteruoli
Create Table SEC_Menuutenteruoli
      (Codmenuutente VARCHAR2(100) Not Null,
       Codruolo VARCHAR2(10) Not Null,
       Flagaccesso VARCHAR2(1))
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Profili
Create Table SEC_Profili
      (Idprofilo NUMBER(38,0) Not Null,
       Idutente NUMBER(38,0),
       Codruolo VARCHAR2(10),
       Datainizio DATE Not Null,
       Datafine DATE)
Tablespace USERS
Storage (Initial 64K);

-- Create Table SEC_Utenti
Create Table SEC_Utenti
      (Idutente NUMBER(38,0) Not Null,
       Nome VARCHAR2(100),
       Cognome VARCHAR2(100),
       Userid VARCHAR2(10),
       Password VARCHAR2(10),
       Email VARCHAR2(100),
       Emailpassword VARCHAR2(100),
       Locale VARCHAR2(5),
       Foto BLOB)
Tablespace USERS
Storage (Initial 64K);

