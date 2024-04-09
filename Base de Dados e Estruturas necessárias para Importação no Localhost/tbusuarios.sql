create database dbinfo;
use dbinfo;
create table if not exists tbusuario(
iduser int primary key auto_increment,
nomeuser varchar (50) not null,
telefone varchar (9),
login varchar (50) not null unique,
senha varchar (30) not null
);

insert into tbusuario (nomeuser, telefone, login, senha)
values ( 'Alves Hatewa', '946602461', 'almohawa', '123456' );
insert into tbusuario (nomeuser, telefone, login, senha)
values ( 'Francisco António', '943202464', 'franio', '654321' );
insert into tbusuario (nomeuser, telefone, login, senha)
values ('Domingos Norónio', '926312443', 'domino', '098765' );


desc tbusuario;
select * from tbusuario;

update tbusuario set nomeuser='Francisco Carlos António' where iduser='2';

