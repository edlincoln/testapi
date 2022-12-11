package com.testapi.testapi.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testapi.testapi.exceptions.ValidacaoException;
import com.testapi.testapi.request.TransacaoRequest;
import com.testapi.testapi.service.TransacaoService;

@RestController
@RequestMapping(value = "transacoes")
public class TransacoesResource {
	
	@Autowired
	private TransacaoService service;

	@PostMapping
	public ResponseEntity<?> processar(@Valid @RequestBody TransacaoRequest request) {
		try {
			service.processarCompra(request);
			return ResponseEntity.status(HttpStatus.CREATED).body("OK");
		} catch (ValidacaoException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		}
	}

}
