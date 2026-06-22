package com.trisha.bff.client;

import com.trisha.bff.model.dto.request.FriendshipRequest;
import com.trisha.bff.model.dto.request.AdventureRequest;
import com.trisha.bff.model.dto.request.PathRequest;
import com.trisha.bff.model.dto.request.EvidenceRequest;
import com.trisha.bff.model.dto.request.MediaRequest;
import com.trisha.bff.model.dto.request.MoveRegionRequest;
import com.trisha.bff.model.dto.request.PointOfInterestRequest;
import com.trisha.bff.model.dto.request.RegionRequest;
import com.trisha.bff.model.dto.request.FollowRequest;
import com.trisha.bff.model.dto.response.FriendshipResponse;
import com.trisha.bff.model.dto.response.AdventureResponse;
import com.trisha.bff.model.dto.response.CountersResponse;
import com.trisha.bff.model.dto.response.FollowStatusResponse;
import com.trisha.bff.model.dto.response.PathResponse;
import com.trisha.bff.model.dto.response.EvidenceResponse;
import com.trisha.bff.model.dto.response.MediaResponse;
import com.trisha.bff.model.dto.response.PageResponse;
import com.trisha.bff.model.dto.response.PointOfInterestResponse;
import com.trisha.bff.model.dto.response.RegionResponse;
import com.trisha.bff.model.dto.response.PublicUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@Slf4j
public class AppClient {

    private static final ParameterizedTypeReference<PageResponse<AdventureResponse>> PAGE_ADVENTURE = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<PageResponse<PathResponse>> PAGE_PATH = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<PageResponse<PointOfInterestResponse>> PAGE_POINT = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<PageResponse<MediaResponse>> PAGE_MEDIA = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<PageResponse<FriendshipResponse>> PAGE_FRIENDSHIP = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<List<PublicUserResponse>> LIST_PUBLIC_USER = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<PageResponse<PublicUserResponse>> PAGE_PUBLIC_USER = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<PageResponse<RegionResponse>> PAGE_REGION = new ParameterizedTypeReference<>() {};

    private final RestClient appRestClient;

    public AppClient(@Qualifier("appRestClient") RestClient appRestClient) {
        this.appRestClient = appRestClient;
    }


    public RegionResponse createRegion(RegionRequest request) {
        return appRestClient.post().uri("/regiao").body(request)
                .retrieve().body(RegionResponse.class);
    }

    public PageResponse<RegionResponse> getMyRegions(Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/regiao")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize()).build())
                .retrieve().body(PAGE_REGION);
    }

    public PageResponse<RegionResponse> discoverRegions(Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/regiao/descobrir")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize()).build())
                .retrieve().body(PAGE_REGION);
    }

    public RegionResponse getRegion(String id) {
        return appRestClient.get().uri("/regiao/{id}", id)
                .retrieve().body(RegionResponse.class);
    }

    public PageResponse<AdventureResponse> getAdventuresByRegion(String regionId, Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/regiao/{id}/aventuras")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize()).build(regionId))
                .retrieve().body(PAGE_ADVENTURE);
    }

    public RegionResponse updateRegion(String id, RegionRequest request) {
        return appRestClient.put().uri("/regiao/{id}", id).body(request)
                .retrieve().body(RegionResponse.class);
    }

    public void deleteRegion(String id) {
        appRestClient.delete().uri("/regiao/{id}", id)
                .retrieve().toBodilessEntity();
    }

    public AdventureResponse createAdventure(AdventureRequest request) {
        return appRestClient.post().uri("/aventura").body(request)
                .retrieve().body(AdventureResponse.class);
    }

    public AdventureResponse moveAdventureRegion(String id, MoveRegionRequest request) {
        return appRestClient.patch().uri("/aventura/{id}/regiao", id).body(request)
                .retrieve().body(AdventureResponse.class);
    }

    public AdventureResponse getAdventure(String id) {
        return appRestClient.get().uri("/aventura/{id}", id)
                .retrieve().body(AdventureResponse.class);
    }

    public PageResponse<AdventureResponse> getAdventuresByUser(String userId, Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/aventura/usuario/{usuarioId}")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build(userId))
                .retrieve().body(PAGE_ADVENTURE);
    }

    public AdventureResponse updateAdventureStatus(String id, String status) {
        return appRestClient.patch()
                .uri(b -> b.path("/aventura/{id}/status").queryParam("status", status).build(id))
                .retrieve().body(AdventureResponse.class);
    }

    public void addParticipant(String adventureId, String userId) {
        appRestClient.post()
                .uri(b -> b.path("/aventura/{id}/participante").queryParam("usuarioId", userId).build(adventureId))
                .retrieve().toBodilessEntity();
    }

    public void deleteAdventure(String id) {
        appRestClient.delete().uri("/aventura/{id}", id)
                .retrieve().toBodilessEntity();
    }

