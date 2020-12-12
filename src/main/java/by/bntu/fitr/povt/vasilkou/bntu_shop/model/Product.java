package by.bntu.fitr.povt.vasilkou.bntu_shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 75, message = "Name should be contains more than 3 characters")
    @NotBlank(message = "Name should be not empty")
    private String name;

    @Size(min = 10, max = 250, message = "Description should be between 10 and 250 characters")
    @NotBlank(message = "Description should be not empty")
    private String description;

    @Digits(integer = 1_000_000, fraction = 2)
    @DecimalMin(value = "0.0", message = "Cost should be positive")
    private BigDecimal cost;

    @Min(value = 0, message = "Amount cannot be negative")
    private Integer amount;
    private boolean status;

    @Column(name = "file_name")
    private String fileName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categories_id", nullable = false)
    private Category category;

    @PrePersist
    private void prePersist(){
        this.status = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id != null ? id.equals(product.id) : product.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
