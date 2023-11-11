-- Create Foreign Key Monitor_Login
Alter Table Monitor_Login Add Constraint MONITOR_LOGIN_FK1 Foreign Key (IDUTENTE) references SEC_UTENTI;

-- Create Foreign Key Sec_Dec_Menu
Alter Table Sec_Dec_Menu Add Constraint SEC_MENU_FK1 Foreign Key (CODTIPOVOCE) references SEC_DEC_TIPIVOCE;

-- Create Foreign Key Sec_Dec_Menuutente
Alter Table Sec_Dec_Menuutente Add Constraint SEC_DEC_MENUUTENTE_FK1 Foreign Key (CODTIPOVOCE) references SEC_DEC_TIPIVOCE;

-- Create Foreign Key Sec_Funzioniruoli
Alter Table Sec_Funzioniruoli Add Constraint SEC_FUNZIONIRUOLI_FK1 Foreign Key (CODFUNZIONE) references SEC_DEC_FUNZIONI;

-- Create Foreign Key Sec_Funzioniruoli
Alter Table Sec_Funzioniruoli Add Constraint SEC_FUNZIONIRUOLI_FK2 Foreign Key (CODRUOLO) references SEC_DEC_RUOLI;

-- Create Foreign Key Sec_Menuruoli
Alter Table Sec_Menuruoli Add Constraint SEC_MENURUOLI_FK1 Foreign Key (CODMENU) references SEC_DEC_MENU;

-- Create Foreign Key Sec_Menuruoli
Alter Table Sec_Menuruoli Add Constraint SEC_MENURUOLI_FK2 Foreign Key (CODRUOLO) references SEC_DEC_RUOLI;

-- Create Foreign Key Sec_Menuutenteruoli
Alter Table Sec_Menuutenteruoli Add Constraint SEC_MENUUTENTERUOLI_FK1 Foreign Key (CODMENUUTENTE) references SEC_DEC_MENUUTENTE;

-- Create Foreign Key Sec_Menuutenteruoli
Alter Table Sec_Menuutenteruoli Add Constraint SEC_MENUUTENTERUOLI_FK2 Foreign Key (CODRUOLO) references SEC_DEC_RUOLI;

-- Create Foreign Key Sec_Profili
Alter Table Sec_Profili Add Constraint SEC_PROFILI_FK1 Foreign Key (IDUTENTE) references SEC_UTENTI;

-- Create Foreign Key Sec_Profili
Alter Table Sec_Profili Add Constraint SEC_PROFILI_FK2 Foreign Key (CODRUOLO) references SEC_DEC_RUOLI;

