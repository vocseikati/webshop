create table categories_aud (id int4 not null, rev int4 not null, revtype int2, name varchar(255), primary key (id, rev));
create table products_aud (id int4 not null, rev int4 not null, revtype int2, name varchar(255), price float8, category_id int4, primary key (id, rev));
create table revinfo (rev int4 not null, revtstmp int8, primary key (rev));
alter table if exists categories_aud add constraint FK6ti58h8w0q47oacscu06hcite foreign key (rev) references revinfo;
alter table if exists products_aud add constraint FKis5p0x6t8gvib9m5ra1fiybi3 foreign key (rev) references revinfo;
