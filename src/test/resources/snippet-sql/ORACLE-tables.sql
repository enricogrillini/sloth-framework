-- Create Table Aforismi
Create Table Aforismi
      (Idaforisma NUMBER(38,0) Not Null,
       Aforisma VARCHAR2(1000),
       Autore VARCHAR2(100),
       Dataultimapublicazione DATE)
Tablespace USERS
Storage (Initial 64K);

Comment On Column Aforismi.Idaforisma Is '';
Comment On Column Aforismi.Aforisma Is '';
Comment On Column Aforismi.Autore Is '';
Comment On Column Aforismi.Dataultimapublicazione Is '';

-- Create Table Frame_Properties
Create Table Frame_Properties
      (Codproperty VARCHAR2(250) Not Null,
       Type VARCHAR2(50) Not Null,
       Value VARCHAR2(200) Not Null)
Tablespace USERS
Storage (Initial 64K);

Comment On Column Frame_Properties.Codproperty Is '';
Comment On Column Frame_Properties.Type Is '';
Comment On Column Frame_Properties.Value Is '';

-- Create Table Monitor_Login
Create Table Monitor_Login
      (Idlogin NUMBER(38,0) Not Null,
       Idutente NUMBER(38,0),
       Dataora DATE)
Tablespace USERS
Storage (Initial 64K);

Comment On Column Monitor_Login.Idlogin Is '';
Comment On Column Monitor_Login.Idutente Is '';
Comment On Column Monitor_Login.Dataora Is '';

-- Create Table Sec_Dec_Funzioni
Create Table Sec_Dec_Funzioni
      (Codfunzione VARCHAR2(100) Not Null,
       Descrizionebreve VARCHAR2(20) Not Null,
       Descrizionelunga VARCHAR2(100) Not Null,
       Posizione NUMBER(38,0) Not Null,
       Flagvalido VARCHAR2(1) Not Null)
Tablespace USERS
Storage (Initial 64K);

Comment On Column Sec_Dec_Funzioni.Codfunzione Is '';
Comment On Column Sec_Dec_Funzioni.Descrizionebreve Is '';
Comment On Column Sec_Dec_Funzioni.Descrizionelunga Is '';
Comment On Column Sec_Dec_Funzioni.Posizione Is '';
Comment On Column Sec_Dec_Funzioni.Flagvalido Is '';

-- Create Table Sec_Dec_Menu
Create Table Sec_Dec_Menu
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

Comment On Column Sec_Dec_Menu.Codmenu Is '';
Comment On Column Sec_Dec_Menu.Descrizionebreve Is '';
Comment On Column Sec_Dec_Menu.Descrizionelunga Is '';
Comment On Column Sec_Dec_Menu.Posizione Is '';
Comment On Column Sec_Dec_Menu.Flagvalido Is '';
Comment On Column Sec_Dec_Menu.Codtipovoce Is '';
Comment On Column Sec_Dec_Menu.Livello Is '';
Comment On Column Sec_Dec_Menu.Imghtml Is '';
Comment On Column Sec_Dec_Menu.Link Is '';

-- Create Table Sec_Dec_Menuutente
Create Table Sec_Dec_Menuutente
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

Comment On Column Sec_Dec_Menuutente.Codmenuutente Is '';
Comment On Column Sec_Dec_Menuutente.Descrizionebreve Is '';
Comment On Column Sec_Dec_Menuutente.Descrizionelunga Is '';
Comment On Column Sec_Dec_Menuutente.Posizione Is '';
Comment On Column Sec_Dec_Menuutente.Flagvalido Is '';
Comment On Column Sec_Dec_Menuutente.Codtipovoce Is '';
Comment On Column Sec_Dec_Menuutente.Imghtml Is '';
Comment On Column Sec_Dec_Menuutente.Link Is '';

-- Create Table Sec_Dec_Ruoli
Create Table Sec_Dec_Ruoli
      (Codruolo VARCHAR2(10) Not Null,
       Descrizionebreve VARCHAR2(20) Not Null,
       Descrizionelunga VARCHAR2(100) Not Null,
       Posizione NUMBER(38,0) Not Null,
       Flagvalido VARCHAR2(1) Not Null)
Tablespace USERS
Storage (Initial 64K);

