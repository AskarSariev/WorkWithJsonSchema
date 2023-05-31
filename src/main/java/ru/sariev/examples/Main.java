package ru.sariev.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Car car = new Car("yellow", "renault");

        /**
         * Java Object to JSON
         */

        objectMapper.writeValue(new File("car.json"), car);

        String carAsString = objectMapper.writeValueAsString(car); // Object to JSON string
        System.out.println(carAsString);

        /**
         * JSON to Java Object
         */

        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        car = objectMapper.readValue(json, Car.class);
        System.out.println(car);

        car = objectMapper.readValue(new File("car.json"), Car.class);
        System.out.println(car);
        car = objectMapper.readValue(new URL("file:car.json"), Car.class);
        System.out.println(car);

        /**
         * JSON to Jackson JsonNode
         */


    }
}

