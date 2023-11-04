package pl.lotto.domain.numberreveiver;

import pl.lotto.domain.numberreveiver.dto.TicketDto;

class TicketMapper {
    static TicketDto mapFromTicket(Ticket ticket) {
        return TicketDto.builder()
                .numbersFromUser(ticket.numbersFromUser())
                .ticketId(ticket.ticketId())
                .drawDate(ticket.drawDate())
                .build();
    }
}
