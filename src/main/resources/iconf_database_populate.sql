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
