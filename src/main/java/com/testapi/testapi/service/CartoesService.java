package com.testapi.testapi.service;

import com.testapi.testapi.entity.Cartoes;
import com.testapi.testapi.exceptions.NotFoundException;
import com.testapi.testapi.exceptions.ValidacaoException;
import com.testapi.testapi.request.CartaoRequest;

public interface CartoesService {

	Cartoes listarSaldoCartao(Long numeroCartao) throws NotFoundException;

	Cartoes salvarCartao(CartaoRequest request) throws ValidacaoException;

}
