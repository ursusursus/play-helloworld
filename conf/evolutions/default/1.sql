# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table drug (
  id                        bigint not null,
  name                      varchar(255),
  user_id                   bigint,
  constraint pk_drug primary key (id))
;

create table user (
  id                        bigint not null,
  email                     varchar(255),
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (id))
;

create sequence drug_seq;

create sequence user_seq;

alter table drug add constraint fk_drug_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_drug_user_1 on drug (user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists drug;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists drug_seq;

drop sequence if exists user_seq;

