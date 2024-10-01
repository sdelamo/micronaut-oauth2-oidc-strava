package example.micronaut;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;

@Client(id = "strava")
public interface StravaApi {

    @Get("/api/v3/athlete")
    Mono<Athlete> fetchAthlete(@Header(HttpHeaders.AUTHORIZATION) String authorization);
}
