package pl.lotto.infrastructure.numberreceiver.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.domain.numberreceiver.NumberReceiverFacade;
import pl.lotto.domain.numberreceiver.dto.NumberReceiverResultDto;

import java.util.HashSet;
import java.util.Set;

@RestController
@AllArgsConstructor
public class InputNumbersRestController {

    private final NumberReceiverFacade numberReceiverFacade;
    @PostMapping("/inputNumbers")
    public ResponseEntity<NumberReceiverResultDto> inputNumbers(@RequestBody @Valid InputNumbersRequestDto inputNumbersRequestDto) {
        Set<Integer> numbersFromUser = new HashSet<>(inputNumbersRequestDto.inputNumbers());
        NumberReceiverResultDto numberReceiverResultDto = numberReceiverFacade.inputNumbers(numbersFromUser);
        return ResponseEntity.ok(numberReceiverResultDto);
    }
}
