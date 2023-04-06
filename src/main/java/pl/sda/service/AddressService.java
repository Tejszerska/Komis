package pl.sda.service;
import pl.sda.dao.ClientDao;
import pl.sda.entities.Address;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AddressService{
    private static ClientDao clientDao = new ClientDao();
    private static Address address = new Address();
    public String checkPostcode(String city) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://kodpocztowy.intami.pl/city/"+city)) //TODO - w kolejnej wersji: api sciąga kody z miasta do pewnej liczby kodów, a powyżej w oparciu o miasto i ulicę
                    .GET()
                    .header("Accept","text/plain") //TODO - w kolejnej wersji: zmiana na json i edycja formatu np. jedna linijka z przecinkami
                    .build();
            try {
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());
                return response.body();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
