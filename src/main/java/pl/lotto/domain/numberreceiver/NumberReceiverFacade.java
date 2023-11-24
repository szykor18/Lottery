package pl.lotto.domain.numberreceiver;

import lombok.AllArgsConstructor;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.numberreceiver.dto.NumberReceiverResultDto;
import pl.lotto.domain.numberreceiver.dto.TicketDto;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import static pl.lotto.domain.numberreceiver.ValidationResult.INPUT_SUCCESS;

@AllArgsConstructor
public class NumberReceiverFacade {
    private final NumberValidator validator;
    private final TicketRepository repository;
    private final HashGenerable hashGenerator;
    private final DrawDateFacade drawDateFacade;

    public NumberReceiverResultDto inputNumbers(Set<Integer> numbersFromUser) {
        List<ValidationResult> validationResultList = validator.validate(numbersFromUser);
        if (!validationResultList.isEmpty()) {
            String resultMessage = validator.createResultMessage();
            return NumberReceiverResultDto.builder()
                    .ticketDto(null)
                    .message(resultMessage)
                    .build();
        }
        String hash = hashGenerator.getHash();
        LocalDateTime drawDate = drawDateFacade.getNextDrawDate();
        Ticket savedTicket = repository.save(new Ticket(hash, drawDate, numbersFromUser));
        return NumberReceiverResultDto.builder()
                .ticketDto(TicketMapper.mapFromTicket(savedTicket))
                .message(INPUT_SUCCESS.info)
                .build();
    }
    public List<TicketDto> retrieveAllTicketsByNextDrawDate(LocalDateTime date) {
        LocalDateTime nextDrawDate = drawDateFacade.getNextDrawDate();
        if (date.isAfter(nextDrawDate)) {
            return Collections.emptyList();
        }
        return repository.findAllTicketsByDrawDate(date)
                .stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }
    public List<TicketDto> retrieveAllTicketsByNextDrawDate() {
        LocalDateTime nextDrawDate = drawDateFacade.getNextDrawDate();
        return retrieveAllTicketsByNextDrawDate(nextDrawDate);
    }
}
