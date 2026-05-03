package com.cashpoint.bck.persistencia.dtos;

import com.cashpoint.back.persistencia.entidades.enums.EstadoVenta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VentaResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private Double total;
    private com.cashpoint.back.persistencia.entidades.enums.EstadoVenta estado;
    private List<DetalleVentaResponseDTO> detallesR = new ArrayList<DetalleVentaResponseDTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    public void setDetalles(List<DetalleVentaResponseDTO> detallesR) {
        this.detallesR = detallesR;
    }
}
