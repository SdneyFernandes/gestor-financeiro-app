package br.com.sdney.gestor_financeiro_app.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import br.com.sdney.gestor_financeiro_app.entities.ContaConectada;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContaConectada> contasConectadas;
}
