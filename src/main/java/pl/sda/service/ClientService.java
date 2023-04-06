package pl.sda.service;

import pl.sda.dao.AddressDao;
import pl.sda.dao.ClientDao;
import pl.sda.entities.Address;
import pl.sda.entities.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientService {
    private static ClientDao clientDao = new ClientDao();
    private static AddressDao addressDao = new AddressDao();
    private static AddressService addressService = new AddressService();
    private static Scanner scanner = new Scanner(System.in);

    public void save(Client patient) {
        clientDao.save(patient);
    } // met. skopiowana z MedicalApp, służy do testów?
    public boolean createClientWithAddress(String name, String surname, String pesel, String city, String street, String houseNum, String flatNum, String phone, String email, String commments) {
        if (!clientDao.existsByPesel(pesel)) {
            Address address = new Address(city, street, houseNum);
            if(!flatNum.isBlank()){
                address.setFlatNum(flatNum);
            }
            System.out.println("Lista sugerowanych kodów pocztowych: ");
            System.out.println(addressService.checkPostcode(city));

            System.out.println("Wpisz wybrany kod pocztowy w formacie XX-XXX");
            String postcode = scanner.next();
            address.setPostcode(postcode);

            List<Address> clientsAddresses = new ArrayList<>();
            clientsAddresses.add(address);

            Client client = new Client(null, null, clientsAddresses, name, surname, pesel, phone, email, commments);

            addressDao.save(address);
            clientDao.save(client);
            return true;
        }
        return false;
    }
}
