package pl.lotto.http.numbergenerator;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.server.ResponseStatusException;
import pl.lotto.domain.numbergenerator.RandomNumbersGenerable;
import pl.lotto.infrastructure.numbergenerator.http.RandomNumberGeneratorRestTemplateConfigurationProperties;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class RandomNumberGeneratorRestTemplateErrorsIntegrationTest {

    RandomNumberGeneratorRestTemplateConfigurationProperties properties = new RandomNumberGeneratorRestTemplateConfigurationProperties("http://localhost", wireMockServer.getPort(), 1000, 1000);
    RandomNumbersGenerable randomNumbersGenerable = new RandomNumberGeneratorRestTemplateIntegrationTestConfig().remoteNumberGeneratorClient(properties);

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @Test
    public void should_throw_exception_500_when_fault_connection_reset_by_peer() {
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)
                ));
        //when
        Throwable throwable = catchThrowable( () -> randomNumbersGenerable.generateSixRandomNumbers(1, 99, 25));
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
    @Test
    public void should_throw_exception_500_when_fault_empty_response() {
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withFault(Fault.EMPTY_RESPONSE)
                ));
        //when
        Throwable throwable = catchThrowable( () -> randomNumbersGenerable.generateSixRandomNumbers(1, 99, 25));
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
    @Test
    public void should_throw_exception_500_when_malformed_response_chunk() {
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withFault(Fault.MALFORMED_RESPONSE_CHUNK)
                ));
        //when
        Throwable throwable = catchThrowable( () -> randomNumbersGenerable.generateSixRandomNumbers(1, 99, 25));
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
    @Test
    public void should_throw_exception_500_fault_random_data_then_close() {
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withFault(Fault.RANDOM_DATA_THEN_CLOSE)
                ));
        //when
        Throwable throwable = catchThrowable( () -> randomNumbersGenerable.generateSixRandomNumbers(1, 99, 25));
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
    @Test
    public void should_throw_exception_204_when_status_is_204_no_content() {
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_NO_CONTENT)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25]
                                """.trim()))
                );
        //when
        Throwable throwable = catchThrowable( () -> randomNumbersGenerable.generateSixRandomNumbers(1, 99, 25));
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("204 NO_CONTENT");
    }
    @Test
    public void should_throw_exception_500_when_response_delay_is_5000_ms_and_client_has_1000_read_timeout() {
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withFixedDelay(5000)
                ));
        //when
        Throwable throwable = catchThrowable( () -> randomNumbersGenerable.generateSixRandomNumbers(1, 99, 25));
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
    @Test
    public void should_throw_exception_404_when_service_returning_not_found_status() {
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_NOT_FOUND)
                        .withHeader("Content-Type", "application/json")
                ));
        //when
        Throwable throwable = catchThrowable( () -> randomNumbersGenerable.generateSixRandomNumbers(1, 99, 25));
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("404 NOT_FOUND");
    }
    @Test
    public void should_throw_exception_401_when_service_returning_unauthorized_status() {
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_UNAUTHORIZED)
                        .withHeader("Content-Type", "application/json")
                ));
        //when
        Throwable throwable = catchThrowable( () -> randomNumbersGenerable.generateSixRandomNumbers(1, 99, 25));
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("401 UNAUTHORIZED");
    }
}
