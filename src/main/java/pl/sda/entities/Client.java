package pl.sda.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "client", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Contract> contracts;
    @ManyToMany(mappedBy = "clients")
    private List<Address> addresses;
    // wiele ponieważ może być wiele adresów np. do wysyłki - funkcjonalność do zaimplementowania w ver2
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(length = 11, nullable = false)
    private String pesel;
    private String phone;
    private String email;
    private String comments;

    public Client(String name, String surname, String pesel) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
    }
}
