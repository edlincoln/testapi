package com.testapi.testapi.service;

import com.testapi.testapi.entity.Cartoes;
import com.testapi.testapi.exceptions.ValidacaoException;
import com.testapi.testapi.request.TransacaoRequest;

public interface TransacaoService {
	Cartoes processarCompra(TransacaoRequest request) throws ValidacaoException;
}
