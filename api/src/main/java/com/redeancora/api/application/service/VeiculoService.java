package com.redeancora.api.application.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.redeancora.api.application.dto.PecaDTO;
import com.redeancora.api.application.dto.VeiculoDTO;
import com.redeancora.api.domain.entity.Peca;
import com.redeancora.api.domain.entity.Veiculo;
import com.redeancora.api.domain.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private PecaService pecaService;

    @Transactional
    public List<PecaDTO> findPecasPorVeiculo(String modelo) {
        System.out.println("Modelo: " + modelo);
        Specification<Veiculo> specification = (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            if (modelo != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(criteriaBuilder.lower(root.get("veiculo")), modelo.toLowerCase())
                );
            }

            return predicates;
        };

        List<Veiculo> veiculos = veiculoRepository.findAll(specification);

        return veiculos.stream()
                .flatMap(veiculo -> {
                    Peca peca = veiculo.getPeca();
                    System.out.println("Peca encontrada: " + peca.getNome());

                    // Aqui você pode criar uma lista de VeiculoDTOs para adicionar no PecaDTO
                    VeiculoDTO veiculoDTO = new VeiculoDTO(veiculo.getId(), veiculo.getVeiculo(), veiculo.getAno());

                    // Adicionando o VeiculoDTO na lista de veículos da peça
                    return List.of(new PecaDTO(
                            peca.getId(),
                            peca.getNome(),
                            peca.getDescricao(),
                            peca.getPreco(),
                            peca.getDisponibilidade(),
                            peca.getMarca(),
                            peca.getTipo(),
                            List.of(veiculoDTO) // Adicione a lista de VeiculoDTOs
                    )).stream();
                }).collect(Collectors.toList());
    }
}
