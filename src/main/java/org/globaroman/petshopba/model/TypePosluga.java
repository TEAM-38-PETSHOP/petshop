package org.globaroman.petshopba.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "type_posluga")
@Data
public class TypePosluga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "number_list", nullable = false)
    private Long numberList;
    private String name;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "posluga_id")
    private Posluga posluga;
}
