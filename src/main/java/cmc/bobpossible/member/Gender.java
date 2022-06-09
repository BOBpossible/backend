package cmc.bobpossible.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum Gender {
    MALE, FEMALE, NONE;

    @JsonCreator
    public static Gender create(String value) {
        return Stream.of(values())
                .filter(v -> v.toString().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

}
