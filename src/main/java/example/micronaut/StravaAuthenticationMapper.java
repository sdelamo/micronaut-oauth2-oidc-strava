package example.micronaut;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpHeaderValues;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

import java.util.Collections;
import java.util.Map;

@Named("strava")
@Singleton
class StravaAuthenticationMapper implements OauthAuthenticationMapper {
    private final StravaApi stravaApi;

    StravaAuthenticationMapper(StravaApi stravaApi) {
        this.stravaApi = stravaApi;
    }

    @Override
    public Publisher<AuthenticationResponse> createAuthenticationResponse(TokenResponse tokenResponse, @Nullable State state) {

        return stravaApi.fetchAthlete(HttpHeaderValues.AUTHORIZATION_PREFIX_BEARER + " " + tokenResponse.getAccessToken())
                .map(athlete -> {
                    Map<String, Object> attributes = Map.of(ACCESS_TOKEN_KEY, tokenResponse.getAccessToken(),
                            "firstname", athlete.firstname(), "lastname", athlete.lastname());
                    return AuthenticationResponse.success("" + athlete.id(),  attributes);
                });
    }
}
