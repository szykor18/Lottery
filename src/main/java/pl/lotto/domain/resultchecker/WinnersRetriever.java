package pl.lotto.domain.resultchecker;


import pl.lotto.persistence.model.Player;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class WinnersRetriever {
    private final static int HIT_NUMBERS_TO_WON = 3;

    List<Player> retrieveWinners(List<Ticket> tickets, Set<Integer> winningNumbers) {
        return tickets.stream()
                .map(ticket -> {
                    Set<Integer> hitNumbers = calculateHits(ticket, winningNumbers);
                    return buildPlayer(ticket, hitNumbers);
                })
                .toList();
    }

    private Player buildPlayer(Ticket ticket, Set<Integer> hitNumbers) {
        boolean isWinner = isWinner(hitNumbers);
        return Player.builder()
                .hash(ticket.hash())
                .numbers(ticket.numbersFromUser())
                .hitNumbers(hitNumbers)
                .drawDate(ticket.drawDate())
                .isWinner(isWinner)
                .build();
    }

    private boolean isWinner(Set<Integer> hitNumbers) {
        return hitNumbers.size() >= HIT_NUMBERS_TO_WON;
    }

    private Set<Integer> calculateHits(Ticket ticket, Set<Integer> winningNumbers) {
        return ticket.numbersFromUser().stream()
                .filter(winningNumbers::contains)
                .collect(Collectors.toSet());
    }
}
