-- Create Related Foreign Key Sec_Menuruoli
Alter Table Sec_Menuruoli Add Constraint SEC_MENURUOLI_FK1 Foreign Key (CODMENU) references SEC_DEC_MENU;

