-- Create Table AFORISMI
Create Table AFORISMI
      (Idaforisma NUMERIC Not Null,
       Aforisma CHARACTER VARYING,
       Autore CHARACTER VARYING,
       Dataultimapublicazione TIMESTAMP);

Comment On Column AFORISMI.Idaforisma Is '';
Comment On Column AFORISMI.Aforisma Is '';
Comment On Column AFORISMI.Autore Is '';
Comment On Column AFORISMI.Dataultimapublicazione Is '';

-- Create Table FRAME_Properties
Create Table FRAME_Properties
      (Codproperty CHARACTER VARYING Not Null,
       Type CHARACTER VARYING Not Null,
       Value CHARACTER VARYING Not Null);

Comment On Column FRAME_Properties.Codproperty Is '';
Comment On Column FRAME_Properties.Type Is '';
Comment On Column FRAME_Properties.Value Is '';

-- Create Table MONITOR_Login
Create Table MONITOR_Login
      (Idlogin NUMERIC Not Null,
       Idutente NUMERIC,
       Dataora TIMESTAMP);

Comment On Column MONITOR_Login.Idlogin Is '';
Comment On Column MONITOR_Login.Idutente Is '';
Comment On Column MONITOR_Login.Dataora Is '';

-- Create Table SEC_Dec_Funzioni
Create Table SEC_Dec_Funzioni
      (Codfunzione CHARACTER VARYING Not Null,
       Descrizionebreve CHARACTER VARYING Not Null,
       Descrizionelunga CHARACTER VARYING Not Null,
       Posizione NUMERIC Not Null,
       Flagvalido CHARACTER VARYING Not Null);

Comment On Column SEC_Dec_Funzioni.Codfunzione Is '';
Comment On Column SEC_Dec_Funzioni.Descrizionebreve Is '';
Comment On Column SEC_Dec_Funzioni.Descrizionelunga Is '';
Comment On Column SEC_Dec_Funzioni.Posizione Is '';
Comment On Column SEC_Dec_Funzioni.Flagvalido Is '';

-- Create Table SEC_Dec_Menu
Create Table SEC_Dec_Menu
      (Codmenu CHARACTER VARYING Not Null,
       Descrizionebreve CHARACTER VARYING Not Null,
       Descrizionelunga CHARACTER VARYING Not Null,
       Posizione NUMERIC Not Null,
       Flagvalido CHARACTER VARYING Not Null,
       Codtipovoce CHARACTER VARYING Not Null,
       Livello NUMERIC Not Null,
       Imghtml CHARACTER VARYING,
       Link CHARACTER VARYING);

Comment On Column SEC_Dec_Menu.Codmenu Is '';
Comment On Column SEC_Dec_Menu.Descrizionebreve Is '';
Comment On Column SEC_Dec_Menu.Descrizionelunga Is '';
Comment On Column SEC_Dec_Menu.Posizione Is '';
Comment On Column SEC_Dec_Menu.Flagvalido Is '';
Comment On Column SEC_Dec_Menu.Codtipovoce Is '';
Comment On Column SEC_Dec_Menu.Livello Is '';
Comment On Column SEC_Dec_Menu.Imghtml Is '';
Comment On Column SEC_Dec_Menu.Link Is '';

-- Create Table SEC_Dec_Menuutente
Create Table SEC_Dec_Menuutente
      (Codmenuutente CHARACTER VARYING Not Null,
       Descrizionebreve CHARACTER VARYING Not Null,
       Descrizionelunga CHARACTER VARYING Not Null,
       Posizione NUMERIC Not Null,
       Flagvalido CHARACTER VARYING Not Null,
       Codtipovoce CHARACTER VARYING Not Null,
       Imghtml CHARACTER VARYING,
       Link CHARACTER VARYING);

Comment On Column SEC_Dec_Menuutente.Codmenuutente Is '';
Comment On Column SEC_Dec_Menuutente.Descrizionebreve Is '';
Comment On Column SEC_Dec_Menuutente.Descrizionelunga Is '';
Comment On Column SEC_Dec_Menuutente.Posizione Is '';
Comment On Column SEC_Dec_Menuutente.Flagvalido Is '';
Comment On Column SEC_Dec_Menuutente.Codtipovoce Is '';
Comment On Column SEC_Dec_Menuutente.Imghtml Is '';
Comment On Column SEC_Dec_Menuutente.Link Is '';

