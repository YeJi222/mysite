alter table user add column role enum('USER', 'ADMIN') NOT NULL DEFAULT 'USER';

desc user;
