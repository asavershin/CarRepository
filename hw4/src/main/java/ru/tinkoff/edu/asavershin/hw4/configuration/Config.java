package ru.tinkoff.edu.asavershin.hw4.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Person;

@Configuration
public class Config {

    @Value("${person.name}")
    private String name;
    @Value("${person.age}")
    private Integer age;

    @Bean
    public Person motherBean(){
        return new Person(name, age);
    }

}
