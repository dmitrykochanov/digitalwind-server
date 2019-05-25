insert into users(id, login, password)
values (1, 'user1', '$2a$10$GZy/3PC0HhY2a3AByoYs1uxYpJ7K59R6tzgpix/VQSOvQQChMrdO.');
insert into users(id, login, password)
values (2, 'admin1', '$2a$10$maLtWx5w0qollVsT7HANV.dffyvjdAodXJH.AuQAp.92VdyURdhQi');

insert into roles (id, name)
values (1, 'USER');
insert into roles (id, name)
values (2, 'ADMIN');

insert into users_roles (user_id, role_id)
values (1, 1);
insert into users_roles (user_id, role_id)
values (2, 1);
insert into users_roles (user_id, role_id)
values (2, 2);

insert into news (title, body, image_url)
VALUES ('news1', 'body1', 'url1');
insert into news (title, body, image_url)
VALUES ('news2', 'body2', 'url3');
insert into news (title, body, image_url)
VALUES ('news3', 'body3', 'url3');

insert into pictures (title, image_url, number, city, description)
values ('picture1', 'url1', '1111111111', 'saratov', 'description1');
insert into pictures (title, image_url, number, city, description)
values ('picture2', 'url2', '2222222222', 'saratov', 'description2');
insert into pictures (title, image_url, number, city, description)
values ('picture3', 'url3', '3333333333', 'saratov', 'description3');

insert into comments (picture_id, date, author_id, body)
values ('1', '1558790033', '1', 'comment1');
insert into comments (picture_id, date, author_id, body)
values ('1', '1558790500', '2', 'comment2');

insert into ratings (user_id, picture_id, rate)
values ('1', '1', '5');
insert into ratings (user_id, picture_id, rate)
values ('2', '1', '10');
