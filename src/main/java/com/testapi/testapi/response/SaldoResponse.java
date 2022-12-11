package com.testapi.testapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SaldoResponse {

    private BigDecimal saldo;
}
