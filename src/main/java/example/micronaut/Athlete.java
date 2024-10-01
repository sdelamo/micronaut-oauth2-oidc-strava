package example.micronaut;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Athlete(
    Long id,
    String username,
    String firstname,
    String lastname) {
}
