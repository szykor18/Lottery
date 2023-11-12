package pl.lotto.domain.numbergenerator;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import pl.lotto.domain.numberreceiver.NumberReceiverFacade;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WinningNumbersGeneratorFacadeTest {
    private final WinningNumbersRepository winningNumbersRepository = new WinningNumbersRepositoryTestImpl();
    NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorFacade(
            new SecureRandomNumbersGenerator(),
            new WinningNumbersValidator(),
            numberReceiverFacade,
            new WinningNumbersRepositoryTestImpl()
    );
    @Test
    public void should_return_six_numbers() {
        //given
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        //when
        WinningNumbersDto generatedNumbers = winningNumbersGeneratorFacade.generateWinningNumbers();
        //then
        assertThat(generatedNumbers.winningNumbers().size()).isEqualTo(6);
    }
    @Test
    public void should_return_all_numbers_in_range() {
        //given
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        //when
        WinningNumbersDto generatedNumbers = winningNumbersGeneratorFacade.generateWinningNumbers();
        //then
        int min_bound = 1, max_bound = 99;
        boolean areInRange = generatedNumbers.winningNumbers().stream().allMatch(number -> number >= min_bound
                && number <= max_bound);
        assertThat(areInRange).isTrue();
    }
    @Test
    public void should_throw_an_exception_when_number_out_of_range() {
        //given
        Set<Integer> numbersOutOfRange = Set.of(1,2,3,4,5,100);
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorFacade(
                new WinningNumbersGeneratorTestImpl(numbersOutOfRange),
                new WinningNumbersValidator(),
                numberReceiverFacade,
                new WinningNumbersRepositoryTestImpl()
        );
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        //when
        //then
        assertThrows(IllegalStateException.class ,winningNumbersGeneratorFacade::generateWinningNumbers, "Number out of range!");
    }
    @Test
    public void should_return_collection_of_unique_values() {
        //given
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        //when
        WinningNumbersDto generatedNumbers = winningNumbersGeneratorFacade.generateWinningNumbers();
        //then
        int generatedNumbersSize = new HashSet<>(generatedNumbers.winningNumbers()).size();
        assertThat(generatedNumbersSize).isEqualTo(6);
    }
    @Test
    public void should_return_winning_numbers_by_given_date() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023,11,11,12,0,0);
        Set<Integer> generatedWinningNumbers = Set.of(1, 2, 3, 4, 5, 6);
        String id = UUID.randomUUID().toString();
        WinningNumbers winningNumbers = WinningNumbers.builder()
                .id(id)
                .drawDate(drawDate)
                .winningNumbers(generatedWinningNumbers)
                .build();
        winningNumbersRepository.save(winningNumbers);
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(drawDate);
        winningNumbersGeneratorFacade = new WinningNumbersGeneratorFacade(new WinningNumbersGeneratorTestImpl(Set.of(1,2,3,4,5,6)), new WinningNumbersValidator(),
                numberReceiverFacade, winningNumbersRepository);
        //when
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.retrieveWinningNumbersByDate(drawDate);
        //then
        WinningNumbersDto expectedWinningNumbersDto = WinningNumbersDto.builder()
                .drawDate(drawDate)
                .winningNumbers(generatedWinningNumbers)
                .build();
        assertThat(winningNumbersDto).isEqualTo(expectedWinningNumbersDto);
    }
    @Test
    public void should_throw_an_exception_when_fail_to_retrieve_numbers_by_given_date() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023, 11, 11, 12, 0, 0);
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(drawDate);
        winningNumbersGeneratorFacade = new WinningNumbersGeneratorFacade(new WinningNumbersGeneratorTestImpl(), new WinningNumbersValidator(),
                numberReceiverFacade, winningNumbersRepository);
        //when
        //then
        assertThrows(WinningNumbersNotFoundException.class, () -> winningNumbersGeneratorFacade.retrieveWinningNumbersByDate(drawDate), "Not Found");
    }
    @Test
    public void it_should_return_true_if_numbers_are_generated_by_given_date() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023,11,11,12,0,0);
        Set<Integer> generatedWinningNumbers = Set.of(1, 2, 3, 4, 5, 6);
        String id = UUID.randomUUID().toString();
        WinningNumbers winningNumbers = WinningNumbers.builder()
                .id(id)
                .drawDate(drawDate)
                .winningNumbers(generatedWinningNumbers)
                .build();
        winningNumbersRepository.save(winningNumbers);
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(drawDate);
        winningNumbersGeneratorFacade = new WinningNumbersGeneratorFacade(new WinningNumbersGeneratorTestImpl(), new WinningNumbersValidator(),
                numberReceiverFacade, winningNumbersRepository);
        //when
        boolean areWinningNumbersGeneratedByDate = winningNumbersGeneratorFacade.areWinningNumbersGeneratedByDate();
        //then
        assertThat(areWinningNumbersGeneratedByDate).isTrue();
    }
}