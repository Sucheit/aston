insert into groups (name, description)
values ('group1', 'group1desc1');
insert into groups (name, description)
values ('group2', 'group2desc2');


insert into users (first_name, last_name)
values ('emma', 'hill');
insert into users (first_name, last_name)
values ('tom', 'anderson');


insert into user_groups (user_id, group_id)
values (1, 1);
insert into user_groups (user_id, group_id)
values (1, 2);
insert into user_groups (user_id, group_id)
values (2, 2);