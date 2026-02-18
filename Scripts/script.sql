CREATE DATABASE BDRURAL CHARACTER SET utf8 COLLATE utf8_general_ci;

use BDRURAL

CREATE USER 'rural'@'%' IDENTIFIED BY 'passrural';

GRANT SELECT, INSERT, UPDATE, DELETE ON BDRURAL.* TO 'rural'@'%';

FLUSH PRIVILEGES;

CREATE TABLE Tipos (
    Codigo INT PRIMARY KEY,
    Descripcion VARCHAR(40) UNIQUE
);


CREATE TABLE Alojamientos ( 
Referencia INT AUTO_INCREMENT PRIMARY KEY, 
Nombre VARCHAR(60) NOT NULL, 
Poblacion VARCHAR(40) NOT NULL, 
Provincia VARCHAR(30) NOT NULL, 
Capacidad TINYINT UNSIGNED NOT NULL CHECK (Capacidad > 0), 
Tipo INT, Ubicacion ENUM('Aislada', 'En población') NOT NULL, 
Alquilado BOOLEAN DEFAULT 0, 
FOREIGN KEY (Tipo) REFERENCES Tipos(Codigo) );


INSERT INTO Tipos (Codigo, Descripcion) VALUES 
(1, 'Casa Rural'),
(2, 'Hotel'),
(3, 'Apartamento'),
(4, 'Albergue'),
(5, 'Camping');


INSERT INTO Alojamientos (Nombre, Poblacion, Provincia, Capacidad, Tipo, Ubicacion, Alquilado) VALUES 
('La Casita del Monte', 'Cazorla', 'Jaén', 4, 1, 'Aislada', 0),
('Hotel Central', 'Granada', 'Granada', 20, 2, 'En población', 1),
('Apartamento Marítimo', 'Nerja', 'Málaga', 6, 3, 'En población', 0),
('Albergue Juvenil', 'Cercedilla', 'Madrid', 50, 4, 'En población', 0),
('Camping Pinar', 'Conil', 'Cádiz', 100, 5, 'Aislada', 0);
