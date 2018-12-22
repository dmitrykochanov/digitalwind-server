drop table if exists users_roles;
drop table if exists conferences_participants;
drop table if exists users;
drop table if exists roles;
drop table if exists conferences;

-- Auth
create table users
(
  id         bigint primary key auto_increment,
  email      varchar(255) unique not null,
  first_name varchar(255)        not null,
  last_name  varchar(255)        not null,
  password   varchar(255)        not null
);

create table roles
(
  id   bigint primary key auto_increment,
  name varchar(64) unique not null
);

create table users_roles
(
  user_id bigint,
  role_id bigint,
  constraint users_roles_pk primary key (user_id, role_id),
  constraint users_roles_user_fk foreign key (user_id) references users (id) on delete cascade,
  constraint users_roles_role_fk foreign key (role_id) references roles (id) on delete cascade
);

-- Conferences
create table conferences
(
  id          bigint primary key auto_increment,
  name        varchar(255) not null,
  description text         not null,
  image_url   varchar(255) not null
);

create table conferences_participants
(
  conference_id bigint,
  user_id       bigint,
  constraint conferences_participants_pk primary key (conference_id, user_id),
  constraint conferences_participants_conference_fk foreign key (conference_id) references conferences (id) on delete cascade,
  constraint conferences_participants_user_fk foreign key (user_id) references users (id) on delete cascade
);