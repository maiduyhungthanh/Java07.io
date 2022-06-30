## Trang chủ
![alt](https://share.balsamiq.com/g/dPhabAc146rNAMQ9xaKXXc.png)

create table APP_USER ( USER_ID BIGINT not null, USER_NAME VARCHAR(36) not null, ENCRYTED_PASSWORD VARCHAR(128) not null, ENABLED BIT not null ) ;
alter table APP_USER add constraint APP_USER_PK primary key (USER_ID);
alter table APP_USER add constraint APP_USER_UK unique (USER_NAME);
create table APP_ROLE ( ROLE_ID BIGINT not null, ROLE_NAME VARCHAR(30) not null ) ;
alter table APP_ROLE add constraint APP_ROLE_PK primary key (ROLE_ID);
create table USER_ROLE ( ID BIGINT not null, USER_ID BIGINT not null, ROLE_ID BIGINT not null );
alter table USER_ROLE add constraint USER_ROLE_PK primary key (ID);
alter table USER_ROLE add constraint USER_ROLE_UK unique (USER_ID, ROLE_ID);
alter table USER_ROLE add constraint USER_ROLE_FK1 foreign key (USER_ID) references APP_USER (USER_ID);
alter table USER_ROLE add constraint USER_ROLE_FK2 foreign key (ROLE_ID) references APP_ROLE (ROLE_ID);
alter table APP_ROLE add constraint APP_ROLE_UK unique (ROLE_NAME);
insert into App_User (USER_ID, USER_NAME, ENCRYTED_PASSWORD, ENABLED) values (1, 'user', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
insert into App_User (USER_ID, USER_NAME, ENCRYTED_PASSWORD, ENABLED) values (2, 'operator', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
insert into App_User (USER_ID, USER_NAME, ENCRYTED_PASSWORD, ENABLED) values (3, 'admin', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
insert into app_role (ROLE_ID, ROLE_NAME) values (3, 'ROLE_ADMIN');
insert into app_role (ROLE_ID, ROLE_NAME) values (2, 'ROLE_OPERATOR');
insert into app_role (ROLE_ID, ROLE_NAME) values (1, 'ROLE_USER');
insert into user_role (ID, USER_ID, ROLE_ID) values (1, 1, 1);
insert into user_role (ID, USER_ID, ROLE_ID) values (2, 2, 2);
insert into user_role (ID, USER_ID, ROLE_ID) values (3, 2, 1);
insert into user_role (ID, USER_ID, ROLE_ID) values (4, 3, 1);
insert into user_role (ID, USER_ID, ROLE_ID) values (5, 3, 2);
insert into user_role (ID, USER_ID, ROLE_ID) values (6, 3, 3);
CREATE TABLE Persistent_Logins (
username varchar(64) not null,
series varchar(64) not null,
token varchar(64) not null,
last_used timestamp not null,
PRIMARY KEY (series)
);