-- Create Index frame_properties_idx1
Create Index frame_properties_idx1 on Frame_Properties (type,value);

-- Create Index sec_funzioniruoli_idx1
Create Index sec_funzioniruoli_idx1 on Sec_Funzioniruoli (codruolo);

-- Create Index sec_funzioniruoli_idx2
Create Index sec_funzioniruoli_idx2 on Sec_Funzioniruoli (codfunzione);

-- Create Index sec_menuruoli_idx1
Create Index sec_menuruoli_idx1 on Sec_Menuruoli (codruolo);

-- Create Index sec_menuruoli_idx2
Create Index sec_menuruoli_idx2 on Sec_Menuruoli (codmenu);

-- Create Index sec_menuutenteruoli_idx1
Create Index sec_menuutenteruoli_idx1 on Sec_Menuutenteruoli (codruolo);

-- Create Index sec_menuutenteruoli_idx2
Create Index sec_menuutenteruoli_idx2 on Sec_Menuutenteruoli (codmenuutente);

