package pl.lotto.domain.resultchecker;

import lombok.AllArgsConstructor;
import pl.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import pl.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import pl.lotto.domain.numberreceiver.NumberReceiverFacade;
import pl.lotto.domain.numberreceiver.dto.TicketDto;
import pl.lotto.domain.resultchecker.dto.PlayerDto;
import pl.lotto.domain.resultchecker.dto.PlayersResultsDto;
import java.util.List;
import java.util.Set;
import static pl.lotto.domain.resultchecker.ResultMapper.*;

@AllArgsConstructor
public class ResultCheckerFacade {
    NumberReceiverFacade numberReceiverFacade;
    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;
    PlayerRepository playerRepository;
    WinnersRetriever winnersRetriever;
    public PlayersResultsDto generateResults() {
        List<TicketDto> allTicketsByDate = numberReceiverFacade.retrieveAllTicketsByNextDrawDate();
        List<Ticket> tickets = mapFromTicketDto(allTicketsByDate);
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        Set<Integer> winningNumbers = winningNumbersDto.winningNumbers();
        if (winningNumbers == null || winningNumbers.isEmpty()) {
            return PlayersResultsDto.builder()
                    .message("failed to retrieve winners")
                    .build();
        }
        List<Player> players = winnersRetriever.retrieveWinners(tickets, winningNumbers);
        playerRepository.saveAll(players);
        return PlayersResultsDto.builder()
                .results(mapPlayerToResults(players))
                .message("succeed to retrieve winners")
                .build();
    }
    public PlayerDto findPlayerByHash(String hash) {
        Player playerByHash = playerRepository.findByHash(hash)
                .orElseThrow(() -> new PlayerNotFoundByHashException(hash));
        return mapToPlayerDto(playerByHash);
    }
}
