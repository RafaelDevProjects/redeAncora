package com.redeancora.api.application.dto;

import java.util.List;

public record PecaDTO(
        Long id,
        String nome,
        String descricao,
        Double preco,
        Boolean disponibilidade,
        String marca,
        String tipo,
        List<VeiculoDTO> veiculos
) { }
