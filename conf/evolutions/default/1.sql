# --- First database schema

# --- !Ups

set ignorecase true;

create sequence client_seq start with 1;

create sequence project_seq start with 1;

create sequence resource_seq start with 1;

create sequence booking_seq start with 1;

create table client (
  id                        bigint not null,
  name                      varchar(255) not null,
  constraint pk_client primary key (id))
;

create table project (
  id                        bigint not null,
  client_id                 bigint not null,
  code                      varchar(100) not null,
  name                      varchar(255) not null,
  owner                     varchar(255) not null,
  location                  varchar(255) not null,
  constraint pk_project primary key (id) ,
  constraint fk_project_client foreign key (client_id) references client (id) on delete cascade)
;

create table resource (
  id                        bigint not null,
  project_id                bigint not null,
  firstName                 varchar(255) ,
  lastName                  varchar(255) ,
  role                      varchar(255) not null,
  department                varchar(255) not null,
  constraint pk_resource primary key (id),
  constraint fk_resource_project foreign key (project_id) references project (id) on delete cascade)
;

create table booking (
  id                        bigint not null,
  resource_id               bigint not null,
  hours                     varchar(50) not null,
  bookingDate               date not null,
  status                    varchar(100) not null,
  constraint pk_booking  primary key (id),
  constraint fk_booking_resource foreign key (resource_id) references resource(id) on delete cascade)
;
# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists booking;

drop table if exists resource;

drop table if exists project;

drop table if exists client;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists client_seq;

drop sequence if exists project_seq;

drop sequence if exists resource_seq;

drop sequence if exists booking_seq;
