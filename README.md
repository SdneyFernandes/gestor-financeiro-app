# Meu Gestor Open Finance (Estudo de JSF + PrimeFaces)

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green.svg)
![JSF](https://img.shields.io/badge/JSF-Jakarta_Faces_4-blue.svg)
![PrimeFaces](https://img.shields.io/badge/PrimeFaces-13-blue.svg)
![H2](https://img.shields.io/badge/Database-H2_InMemory-lightgrey.svg)

<img width="1000" height="500" alt="iiiiii" src="https://github.com/user-attachments/assets/c4e6023f-fbed-4834-a3c4-4ae92004b24c" />

<img width="1000" height="500" alt="uuuu" src="https://github.com/user-attachments/assets/f78bb326-9fdf-48f0-bf43-830efefabcc1" />


## 📖 Visão Geral do Projeto

Este projeto é um estudo prático que simula um **agregador financeiro inspirado no conceito de Open Finance**. O objetivo principal foi aprender e aplicar uma stack de tecnologia Java tradicionalmente usada no mundo corporativo, composta por **JavaServer Faces (JSF)** e **PrimeFaces**, integrada a um backend moderno com **Spring Boot**.

A aplicação consiste em um dashboard que consome e consolida dados de múltiplas APIs de "bancos fictícios", apresentando uma visão unificada da vida financeira de um usuário.

---

Este projeto foi construído em fases, com decisões claras em cada etapa para maximizar o aprendizado e a simplicidade.

Para simular um ambiente real de Open Finance, a primeira etapa foi criar dois microserviços Spring Boot independentes (`banco-a-api` e `banco-b-api`). Cada um expõe uma API REST simples que retorna dados financeiros fixos (saldo e extrato). Isso permitiu focar na lógica de consumo e agregação na aplicação principal, sem depender de integrações complexas.

A aplicação principal utiliza **Spring Cloud OpenFeign** para se comunicar com as mock APIs. Essa escolha, em vez de um `RestTemplate`, torna o código de integração declarativo, limpo e de fácil manutenção, criando interfaces `Client` que abstraem a complexidade das chamadas HTTP.

Foi criada uma camada de serviço (`DashboardService`) com a responsabilidade única de orquestrar as chamadas para as diferentes APIs, agregar os resultados (somar saldos, unificar e ordenar extratos) e entregar os dados prontos para a camada de visualização.

O coração da interface é um **Managed Bean** do JSF (`DashboardBean`). Graças à integração via **JoinFaces**, este bean é anotado com `@Component` do Spring, permitindo a injeção de dependências do Spring (como o `DashboardService`) diretamente. O ciclo de vida `@ViewScoped` garante que os dados sejam carregados uma vez por visita à página.

Para acelerar o desenvolvimento e focar na lógica da aplicação e na nova stack de UI, optei por usar o banco de dados em memória **H2**[cite: 7]. Isso eliminou a necessidade de gerenciar um container Docker para o banco de dados nesta fase de prática, permitindo que a aplicação suba de forma autossuficiente com um único comando.

---

## 🛠️ Tech Stack

-   **Aplicação Principal:** Java 21, Spring Boot 3, JSF 4, PrimeFaces 13, Spring Data JPA, JoinFaces.
-   **Mock APIs:** Java 21, Spring Boot 3, Spring Web.
-   **Integração:** Spring Cloud OpenFeign.
-   **Banco de Dados:** H2 InMemory Database.
-   **Testes:** JUnit 5, Mockito.
-   **Build & Dependências:** Maven, Lombok.

---

## 🚀 Como Executar (Ambiente de Prática Local)

### Pré-requisitos
-   Git
-   JDK 21+
-   Maven 3.8+

### 1. Clone os 3 Repositórios
Você precisará da aplicação principal e dos dois bancos fictícios.
```bash
git clone [https://github.com/SdneyFernandes/gestor-financeiro-app.git]
git clone [https://github.com/SdneyFernandes/banco-b-api.git]
git clone [https://github.com/SdneyFernandes/banco-b-api.git]
```

### 2. Inicie as APIs dos Bancos Fictícios
Você precisará de **dois terminais separados**.

* **Terminal 1:**
    ```bash
    cd banco-a-api
    mvn spring-boot:run
    ```

* **Terminal 2:**
    ```bash
    cd banco-b-api
    mvn spring-boot:run
    ```
Aguarde até que ambas as aplicações iniciem (uma na porta `8081` e outra na `8082`).

### 3. Inicie a Aplicação Principal
Em um **terceiro terminal**.
```bash
cd meu-gestor-financeiro-app
mvn spring-boot:run
```

### 4. Acesse a Aplicação
Quando tudo estiver no ar, abra seu navegador e acesse:
**`http://localhost:8080/index.xhtml`**

Clique no botão para navegar até o dashboard e ver os dados consolidados. Você também pode acessar o console do banco de dados em memória em `http://localhost:8080/h2-console`.
