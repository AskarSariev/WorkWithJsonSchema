package ru.sariev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Set;

@RestController
public class MyController {

    @PostMapping("/without")
    public void createPerson(@RequestBody Person person) {
        System.out.println(person);
    }

    @PostMapping("/validation")
    public void createPersonWithValidation(@RequestBody String requestStr) throws JsonProcessingException {
        InputStream schemaAsStream = MyController.class.getClassLoader().getResourceAsStream("person.schema.json");
        JsonSchema schema = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7).getSchema(schemaAsStream);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(requestStr);

        Set<ValidationMessage> errors = schema.validate(jsonNode);
        String errorsCombined = "";
        for (ValidationMessage error : errors) {
            errorsCombined += error.toString() + "\n";
        }

        if (errors.size() > 0) {
            throw new RuntimeException("Please fix your json! " + errorsCombined);
        }

        Person person = objectMapper.readValue(requestStr, Person.class);
        System.out.println(person);
        // xxxdev1
    }
}
