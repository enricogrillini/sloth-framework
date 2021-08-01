-- Primary Key AFORISMI
Alter Table AFORISMI Add Constraint AFORISMI_PK Primary Key (IDAFORISMA);

-- Primary Key SEC_Dec_Funzioni
Alter Table SEC_Dec_Funzioni Add Constraint SEC_DEC_FUNZIONI_PK Primary Key (CODFUNZIONE);

-- Primary Key SEC_Dec_Menu
Alter Table SEC_Dec_Menu Add Constraint SEC_DEC_MENU_PK Primary Key (CODMENU);

-- Primary Key SEC_Dec_Menuutente
Alter Table SEC_Dec_Menuutente Add Constraint SEC_DEC_MENUUTENTE_PK Primary Key (CODMENUUTENTE);

-- Primary Key SEC_Dec_Ruoli
Alter Table SEC_Dec_Ruoli Add Constraint SEC_DEC_RUOLI_PK Primary Key (CODRUOLO);

-- Primary Key SEC_Dec_Tipivoce
Alter Table SEC_Dec_Tipivoce Add Constraint SEC_DEC_TIPIVOCE_PK Primary Key (CODTIPOVOCE);

-- Primary Key SEC_Funzioniruoli
Alter Table SEC_Funzioniruoli Add Constraint SEC_FUNZIONIRUOLI_PK Primary Key (CODFUNZIONE,CODRUOLO);

-- Primary Key SEC_Menuruoli
Alter Table SEC_Menuruoli Add Constraint SEC_MENURUOLI_PK Primary Key (CODMENU,CODRUOLO);

-- Primary Key SEC_Menuutenteruoli
Alter Table SEC_Menuutenteruoli Add Constraint SEC_MENUUTENTERUOLI_PK Primary Key (CODMENUUTENTE,CODRUOLO);

-- Primary Key SEC_Profili
Alter Table SEC_Profili Add Constraint SEC_PROFILI_PK Primary Key (IDPROFILO);

-- Primary Key SEC_Utenti
Alter Table SEC_Utenti Add Constraint SEC_UTENTI_PK Primary Key (IDUTENTE);

