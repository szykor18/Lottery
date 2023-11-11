package pl.lotto.domain.resultannouncer.dto;

import lombok.Builder;

@Builder
public record ResultAnnouncerDto(ResponseDto responseDto, String message) {
}
