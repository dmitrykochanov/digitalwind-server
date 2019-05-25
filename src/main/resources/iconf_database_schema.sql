drop table if exists users_roles;
drop table if exists users;
drop table if exists roles;

-- Auth
create table users
(
  id         bigint primary key auto_increment,
  login      varchar(255) unique not null,
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
