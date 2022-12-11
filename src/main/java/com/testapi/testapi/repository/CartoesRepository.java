package com.testapi.testapi.repository;

import com.testapi.testapi.entity.Cartoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartoesRepository extends JpaRepository<Cartoes, Long> {

    Optional<Cartoes> findByNumeroCartao(Long numeroCartao);
}