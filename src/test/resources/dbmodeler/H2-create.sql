-- Create Table AFORISMI
Create Table AFORISMI
      (Idaforisma NUMBER(38,0) Not Null,
       Aforisma VARCHAR2(1000),
       Autore VARCHAR2(100),
       Dataultimapublicazione DATE);

Comment On Column AFORISMI.Idaforisma Is '';
Comment On Column AFORISMI.Aforisma Is '';
Comment On Column AFORISMI.Autore Is '';
Comment On Column AFORISMI.Dataultimapublicazione Is '';

-- Create Table MONITOR_Login
Create Table MONITOR_Login
      (Idlogin NUMBER(38,0) Not Null,
       Idutente NUMBER(38,0),
       Dataora DATE);

Comment On Column MONITOR_Login.Idlogin Is '';
Comment On Column MONITOR_Login.Idutente Is '';
Comment On Column MONITOR_Login.Dataora Is '';

-- Create Table SEC_Dec_Funzioni
Create Table SEC_Dec_Funzioni
      (Codfunzione VARCHAR2(100) Not Null,
       Descrizionebreve VARCHAR2(20) Not Null,
       Descrizionelunga VARCHAR2(100) Not Null,
       Posizione NUMBER(38,0) Not Null,
       Flagvalido VARCHAR2(1) Not Null);

Comment On Column SEC_Dec_Funzioni.Codfunzione Is '';
Comment On Column SEC_Dec_Funzioni.Descrizionebreve Is '';
Comment On Column SEC_Dec_Funzioni.Descrizionelunga Is '';
Comment On Column SEC_Dec_Funzioni.Posizione Is '';
Comment On Column SEC_Dec_Funzioni.Flagvalido Is '';

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
       Link VARCHAR2(200));

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
      (Codmenuutente VARCHAR2(100) Not Null,
       Descrizionebreve VARCHAR2(20) Not Null,
       Descrizionelunga VARCHAR2(100) Not Null,
       Posizione NUMBER(38,0) Not Null,
       Flagvalido VARCHAR2(1) Not Null,
       Codtipovoce VARCHAR2(10) Not Null,
       Imghtml VARCHAR2(100),
       Link VARCHAR2(200));

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
      (Codruolo VARCHAR2(10) Not Null,
       Descrizionebreve VARCHAR2(20) Not Null,
       Descrizionelunga VARCHAR2(100) Not Null,
       Posizione NUMBER(38,0) Not Null,
       Flagvalido VARCHAR2(1) Not Null);

Comment On Column SEC_Dec_Ruoli.Codruolo Is '';
Comment On Column SEC_Dec_Ruoli.Descrizionebreve Is '';
Comment On Column SEC_Dec_Ruoli.Descrizionelunga Is '';
Comment On Column SEC_Dec_Ruoli.Posizione Is '';
Comment On Column SEC_Dec_Ruoli.Flagvalido Is '';

-- Create Table SEC_Dec_Tipivoce
Create Table SEC_Dec_Tipivoce
      (Codtipovoce VARCHAR2(10) Not Null,
       Descrizionebreve VARCHAR2(20) Not Null,
       Descrizionelunga VARCHAR2(100) Not Null,
       Posizione NUMBER(38,0) Not Null,
       Flagvalido VARCHAR2(1) Not Null,
       Flagmenuutente VARCHAR2(1));

Comment On Column SEC_Dec_Tipivoce.Codtipovoce Is '';
Comment On Column SEC_Dec_Tipivoce.Descrizionebreve Is '';
Comment On Column SEC_Dec_Tipivoce.Descrizionelunga Is '';
Comment On Column SEC_Dec_Tipivoce.Posizione Is '';
Comment On Column SEC_Dec_Tipivoce.Flagvalido Is '';
Comment On Column SEC_Dec_Tipivoce.Flagmenuutente Is '';

-- Create Table SEC_Funzioniruoli
Create Table SEC_Funzioniruoli
      (Codfunzione VARCHAR2(100) Not Null,
       Codruolo VARCHAR2(10) Not Null,
       Flagaccesso VARCHAR2(1));

Comment On Column SEC_Funzioniruoli.Codfunzione Is '';
Comment On Column SEC_Funzioniruoli.Codruolo Is '';
Comment On Column SEC_Funzioniruoli.Flagaccesso Is '';

-- Create Table SEC_Menuruoli
Create Table SEC_Menuruoli
      (Codmenu VARCHAR2(100) Not Null,
       Codruolo VARCHAR2(10) Not Null,
       Flagaccesso VARCHAR2(1));

Comment On Column SEC_Menuruoli.Codmenu Is '';
Comment On Column SEC_Menuruoli.Codruolo Is '';
Comment On Column SEC_Menuruoli.Flagaccesso Is '';

-- Create Table SEC_Menuutenteruoli
Create Table SEC_Menuutenteruoli
      (Codmenuutente VARCHAR2(100) Not Null,
       Codruolo VARCHAR2(10) Not Null,
       Flagaccesso VARCHAR2(1));

