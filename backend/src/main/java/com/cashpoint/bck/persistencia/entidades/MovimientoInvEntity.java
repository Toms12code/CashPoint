package com.cashpoint.bck.persistencia.entidades;

import com.cashpoint.bck.persistencia.entidades.enums.TipoMovimiento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "movimiento_ventas")
@Getter
@Setter
@NoArgsConstructor
public class MovimientoInvEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private ProductoEntity producto;

    @Enumerated(EnumType.STRING)
    private TipoMovimiento estado;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private LocalDate fecha;

    private String motivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
    }

    public TipoMovimiento getEstado() {
        return estado;
    }

    public void setEstado(TipoMovimiento estado) {
        this.estado = estado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
