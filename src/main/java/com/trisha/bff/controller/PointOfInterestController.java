package com.trisha.bff.controller;

import com.trisha.bff.model.dto.request.EvidenceRequest;
import com.trisha.bff.model.dto.request.PointOfInterestRequest;
import com.trisha.bff.model.dto.response.EvidenceResponse;
import com.trisha.bff.model.dto.response.PageResponse;
import com.trisha.bff.model.dto.response.PointOfInterestResponse;
import com.trisha.bff.service.PointOfInterestBffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff/pontos-interesse")
@RequiredArgsConstructor
public class PointOfInterestController {

    private final PointOfInterestBffService pointService;

    @PostMapping
    public PointOfInterestResponse create(@RequestBody @Valid PointOfInterestRequest request) {
        return pointService.create(request);
    }

    @GetMapping("/{id}")
    public PointOfInterestResponse getById(@PathVariable String id) {
        return pointService.getById(id);
    }

    @GetMapping("/caminho/{caminhoId}")
    public PageResponse<PointOfInterestResponse> getByPath(@PathVariable("caminhoId") String pathId, Pageable pageable) {
        return pointService.getByPath(pathId, pageable);
    }

    @PostMapping("/evidencia")
    public EvidenceResponse addEvidence(@RequestBody @Valid EvidenceRequest request) {
        return pointService.addEvidence(request);
    }
}
