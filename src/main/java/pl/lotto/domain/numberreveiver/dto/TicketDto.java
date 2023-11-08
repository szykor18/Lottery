package pl.lotto.domain.numberreveiver.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
@Builder
public record TicketDto(String hash, LocalDateTime drawDate, Set<Integer> numbersFromUser) {
}
