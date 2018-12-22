drop table if exists conferences_participants;
drop table if exists users;
drop table if exists conferences;

-- Users
create table users
(
  id         bigint primary key auto_increment,
  email      varchar(255) unique not null,
  first_name varchar(255)        not null,
  last_name  varchar(255)        not null,
  password   varchar(255)        not null
);

-- Conferences
create table conferences
(
  id          bigint primary key auto_increment,
  name        varchar(255) not null,
  description text         not null,
  image_url   varchar(255) not null
);

-- Participants
create table conferences_participants
(
  conference_id bigint,
  user_id       bigint,
  constraint conferences_participants_pk PRIMARY KEY (conference_id, user_id),
  constraint conference_fk foreign key (conference_id) references conferences (id)
    on delete cascade,
  constraint user_fk foreign key (user_id) references users (id)
    on delete cascade
);