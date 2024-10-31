package com.redeancora.api.application.controller;

import com.redeancora.api.application.dto.PecaDTO;
import com.redeancora.api.application.service.PecaService;
import com.redeancora.api.domain.entity.Peca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pecas")
public class PecaController {

    @Autowired
    private PecaService pecaService;



    //GET http://localhost:8080/api/pecas?nome=freio&marca=ABC&precoMin=100&precoMax=500&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<PecaDTO>> getPecas(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Double precoMin,
            @RequestParam(required = false) Double precoMax,
            @RequestParam(required = false) Boolean disponibilidade,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Peca> specification = (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            if (nome != null) {
                // Usar LIKE com LOWER para permitir busca não sensível a caixa
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("nome")),
                                "%" + nome.toLowerCase() + "%"
                        )
                );
            }

            if (marca != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.like(
                                root.get("marca"),
                                marca // Não usar toLowerCase() aqui
                        )
                );
            }

            if (tipo != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("tipo")),
                                tipo.toLowerCase()
                        )
                );
            }

            if (precoMin != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), precoMin)
                );
            }

            if (precoMax != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.lessThanOrEqualTo(root.get("preco"), precoMax)
                );
            }

            if (disponibilidade != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("disponibilidade"), disponibilidade)
                );
            }

            return predicates;
        };

        Page<PecaDTO> pecasDTO = pecaService.findAll(pageable, specification);
        return ResponseEntity.ok(pecasDTO);
    }
}
