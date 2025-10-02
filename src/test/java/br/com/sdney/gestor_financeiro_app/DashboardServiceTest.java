package br.com.sdney.gestor_financeiro_app.services;

import br.com.sdney.gestor_financeiro_app.clients.*;
import br.com.sdney.gestor_financeiro_app.dtos. *;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private BancoAClient bancoAClient;

    @Mock
    private BancoBClient bancoBClient;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    void deveSomarSaldosDosDoisBancosCorretamente() {

        when(bancoAClient.getSaldo("123")).thenReturn(new SaldoDTO(new BigDecimal("1000.50")));
        when(bancoBClient.getSaldo("456")).thenReturn(new SaldoDTO(new BigDecimal("2000.00")));

        BigDecimal saldoConsolidado = dashboardService.getSaldoConsolidado("123", "456");
        assertEquals(new BigDecimal("3000.50"), saldoConsolidado);
    }

    @Test
    void deveUnificarEOrdenarExtratosCorretamente() {

        TransacaoDTO transacaoA = new TransacaoDTO(LocalDate.now().minusDays(1), "Compra A", new BigDecimal("-50"));
        TransacaoDTO transacaoB = new TransacaoDTO(LocalDate.now(), "Compra B", new BigDecimal("-100"));
        when(bancoAClient.getExtrato("123")).thenReturn(List.of(transacaoA));
        when(bancoBClient.getExtrato("456")).thenReturn(List.of(transacaoB));

        List<TransacaoDTO> extratoUnificado = dashboardService.getExtratoUnificado("123", "456");
        assertEquals(2, extratoUnificado.size());
        assertEquals("Compra B", extratoUnificado.get(0).descricao());
    }
}