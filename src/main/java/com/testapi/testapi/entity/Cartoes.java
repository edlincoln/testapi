package com.testapi.testapi.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cartoes implements Serializable {

    private static final long serialVersionUID = -6081836395422397830L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long numeroCartao;

    private String senha;

    @NumberFormat(style = Style.CURRENCY)
    private BigDecimal saldo;
    
    @Version
    @Column(columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version = 0L;
    
    public boolean isValidSenha(String senha) {
    	return senha.equals(this.senha);
    }
    
    public boolean isValidSaldo(BigDecimal saldo) {
    	 return saldo.compareTo(this.saldo) < 1 ? true : false;
    }

}
