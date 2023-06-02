/* Populates tables */
INSERT INTO tipo_evento (nombre,descripcion, date_created, last_update) VALUES('Cultural','Obras de teatro, Eventos artisticos, Fiestas tradicionales','2023-05-25 12:00:00', '2023-05-26 12:00:00');
INSERT INTO tipo_evento (nombre,descripcion, date_created, last_update) VALUES('Deportivo','Partidos de futbol, partidos de basquet, cualquier deporte','2023-05-25 12:00:00', '2023-05-26 12:00:00');

INSERT INTO evento (fecha_evento, hora_fin, hora_inicio, date_created, last_update, descripcion, media, nombre, total_entradas, tipo_evento_id) VALUES ('2023-05-26', '20:30:00', '21:30:00', '2023-05-25 12:00:00', '2023-05-26 12:00:00', 'Descripcion del evento 1', 'Imagen 1', 'Evento 1',100,1);
INSERT INTO evento (fecha_evento, hora_fin, hora_inicio, date_created, last_update, descripcion, media, nombre, total_entradas, tipo_evento_id) VALUES ('2023-05-26', '21:30:00', '22:30:00', '2023-05-25 12:00:00', '2023-05-26 12:00:00', 'Descripcion del evento 2', 'Imagen 2', 'Evento 2',50,2);
INSERT INTO evento (fecha_evento, hora_fin, hora_inicio, date_created, last_update, descripcion, media, nombre, total_entradas, tipo_evento_id) VALUES ('2023-05-26', '21:30:00', '22:30:00', '2023-05-25 12:00:00', '2023-05-26 12:00:00', 'Descripcion del evento 3', 'Imagen 3', 'Evento 3',100,1);
INSERT INTO evento (fecha_evento, hora_fin, hora_inicio, date_created, last_update, descripcion, media, nombre, total_entradas, tipo_evento_id) VALUES ('2023-05-26', '21:30:00', '22:30:00', '2023-05-25 12:00:00', '2023-05-26 12:00:00', 'Descripcion del evento 4', 'Imagen 4', 'Evento 4',50,2);

INSERT INTO localidad(nombre, descripcion, entradas, evento_id ) VALUES('Entrada única', 'Público en general', 100,1);
INSERT INTO localidad(nombre, descripcion, entradas, evento_id ) VALUES('General', 'Público en general', 25,2);
INSERT INTO localidad(nombre, descripcion, entradas, evento_id ) VALUES('VIP', 'Autoridades', 25,2);




