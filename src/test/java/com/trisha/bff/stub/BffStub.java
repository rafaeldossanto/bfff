package com.trisha.bff.stub;

import com.trisha.bff.model.dto.request.AmizadeRequest;
import com.trisha.bff.model.dto.request.AventuraRequest;
import com.trisha.bff.model.dto.request.CaminhoRequest;
import com.trisha.bff.model.dto.request.EvidenciaRequest;
import com.trisha.bff.model.dto.request.MidiaRequest;
import com.trisha.bff.model.dto.request.PontoGpsRequest;
import com.trisha.bff.model.dto.request.PontoInteresseRequest;
import com.trisha.bff.model.dto.request.SessaoRequest;
import com.trisha.bff.model.dto.request.UsuarioCreateRequest;
import com.trisha.bff.model.dto.request.UsuarioUpdateRequest;
import com.trisha.bff.model.dto.response.AmizadeResponse;
import com.trisha.bff.model.dto.response.AutenticacaoResponse;
import com.trisha.bff.model.dto.response.AventuraResponse;
import com.trisha.bff.model.dto.response.CaminhoResponse;
import com.trisha.bff.model.dto.response.EvidenciaResponse;
import com.trisha.bff.model.dto.response.MidiaResponse;
import com.trisha.bff.model.dto.response.PontoGpsResponse;
import com.trisha.bff.model.dto.response.PontoInteresseResponse;
import com.trisha.bff.model.dto.response.SessaoResponse;
import com.trisha.bff.model.dto.response.UsuarioResponse;

import java.time.LocalDateTime;

/**
 * Facilitador de testes para os DTOs de request e response do BFF.
 */
public final class BffStub {

    public static final String USUARIO_ID = "usuario-1";
    public static final String AVENTURA_ID = "aventura-1";
    public static final String CAMINHO_ID = "caminho-1";
    public static final String PONTO_ID = "ponto-1";
    public static final String SESSAO_ID = "sessao-1";

    private BffStub() {
    }

    // ----------------------------- Usuario ------------------------------

    public static UsuarioResponse umUsuario() {
        return new UsuarioResponse(USUARIO_ID, "Rafael", "rafael@trilha.com",
                "USR-1", "ATIVO", LocalDateTime.now(), LocalDateTime.now());
    }

    public static AutenticacaoResponse umaAutenticacao() {
        return new AutenticacaoResponse(umUsuario(), "jwt-token", 7200);
    }

    public static UsuarioCreateRequest umUsuarioCreateRequest() {
        return new UsuarioCreateRequest("Rafael", "rafael@trilha.com", "senha123");
    }

    public static UsuarioUpdateRequest umUsuarioUpdateRequest() {
        return new UsuarioUpdateRequest("Rafael Santos", null);
    }

    // ----------------------------- Aventura -----------------------------

    public static AventuraResponse umaAventura() {
        return new AventuraResponse(AVENTURA_ID, USUARIO_ID, "regiao-1", "Pico da Bandeira",
                "PLANEJADA", "PRIVADA", LocalDateTime.now());
    }

    public static AventuraRequest umaAventuraRequest() {
        return new AventuraRequest("regiao-1", "Pico da Bandeira", "PRIVADA");
    }

    // ----------------------------- Caminho ------------------------------

    public static CaminhoResponse umCaminho() {
        return new CaminhoResponse(CAMINHO_ID, AVENTURA_ID, USUARIO_ID, "ROXO", 1,
                LocalDateTime.now(), null, null);
    }

    public static CaminhoRequest umCaminhoRequest() {
        return new CaminhoRequest(AVENTURA_ID, "ROXO");
    }

    // ------------------------- Ponto de interesse -----------------------

    public static PontoInteresseResponse umPonto() {
        return new PontoInteresseResponse(PONTO_ID, CAMINHO_ID, USUARIO_ID, "MIRANTE",
                "Mirante", "Vista", -20.43, -41.79, 3, LocalDateTime.now());
    }

    public static PontoInteresseRequest umPontoRequest() {
        return new PontoInteresseRequest(CAMINHO_ID, "MIRANTE",
                "Mirante", "Vista", -20.43, -41.79);
    }

    public static EvidenciaResponse umaEvidencia() {
        return new EvidenciaResponse("evidencia-1", PONTO_ID, USUARIO_ID,
                "https://cdn/foto.jpg", "VISTA", true, LocalDateTime.now());
    }

    public static EvidenciaRequest umaEvidenciaRequest() {
        return new EvidenciaRequest(PONTO_ID, "https://cdn/foto.jpg",
                "VISTA", -20.43, -41.79);
    }

    // ------------------------------ Midia -------------------------------

    public static MidiaResponse umaMidia() {
        return new MidiaResponse("midia-1", AVENTURA_ID, CAMINHO_ID, "FOTO",
                "https://cdn/midia.jpg", 0.30, 1.5, LocalDateTime.now());
    }

    public static MidiaRequest umaMidiaRequest() {
        return new MidiaRequest(AVENTURA_ID, CAMINHO_ID, "FOTO",
                "https://cdn/midia.jpg", -20.43, -41.79, 1.5, 0.30);
    }

    // ----------------------------- Amizade ------------------------------

    public static AmizadeResponse umaAmizade() {
        return new AmizadeResponse("amizade-1", USUARIO_ID, "usuario-2", "ACEITA",
                LocalDateTime.now(), LocalDateTime.now());
    }

    public static AmizadeRequest umaAmizadeRequest() {
        return new AmizadeRequest("rafael#2");
    }

    // ---------------------------- Localizacao ---------------------------

    public static SessaoResponse umaSessao() {
        return new SessaoResponse(SESSAO_ID, CAMINHO_ID, USUARIO_ID, "EM_ANDAMENTO",
                false, 5.0, null, LocalDateTime.now(), null);
    }

    public static SessaoRequest umaSessaoRequest() {
        return new SessaoRequest(CAMINHO_ID, USUARIO_ID, true, 10.0);
    }

    public static PontoGpsResponse umPontoGps() {
        return new PontoGpsResponse("gps-1", SESSAO_ID, -20.43, -41.79, 800.0, 5.0, 1.2,
                1, LocalDateTime.now(), null, null);
    }

    public static PontoGpsRequest umPontoGpsRequest() {
        return new PontoGpsRequest(SESSAO_ID, -20.43, -41.79, 800.0, 5.0, 1.2);
    }
}
