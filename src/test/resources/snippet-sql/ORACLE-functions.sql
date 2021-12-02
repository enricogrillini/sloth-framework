-- Function SEC_GETRUOLIFUNZIONE
Create or Replace Function sec_GetruoliFunzione (InCodFunzione In Varchar2) Return Varchar2 is
  Result Varchar2(1000);

  Cursor Cruoli Is
    Select R.Descrizionebreve
    From Sec_Dec_Ruoli R, Sec_Funzioniruoli Fr
    Where R.Codruolo = Fr.Codruolo And
          Fr.Codfunzione = Incodfunzione And
          Fr.Flagaccesso = 'S'
    Order by 1;

Begin
  For Rruoli In Cruoli Loop
    If Result Is Null Then
      Result := Result || Rruoli.Descrizionebreve;
    Else
      Result := Result || ', ' || Rruoli.Descrizionebreve;
    End If;
  End Loop;

  return Result;
End;


-- Function SEC_GETRUOLIMENU
Create or Replace Function sec_GetruoliMenu (InCodMenu In Varchar2) Return Varchar2 is
  Result Varchar2(1000);

  Cursor Cruoli Is
    Select R.Descrizionebreve
    From Sec_Dec_Ruoli R, Sec_Menuruoli mr
    Where R.Codruolo = mr.Codruolo And
          mr.CodMenu = IncodMenu And
          mr.Flagaccesso = 'S'
    Order by 1;

Begin
  For Rruoli In Cruoli Loop
    If Result Is Null Then
      Result := Result || Rruoli.Descrizionebreve;
    Else
      Result := Result || ', ' || Rruoli.Descrizionebreve;
    End If;
  End Loop;

  return Result;
End;


-- Function SEC_GETRUOLIMENUUTENTE
Create or Replace Function Sec_GetruoliMenuUtente (InCodMenuUtente In Varchar2) Return Varchar2 is
  Result Varchar2(1000);

  Cursor Cruoli Is
    Select R.Descrizionebreve
    From Sec_Dec_Ruoli R, Sec_MenuUtenteruoli mr
    Where R.Codruolo = mr.Codruolo And
          mr.CodMenuUtente = IncodMenuUtente And
          mr.Flagaccesso = 'S'
    Order by 1;

Begin
  For Rruoli In Cruoli Loop
    If Result Is Null Then
      Result := Result || Rruoli.Descrizionebreve;
    Else
      Result := Result || ', ' || Rruoli.Descrizionebreve;
    End If;
  End Loop;

  return Result;
End;


