-- Script para desalquilar todos los alojamientos
-- Ejecutar este script en MySQL para resetear el estado de todos los alojamientos a disponibles

USE BDRURAL;

-- Actualizar todos los alojamientos para marcarlos como disponibles (Alquilado = 0)
UPDATE ALOJAMIENTOS SET Alquilado = 0;

-- Verificar el resultado
SELECT Referencia, Nombre, Alquilado FROM ALOJAMIENTOS;
