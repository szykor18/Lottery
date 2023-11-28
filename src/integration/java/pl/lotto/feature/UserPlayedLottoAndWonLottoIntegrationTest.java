package pl.lotto.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.lotto.BaseIntegrationTest;
import pl.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import pl.lotto.domain.numbergenerator.WinningNumbersNotFoundException;
import pl.lotto.domain.numberreceiver.dto.NumberReceiverResultDto;
import pl.lotto.domain.resultannouncer.dto.ResponseDto;
import pl.lotto.domain.resultannouncer.dto.ResultAnnouncerDto;
import pl.lotto.domain.resultchecker.PlayerNotFoundByHashException;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;
import pl.lotto.domain.resultchecker.dto.PlayerDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserPlayedLottoAndWonLottoIntegrationTest extends BaseIntegrationTest {

    @Autowired
    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;
    @Autowired
    ResultCheckerFacade resultCheckerFacade;

    @Test
    public void should_user_win_and_system_should_generate_winners() throws Exception {

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

        //step 2: system fetched winning numbers for draw date: 18.11.2023 12:00
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023, 11, 18, 12, 0, 0);
        //when&&then
        await()
                .atMost(Duration.ofSeconds(20))
                .pollInterval(Duration.ofSeconds(1))
                .until(() -> {
                            try {
                                return !winningNumbersGeneratorFacade.retrieveWinningNumbersByDate(drawDate).winningNumbers().isEmpty();
                            } catch (WinningNumbersNotFoundException exception) {
                                return false;
                            }
                        }
                );
        //step 3: user made POST /inputNumbers with 6 numbers (1, 2, 3, 4, 5, 6) at 15-11-2023 11:00 and system returned OK(200) with message: “success” and Ticket (DrawDate:19.11.2022 12:00 (Saturday), TicketId: sampleTicketId)
        //given
        ResultActions performPostInputNumbers = mockMvc.perform(post("/inputNumbers")
                .content("""
                        {
                        "inputNumbers": [1,2,3,4,5,6]
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON));
        //when
        MvcResult mvcResult = performPostInputNumbers.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        NumberReceiverResultDto numberReceiverResultDto = objectMapper.readValue(json, NumberReceiverResultDto.class);
        String ticketId = numberReceiverResultDto.ticketDto().hash();
        assertAll(
                () -> assertThat(numberReceiverResultDto.ticketDto().drawDate()).isEqualTo(drawDate),
                () -> assertThat(numberReceiverResultDto.message()).isEqualTo("SUCCESS"),
                () -> assertThat(ticketId).isNotNull()
        );


        //step 4: user made GET /results/notExistingId and system returned 404(NOT_FOUND) and body with (message: Not found for id: notExistingId and status NOT_FOUND)
        //given && when
        ResultActions performGetResultsWithNotExistingId = mockMvc.perform(get("/results/notExistingId"));
        //then
        performGetResultsWithNotExistingId.andExpect(status().isNotFound()).andExpect(
                content().json("""
                            {
                            "message": "Not found for id: notExistingId",
                            "status": "NOT_FOUND"
                            }
                        """.trim()
                )
        );


        //step 5: 3 days and 55 minute passed, and it is 5 minute before the draw date (18.11.2023 11:55)
        clock.advanceInTimeBy(Duration.ofDays(3));
        clock.advanceInTimeBy(Duration.ofMinutes(55));


        //step 6: system generated result for TicketId: sampleTicketId with draw date 19.11.2022 12:00, and saved it with 6 hit numbers, and won message
        //given && when && then
        await()
                .atMost(Duration.ofSeconds(20))
                .pollInterval(Duration.ofSeconds(1))
                .until(
                        () -> {
                            try {
                                PlayerDto playerByHash = resultCheckerFacade.findPlayerByHash(ticketId);
                                return !playerByHash.numbers().isEmpty();
                            } catch (PlayerNotFoundByHashException exception) {
                                return false;
                            }
                        }
                );


        //step 7: 6 minutes passed, and it is 1 minute after announcement time (18.11.2023 12:01)
        clock.advanceInTimeBy(Duration.ofMinutes(6));


        //step 8: user made GET /results/sampleTicketId and system returned 200 (OK)
        //given && when
        ResultActions performGetResults = mockMvc.perform(get("/results/" + ticketId));
        MvcResult mvcResultOfPlayer = performGetResults.andExpect(status().isOk()).andReturn();
        String jsonPlayerResult = mvcResultOfPlayer.getResponse().getContentAsString();
        ResultAnnouncerDto resultAnnouncerDto = objectMapper.readValue(jsonPlayerResult, ResultAnnouncerDto.class);
        //then
        assertAll(
                () -> assertThat(resultAnnouncerDto.responseDto().hash()).isEqualTo(ticketId),
                () -> assertThat(resultAnnouncerDto.responseDto().wonNumbers()).hasSize(6),
                () -> assertThat(resultAnnouncerDto.responseDto().drawDate()).isEqualTo(drawDate),
                () -> assertThat(resultAnnouncerDto.message()).isEqualTo("You won, congratulations!")
        );
    }
}