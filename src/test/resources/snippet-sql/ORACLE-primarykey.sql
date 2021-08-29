-- Primary Key Aforismi
Alter Table Aforismi Add Constraint AFORISMI_PK Primary Key (IDAFORISMA);

-- Primary Key Monitor_Login
Alter Table Monitor_Login Add Constraint MONITOR_LOGIN_PK Primary Key (IDLOGIN);

-- Primary Key Sec_Dec_Funzioni
Alter Table Sec_Dec_Funzioni Add Constraint SEC_DEC_FUNZIONI_PK Primary Key (CODFUNZIONE);

-- Primary Key Sec_Dec_Menu
Alter Table Sec_Dec_Menu Add Constraint SEC_DEC_MENU_PK Primary Key (CODMENU);

-- Primary Key Sec_Dec_Menuutente
Alter Table Sec_Dec_Menuutente Add Constraint SEC_DEC_MENUUTENTE_PK Primary Key (CODMENUUTENTE);

-- Primary Key Sec_Dec_Ruoli
Alter Table Sec_Dec_Ruoli Add Constraint SEC_DEC_RUOLI_PK Primary Key (CODRUOLO);

-- Primary Key Sec_Dec_Tipivoce
Alter Table Sec_Dec_Tipivoce Add Constraint SEC_DEC_TIPIVOCE_PK Primary Key (CODTIPOVOCE);

-- Primary Key Sec_Funzioniruoli
Alter Table Sec_Funzioniruoli Add Constraint SEC_FUNZIONIRUOLI_PK Primary Key (CODFUNZIONE,CODRUOLO);

-- Primary Key Sec_Menuruoli
Alter Table Sec_Menuruoli Add Constraint SEC_MENURUOLI_PK Primary Key (CODMENU,CODRUOLO);

-- Primary Key Sec_Menuutenteruoli
Alter Table Sec_Menuutenteruoli Add Constraint SEC_MENUUTENTERUOLI_PK Primary Key (CODMENUUTENTE,CODRUOLO);

-- Primary Key Sec_Profili
Alter Table Sec_Profili Add Constraint SEC_PROFILI_PK Primary Key (IDPROFILO);

-- Primary Key Sec_Utenti
Alter Table Sec_Utenti Add Constraint SEC_UTENTI_PK Primary Key (IDUTENTE);

