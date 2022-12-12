package com.testapi.service;

import com.testapi.entity.Cartoes;
import com.testapi.exceptions.NotFoundException;
import com.testapi.exceptions.ValidacaoException;
import com.testapi.request.CartaoRequest;

public interface CartoesService {

	Cartoes listarSaldoCartao(Long numeroCartao) throws NotFoundException;

	Cartoes salvarCartao(CartaoRequest request) throws ValidacaoException;

}
