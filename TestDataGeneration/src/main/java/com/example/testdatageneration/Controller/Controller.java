package com.example.testdatageneration.Controller;

import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class Controller {

    private final Faker faker = new Faker(/*new Locale("ru")*/ new Locale("en"));

    @GetMapping("/generate/address")
    public String generateAddress() {
        String streetName = faker.address().streetName();
        String number = faker.address().buildingNumber();
        String city = faker.address().city();
        String country = faker.address().country();

        return String.format("%s\n%s\n%s\n%s",
                number,
                streetName,
                city,
                country);
    }

    @GetMapping("/generate/string")
    public String generateString(@RequestParam(value = "count", defaultValue = "10") Integer count) {
        String regex = String.format("[a-zA-Z]{%s}", count.toString());
        return faker.regexify(regex);
    }

    @GetMapping("/generate/phoneNumber")
    public String generatePhoneNumber() {
        return String.format("%s\n%s\n",
                faker.phoneNumber().phoneNumber(),
                faker.phoneNumber().cellPhone());
    }

    @GetMapping("/generate/number")
    public String generateNumber(@RequestParam(value = "count", defaultValue = "10") Integer count) {
        String regex = String.format("[1-9]{%s}", count.toString());
        return faker.regexify(regex);
    }

    @GetMapping("/generate/company")
    public String generateCompany() {
        return String.format("%s\n%s\n%s\n%s",
                faker.company().name(),
                faker.company().industry(),
                faker.company().profession(),
                faker.company().url());
    }

    @GetMapping("/generate")
    public String generateRegex(@RequestBody String regex) {
        return String.format("%s\n", faker.regexify(regex));
    }

}