Comment On Column SEC_Menuutenteruoli.Codmenuutente Is '';
Comment On Column SEC_Menuutenteruoli.Codruolo Is '';
Comment On Column SEC_Menuutenteruoli.Flagaccesso Is '';

-- Create Table SEC_Profili
Create Table SEC_Profili
      (Idprofilo NUMBER(38,0) Not Null,
       Idutente NUMBER(38,0),
       Codruolo VARCHAR2(10),
       Datainizio DATE Not Null,
       Datafine DATE);

Comment On Column SEC_Profili.Idprofilo Is '';
Comment On Column SEC_Profili.Idutente Is '';
Comment On Column SEC_Profili.Codruolo Is '';
Comment On Column SEC_Profili.Datainizio Is '';
Comment On Column SEC_Profili.Datafine Is '';

-- Create Table SEC_Utenti
Create Table SEC_Utenti
      (Idutente NUMBER(38,0) Not Null,
       Nome VARCHAR2(100),
       Cognome VARCHAR2(100),
       Userid VARCHAR2(10),
       Password VARCHAR2(100),
       Email VARCHAR2(100),
       Emailpassword VARCHAR2(100),
       Locale VARCHAR2(5),
       Foto BLOB);

Comment On Column SEC_Utenti.Idutente Is '';
Comment On Column SEC_Utenti.Nome Is '';
Comment On Column SEC_Utenti.Cognome Is '';
Comment On Column SEC_Utenti.Userid Is '';
Comment On Column SEC_Utenti.Password Is '';
Comment On Column SEC_Utenti.Email Is '';
Comment On Column SEC_Utenti.Emailpassword Is '';
Comment On Column SEC_Utenti.Locale Is '';
Comment On Column SEC_Utenti.Foto Is '';

-- Create Index AFORISMI_PK
Create Unique Index AFORISMI_PK on AFORISMI (IDAFORISMA);

-- Create Index MONITOR_LOGIN_PK
Create Unique Index MONITOR_LOGIN_PK on MONITOR_Login (IDLOGIN);

-- Create Index SEC_DEC_FUNZIONI_PK
Create Unique Index SEC_DEC_FUNZIONI_PK on SEC_Dec_Funzioni (CODFUNZIONE);

-- Create Index SEC_DEC_MENU_PK
Create Unique Index SEC_DEC_MENU_PK on SEC_Dec_Menu (CODMENU);

-- Create Index SEC_DEC_MENUUTENTE_PK
Create Unique Index SEC_DEC_MENUUTENTE_PK on SEC_Dec_Menuutente (CODMENUUTENTE);

-- Create Index SEC_DEC_RUOLI_PK
Create Unique Index SEC_DEC_RUOLI_PK on SEC_Dec_Ruoli (CODRUOLO);

-- Create Index SEC_DEC_TIPIVOCE_PK
Create Unique Index SEC_DEC_TIPIVOCE_PK on SEC_Dec_Tipivoce (CODTIPOVOCE);

-- Create Index SEC_FUNZIONIRUOLI_IDX1
Create Index SEC_FUNZIONIRUOLI_IDX1 on SEC_Funzioniruoli (CODRUOLO);

-- Create Index SEC_FUNZIONIRUOLI_IDX2
Create Index SEC_FUNZIONIRUOLI_IDX2 on SEC_Funzioniruoli (CODFUNZIONE);

-- Create Index SEC_FUNZIONIRUOLI_PK
Create Unique Index SEC_FUNZIONIRUOLI_PK on SEC_Funzioniruoli (CODFUNZIONE,CODRUOLO);

-- Create Index SEC_MENURUOLI_IDX1
Create Index SEC_MENURUOLI_IDX1 on SEC_Menuruoli (CODRUOLO);

-- Create Index SEC_MENURUOLI_IDX2
Create Index SEC_MENURUOLI_IDX2 on SEC_Menuruoli (CODMENU);

-- Create Index SEC_MENURUOLI_PK
Create Unique Index SEC_MENURUOLI_PK on SEC_Menuruoli (CODMENU,CODRUOLO);

-- Create Index SEC_MENUUTENTERUOLI_IDX1
Create Index SEC_MENUUTENTERUOLI_IDX1 on SEC_Menuutenteruoli (CODRUOLO);

-- Create Index SEC_MENUUTENTERUOLI_IDX2
Create Index SEC_MENUUTENTERUOLI_IDX2 on SEC_Menuutenteruoli (CODMENUUTENTE);

-- Create Index SEC_MENUUTENTERUOLI_PK
Create Unique Index SEC_MENUUTENTERUOLI_PK on SEC_Menuutenteruoli (CODMENUUTENTE,CODRUOLO);

-- Create Index SEC_PROFILI_PK
Create Unique Index SEC_PROFILI_PK on SEC_Profili (IDPROFILO);

-- Create Index SEC_UTENTI_PK
Create Unique Index SEC_UTENTI_PK on SEC_Utenti (IDUTENTE);

-- Create sequence
Create Sequence Monitor_Idlogin;
Create Sequence Sec_Seq_Idutente;
Create Sequence Seq_Idprofilo;

