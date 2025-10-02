package br.com.sdney.gestor_financeiro_app.entities;

import br.com.sdney.gestor_financeiro_app.entities.Usuario;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "contas_conectadas")
@Data
public class ContaConectada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idContaNoBanco;
    private String nomeBanco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
