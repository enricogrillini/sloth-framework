-- Create Table
Create Table Prova
      (Id Integer not null,
       Testo varchar2(100),
       Data date);

alter table prova add constraint prova_pk primary key (Id);

Insert into Prova (Id, Testo, Data)  values (1, 'Aaaaa', PARSEDATETIME('01/01/2020', 'dd/MM/yyyy'));
Insert into Prova (Id, Testo, Data) values (2, 'Bbbbb',  PARSEDATETIME('01/01/2020', 'dd/MM/yyyy'));

