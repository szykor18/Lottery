package pl.lotto.domain.resultannouncer.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record ResultAnnouncerDto(ResponseDto responseDto, String message) implements Serializable {
}
