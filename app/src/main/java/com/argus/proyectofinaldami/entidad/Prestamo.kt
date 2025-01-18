package com.argus.proyectofinaldami.entidad

class Prestamo(var prestamoID:Int? = null,
               var userID:Int? = null,
               var fechaPrestamo:String? = null,
               var detallesPrestamos:List<DetallePrestamo>) {
}