use dbinfo;
create table tbordemservico (
os int primary key,
dataos timestamp default current_timestamp,
mercadoria varchar (100) not null,
servico varchar (100) not null,
tecnico varchar (50) not null,
valor float (10,5) not null,
idcliente int not null,
foreign key(idcliente) references tbclientes(idcliente)
);


select
c.os,dataos,mercadoria, servico, tecnico, valor, 
cl.nomecliente,endereco
from tbordemservico as c
inner join tbclientes as cl
on (c.idcliente = cl.idcliente);