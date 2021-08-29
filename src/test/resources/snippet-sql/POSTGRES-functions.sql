-- Function sec_getruolifunzione
CREATE OR REPLACE FUNCTION gildace.sec_getruolifunzione(incodfunzione character varying)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
DECLARE

  Result Varchar(1000);

  Cruoli Cursor For
    Select R.Descrizionebreve
    From Sec_Dec_Ruoli R, Sec_Funzioniruoli Fr
    Where R.Codruolo = Fr.Codruolo And
          Fr.Codfunzione = Incodfunzione And
          Fr.Flagaccesso = 'S'
    Order by 1;

begin
  Result := '';

  For Rruoli In Cruoli Loop
    If Result = '' Then
      Result := Result || Rruoli.Descrizionebreve;
    Else
      Result := Result || ', ' || Rruoli.Descrizionebreve;
    End If;
  End Loop;

  return Result;
End;
$function$
;

-- Function sec_getruolimenu
CREATE OR REPLACE FUNCTION gildace.sec_getruolimenu(incodmenu character varying)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
DECLARE

  Result Varchar(1000);

  Cruoli Cursor For
    Select R.Descrizionebreve
    From Sec_Dec_Ruoli R, Sec_Menuruoli mr
    Where R.Codruolo = mr.Codruolo And
          mr.CodMenu = IncodMenu And
          mr.Flagaccesso = 'S'
    Order by 1;

begin
  Result := '';

  For Rruoli In Cruoli Loop
    If Result = '' Then
      Result := Result || Rruoli.Descrizionebreve;
    Else
      Result := Result || ', ' || Rruoli.Descrizionebreve;
    End If;
  End Loop;

  return Result;
End;
$function$
;

-- Function sec_getruolimenuutente
CREATE OR REPLACE FUNCTION gildace.sec_getruolimenuutente(incodmenuutente character varying)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
DECLARE

  Result Varchar(1000);

  Cruoli Cursor For
    Select R.Descrizionebreve
    From Sec_Dec_Ruoli R, Sec_MenuUtenteruoli mr
    Where R.Codruolo = mr.Codruolo And
          mr.CodMenuUtente = IncodMenuUtente And
          mr.Flagaccesso = 'S'
    Order by 1;


begin
  Result := '';

  For Rruoli In Cruoli Loop
    If Result = '' Then
      Result := Result || Rruoli.Descrizionebreve;
    Else
      Result := Result || ', ' || Rruoli.Descrizionebreve;
    End If;
  End Loop;

  return Result;
End;
$function$
;

