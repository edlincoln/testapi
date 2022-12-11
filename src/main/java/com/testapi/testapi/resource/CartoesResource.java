package com.testapi.testapi.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testapi.testapi.entity.Cartoes;
import com.testapi.testapi.exceptions.NotFoundException;
import com.testapi.testapi.exceptions.ValidacaoException;
import com.testapi.testapi.request.CartaoRequest;
import com.testapi.testapi.response.CartaoResponse;
import com.testapi.testapi.response.SaldoResponse;
import com.testapi.testapi.service.CartoesService;

@RestController
@RequestMapping(value = "cartoes")
public class CartoesResource {

	@Autowired
	private CartoesService service;

	@PostMapping
	public ResponseEntity<?> cadastrar(@Valid @RequestBody CartaoRequest request) {
		try {
			Cartoes cartao = service.salvarCartao(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(this.buildCartaoResponse(cartao));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(request);
		}
	}

	@GetMapping("{numeroCartao}")
	public ResponseEntity<?> listarNumeroCartao(@Valid @PathVariable Long numeroCartao) {
		Cartoes cartao = null;
		try {
			cartao = service.listarSaldoCartao(numeroCartao);
			return ResponseEntity.ok(new SaldoResponse(cartao.getSaldo()));
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	private CartaoResponse buildCartaoResponse(Cartoes cartao) {
		return CartaoResponse.builder().numeroCartao(cartao.getNumeroCartao()).senha(cartao.getSenha()).build();
	}
}
