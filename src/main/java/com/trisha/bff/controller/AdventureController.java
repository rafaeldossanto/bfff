package com.trisha.bff.controller;

import com.trisha.bff.model.dto.request.AdventureRequest;
import com.trisha.bff.model.dto.request.MoveRegionRequest;
import com.trisha.bff.model.dto.response.AdventureDetailResponse;
import com.trisha.bff.model.dto.response.AdventureResponse;
import com.trisha.bff.model.dto.response.PageResponse;
import com.trisha.bff.service.AdventureBffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff/aventuras")
@RequiredArgsConstructor
public class AdventureController {

    private final AdventureBffService adventureService;

    @PostMapping
    public AdventureResponse create(@RequestBody @Valid AdventureRequest request) {
        return adventureService.create(request);
    }

    @GetMapping("/{id}")
    public AdventureResponse getById(@PathVariable String id) {
        return adventureService.getById(id);
    }

    @GetMapping("/{id}/detalhe")
    public AdventureDetailResponse getDetail(@PathVariable String id) {
        return adventureService.getDetail(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public PageResponse<AdventureResponse> getByUser(@PathVariable("usuarioId") String userId, Pageable pageable) {
        return adventureService.getByUser(userId, pageable);
    }

    @PatchMapping("/{id}/status")
    public AdventureResponse updateStatus(@PathVariable String id, @RequestParam String status) {
        return adventureService.updateStatus(id, status);
    }

    @PatchMapping("/{id}/regiao")
    public AdventureResponse moveRegion(@PathVariable String id, @RequestBody MoveRegionRequest request) {
        return adventureService.moveRegion(id, request);
    }

    @PostMapping("/{id}/participante")
    public void addParticipant(@PathVariable String id, @RequestParam("usuarioId") String userId) {
        adventureService.addParticipant(id, userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        adventureService.delete(id);
    }
}
