package pl.lotto.domain.numbergenerator;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
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
    DrawDateFacade drawDateFacade = mock(DrawDateFacade.class);
    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WInningNumbersGeneratorConfiguration().createForTests(
            new WinningNumbersGeneratorTestImpl(),
            winningNumbersRepository,
            drawDateFacade
    );
    @Test
    public void should_return_six_numbers() {
        //given
        when(drawDateFacade.getNextDrawDate()).thenReturn(LocalDateTime.now());
        //when
        WinningNumbersDto generatedNumbers = winningNumbersGeneratorFacade.generateWinningNumbers();
        //then
        assertThat(generatedNumbers.winningNumbers().size()).isEqualTo(6);
    }
    @Test
    public void should_return_all_numbers_in_range() {
        //given
        when(drawDateFacade.getNextDrawDate()).thenReturn(LocalDateTime.now());
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
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WInningNumbersGeneratorConfiguration().createForTests(
                new WinningNumbersGeneratorTestImpl(numbersOutOfRange),
                winningNumbersRepository,
                drawDateFacade);
        when(drawDateFacade.getNextDrawDate()).thenReturn(LocalDateTime.now());
        //when
        //then
        assertThrows(IllegalStateException.class ,winningNumbersGeneratorFacade::generateWinningNumbers, "Number out of range!");
    }
    @Test
    public void should_return_collection_of_unique_values() {
        //given
        when(drawDateFacade.getNextDrawDate()).thenReturn(LocalDateTime.now());
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
        when(drawDateFacade.getNextDrawDate()).thenReturn(drawDate);
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WInningNumbersGeneratorConfiguration().createForTests(
                new WinningNumbersGeneratorTestImpl(Set.of(1,2,3,4,5,6)),
                winningNumbersRepository,
                drawDateFacade);
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
        when(drawDateFacade.getNextDrawDate()).thenReturn(drawDate);
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WInningNumbersGeneratorConfiguration().createForTests(
                new WinningNumbersGeneratorTestImpl(),
                winningNumbersRepository,
                drawDateFacade);
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
        when(drawDateFacade.getNextDrawDate()).thenReturn(drawDate);
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WInningNumbersGeneratorConfiguration().createForTests(
                new WinningNumbersGeneratorTestImpl(),
                winningNumbersRepository,
                drawDateFacade);
        //when
        boolean areWinningNumbersGeneratedByDate = winningNumbersGeneratorFacade.areWinningNumbersGeneratedByDate();
        //then
        assertThat(areWinningNumbersGeneratedByDate).isTrue();
    }
}