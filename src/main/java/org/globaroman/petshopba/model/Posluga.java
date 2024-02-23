package org.globaroman.petshopba.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "posluga")
@Data
public class Posluga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "number_list", nullable = false)
    private Long numberList;
    private String name;
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "posluga")
    private List<TypePosluga> types;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
}
