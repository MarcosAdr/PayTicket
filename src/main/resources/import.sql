/* Populates tables */
INSERT INTO tipo_evento (nombre,descripcion, date_created, last_update) VALUES('Cultural','Obras de teatro, Eventos artisticos, Fiestas tradicionales','2023-05-25 12:00:00', '2023-05-26 12:00:00');
INSERT INTO tipo_evento (nombre,descripcion, date_created, last_update) VALUES('Deportivo','Partidos de futbol, partidos de basquet, cualquier deporte','2023-05-25 12:00:00', '2023-05-26 12:00:00');

INSERT INTO evento (fecha_evento, hora_fin, hora_inicio, date_created, last_update, descripcion, media, nombre, total_entradas, tipo_evento_id) VALUES ('2023-05-26', '20:30:00', '21:30:00', '2023-05-25 12:00:00', '2023-05-26 12:00:00', 'Descripcion del evento 1', 'Imagen 1', 'Evento 1',50,1);
INSERT INTO evento (fecha_evento, hora_fin, hora_inicio, date_created, last_update, descripcion, media, nombre, total_entradas, tipo_evento_id) VALUES ('2023-05-26', '21:30:00', '22:30:00', '2023-05-25 12:00:00', '2023-05-26 12:00:00', 'Descripcion del evento 2', 'Imagen 2', 'Evento 2',0,2);
INSERT INTO evento (fecha_evento, hora_fin, hora_inicio, date_created, last_update, descripcion, media, nombre, total_entradas, tipo_evento_id) VALUES ('2023-05-26', '21:30:00', '22:30:00', '2023-05-25 12:00:00', '2023-05-26 12:00:00', 'Descripcion del evento 3', 'Imagen 3', 'Evento 3',100,1);
INSERT INTO evento (fecha_evento, hora_fin, hora_inicio, date_created, last_update, descripcion, media, nombre, total_entradas, tipo_evento_id) VALUES ('2023-05-26', '21:30:00', '22:30:00', '2023-05-25 12:00:00', '2023-05-26 12:00:00', 'Descripcion del evento 4', 'Imagen 4', 'Evento 4',50,2);

INSERT INTO precio(precio) VALUES (5);
INSERT INTO precio(precio) VALUES (3);
INSERT INTO precio(precio) VALUES (10);
INSERT INTO precio(precio) VALUES (10);

INSERT INTO localidad(nombre, descripcion, entradas, evento_id ,precio_id) VALUES('General', 'Público en general', 50,1,1);
INSERT INTO localidad(nombre, descripcion, entradas, evento_id ,precio_id) VALUES('Preferencia', 'Butacas autoridades', 50,1,2);
INSERT INTO localidad(nombre, descripcion, entradas, evento_id ,precio_id) VALUES('General', 'Público en general', 25,2,3);
INSERT INTO localidad(nombre, descripcion, entradas, evento_id ,precio_id) VALUES('VIP', 'Autoridades', 25,2,4);

INSERT INTO usuario(username,nombre,apellido,email, password, enabled) VALUES('marcosadr','marcos','lobato','marcoslobato98@gmail.com','$2a$10$Sbf6y6ictIHUkyqYnKNKCOyPzzQW3Rk2943BEtiO0hPDmFabT6A2i',1);
INSERT INTO usuario(username,nombre,apellido,email, password, enabled) VALUES('admin','marcos','lobato','marcosadmin@gmail.com','$2a$10$qhDmG1Ewl9NybPXfxw9Vyeivd24vi0hchZIJXWHc1fnSSaYRjM3ZO',1);

INSERT INTO role(usuario_id, rol) VALUES(1,'ROLE_USER');
INSERT INTO role(usuario_id, rol) VALUES(2,'ROLE_ADMIN');
INSERT INTO role(usuario_id, rol) VALUES(2,'ROLE_USER');
