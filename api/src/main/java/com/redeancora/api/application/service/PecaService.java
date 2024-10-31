package com.redeancora.api.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redeancora.api.application.dto.PecaDTO;
import com.redeancora.api.application.dto.VeiculoDTO;
import com.redeancora.api.domain.entity.Peca;
import com.redeancora.api.domain.entity.Veiculo;
import com.redeancora.api.domain.repository.PecaRepository;
import com.redeancora.api.domain.service.PecaWrapper;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PecaService {
    @Autowired
    private PecaRepository pecaRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    @Transactional
    public void init() {
        try {
            // Certifique-se de incluir o nome do arquivo JSON
            File jsonFile = new File("E:\\PROJETOS\\Java\\redeAncora\\api.json");
            PecaWrapper pecaWrapper = objectMapper.readValue(jsonFile, PecaWrapper.class);

            for (Peca peca : pecaWrapper.getPecas()) {
                List<Veiculo> veiculosParaAdicionar = new ArrayList<>();

                if (peca.getVeiculos() != null) {
                    for (Veiculo veiculo : peca.getVeiculos()) {
                        veiculo.setPeca(peca); // Define a referência para a peça
                        veiculosParaAdicionar.add(veiculo);
                    }
                }

                peca.setVeiculos(veiculosParaAdicionar); // Define a lista de veículos
            }

            pecaRepository.saveAll(pecaWrapper.getPecas()); // Salva todas as peças com seus veículos associados
            System.out.println("Peças e veículos salvos com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo JSON: " + e.getMessage());
        }
    }

    @Transactional
    public Page<PecaDTO> findAll(Pageable pageable, Specification<Peca> specification) {
        Page<Peca> page = pecaRepository.findAll(specification, pageable);

        return page.map(peca -> {
            List<VeiculoDTO> veiculoDTOs = peca.getVeiculos().stream()
                    .map(veiculo -> new VeiculoDTO(veiculo.getId(), veiculo.getVeiculo(), veiculo.getAno()))
                    .toList();

            return new PecaDTO(
                    peca.getId(),
                    peca.getNome(),
                    peca.getDescricao(),
                    peca.getPreco(),
                    peca.getDisponibilidade(),
                    peca.getMarca(),
                    peca.getTipo(),
                    veiculoDTOs
            );
        });
    }
}
