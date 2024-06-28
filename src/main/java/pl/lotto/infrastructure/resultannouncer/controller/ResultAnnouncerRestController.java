package pl.lotto.infrastructure.resultannouncer.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.domain.resultannouncer.ResultAnnouncerFacade;
import pl.lotto.domain.resultannouncer.dto.ResultAnnouncerDto;

import java.util.List;

@RestController
@AllArgsConstructor
public class ResultAnnouncerRestController {

    private final ResultAnnouncerFacade resultAnnouncerFacade;

    @GetMapping("/results/{id}")
    public ResponseEntity<ResultAnnouncerDto> getResults(@PathVariable String id) {
        ResultAnnouncerDto resultAnnouncerDto = resultAnnouncerFacade.checkResult(id);
        return ResponseEntity.ok(resultAnnouncerDto);
    }

    @GetMapping("/results")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResultAnnouncerDto>> getAllResults(@PathVariable String id) {
        List<ResultAnnouncerDto> results = resultAnnouncerFacade.getAllResults();
        return ResponseEntity.ok(results);
    }
}
