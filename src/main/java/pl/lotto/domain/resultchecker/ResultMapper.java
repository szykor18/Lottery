package pl.lotto.domain.resultchecker;

import pl.lotto.domain.numberreceiver.dto.TicketDto;
import pl.lotto.domain.resultchecker.dto.PlayerDto;

import java.util.List;

class ResultMapper {
    static List<Ticket> mapFromTicketDto(List<TicketDto> allTicketsByDate) {
        return allTicketsByDate.stream()
                .map(ticketDto -> Ticket.builder()
                        .hash(ticketDto.hash())
                        .numbersFromUser(ticketDto.numbersFromUser())
                        .drawDate(ticketDto.drawDate())
                        .build()
                )
                .toList();
    }
    static List<PlayerDto> mapPlayerToResults(List<Player> players) {
        return players.stream()
                .map(player -> PlayerDto.builder()
                        .hash(player.hash())
                        .numbers(player.numbers())
                        .hitNumbers(player.hitNumbers())
                        .drawDate(player.drawDate())
                        .isWinner(player.isWinner())
                        .build())
                .toList();
    }
    static PlayerDto mapToPlayerDto(Player playerByHash) {
        return PlayerDto.builder()
                .hash(playerByHash.hash())
                .numbers(playerByHash.numbers())
                .hitNumbers(playerByHash.hitNumbers())
                .drawDate(playerByHash.drawDate())
                .isWinner(playerByHash.isWinner())
                .build();
    }
}
