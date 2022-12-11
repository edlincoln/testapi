package com.testapi.testapi.service;

import com.testapi.testapi.exceptions.ValidacaoException;
import com.testapi.testapi.request.TransacaoRequest;

public interface TransacaoService {
	void processarCompra(TransacaoRequest request) throws ValidacaoException;
}
