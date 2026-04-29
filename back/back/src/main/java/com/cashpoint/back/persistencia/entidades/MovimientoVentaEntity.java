package com.cashpoint.back.persistencia.entidades;

import com.cashpoint.back.persistencia.entidades.enums.TipoMovimiento;
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
public class MovimientoVentaEntity {

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
}
