package com.redeancora.api.domain.repository;

import com.redeancora.api.domain.entity.Peca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PecaRepository extends JpaRepository<Peca, Long>, JpaSpecificationExecutor<Peca> { }
