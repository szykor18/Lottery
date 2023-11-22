package pl.lotto.infrastructure.numberreceiver.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record InputNumbersRequestDto(@NotNull(message = "{inputnumbers.must.not.be.null}")
                                     @NotEmpty(message = "{inputnumbers.must.not.be.empty}")
                                     List<Integer> inputNumbers) {}
