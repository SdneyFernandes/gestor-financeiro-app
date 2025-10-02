package br.com.sdney.gestor_financeiro_app.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoDTO(LocalDate data, String descricao, BigDecimal valor) {
}
