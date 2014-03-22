--liquibase formatted sql

--changeset splumb:create schema
create schema if not exists splumb;
--rollback drop schema splumb;

create table splumb.logs (
  id      int identity(1,1) primary key not null
  ,when   datetime not null
  ,level  varchar(32) not null
  ,msg    varchar(1024) not null
  ,logger varchar(200) not null
  ,thread varchar(200) not null
);
--rollback drop table splumb.logs;


create table splumb.logconfig(
  logger varchar(128) primary key not null
  ,level varchar(40) not null
);
--rollback drop table splumb.logconfig;