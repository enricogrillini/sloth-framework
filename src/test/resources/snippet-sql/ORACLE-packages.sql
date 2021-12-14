-- Package lib_calendario
Create or Replace package lib_calendario is

  LUNEDI constant date := to_date('01/01/2001', 'dd/mm/yyyy');
  PATRONO constant varchar2(6) := '04/10/';

  FESTIVO constant varchar2(10) := 'FEST';
  FERIALE constant varchar2(10) := 'FER';
  PREFESTIVO constant varchar2(10) := 'PRE';

  Function getPasqua(anno in integer) return date;

  -- Ritorna il giorno della settimana (0 = Lunedì)
  Function getDayOfWeek(inGiorno in date) return integer;

  Function getTipoGiorno (inGiorno in date) return varchar2;
  Function isFestivo (inGiorno in date) return boolean;

  Function isLunedi(inData in date) return boolean;
  Function isMartedi(inData in date) return boolean;
  Function isMercoledi(inData in date) return boolean;
  Function isGiovedi(inData in date) return boolean;
  Function isVenerdi(inData in date) return boolean;
  Function isSabato(inData in date) return boolean;
  Function isDomenica(inData in date) return boolean;

end;
/

Create or Replace package body lib_calendario is

  -- Recupera il giorno di Pasqua
  Function getPasqua(anno in integer) return date is
    C integer;
    N integer;
    K integer;
    I integer;
    J integer;
    L integer;

    Mese integer;
    Giorno integer;
  begin

     C := trunc(Anno / 100);
     N := Anno - 19 * trunc(Anno / 19);
     K := trunc((C - 17) / 25);
     I := C - trunc(C / 4) - trunc((C - K) / 3) + 19 * N + 15;
     I := I - 30 * trunc(I / 30);
     I := I - trunc(I / 28) * (1 - trunc(I / 28) * trunc(29 / (I + 1)) * trunc((21 - N) / 11));
     J := Anno + trunc(Anno / 4) + I + 2 - C + trunc(C / 4);
     J := J - 7 * trunc(J / 7);
     L := I - J;
     Mese := 3 + trunc((L + 40) / 44);
     Giorno := L + 28 - 31 * trunc(Mese / 4);

     return to_date (Giorno || '/' || Mese || '/' || Anno, 'dd/mm/yyyy');
  End;

  Function getDayOfWeek(inGiorno in date) return integer is
    begin
      return mod (greatest(inGiorno, LUNEDI) - least(inGiorno, LUNEDI), 7);
    end;

  Function getTipoGiorno (inGiorno in date) return varchar2 is
    begin
      if isFestivo (inGiorno) then
        return FESTIVO;
      elsif isSabato(inGiorno) then
        return PREFESTIVO;
      else
        return FERIALE;
      end if;
    end;

  function isFestivo (inGiorno in date) return boolean is
      Giorno date := trunc(inGiorno);
      Anno varchar2(4) := to_char(inGiorno, 'yyyy');

    begin
      if isDomenica(Giorno) then         -- DOMENICA
        return true;
      elsif Giorno = to_date ('01/01/' || Anno, 'dd/mm/yyyy') then                    -- 01/01 Capodanno
        return true;
      elsif Giorno = to_date ('06/01/' || Anno, 'dd/mm/yyyy') then                    -- 01/06 Epifania
        return true;
      elsif Giorno = to_date ('25/04/' || Anno, 'dd/mm/yyyy') then                    -- 25/04
        return true;
      elsif Giorno = to_date ('01/05/' || Anno, 'dd/mm/yyyy') then                    -- 01/05
        return true;
      elsif Giorno = to_date ('02/06/' || Anno, 'dd/mm/yyyy') then                    -- 02/06 Festa della Repubblica
        return true;
      elsif Giorno = to_date ('15/08/' || Anno, 'dd/mm/yyyy') then                    -- 15/08 Ferragosto
        return true;
      --elsif Giorno = to_date (PATRONO || Anno, 'dd/mm/yyyy') then                     -- Patrono
      --  return true;
      elsif Giorno = to_date ('01/11/' || Anno, 'dd/mm/yyyy') then                    -- 01/11 Ognisanti
        return true;
      elsif Giorno = to_date ('08/12/' || Anno, 'dd/mm/yyyy') then                    -- 08/12 Immacolata concezione
        return true;
      elsif Giorno = to_date ('25/12/' || Anno, 'dd/mm/yyyy') then                    -- 25/12 Natale
        return true;
      elsif Giorno = to_date ('26/12/' || Anno, 'dd/mm/yyyy') then                    -- 26/12 Santo stefano
        return true;
      elsif Giorno = getPasqua(Anno) then                                             --       Pasqua
        return true;
      elsif Giorno = getPasqua(Anno) + 1 then                                         --       Lunedì dell'Angelo
        return true;
      else
        return false;
      end if;

    end;

  Function isLunedi(inData in date) return boolean is
    begin
      return getDayOfWeek(inData) = 0;
    end;

  Function isMartedi(inData in date) return boolean is
    begin
      return getDayOfWeek(inData) = 1;
    end;

  Function isMercoledi(inData in date) return boolean is
    begin
      return getDayOfWeek(inData) = 2;
    end;

  Function isGiovedi(inData in date) return boolean is
    begin
      return getDayOfWeek(inData) = 3;
    end;

  Function isVenerdi(inData in date) return boolean is
    begin
      return getDayOfWeek(inData) = 4;
    end;

  Function isSabato(inData in date) return boolean is
    begin
      return getDayOfWeek(inData) = 5;
    end;


  Function isDomenica(inData in date) return boolean is
    begin
      return getDayOfWeek(inData) = 6;
    end;

end;
/

