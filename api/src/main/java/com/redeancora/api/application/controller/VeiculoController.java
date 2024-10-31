package com.redeancora.api.application.controller;

import com.redeancora.api.application.dto.PecaDTO;
import com.redeancora.api.application.service.VeiculoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {
    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<List<PecaDTO>> getPecasPorVeiculo(
            @RequestParam(required = false) String modelo
    ) {
        List<PecaDTO> pecas = veiculoService.findPecasPorVeiculo(modelo);
        if (pecas.isEmpty()) {
            return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar peças
        }
        return ResponseEntity.ok(pecas); // Retorna 200 com as peças encontradas
    }
}
