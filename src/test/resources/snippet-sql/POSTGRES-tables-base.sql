-- Create Table Aforismi
Create Table Aforismi
      (Idaforisma integer Not Null,
       Aforisma character varying(1000),
       Autore character varying(100),
       Dataultimapublicazione date);

-- Create Table Sec_Dec_Funzioni
Create Table Sec_Dec_Funzioni
      (Codfunzione character varying(100) Not Null,
       Descrizionebreve character varying(20) Not Null,
       Descrizionelunga character varying(100) Not Null,
       Posizione integer Not Null,
       Flagvalido character varying(1) Not Null);

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

-- Create Table Sec_Dec_Ruoli
Create Table Sec_Dec_Ruoli
      (Codruolo character varying(10) Not Null,
       Descrizionebreve character varying(20) Not Null,
       Descrizionelunga character varying(100) Not Null,
       Posizione integer Not Null,
       Flagvalido character varying(1) Not Null);

-- Create Table Sec_Dec_Tipivoce
Create Table Sec_Dec_Tipivoce
      (Codtipovoce character varying(10) Not Null,
       Descrizionebreve character varying(20) Not Null,
       Descrizionelunga character varying(100) Not Null,
       Posizione integer Not Null,
       Flagvalido character varying(1) Not Null,
       Flagmenuutente character varying(1));

-- Create Table Sec_Funzioniruoli
Create Table Sec_Funzioniruoli
      (Codfunzione character varying(100) Not Null,
       Codruolo character varying(10) Not Null,
       Flagaccesso character varying(1));

-- Create Table Sec_Menuruoli
Create Table Sec_Menuruoli
      (Codmenu character varying(100) Not Null,
       Codruolo character varying(10) Not Null,
       Flagaccesso character varying(1));

-- Create Table Sec_Menuutenteruoli
Create Table Sec_Menuutenteruoli
      (Codmenuutente character varying(100) Not Null,
       Codruolo character varying(10) Not Null,
       Flagaccesso character varying(1));

-- Create Table Sec_Profili
Create Table Sec_Profili
      (Idprofilo integer Not Null,
       Idutente integer,
       Codruolo character varying(10),
       Datainizio date Not Null,
       Datafine date);

-- Create Table Sec_Utenti
Create Table Sec_Utenti
      (Idutente integer Not Null,
       Nome character varying(100),
       Cognome character varying(100),
       Userid character varying(10),
       Password character varying(10),
       Email character varying(100),
       Emailpassword character varying(100),
       Locale character varying(5),
       Foto bytea);

