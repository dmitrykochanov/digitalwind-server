drop table if exists users_roles;
drop table if exists comments;
drop table if exists users;
drop table if exists roles;
drop table if exists news;
drop table if exists pictures;


-- Auth
create table users
(
    id       bigint primary key auto_increment,
    login    varchar(255) unique not null,
    password varchar(255)        not null
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

-- News
create table news
(
    id        bigint primary key auto_increment,
    title     varchar(255) not null,
    body      text         not null,
    image_url varchar(255)
);

-- Pictures
create table pictures
(
    id          bigint primary key auto_increment,
    title       varchar(255) not null,
    image_url   varchar(255),
    number      bigint,
    city        varchar(255),
    rate        int,
    description text
);

create table comments
(
    id         bigint primary key auto_increment,
    picture_id bigint   not null,
    date       bigint not null,
    author_id  bigint   not null,
    body       text     not null,
    constraint comments_pictures_fk foreign key (picture_id) references pictures (id) on delete cascade,
    constraint comments_users_fk foreign key (author_id) references users (id) on delete cascade
);
