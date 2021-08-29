-- Create Table Aforismi
Create Table Aforismi
      (Idaforisma integer Not Null,
       Aforisma character varying(1000),
       Autore character varying(100),
       Dataultimapublicazione date);

Comment On Column Aforismi.Idaforisma Is '';
Comment On Column Aforismi.Aforisma Is '';
Comment On Column Aforismi.Autore Is '';
Comment On Column Aforismi.Dataultimapublicazione Is '';

-- Create Table Monitor_Login
Create Table Monitor_Login
      (Idlogin integer Not Null,
       Idutente integer,
       Dataora date);

Comment On Column Monitor_Login.Idlogin Is '';
Comment On Column Monitor_Login.Idutente Is '';
Comment On Column Monitor_Login.Dataora Is '';

-- Create Table Sec_Dec_Funzioni
Create Table Sec_Dec_Funzioni
      (Codfunzione character varying(100) Not Null,
       Descrizionebreve character varying(20) Not Null,
       Descrizionelunga character varying(100) Not Null,
       Posizione integer Not Null,
       Flagvalido character varying(1) Not Null);

Comment On Column Sec_Dec_Funzioni.Codfunzione Is '';
Comment On Column Sec_Dec_Funzioni.Descrizionebreve Is '';
Comment On Column Sec_Dec_Funzioni.Descrizionelunga Is '';
Comment On Column Sec_Dec_Funzioni.Posizione Is '';
Comment On Column Sec_Dec_Funzioni.Flagvalido Is '';

-- Create Table Sec_Dec_Menu
Create Table Sec_Dec_Menu
      (Codmenu character varying(100) Not Null,
       Descrizionebreve character varying(20) Not Null,
       Descrizionelunga character varying(100) Not Null,
       Posizione integer Not Null,
       Flagvalido character varying(1) Not Null,
       Codtipovoce character varying(10) Not Null,
       Livello integer Not Null,
       Imghtml character varying(100),
       Link character varying(200));

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
      (Codmenuutente character varying(100) Not Null,
       Descrizionebreve character varying(20) Not Null,
       Descrizionelunga character varying(100) Not Null,
       Posizione integer Not Null,
       Flagvalido character varying(1) Not Null,
       Codtipovoce character varying(10) Not Null,
       Imghtml character varying(100),
       Link character varying(200));

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
      (Codruolo character varying(10) Not Null,
       Descrizionebreve character varying(20) Not Null,
       Descrizionelunga character varying(100) Not Null,
       Posizione integer Not Null,
       Flagvalido character varying(1) Not Null);

Comment On Column Sec_Dec_Ruoli.Codruolo Is '';
Comment On Column Sec_Dec_Ruoli.Descrizionebreve Is '';
Comment On Column Sec_Dec_Ruoli.Descrizionelunga Is '';
Comment On Column Sec_Dec_Ruoli.Posizione Is '';
Comment On Column Sec_Dec_Ruoli.Flagvalido Is '';

-- Create Table Sec_Dec_Tipivoce
Create Table Sec_Dec_Tipivoce
      (Codtipovoce character varying(10) Not Null,
       Descrizionebreve character varying(20) Not Null,
       Descrizionelunga character varying(100) Not Null,
       Posizione integer Not Null,
       Flagvalido character varying(1) Not Null,
       Flagmenuutente character varying(1));

Comment On Column Sec_Dec_Tipivoce.Codtipovoce Is '';
Comment On Column Sec_Dec_Tipivoce.Descrizionebreve Is '';
Comment On Column Sec_Dec_Tipivoce.Descrizionelunga Is '';
Comment On Column Sec_Dec_Tipivoce.Posizione Is '';
Comment On Column Sec_Dec_Tipivoce.Flagvalido Is '';
Comment On Column Sec_Dec_Tipivoce.Flagmenuutente Is '';

-- Create Table Sec_Funzioniruoli
Create Table Sec_Funzioniruoli
      (Codfunzione character varying(100) Not Null,
       Codruolo character varying(10) Not Null,
       Flagaccesso character varying(1));

Comment On Column Sec_Funzioniruoli.Codfunzione Is '';
Comment On Column Sec_Funzioniruoli.Codruolo Is '';
Comment On Column Sec_Funzioniruoli.Flagaccesso Is '';

-- Create Table Sec_Menuruoli
Create Table Sec_Menuruoli
      (Codmenu character varying(100) Not Null,
       Codruolo character varying(10) Not Null,
       Flagaccesso character varying(1));

Comment On Column Sec_Menuruoli.Codmenu Is '';
Comment On Column Sec_Menuruoli.Codruolo Is '';
Comment On Column Sec_Menuruoli.Flagaccesso Is '';

-- Create Table Sec_Menuutenteruoli
Create Table Sec_Menuutenteruoli
      (Codmenuutente character varying(100) Not Null,
       Codruolo character varying(10) Not Null,
       Flagaccesso character varying(1));

Comment On Column Sec_Menuutenteruoli.Codmenuutente Is '';
Comment On Column Sec_Menuutenteruoli.Codruolo Is '';
Comment On Column Sec_Menuutenteruoli.Flagaccesso Is '';

-- Create Table Sec_Profili
Create Table Sec_Profili
      (Idprofilo integer Not Null,
       Idutente integer,
       Codruolo character varying(10),
       Datainizio date Not Null,
       Datafine date);

Comment On Column Sec_Profili.Idprofilo Is '';
Comment On Column Sec_Profili.Idutente Is '';
Comment On Column Sec_Profili.Codruolo Is '';
Comment On Column Sec_Profili.Datainizio Is '';
Comment On Column Sec_Profili.Datafine Is '';

-- Create Table Sec_Utenti
Create Table Sec_Utenti
      (Idutente integer Not Null,
       Nome character varying(100),
       Cognome character varying(100),
       Userid character varying(10),
       Password character varying(100),
       Email character varying(100),
       Emailpassword character varying(100),
       Locale character varying(5),
       Foto bytea);

Comment On Column Sec_Utenti.Idutente Is '';
Comment On Column Sec_Utenti.Nome Is '';
Comment On Column Sec_Utenti.Cognome Is '';
Comment On Column Sec_Utenti.Userid Is '';
Comment On Column Sec_Utenti.Password Is '';
Comment On Column Sec_Utenti.Email Is '';
Comment On Column Sec_Utenti.Emailpassword Is '';
Comment On Column Sec_Utenti.Locale Is '';
Comment On Column Sec_Utenti.Foto Is '';

