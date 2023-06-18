package ist.challenge.agil_syofian_hidayat.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PeopleServiceTest {

    @Autowired
    private PeopleService peopleService;

    @Test
    void getPeople() {
        String result = peopleService.getPeople();
        assertFalse(result.isEmpty());
    }

    @Test
    void getPeopleByName() {
    }
}