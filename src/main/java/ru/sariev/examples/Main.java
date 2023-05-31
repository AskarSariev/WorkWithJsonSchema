package ru.sariev.examples;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

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

        System.out.println("*************************");

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

        System.out.println("*************************");

        /**
         * JSON to Jackson JsonNode
         */

        json = "{ \"color\" : \"Black\", \"type\" : \"FIAT\" }";
        JsonNode jsonNode = objectMapper.readTree(json);
        String color = jsonNode.get("color").asText();
        System.out.println(color);

        System.out.println("*************************");

        /**
         * Creating a Java List from a JSON Array String
         */

        String jsonCarArray = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, " +
                               "{ \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
        List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});
        listCar.stream().forEach(System.out::println);

        System.out.println("*************************");

        /**
         * Creating Java Map from JSON String
         */

        json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>(){});
        map.forEach((k, v) -> {
            System.out.println(k + " - " + v);
        });

        System.out.println("*************************");

        /**
         * Configuring Serialization or Deserialization feature
         */
        // Ignore a new field "year"
        String jsonString = "{ \"color\" : \"Black\", \"type\" : \"Fiat\", \"year\" : \"1970\" }";
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        car = objectMapper.readValue(jsonString, Car.class);
        JsonNode jsonNodeRoot = objectMapper.readTree(jsonString);
        JsonNode jsonNodeYear = jsonNodeRoot.get("year");
        String year = jsonNodeYear.asText();
        System.out.println(year);

        System.out.println("*************************");


    }
}

