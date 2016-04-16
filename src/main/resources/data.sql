
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
(3, 'siva@gmail.com', '$2a$10$UFEPYW7Rx1qZqdHajzOnB.VBR3rvm7OI7uSix4RadfQiNhkZOi2fi', 'Siva'),
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
