-- Create Index AFORISMI_PK
Create Unique Index AFORISMI_PK on AFORISMI (IDAFORISMA);

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

