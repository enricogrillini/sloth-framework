-- Create Foreign Key Monitor_Login
Alter Table Monitor_Login Add Constraint monitor_login_fk1 Foreign Key (Idutente) references sec_utenti;

-- Create Foreign Key Sec_Dec_Menu
Alter Table Sec_Dec_Menu Add Constraint sec_menu_fk1 Foreign Key (Codtipovoce) references sec_dec_tipivoce;

-- Create Foreign Key Sec_Dec_Menuutente
Alter Table Sec_Dec_Menuutente Add Constraint sec_dec_menuutente_fk1 Foreign Key (Codtipovoce) references sec_dec_tipivoce;

-- Create Foreign Key Sec_Funzioniruoli
Alter Table Sec_Funzioniruoli Add Constraint sec_funzioniruoli_fk1 Foreign Key (Codfunzione) references sec_dec_funzioni;

-- Create Foreign Key Sec_Funzioniruoli
Alter Table Sec_Funzioniruoli Add Constraint sec_funzioniruoli_fk2 Foreign Key (Codruolo) references sec_dec_ruoli;

-- Create Foreign Key Sec_Menuruoli
Alter Table Sec_Menuruoli Add Constraint sec_menuruoli_fk1 Foreign Key (Codmenu) references sec_dec_menu;

-- Create Foreign Key Sec_Menuruoli
Alter Table Sec_Menuruoli Add Constraint sec_menuruoli_fk2 Foreign Key (Codruolo) references sec_dec_ruoli;

-- Create Foreign Key Sec_Menuutenteruoli
Alter Table Sec_Menuutenteruoli Add Constraint sec_menuutenteruoli_fk1 Foreign Key (Codmenuutente) references sec_dec_menuutente;

-- Create Foreign Key Sec_Menuutenteruoli
Alter Table Sec_Menuutenteruoli Add Constraint sec_menuutenteruoli_fk2 Foreign Key (Codruolo) references sec_dec_ruoli;

-- Create Foreign Key Sec_Profili
Alter Table Sec_Profili Add Constraint sec_profili_fk1 Foreign Key (Idutente) references sec_utenti;

-- Create Foreign Key Sec_Profili
Alter Table Sec_Profili Add Constraint sec_profili_fk2 Foreign Key (Codruolo) references sec_dec_ruoli;

