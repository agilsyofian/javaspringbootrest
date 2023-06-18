package ist.challenge.agil_syofian_hidayat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PeopleService {

    @Autowired
    RestTemplate restTemplate;
    @Value("${url.swapi.people}")
    private String urlSwapiPeople;

    public String getPeople() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        return restTemplate.exchange(
                urlSwapiPeople, HttpMethod.GET, entity, String.class).getBody();
    }

    public String getPeopleByName(String name) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        return restTemplate.exchange(
                urlSwapiPeople+"?search="+name, HttpMethod.GET, entity, String.class).getBody();
    }
}
