create sequence hibernate_sequence start 1 increment 1;
create table categories (id int4 not null, name varchar(255), primary key (id));
alter table if exists categories add constraint UK_t8o6pivur7nn124jehx7cygw5 unique (name);