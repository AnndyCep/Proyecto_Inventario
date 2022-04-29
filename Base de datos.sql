CREATE DATABASE productos;
USE productos;
create table productos (codigo INT auto_increment NOT NULL, 
nombre varchar (20) unique NOT null, 
precio double not null,
Inventario int not null,
primary key (codigo));

INSERT INTO productos (nombre,precio,inventario) values ("Camisa", 8000.0, 65), 
("Medias", 2300.0, 15), ("Cordones", 2500.0,  38), ("Cachuchas", 9300.0, 55), 
("Pantalonetas", 2100.0, 42), ("Chaquetas", 30000.0, 3), ("Zapatos", 40000.0, 41), ("Busos", 500.0, 8), 
("Gorros", 10000.0, 806), ("Guantes", 15000.0, 10);

select * from productos;
