package com.example.proyectosataapp.models;

public class TicketAsignacion {

    private String fecha_asignacion;
    private String tecnico_id;
    private String fotoUrl;

    public TicketAsignacion(String fecha_asignacion, String tecnico_id, String fotoUrl) {
        this.fecha_asignacion = fecha_asignacion;
        this.tecnico_id = tecnico_id;
        this.fotoUrl = fotoUrl;
    }

    public TicketAsignacion() {
    }

    public String getFecha_asignacion() {
        return fecha_asignacion;
    }

    public void setFecha_asignacion(String fecha_asignacion) {
        this.fecha_asignacion = fecha_asignacion;
    }

    public String getTecnico_id() {
        return tecnico_id;
    }

    public void setTecnico_id(String tecnico_id) {
        this.tecnico_id = tecnico_id;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    @Override
    public String toString() {
        return "TicketAsignacion{" +
                "fecha_asignacion='" + fecha_asignacion + '\'' +
                ", tecnico_id='" + tecnico_id + '\'' +
                ", fotoUrl='" + fotoUrl + '\'' +
                '}';
    }
}
