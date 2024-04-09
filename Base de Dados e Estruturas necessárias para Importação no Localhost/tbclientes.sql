
use dbinfo;
create table tbclientes (
idcliente int primary key auto_increment,
nomecliente varchar (50) not null,
email varchar (50),
dataNascimento date not null,
sexo set ('Masculino', 'Feminino'),
naturalidade varchar (50),
Nacionalidade varchar (50),
endereco varchar (50)
);

desc tbclientes;