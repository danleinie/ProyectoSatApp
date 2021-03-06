package com.example.proyectosataapp.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ticket {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("creado_por")
    @Expose
    private UserResponseRegister creadoPor;
    @SerializedName("fecha_creacion")
    @Expose
    private String fechaCreacion;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("anotaciones")
    @Expose
    private List<Object> anotaciones = null;
    @SerializedName("asignaciones")
    @Expose
    private List<TicketAsignacion> asignaciones = null;
    @SerializedName("fotos")
    @Expose
    private List<String> fotos = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("inventariable")
    @Expose
    private String inventariableId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserResponseRegister getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(UserResponseRegister creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Object> getAnotaciones() {
        return anotaciones;
    }

    public void setAnotaciones(List<Object> anotaciones) {
        this.anotaciones = anotaciones;
    }

    public List<TicketAsignacion> getAsignaciones() {
        return asignaciones;
    }

    public void setInventariableId(String inventariableId) {
        this.inventariableId = inventariableId;
    }

    public String getInventariableId() {
        return inventariableId;
    }

    public void setAsignaciones(List<TicketAsignacion> asignaciones) {
        this.asignaciones = asignaciones;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", creadoPor=" + creadoPor +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", estado='" + estado + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", anotaciones=" + anotaciones +
                ", asignaciones=" + asignaciones +
                ", fotos=" + fotos +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}