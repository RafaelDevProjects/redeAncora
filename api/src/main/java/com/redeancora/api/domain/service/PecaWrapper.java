package com.redeancora.api.domain.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.redeancora.api.domain.entity.Peca;

import java.util.List;

public class PecaWrapper {
    @JsonProperty("pecas")
    private List<Peca> pecas;
    public List<Peca> getPecas() {
        return pecas;
    }

    public void setPecas(List<Peca> pecas){
        this.pecas = pecas;
    }
}
