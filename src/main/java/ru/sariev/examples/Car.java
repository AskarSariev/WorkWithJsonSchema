package ru.sariev.examples;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {

    @Getter @Setter
    private String color;

    @Getter @Setter
    private String type;
}
