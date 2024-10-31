package com.redeancora.api.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Peca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;
    private String descricao;
    private Double preco;
    private Boolean disponibilidade;
    private String marca;
    private String tipo;

    @ElementCollection
    @JsonProperty("ano_compatibilidade")
    private List<Integer> anoCompatibilidade;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "peca", orphanRemoval = true)
    private List<Veiculo> veiculos;


    //getter and setter

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public Boolean getDisponibilidade() {
        return disponibilidade;
    }

    public String getMarca() {
        return marca;
    }

    public String getTipo() {
        return tipo;
    }

    public List<Integer> getAnoCompatibilidade() {
        return anoCompatibilidade;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setDisponibilidade(Boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setAnoCompatibilidade(List<Integer> anoCompatibilidade) {
        this.anoCompatibilidade = anoCompatibilidade;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addVeiculo(Veiculo veiculo) {
        veiculo.setPeca(this);
        this.veiculos.add(veiculo);
    }
}
