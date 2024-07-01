package com.argus.proyectofinaldami.entidad

class Prestamo(var prestamoID:Int? = null,
               var userID:Int = 10002,
               var fechaPrestamo:String? = null,
               var detallesPrestamos:List<DetallePrestamo>) {
}