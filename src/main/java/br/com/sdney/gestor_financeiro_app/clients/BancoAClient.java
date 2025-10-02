package br.com.sdney.gestor_financeiro_app.clients;

import br.com.sdney.gestor_financeiro_app.dtos. *;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "banco-a", url = "http://localhost:8081")
public interface BancoAClient {

    @GetMapping("/contas/{id}/saldo")
    SaldoDTO getSaldo(@PathVariable String id);

    @GetMapping("/contas/{id}/extrato")
    List<TransacaoDTO> getExtrato(@PathVariable String id);
}
