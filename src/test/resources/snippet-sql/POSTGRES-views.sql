-- View View_Sec_Funzioni
Create or replace view View_Sec_Funzioni As
 SELECT f.codfunzione,
    f.descrizionebreve,
    f.descrizionelunga,
    f.posizione,
    f.flagvalido,
    sec_getruolifunzione(f.codfunzione) AS ruoli
   FROM sec_dec_funzioni f;


-- View View_Sec_Menu
Create or replace view View_Sec_Menu As
 SELECT m.codmenu,
    m.descrizionebreve,
    m.descrizionelunga,
    m.posizione,
    m.flagvalido,
    m.codtipovoce,
    m.livello,
    m.imghtml,
    m.link,
    sec_getruolimenu(m.codmenu) AS ruoli
   FROM sec_dec_menu m;


-- View View_Sec_Menuutente
Create or replace view View_Sec_Menuutente As
 SELECT m.codmenuutente,
    m.descrizionebreve,
    m.descrizionelunga,
    m.posizione,
    m.flagvalido,
    m.codtipovoce,
    m.imghtml,
    m.link,
    sec_getruolimenuutente(m.codmenuutente) AS ruoli
   FROM sec_dec_menuutente m;


