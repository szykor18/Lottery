package pl.lotto.domain.numberreceiver.dto;

import lombok.Builder;

@Builder
public record NumberReceiverResultDto(TicketDto ticketDto, String message) {
}
