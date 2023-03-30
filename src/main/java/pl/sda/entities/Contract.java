package pl.sda.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "employeeId")
    @ManyToOne
    private Employee employee;
    @JoinColumn(name = "clientId")
    @ManyToOne
    private Client client;
    @OneToMany(mappedBy = "contract", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Good> goods;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private BigDecimal paid; //ma być suma zapłaconych towarów
    private BigDecimal value; //pamietaj o opakowaniu do metod!!!, j.w.
    private String comment;

    public Contract(Employee employee, Client client, List<Good> goods) {
        this.employee = employee;
        this.client = client;
        this.goods = new ArrayList<>();
        this.date = LocalDate.now();
        this.paid = goods.stream().map(i -> i.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add); // czy to jest dobrze??? czy może być w konstruktorze?
    }

    public Contract(Long id, Employee employee, Client client, List<Good> goods, LocalDate date, BigDecimal paid, BigDecimal value, String comment) {
        this.id = id;
        this.employee = employee;
        this.client = client;
        this.goods =  new ArrayList<>();
        this.date = LocalDate.now();
        this.paid  = goods.stream().map(i -> i.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.value = goods.stream().map(i -> i.getValue()).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.comment = comment;
    }
}
