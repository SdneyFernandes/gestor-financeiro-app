package br.com.sdney.gestor_financeiro_app.services;

import br.com.sdney.gestor_financeiro_app.clients.*;
import br.com.sdney.gestor_financeiro_app.dtos. *;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private BancoAClient bancoAClient;

    @Autowired
    private BancoBClient bancoBClient;

    public BigDecimal getSaldoConsolidado(String idContaBancoA, String idContaBancoB) {
        // Chama as duas APIs para buscar os saldos
        BigDecimal saldoA = bancoAClient.getSaldo(idContaBancoA).saldo();
        BigDecimal saldoB = bancoBClient.getSaldo(idContaBancoB).saldo();

        // Soma os saldos e retorna
        return saldoA.add(saldoB);
    }

    public List<TransacaoDTO> getExtratoUnificado(String idContaBancoA, String idContaBancoB) {
        // Chama as duas APIs para buscar os extratos
        List<TransacaoDTO> extratoA = bancoAClient.getExtrato(idContaBancoA);
        List<TransacaoDTO> extratoB = bancoBClient.getExtrato(idContaBancoB);

        // Junta as duas listas em uma só
        List<TransacaoDTO> extratoUnificado = new ArrayList<>();
        extratoUnificado.addAll(extratoA);
        extratoUnificado.addAll(extratoB);

        e para a mais antiga
        return extratoUnificado.stream()
                .sorted(Comparator.comparing(TransacaoDTO::data).reversed())
                .collect(Collectors.toList());
    }

public SaldoDTO getSaldoBancoA(String idContaBancoA) {
    return bancoAClient.getSaldo(idContaBancoA);
}

public SaldoDTO getSaldoBancoB(String idContaBancoB) {
    return bancoBClient.getSaldo(idContaBancoB);
}
}