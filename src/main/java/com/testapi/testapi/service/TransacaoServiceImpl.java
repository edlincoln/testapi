package com.testapi.testapi.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testapi.testapi.entity.Cartoes;
import com.testapi.testapi.enums.TransactionErrorEnum;
import com.testapi.testapi.exceptions.ValidacaoException;
import com.testapi.testapi.repository.CartoesRepository;
import com.testapi.testapi.request.TransacaoRequest;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransacaoServiceImpl implements TransacaoService {
	
	@Autowired
	private CartoesRepository repository;

	@Override
	@Transactional(value = TxType.REQUIRES_NEW)
	public void processarCompra(TransacaoRequest request) throws ValidacaoException {
		log.debug("Processando transacao");
		Optional<Cartoes> opCartao = repository.findByNumeroCartao(request.getNumeroCartao());
		if (!opCartao.isPresent()) {
			throw new ValidacaoException(TransactionErrorEnum.CARTAO_INEXISTENTE.toString());
		}
		
		Cartoes cartao = opCartao.get();
		
		if (!cartao.isValidSenha(request.getSenha())) {
			throw new ValidacaoException(TransactionErrorEnum.SENHA_INVALIDA.toString());
		}
		
		if (!cartao.isValidSaldo(request.getValor())) {
			throw new ValidacaoException(TransactionErrorEnum.SALDO_INSUFICIENTE.toString());
		}
		
		BigDecimal novoSaldo = cartao.getSaldo().subtract(request.getValor());
		
		cartao.setSaldo(novoSaldo);
		
		repository.save(cartao);
	}

}
