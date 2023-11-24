package pl.lotto.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import pl.lotto.BaseIntegrationTest;
import pl.lotto.domain.numbergenerator.RandomNumbersGenerable;
import pl.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import pl.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;
import pl.lotto.domain.numbergenerator.dto.WinningNumbersDto;

public class UserPlayedLottoAndWonLottoIntegrationTest extends BaseIntegrationTest {

    @Autowired
    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;
    @Test
    public void should_user_win_and_system_should_generate_winners() {

    //step 1: external service returns 6 random numbers (1,2,3,4,5,6)
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", "application/json")
                                .withBody("""
                                        [1,2,3,4,5,6,82,82,83,83,86,57,10,81,53,93,50,54,31,88,15,43,79,32,43]
                                        """.trim()
                                )));
        //when
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        System.out.println(winningNumbersDto);
        //then


    //step 2: system fetched winning numbers for draw date: 11.11.2023 12:00
    //step 3: user made POST /inputNumbers with 6 numbers (1, 2, 3, 4, 5, 6) at 16-11-2022 10:00 and system returned OK(200) with message: “success” and Ticket (DrawDate:19.11.2022 12:00 (Saturday), TicketId: sampleTicketId)
    //step 4: 3 days and 1 minute passed, and it is 1 minute after the draw date (19.11.2022 12:01)
    //step 5: system generated result for TicketId: sampleTicketId with draw date 19.11.2022 12:00, and saved it with 6 hits
    //step 6: 3 hours passed, and it is 1 minute after announcement time (19.11.2022 15:01)
    //step 7: user made GET /results/sampleTicketId and system returned 200 (OK)

    }

}
