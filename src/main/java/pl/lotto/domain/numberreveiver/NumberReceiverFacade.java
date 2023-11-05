package pl.lotto.domain.numberreveiver;

import lombok.AllArgsConstructor;
import pl.lotto.domain.numberreveiver.dto.InputNumbersResultDto;
import pl.lotto.domain.numberreveiver.dto.TicketDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import static pl.lotto.domain.numberreveiver.ValidationResult.INPUT_SUCCESS;

@AllArgsConstructor
public class NumberReceiverFacade {
    private final NumberValidator validator;
    private final NumberReceiverRepository repository;
    private final HashGenerable hashGenerator;
    private final Clock clock;

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
        LocalDateTime drawDate = LocalDateTime.now(clock);
        Ticket savedTicket = repository.save(new Ticket(hash, drawDate, numbersFromUser));
        return InputNumbersResultDto.builder()
                .ticketDto(TicketMapper.mapFromTicket(savedTicket))
                .message(INPUT_SUCCESS.info)
                .build();
    }
    public List<TicketDto> userNumbers(LocalDateTime date) {
        List<Ticket> allTicketsByDrawDate = repository.findAllTicketsByDrawDate(date);
        return allTicketsByDrawDate
                .stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }
}
