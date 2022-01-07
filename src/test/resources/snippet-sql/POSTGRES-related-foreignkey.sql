-- Foreign Key Sec_Menuruoli
Alter Table Sec_Menuruoli Add Constraint sec_menuruoli_fk1 Foreign Key (Codmenu) references sec_dec_menu;

