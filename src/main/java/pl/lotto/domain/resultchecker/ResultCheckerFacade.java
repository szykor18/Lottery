package pl.lotto.domain.resultchecker;

import lombok.AllArgsConstructor;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import pl.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import pl.lotto.domain.numberreceiver.NumberReceiverFacade;
import pl.lotto.domain.numberreceiver.dto.TicketDto;
import pl.lotto.domain.resultchecker.dto.PlayerDto;
import pl.lotto.domain.resultchecker.dto.PlayersResultsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import static pl.lotto.domain.resultchecker.ResultMapper.*;

@AllArgsConstructor
public class ResultCheckerFacade {
    private NumberReceiverFacade numberReceiverFacade;
    private WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;
    private PlayerRepository playerRepository;
    private WinnersRetriever winnersRetriever;
    private DrawDateFacade drawDateFacade;

    public PlayersResultsDto generateResults() {
        LocalDateTime drawDate = drawDateFacade.getNextDrawDate();
        List<TicketDto> allTicketsByDate = numberReceiverFacade.retrieveAllTicketsByNextDrawDate();
        List<Ticket> tickets = mapFromTicketDto(allTicketsByDate);
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.getWinningNumbersByDrawDate(drawDate);
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
        Player playerByHash = playerRepository.findByHash(hash).orElse(null);
        if (playerByHash == null) {
            List<TicketDto> ticketDtos = numberReceiverFacade.retrieveAllTicketsByNextDrawDate();
            boolean isTicketWaitingForDraw = ticketDtos.stream()
                    .map(TicketDto::hash)
                    .anyMatch(hashFromList -> ticketDtos.contains(hash));
            if (isTicketWaitingForDraw) {
                throw new RuntimeException("Your ticket is waiting for the draw, please come back after Saturday 12:00 p.m.");
            }
            throw new PlayerNotFoundByHashException(hash);
        }
        return mapToPlayerDto(playerByHash);
    }
}
