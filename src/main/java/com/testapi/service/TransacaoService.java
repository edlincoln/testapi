package com.testapi.service;

import com.testapi.entity.Cartoes;
import com.testapi.exceptions.ValidacaoException;
import com.testapi.request.TransacaoRequest;

public interface TransacaoService {
	Cartoes processarCompra(TransacaoRequest request) throws ValidacaoException;
}
