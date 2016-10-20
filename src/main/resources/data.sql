
delete from emp_schedule;
delete from emp_status;
delete from role_permission;
delete from  user_role;
delete from  permissions;
delete from  roles;
delete from  users;


INSERT INTO permissions (id, name) VALUES 
(1, 'MANAGE_USERS'),
(2, 'MANAGE_ROLES'),
(3, 'MANAGE_PERMISSIONS'),
(4, 'MANAGE_SETTINGS')
;

INSERT INTO roles (id, name) VALUES 
(1, 'ROLE_SUPER_ADMIN'),
(2, 'ROLE_ADMIN'),
(3, 'ROLE_USER')
;

INSERT INTO users (id, email, password, name) VALUES 
(1, 'superadmin@gmail.com', '$2a$10$p3PHnpoBAnzZiL8mr3gMieMhVVSb585ajC2ZsBB0kwb4KvZKFSdNi', 'Super Admin'),
(2, 'admin@gmail.com', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 'Admin'),
(3, 'jim@gmail.com', '$2a$10$UFEPYW7Rx1qZqdHajzOnB.VBR3rvm7OI7uSix4RadfQiNhkZOi2fi', 'jim'),
(4, 'user@gmail.com', '$2a$10$ByIUiNaRfBKSV6urZoBBxe4UbJ/sS6u1ZaPORHF9AtNWAuVPVz1by', 'DemoUser')
;

insert into role_permission(role_id, perm_id) values
(1,1),(1,2),(1,3),(1,4),
(2,1),(2,2),(2,3),
(3,4)
;

insert into user_role(user_id, role_id) values
(1,1),
(2,2),
(3,3),
(4,3)
;

insert into emp_status (id, code, description, style) values
('1','AV_OFF','Available - In Office','btn-info'),
('2','AV_SHIP','Available - On Ship(Allure)','btn-primary'),
('3','OOO_VAC','Out Of Office - Vacation','btn-danger'),
('4','OOO_SHIP','Out Of Office - On Ship','btn-warning'),
('5','OOO_DD','Out Of Office - Dry Dock','btn-default'),
('6','OOO_NB','Out Of Office - New Build','bg-olive')
;

insert into emp_schedule(id, emp_id, from_date, to_date, status) values
(1,2,'2016-01-01', '2016-01-10', 2),

(2,2,'2016-02-01', '2016-02-15', 1),
(3,2,'2016-03-01', '2016-03-15', 2),
(4,2,'2016-04-01', '2016-04-20', 3),
(5,2,'2016-05-01', '2016-05-10', 3),
(6,2,'2016-06-01', '2016-06-25', 5),

(7,3,'2016-01-01', '2016-01-30', 1),
(8,3,'2016-02-01', '2016-02-28', 1),
(9,3,'2016-03-01', '2016-03-30', 5),
(10,3,'2016-04-01', '2016-04-30', 5),
(11,3,'2016-05-01', '2016-05-30', 5),
(12,3,'2016-06-01', '2016-06-30', 6),

(13,4,'2016-01-01', '2016-01-30', 4),
(14,4,'2016-02-01', '2016-02-28', 4),
(15,4,'2016-03-01', '2016-03-30', 4),
(16,4,'2016-04-01', '2016-04-30', 4),
(17,4,'2016-05-01', '2016-05-30', 2),
(18,4,'2016-06-01', '2016-06-30', 2),

(19,2,'2016-01-15', '2016-01-25', 3)
;