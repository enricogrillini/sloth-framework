-- Foreign Key SEC_Dec_Menu
Alter Table SEC_Dec_Menu Add Constraint SEC_MENU_FK1 Foreign Key (CODTIPOVOCE) references SEC_DEC_TIPIVOCE;

-- Foreign Key SEC_Dec_Menuutente
Alter Table SEC_Dec_Menuutente Add Constraint SEC_DEC_MENUUTENTE_FK1 Foreign Key (CODTIPOVOCE) references SEC_DEC_TIPIVOCE;

-- Foreign Key SEC_Funzioniruoli
Alter Table SEC_Funzioniruoli Add Constraint SEC_FUNZIONIRUOLI_FK1 Foreign Key (CODFUNZIONE) references SEC_DEC_FUNZIONI;

-- Foreign Key SEC_Funzioniruoli
Alter Table SEC_Funzioniruoli Add Constraint SEC_FUNZIONIRUOLI_FK2 Foreign Key (CODRUOLO) references SEC_DEC_RUOLI;

-- Foreign Key SEC_Menuruoli
Alter Table SEC_Menuruoli Add Constraint SEC_MENURUOLI_FK1 Foreign Key (CODMENU) references SEC_DEC_MENU;

-- Foreign Key SEC_Menuruoli
Alter Table SEC_Menuruoli Add Constraint SEC_MENURUOLI_FK2 Foreign Key (CODRUOLO) references SEC_DEC_RUOLI;

-- Foreign Key SEC_Menuutenteruoli
Alter Table SEC_Menuutenteruoli Add Constraint SEC_MENUUTENTERUOLI_FK1 Foreign Key (CODMENUUTENTE) references SEC_DEC_MENUUTENTE;

-- Foreign Key SEC_Menuutenteruoli
Alter Table SEC_Menuutenteruoli Add Constraint SEC_MENUUTENTERUOLI_FK2 Foreign Key (CODRUOLO) references SEC_DEC_RUOLI;

-- Foreign Key SEC_Profili
Alter Table SEC_Profili Add Constraint SEC_PROFILI_FK1 Foreign Key (IDUTENTE) references SEC_UTENTI;

-- Foreign Key SEC_Profili
Alter Table SEC_Profili Add Constraint SEC_PROFILI_FK2 Foreign Key (CODRUOLO) references SEC_DEC_RUOLI;

