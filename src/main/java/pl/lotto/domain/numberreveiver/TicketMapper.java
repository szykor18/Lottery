package pl.lotto.domain.numberreveiver;

import pl.lotto.domain.numberreveiver.dto.TicketDto;

class TicketMapper {
    static TicketDto mapFromTicket(Ticket ticket) {
        return TicketDto.builder()
                .numbersFromUser(ticket.numbersFromUser())
                .hash(ticket.hash())
                .drawDate(ticket.drawDate())
                .build();
    }
}