    public PathResponse startPath(PathRequest request) {
        return appRestClient.post().uri("/caminho").body(request)
                .retrieve().body(PathResponse.class);
    }

    public PathResponse finishPath(String id, Double totalDistanceKm) {
        return appRestClient.patch()
                .uri(b -> b.path("/caminho/{id}/finalizar").queryParam("distanciaTotalKm", totalDistanceKm).build(id))
                .retrieve().body(PathResponse.class);
    }

    public PageResponse<PathResponse> getPathsByAdventure(String adventureId, Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/caminho/aventura/{aventuraId}")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build(adventureId))
                .retrieve().body(PAGE_PATH);
    }

    public PageResponse<PathResponse> getPathsByUser(String userId, Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/caminho/usuario/{usuarioId}")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build(userId))
                .retrieve().body(PAGE_PATH);
    }


    public PointOfInterestResponse createPoint(PointOfInterestRequest request) {
        return appRestClient.post().uri("/ponto-interesse").body(request)
                .retrieve().body(PointOfInterestResponse.class);
    }

    public PointOfInterestResponse getPoint(String id) {
        return appRestClient.get().uri("/ponto-interesse/{id}", id)
                .retrieve().body(PointOfInterestResponse.class);
    }

    public PageResponse<PointOfInterestResponse> getPointsByPath(String pathId, Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/ponto-interesse/caminho/{caminhoId}")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build(pathId))
                .retrieve().body(PAGE_POINT);
    }

    public EvidenceResponse addEvidence(EvidenceRequest request) {
        return appRestClient.post().uri("/ponto-interesse/evidencia").body(request)
                .retrieve().body(EvidenceResponse.class);
    }


    public MediaResponse saveMedia(MediaRequest request) {
        return appRestClient.post().uri("/midia").body(request)
                .retrieve().body(MediaResponse.class);
    }

    public PageResponse<MediaResponse> getMediaByAdventure(String adventureId, Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/midia/aventura/{aventuraId}")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build(adventureId))
                .retrieve().body(PAGE_MEDIA);
    }

    public PageResponse<MediaResponse> getMediaByPath(String pathId, Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/midia/caminho/{caminhoId}")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build(pathId))
                .retrieve().body(PAGE_MEDIA);
    }

    public void deleteMedia(String id) {
        appRestClient.delete().uri("/midia/{id}", id)
                .retrieve().toBodilessEntity();
    }


    public FriendshipResponse requestFriendship(FriendshipRequest request) {
        return appRestClient.post().uri("/amizade").body(request)
                .retrieve().body(FriendshipResponse.class);
    }

    public FriendshipResponse respondFriendship(String id, String status) {
        return appRestClient.patch()
                .uri(b -> b.path("/amizade/{id}/responder").queryParam("status", status).build(id))
                .retrieve().body(FriendshipResponse.class);
    }

    public PageResponse<FriendshipResponse> getPending(Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/amizade/pendentes")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build())
                .retrieve().body(PAGE_FRIENDSHIP);
    }

    public PageResponse<FriendshipResponse> getFriends(Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/amizade/amigos")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build())
                .retrieve().body(PAGE_FRIENDSHIP);
    }

    // ----------------------------- Seguidores ---------------------------

    public void follow(String code) {
        appRestClient.post().uri("/seguidor").body(new FollowRequest(code)).retrieve().toBodilessEntity();
    }

    public void unfollow(String code) {
        appRestClient.method(HttpMethod.DELETE).uri("/seguidor").body(new FollowRequest(code))
                .retrieve().toBodilessEntity();
    }

    public PageResponse<PublicUserResponse> getFollowers(String code, Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/seguidor/seguidores")
                        .queryParam("codigo", code)
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize()).build())
                .retrieve().body(PAGE_PUBLIC_USER);
    }

    public PageResponse<PublicUserResponse> getFollowing(String code, Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/seguidor/seguindo")
                        .queryParam("codigo", code)
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize()).build())
                .retrieve().body(PAGE_PUBLIC_USER);
    }

    public CountersResponse getCounters(String code) {
        return appRestClient.get()
                .uri(b -> b.path("/seguidor/contadores").queryParam("codigo", code).build())
                .retrieve().body(CountersResponse.class);
    }

    public FollowStatusResponse getFollowStatus(String code) {
        return appRestClient.get()
                .uri(b -> b.path("/seguidor/status").queryParam("codigo", code).build())
                .retrieve().body(FollowStatusResponse.class);
    }

    // --------------------------- Busca de usuario -----------------------

    public PublicUserResponse findUserByCode(String userCode) {
        return appRestClient.get().uri("/usuario/codigo/{codigo}", userCode)
                .retrieve().body(PublicUserResponse.class);
    }

    public List<PublicUserResponse> autocompleteUser(String term) {
        return appRestClient.get()
                .uri(b -> b.path("/usuario/busca").queryParam("termo", term).build())
                .retrieve().body(LIST_PUBLIC_USER);
    }
}
