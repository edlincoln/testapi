package com.testapi.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testapi.entity.Cartoes;
import com.testapi.enums.TransactionErrorEnum;
import com.testapi.exceptions.NotFoundException;
import com.testapi.exceptions.ValidacaoException;
import com.testapi.repository.CartoesRepository;
import com.testapi.request.CartaoRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartoesServiceImpl implements CartoesService {

	@Autowired
	private CartoesRepository repository;

	private static final BigDecimal SALDO_INICIAL = new BigDecimal(500l);

	public Cartoes salvarCartao(CartaoRequest request) throws ValidacaoException {
		log.debug("Salvando cartão");
		Optional<Cartoes> opCartao = repository.findByNumeroCartao(request.getNumeroCartao());
		if (opCartao.isPresent()) {
			throw new ValidacaoException(TransactionErrorEnum.CARTAO_INEXISTENTE.toString());
		}

		Cartoes cartao = Cartoes.builder()
				.numeroCartao(request.getNumeroCartao())
				.senha(request.getSenha())
				.saldo(SALDO_INICIAL).build();

		return repository.save(cartao);

	}

	public Cartoes listarSaldoCartao(Long numeroCartao) throws NotFoundException {
		log.debug("listando cartão: %s", numeroCartao);
		return repository.findByNumeroCartao(numeroCartao).orElseThrow(() -> (new NotFoundException("Not found")));
	}
}
