package pl.lotto.domain.numberreveiver;

import lombok.AllArgsConstructor;
import pl.lotto.domain.numberreveiver.dto.InputNumbersResultDto;
import pl.lotto.domain.numberreveiver.dto.TicketDto;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import static pl.lotto.domain.numberreveiver.ValidationResult.INPUT_SUCCESS;

@AllArgsConstructor
public class NumberReceiverFacade {
    private final NumberValidator validator;
    private final NumberReceiverRepository repository;
    private final HashGenerable hashGenerator;
    private final DrawDateGenerator drawDateGenerator;

    public InputNumbersResultDto inputNumbers(Set<Integer> numbersFromUser) {
        List<ValidationResult> validationResultList = validator.validate(numbersFromUser);
        if (!validationResultList.isEmpty()) {
            String resultMessage = validator.createResultMessage();
            return InputNumbersResultDto.builder()
                    .ticketDto(null)
                    .message(resultMessage)
                    .build();
        }
        String hash = hashGenerator.getHash();
        LocalDateTime drawDate = drawDateGenerator.getNextDrawDate();
        Ticket savedTicket = repository.save(new Ticket(hash, drawDate, numbersFromUser));
        return InputNumbersResultDto.builder()
                .ticketDto(TicketMapper.mapFromTicket(savedTicket))
                .message(INPUT_SUCCESS.info)
                .build();
    }
    public List<TicketDto> retrieveAllTicketsByNextDrawDate(LocalDateTime date) {
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();
        if (date.isAfter(nextDrawDate)) {
            return Collections.emptyList();
        }
        return repository.findAllTicketsByDrawDate(date)
                .stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }
    public List<TicketDto> retrieveAllTicketsByNextDrawDate() {
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();
        return retrieveAllTicketsByNextDrawDate(nextDrawDate);
    }

    public LocalDateTime retrieveNextDrawDate() {
        return drawDateGenerator.getNextDrawDate();
    }
}
