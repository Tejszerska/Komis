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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinTable(joinColumns = @JoinColumn(name = "addressId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "clientId", referencedColumnName = "id")
    )
    @ManyToMany
    private List<Client> clients;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String street;

    @Column(name = "house_number", nullable = false)
    private String houseNum;
    @Column(name = "flat_number")
    private String flatNum;
    private String postcode;

    public Address(String city, String street, String houseNum, String flatNum) {
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
        this.flatNum = flatNum;
    }
}
