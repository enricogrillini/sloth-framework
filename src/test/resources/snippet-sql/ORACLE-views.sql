-- View View_Sec_Funzioni
Create or replace view View_Sec_Funzioni As
Select f."CODFUNZIONE",f."DESCRIZIONEBREVE",f."DESCRIZIONELUNGA",f."POSIZIONE",f."FLAGVALIDO",
       sec_GetruoliFunzione(f.codFunzione) Ruoli
from sec_dec_funzioni f
/

-- View View_Sec_Menu
Create or replace view View_Sec_Menu As
Select m."CODMENU",m."DESCRIZIONEBREVE",m."DESCRIZIONELUNGA",m."POSIZIONE",m."FLAGVALIDO",m."CODTIPOVOCE",m."LIVELLO",m."IMGHTML",m."LINK",
       sec_GetruoliMenu(m.codMenu) Ruoli
from Sec_Dec_Menu m
/

-- View View_Sec_Menuutente
Create or replace view View_Sec_Menuutente As
Select m."CODMENUUTENTE",m."DESCRIZIONEBREVE",m."DESCRIZIONELUNGA",m."POSIZIONE",m."FLAGVALIDO",m."CODTIPOVOCE",m."IMGHTML",m."LINK",
       sec_GetruoliMenuUtente(m.codMenuUtente) Ruoli
from Sec_Dec_Menuutente m
/

