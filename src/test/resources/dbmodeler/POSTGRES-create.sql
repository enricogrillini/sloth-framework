Create table pg_class (
	relname varchar2(100),
	relkind varchar2(1)
);

Insert into pg_class (relname, relkind) values ('seq_idprova', 'S');