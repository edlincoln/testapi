package com.testapi.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testapi.entity.Cartoes;
import com.testapi.enums.TransactionErrorEnum;
import com.testapi.exceptions.ValidacaoException;
import com.testapi.repository.CartoesRepository;
import com.testapi.request.TransacaoRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransacaoServiceImpl implements TransacaoService {
	
	@Autowired
	private CartoesRepository repository;

	@Override
	@Transactional
	public Cartoes processarCompra(TransacaoRequest request) throws ValidacaoException {
		log.debug("Processando transacao");
		Optional<Cartoes> opCartao = repository.findByNumeroCartao(request.getNumeroCartao());
		if (!opCartao.isPresent()) {
			throw new ValidacaoException(TransactionErrorEnum.CARTAO_INEXISTENTE.toString());
		}
		
		Cartoes cartao = opCartao.get();
		
		if (!cartao.isValidSenha(request.getSenhaCartao())) {
			throw new ValidacaoException(TransactionErrorEnum.SENHA_INVALIDA.toString());
		}
		
		if (!cartao.isValidSaldo(request.getValor())) {
			throw new ValidacaoException(TransactionErrorEnum.SALDO_INSUFICIENTE.toString());
		}
		
		BigDecimal novoSaldo = cartao.getSaldo().subtract(request.getValor());
		
		cartao.setSaldo(novoSaldo);
		
		return repository.save(cartao);
	}

}
