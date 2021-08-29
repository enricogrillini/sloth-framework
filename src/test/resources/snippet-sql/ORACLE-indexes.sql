-- Create Index AFORISMI_PK
Create Unique Index AFORISMI_PK on Aforismi (IDAFORISMA)
Tablespace USERS
Storage (Initial 128K);

-- Create Index MONITOR_LOGIN_PK
Create Unique Index MONITOR_LOGIN_PK on Monitor_Login (IDLOGIN)
Tablespace USERS
Storage (Initial 64K);

-- Create Index SEC_DEC_FUNZIONI_PK
Create Unique Index SEC_DEC_FUNZIONI_PK on Sec_Dec_Funzioni (CODFUNZIONE)
Tablespace USERS
Storage (Initial 128K);

-- Create Index SEC_DEC_MENU_PK
Create Unique Index SEC_DEC_MENU_PK on Sec_Dec_Menu (CODMENU)
Tablespace USERS
Storage (Initial 128K);

-- Create Index SEC_DEC_MENUUTENTE_PK
Create Unique Index SEC_DEC_MENUUTENTE_PK on Sec_Dec_Menuutente (CODMENUUTENTE)
Tablespace USERS
Storage (Initial 128K);

-- Create Index SEC_DEC_RUOLI_PK
Create Unique Index SEC_DEC_RUOLI_PK on Sec_Dec_Ruoli (CODRUOLO)
Tablespace USERS
Storage (Initial 64K);

-- Create Index SEC_DEC_TIPIVOCE_PK
Create Unique Index SEC_DEC_TIPIVOCE_PK on Sec_Dec_Tipivoce (CODTIPOVOCE)
Tablespace USERS
Storage (Initial 64K);

-- Create Index SEC_FUNZIONIRUOLI_IDX1
Create Index SEC_FUNZIONIRUOLI_IDX1 on Sec_Funzioniruoli (CODRUOLO)
Tablespace USERS
Storage (Initial 64K);

-- Create Index SEC_FUNZIONIRUOLI_IDX2
Create Index SEC_FUNZIONIRUOLI_IDX2 on Sec_Funzioniruoli (CODFUNZIONE)
Tablespace USERS
Storage (Initial 64K);

-- Create Index SEC_FUNZIONIRUOLI_PK
Create Unique Index SEC_FUNZIONIRUOLI_PK on Sec_Funzioniruoli (CODFUNZIONE,CODRUOLO)
Tablespace USERS
Storage (Initial 64K);

-- Create Index SEC_MENURUOLI_IDX1
Create Index SEC_MENURUOLI_IDX1 on Sec_Menuruoli (CODRUOLO)
Tablespace USERS
Storage (Initial 64K);

-- Create Index SEC_MENURUOLI_IDX2
Create Index SEC_MENURUOLI_IDX2 on Sec_Menuruoli (CODMENU)
Tablespace USERS
Storage (Initial 64K);

-- Create Index SEC_MENURUOLI_PK
Create Unique Index SEC_MENURUOLI_PK on Sec_Menuruoli (CODMENU,CODRUOLO)
Tablespace USERS
Storage (Initial 64K);

-- Create Index SEC_MENUUTENTERUOLI_IDX1
Create Index SEC_MENUUTENTERUOLI_IDX1 on Sec_Menuutenteruoli (CODRUOLO)
Tablespace USERS
Storage (Initial 64K);

-- Create Index SEC_MENUUTENTERUOLI_IDX2
Create Index SEC_MENUUTENTERUOLI_IDX2 on Sec_Menuutenteruoli (CODMENUUTENTE)
Tablespace USERS
Storage (Initial 64K);

-- Create Index SEC_MENUUTENTERUOLI_PK
Create Unique Index SEC_MENUUTENTERUOLI_PK on Sec_Menuutenteruoli (CODMENUUTENTE,CODRUOLO)
Tablespace USERS
Storage (Initial 64K);

-- Create Index SEC_PROFILI_PK
Create Unique Index SEC_PROFILI_PK on Sec_Profili (IDPROFILO)
Tablespace USERS
Storage (Initial 64K);

-- Create Index SEC_UTENTI_PK
Create Unique Index SEC_UTENTI_PK on Sec_Utenti (IDUTENTE)
Tablespace USERS
Storage (Initial 128K);

