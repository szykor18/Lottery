package pl.lotto.apivalidationerror;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.lotto.BaseIntegrationTest;
import pl.lotto.infrastructure.apivalidation.ApiValidationErrorResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_400_bad_request_when_input_numbers_is_null_and_empty() throws Exception {
        //given && when
        ResultActions performPostWithNullAndEmptyInputNumbers = mockMvc.perform(post("/inputNumbers")
                .content("""
                        {
                                                  
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON)
        );
        MvcResult resultNullAndEmptyInputNumbers = performPostWithNullAndEmptyInputNumbers.andExpect(status().isBadRequest()).andReturn();
        String jsonEmptyAndNullInputNumbers = resultNullAndEmptyInputNumbers.getResponse().getContentAsString();
        ApiValidationErrorResponse errorMustNotBeEmptyAndNotNull = objectMapper.readValue(jsonEmptyAndNullInputNumbers, ApiValidationErrorResponse.class);
        //then
        assertThat(errorMustNotBeEmptyAndNotNull.validationMessages()).containsExactlyInAnyOrder(
                "input numbers must not be empty", "input numbers must not be null"
        );

    }

    @Test
    public void should_return_400_bad_request_when_input_numbers_is_empty() throws Exception {
        //given && when
        ResultActions performPostWithEmptyInputNumbers = mockMvc.perform(post("/inputNumbers")
                .content("""
                        {
                        "inputNumbers": []
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult resultEmptyInputNumbers = performPostWithEmptyInputNumbers.andExpect(status().isBadRequest()).andReturn();
        String jsonEmptyInputNumbers = resultEmptyInputNumbers.getResponse().getContentAsString();
        ApiValidationErrorResponse apiValidationErrorResponse = objectMapper.readValue(jsonEmptyInputNumbers, ApiValidationErrorResponse.class);
        //then
        assertThat(apiValidationErrorResponse.validationMessages()).containsExactlyInAnyOrder(
                "input numbers must not be empty"
        );
    }

}
