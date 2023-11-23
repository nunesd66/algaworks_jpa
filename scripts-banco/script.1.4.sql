create database cadastroevento;

use cadastroevento;

create table cadastroevento.evento (
	id bigint not null auto_increment,
    nome varchar(60) not null,
    data datetime not null,
    primary key(id)
);

select * from cadastroevento.evento;

insert into evento (id, nome, data) values (null, 'Palestra João Silva', sysdate());


