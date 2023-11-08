package pl.lotto.domain.numberreceiver;

import pl.lotto.domain.numberreceiver.dto.TicketDto;

class TicketMapper {
    static TicketDto mapFromTicket(Ticket ticket) {
        return TicketDto.builder()
                .numbersFromUser(ticket.numbersFromUser())
                .hash(ticket.hash())
                .drawDate(ticket.drawDate())
                .build();
    }
}
