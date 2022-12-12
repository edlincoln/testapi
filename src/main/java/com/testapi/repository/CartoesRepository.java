package com.testapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testapi.entity.Cartoes;

import java.util.Optional;

@Repository
public interface CartoesRepository extends JpaRepository<Cartoes, Long> {

    Optional<Cartoes> findByNumeroCartao(Long numeroCartao);
}