package org.moneyMinder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellido;
    private String correo;
    @Column(name = "contraseña")
    private String pass;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartera")
    private Cartera cartera;
    @OneToMany(mappedBy = "usuario")
    List<Gasto> gastos;
    @OneToMany(mappedBy = "usuario")
    List<Ingreso> ingresos;
}