package pl.sda.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sda.enums.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "contractId")
    @ManyToOne
    private Contract contract;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated( value = EnumType.STRING)
    private Category category;
    @Column(nullable = false)
    private BigDecimal price;
    private BigDecimal value;

    public Good(String name, Category category, BigDecimal price, BigDecimal value, Contract contract) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.value = value;
        this.contract = contract;
    }

}