Comment On Column Sec_Dec_Ruoli.Codruolo Is '';
Comment On Column Sec_Dec_Ruoli.Descrizionebreve Is '';
Comment On Column Sec_Dec_Ruoli.Descrizionelunga Is '';
Comment On Column Sec_Dec_Ruoli.Posizione Is '';
Comment On Column Sec_Dec_Ruoli.Flagvalido Is '';

-- Create Table Sec_Dec_Tipivoce
Create Table Sec_Dec_Tipivoce
      (Codtipovoce VARCHAR2(10) Not Null,
       Descrizionebreve VARCHAR2(20) Not Null,
       Descrizionelunga VARCHAR2(100) Not Null,
       Posizione NUMBER(38,0) Not Null,
       Flagvalido VARCHAR2(1) Not Null,
       Flagmenuutente VARCHAR2(1))
Tablespace USERS
Storage (Initial 64K);

Comment On Column Sec_Dec_Tipivoce.Codtipovoce Is '';
Comment On Column Sec_Dec_Tipivoce.Descrizionebreve Is '';
Comment On Column Sec_Dec_Tipivoce.Descrizionelunga Is '';
Comment On Column Sec_Dec_Tipivoce.Posizione Is '';
Comment On Column Sec_Dec_Tipivoce.Flagvalido Is '';
Comment On Column Sec_Dec_Tipivoce.Flagmenuutente Is '';

-- Create Table Sec_Funzioniruoli
Create Table Sec_Funzioniruoli
      (Codfunzione VARCHAR2(100) Not Null,
       Codruolo VARCHAR2(10) Not Null,
       Flagaccesso VARCHAR2(1))
Tablespace USERS
Storage (Initial 64K);

Comment On Column Sec_Funzioniruoli.Codfunzione Is '';
Comment On Column Sec_Funzioniruoli.Codruolo Is '';
Comment On Column Sec_Funzioniruoli.Flagaccesso Is '';

-- Create Table Sec_Menuruoli
Create Table Sec_Menuruoli
      (Codmenu VARCHAR2(100) Not Null,
       Codruolo VARCHAR2(10) Not Null,
       Flagaccesso VARCHAR2(1))
Tablespace USERS
Storage (Initial 64K);

Comment On Column Sec_Menuruoli.Codmenu Is '';
Comment On Column Sec_Menuruoli.Codruolo Is '';
Comment On Column Sec_Menuruoli.Flagaccesso Is '';

-- Create Table Sec_Menuutenteruoli
Create Table Sec_Menuutenteruoli
      (Codmenuutente VARCHAR2(100) Not Null,
       Codruolo VARCHAR2(10) Not Null,
       Flagaccesso VARCHAR2(1))
Tablespace USERS
Storage (Initial 64K);

Comment On Column Sec_Menuutenteruoli.Codmenuutente Is '';
Comment On Column Sec_Menuutenteruoli.Codruolo Is '';
Comment On Column Sec_Menuutenteruoli.Flagaccesso Is '';

-- Create Table Sec_Profili
Create Table Sec_Profili
      (Idprofilo NUMBER(38,0) Not Null,
       Idutente NUMBER(38,0),
       Codruolo VARCHAR2(10),
       Datainizio DATE Not Null,
       Datafine DATE)
Tablespace USERS
Storage (Initial 64K);

Comment On Column Sec_Profili.Idprofilo Is '';
Comment On Column Sec_Profili.Idutente Is '';
Comment On Column Sec_Profili.Codruolo Is '';
Comment On Column Sec_Profili.Datainizio Is '';
Comment On Column Sec_Profili.Datafine Is '';

-- Create Table Sec_Utenti
Create Table Sec_Utenti
      (Idutente NUMBER(38,0) Not Null,
       Nome VARCHAR2(100),
       Cognome VARCHAR2(100),
       Userid VARCHAR2(10),
       Password VARCHAR2(100),
       Email VARCHAR2(100),
       Emailpassword VARCHAR2(100),
       Locale VARCHAR2(5),
       Foto BLOB)
Tablespace USERS
Storage (Initial 64K);

Comment On Column Sec_Utenti.Idutente Is '';
Comment On Column Sec_Utenti.Nome Is '';
Comment On Column Sec_Utenti.Cognome Is '';
Comment On Column Sec_Utenti.Userid Is '';
Comment On Column Sec_Utenti.Password Is '';
Comment On Column Sec_Utenti.Email Is '';
Comment On Column Sec_Utenti.Emailpassword Is '';
Comment On Column Sec_Utenti.Locale Is '';
Comment On Column Sec_Utenti.Foto Is '';

