package com.testapi.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.testapi.entity.Cartoes;

class CartoesTest {

	Cartoes cartao;

	private static final Long ID = 1l;
	private static final Long NUMERO = 12340987l;
	private static final String SENHA = "1234";
	private static final String SENHA_ERRADA = "4321";
	private static final BigDecimal SALDO_INICIAL = new BigDecimal(500l);
	private static final BigDecimal SALDO_MAIOR = new BigDecimal(700l);
	private static final BigDecimal SALDO_MENOR = new BigDecimal(300l);

	@BeforeEach
	void setUp() {
		cartao = Cartoes.builder()
				.id(ID)
				.numeroCartao(NUMERO)
				.senha(SENHA)
				.saldo(SALDO_INICIAL)
				.build();
	}

	@Test
	@DisplayName("Teste senha ok")
	void isValidSenhaOk() {
		boolean ok = cartao.isValidSenha(SENHA);
		assertTrue(ok);
	}

	@Test
	@DisplayName("Teste senha não ok")
	void isValidSenhaNOk() {
		boolean ok = cartao.isValidSenha(SENHA_ERRADA);
		assertFalse(ok);
	}

	@Test
	@DisplayName("Teste saldo quando o valor é igual")
	void isValidSaldoValorIgual() {
		boolean ok = cartao.isValidSaldo(SALDO_INICIAL);
		assertTrue(ok);
	}
	
	@Test
	@DisplayName("Teste saldo quando o valor é menor")
	void isValidSaldoValorMenor() {
		boolean ok = cartao.isValidSaldo(SALDO_MENOR);
		assertTrue(ok);
	}
	
	@Test
	@DisplayName("Teste saldo quando o valor é maior")
	void isValidSaldoValorMaior() {
		boolean ok = cartao.isValidSaldo(SALDO_MAIOR);
		assertFalse(ok);
	}
}