-- Create Table SEC_Dec_Ruoli
Create Table SEC_Dec_Ruoli
      (Codruolo CHARACTER VARYING Not Null,
       Descrizionebreve CHARACTER VARYING Not Null,
       Descrizionelunga CHARACTER VARYING Not Null,
       Posizione NUMERIC Not Null,
       Flagvalido CHARACTER VARYING Not Null);

Comment On Column SEC_Dec_Ruoli.Codruolo Is '';
Comment On Column SEC_Dec_Ruoli.Descrizionebreve Is '';
Comment On Column SEC_Dec_Ruoli.Descrizionelunga Is '';
Comment On Column SEC_Dec_Ruoli.Posizione Is '';
Comment On Column SEC_Dec_Ruoli.Flagvalido Is '';

-- Create Table SEC_Dec_Tipivoce
Create Table SEC_Dec_Tipivoce
      (Codtipovoce CHARACTER VARYING Not Null,
       Descrizionebreve CHARACTER VARYING Not Null,
       Descrizionelunga CHARACTER VARYING Not Null,
       Posizione NUMERIC Not Null,
       Flagvalido CHARACTER VARYING Not Null,
       Flagmenuutente CHARACTER VARYING);

Comment On Column SEC_Dec_Tipivoce.Codtipovoce Is '';
Comment On Column SEC_Dec_Tipivoce.Descrizionebreve Is '';
Comment On Column SEC_Dec_Tipivoce.Descrizionelunga Is '';
Comment On Column SEC_Dec_Tipivoce.Posizione Is '';
Comment On Column SEC_Dec_Tipivoce.Flagvalido Is '';
Comment On Column SEC_Dec_Tipivoce.Flagmenuutente Is '';

-- Create Table SEC_Funzioniruoli
Create Table SEC_Funzioniruoli
      (Codfunzione CHARACTER VARYING Not Null,
       Codruolo CHARACTER VARYING Not Null,
       Flagaccesso CHARACTER VARYING);

Comment On Column SEC_Funzioniruoli.Codfunzione Is '';
Comment On Column SEC_Funzioniruoli.Codruolo Is '';
Comment On Column SEC_Funzioniruoli.Flagaccesso Is '';

-- Create Table SEC_Menuruoli
Create Table SEC_Menuruoli
      (Codmenu CHARACTER VARYING Not Null,
       Codruolo CHARACTER VARYING Not Null,
       Flagaccesso CHARACTER VARYING);

Comment On Column SEC_Menuruoli.Codmenu Is '';
Comment On Column SEC_Menuruoli.Codruolo Is '';
Comment On Column SEC_Menuruoli.Flagaccesso Is '';

-- Create Table SEC_Menuutenteruoli
Create Table SEC_Menuutenteruoli
      (Codmenuutente CHARACTER VARYING Not Null,
       Codruolo CHARACTER VARYING Not Null,
       Flagaccesso CHARACTER VARYING);

Comment On Column SEC_Menuutenteruoli.Codmenuutente Is '';
Comment On Column SEC_Menuutenteruoli.Codruolo Is '';
Comment On Column SEC_Menuutenteruoli.Flagaccesso Is '';

-- Create Table SEC_Profili
Create Table SEC_Profili
      (Idprofilo NUMERIC Not Null,
       Idutente NUMERIC,
       Codruolo CHARACTER VARYING,
       Datainizio TIMESTAMP Not Null,
       Datafine TIMESTAMP);

Comment On Column SEC_Profili.Idprofilo Is '';
Comment On Column SEC_Profili.Idutente Is '';
Comment On Column SEC_Profili.Codruolo Is '';
Comment On Column SEC_Profili.Datainizio Is '';
Comment On Column SEC_Profili.Datafine Is '';

-- Create Table SEC_Utenti
Create Table SEC_Utenti
      (Idutente NUMERIC Not Null,
       Nome CHARACTER VARYING,
       Cognome CHARACTER VARYING,
       Userid CHARACTER VARYING,
       Password CHARACTER VARYING,
       Email CHARACTER VARYING,
       Emailpassword CHARACTER VARYING,
       Locale CHARACTER VARYING,
       Foto BINARY LARGE OBJECT);

Comment On Column SEC_Utenti.Idutente Is '';
Comment On Column SEC_Utenti.Nome Is '';
Comment On Column SEC_Utenti.Cognome Is '';
Comment On Column SEC_Utenti.Userid Is '';
Comment On Column SEC_Utenti.Password Is '';
Comment On Column SEC_Utenti.Email Is '';
Comment On Column SEC_Utenti.Emailpassword Is '';
Comment On Column SEC_Utenti.Locale Is '';
Comment On Column SEC_Utenti.Foto Is '';

