package ist.challenge.agil_syofian_hidayat.controller;

import ist.challenge.agil_syofian_hidayat.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @GetMapping(path = "/", produces = "application/json")
    public String getPeople() {
        return peopleService.getPeople();
    }

    @GetMapping(produces = "application/json")
    public String getPeopleByName(@RequestParam("name") String name) {
        return peopleService.getPeopleByName(name);
    }

}
