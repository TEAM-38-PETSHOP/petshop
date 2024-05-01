package org.globaroman.petshopba.model.groom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "type_posluga")
@Data
public class TypePetService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "number_list", nullable = false)
    private Long numberList;
    @Column(name = "t_posluga_name_id")
    private String typePetServiceNameId;
    private String name;
    private String price;

    @ManyToOne
    @JoinColumn(name = "posluga_id")
    private PetService petService;
}
