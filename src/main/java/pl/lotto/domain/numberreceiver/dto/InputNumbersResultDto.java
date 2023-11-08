package pl.lotto.domain.numberreceiver.dto;

import lombok.Builder;

@Builder
public record InputNumbersResultDto(TicketDto ticketDto, String message) {
}
