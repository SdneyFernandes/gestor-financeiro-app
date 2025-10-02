package br.com.sdney.gestor_financeiro_app.beans;

import br.com.sdney.gestor_financeiro_app.dtos.TransacaoDTO;
import br.com.sdney.gestor_financeiro_app.services.DashboardService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Component("dashboardBean")
@ViewScoped
@Getter
public class DashboardBean implements Serializable {

    @Autowired
    private DashboardService dashboardService;

    private BigDecimal saldoConsolidado;
    private List<TransacaoDTO> extratoUnificado;

    @PostConstruct
    public void init() {
        String idContaBancoA = "123";
        String idContaBancoB = "456";

        saldoConsolidado = dashboardService.getSaldoConsolidado(idContaBancoA, idContaBancoB);
        extratoUnificado = dashboardService.getExtratoUnificado(idContaBancoA, idContaBancoB);
    }
}