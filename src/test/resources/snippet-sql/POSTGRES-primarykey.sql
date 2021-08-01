-- Primary Key Aforismi
Alter Table Aforismi Add Constraint aforismi_pk Primary Key (Idaforisma);

-- Primary Key Sec_Dec_Funzioni
Alter Table Sec_Dec_Funzioni Add Constraint sec_dec_funzioni_pk Primary Key (Codfunzione);

-- Primary Key Sec_Dec_Menu
Alter Table Sec_Dec_Menu Add Constraint sec_dec_menu_pk Primary Key (Codmenu);

-- Primary Key Sec_Dec_Menuutente
Alter Table Sec_Dec_Menuutente Add Constraint sec_dec_menuutente_pk Primary Key (Codmenuutente);

-- Primary Key Sec_Dec_Ruoli
Alter Table Sec_Dec_Ruoli Add Constraint sec_dec_ruoli_pk Primary Key (Codruolo);

-- Primary Key Sec_Dec_Tipivoce
Alter Table Sec_Dec_Tipivoce Add Constraint sec_dec_tipivoce_pk Primary Key (Codtipovoce);

-- Primary Key Sec_Funzioniruoli
Alter Table Sec_Funzioniruoli Add Constraint sec_funzioniruoli_pk Primary Key (Codfunzione,Codruolo);

-- Primary Key Sec_Menuruoli
Alter Table Sec_Menuruoli Add Constraint sec_menuruoli_pk Primary Key (Codmenu,Codruolo);

-- Primary Key Sec_Menuutenteruoli
Alter Table Sec_Menuutenteruoli Add Constraint sec_menuutenteruoli_pk Primary Key (Codmenuutente,Codruolo);

-- Primary Key Sec_Profili
Alter Table Sec_Profili Add Constraint sec_profili_pk Primary Key (Idprofilo);

-- Primary Key Sec_Utenti
Alter Table Sec_Utenti Add Constraint sec_utenti_pk Primary Key (Idutente);

