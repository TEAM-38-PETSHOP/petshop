package org.globaroman.petshopba.model.groom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.globaroman.petshopba.model.Animal;

@Entity
@Table(name = "posluga")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "number_list", nullable = false)
    @OrderBy
    private Long numberList;
    private String name;
    private String description;
    @Column(name = "posluga_name_id")
    private String petServiceNameId;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "petService")
    private List<TypePetService> types;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    public PetService(Long id) {
        this.id = id;
    }
}
