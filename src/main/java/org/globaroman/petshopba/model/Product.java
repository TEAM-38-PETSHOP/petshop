package org.globaroman.petshopba.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    @Column(name = "product_name_id")
    private String productNameId;
    private String brand;
    private BigDecimal price;
    @Column(name = "country_product")
    private String countryProduct;
    @Column(name = "group_product")
    private String groupProduct;
    @Column(name = "breed_size")
    private String breedSize;
    private String type;
    private String clazz;
    private String weight;
    private String season;
    private String color;
    private String destination;
    private String ageAnimal;
    private String packaging;
    @Column(name = "product_size")
    private String productSize;
    private String description;
    private String composition;
    @Column(name = "composition_analysis")
    private String compositionAnalysis;
    @Column(name = "composition_energy_value")
    private String compositionEnergyValue;
    @Column(name = "composition_expiration")
    private String compositionExpiration;
    private String instruction;
    @Column(name = "instruction_why_buy")
    private String instructionWhyBuy;
    @Column(name = "entry_date")
    private LocalDateTime entryDate;
    @Column(name = "is_available")
    private boolean isAvailable;

    @ManyToMany
    @JoinTable(
            name = "products_animals",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_id"))
    @JsonIgnore
    private List<Animal> animals;

    @ManyToMany
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonIgnore
    private List<Category> categories;

    @ElementCollection
    @CollectionTable(name = "product_image_urls", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
