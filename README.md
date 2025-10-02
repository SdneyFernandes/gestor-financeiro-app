# Meu Gestor Open Finance (Estudo de JSF + PrimeFaces)

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green.svg)
![JSF](https://img.shields.io/badge/JSF-Jakarta_Faces_4-blue.svg)
![PrimeFaces](https://img.shields.io/badge/PrimeFaces-13-blue.svg)
![H2](https://img.shields.io/badge/Database-H2_InMemory-lightgrey.svg)

## 📖 Visão Geral do Projeto

Este projeto é um estudo prático que simula um **agregador financeiro inspirado no conceito de Open Finance**. O objetivo principal foi aprender e aplicar uma stack de tecnologia Java tradicionalmente usada no mundo corporativo, composta por **JavaServer Faces (JSF)** e **PrimeFaces**, integrada a um backend moderno com **Spring Boot**.

A aplicação consiste em um dashboard que consome e consolida dados de múltiplas APIs de "bancos fictícios", apresentando uma visão unificada da vida financeira de um usuário.

---

## 🧠 Lógica de Construção e Decisões de Arquitetura

Este projeto foi construído em fases, com decisões claras em cada etapa para maximizar o aprendizado e a simplicidade.

* **1. Simulação do Ecossistema (Mock APIs):**
    > [cite_start]Para simular um ambiente real de Open Finance, a primeira etapa foi criar dois microserviços Spring Boot independentes (`banco-a-api` e `banco-b-api`)[cite: 1, 2]. Cada um expõe uma API REST simples que retorna dados financeiros fixos (saldo e extrato). Isso permitiu focar na lógica de consumo e agregação na aplicação principal, sem depender de integrações complexas.

* **2. Agregação de Dados com OpenFeign:**
    > [cite_start]A aplicação principal utiliza **Spring Cloud OpenFeign** para se comunicar com as mock APIs[cite: 3, 4]. Essa escolha, em vez de um `RestTemplate`, torna o código de integração declarativo, limpo e de fácil manutenção, criando interfaces `Client` que abstraem a complexidade das chamadas HTTP.

* **3. Camada de Serviço e Lógica de Negócio:**
    > [cite_start]Foi criada uma camada de serviço (`DashboardService`) [cite: 5] com a responsabilidade única de orquestrar as chamadas para as diferentes APIs, agregar os resultados (somar saldos, unificar e ordenar extratos) e entregar os dados prontos para a camada de visualização.

* **4. Integração JSF + Spring:**
    > [cite_start]O coração da interface é um **Managed Bean** do JSF (`DashboardBean`)[cite: 6]. Graças à integração via **JoinFaces**, este bean é anotado com `@Component` do Spring, permitindo a injeção de dependências do Spring (como o `DashboardService`) diretamente. O ciclo de vida `@ViewScoped` garante que os dados sejam carregados uma vez por visita à página.

* **5. Foco no Desenvolvimento Rápido (Banco de Dados H2):**
    > [cite_start]Para acelerar o desenvolvimento e focar na lógica da aplicação e na nova stack de UI, optei por usar o banco de dados em memória **H2**[cite: 7]. Isso eliminou a necessidade de gerenciar um container Docker para o banco de dados nesta fase de prática, permitindo que a aplicação suba de forma autossuficiente com um único comando.

---

## 🏛️ Arquitetura e Fluxo de Dados

O fluxo de dados da aplicação principal é o seguinte:

```mermaid
graph TD
    subgraph "Aplicações Externas (Mock)"
        API_A[🏦 Banco A API]
        API_B[🏦 Banco B API]
    end

    subgraph "Aplicação Principal (Meu Gestor Financeiro)"
        UI[💻 Interface Web <br> (dashboard.xhtml)]
        BEAN[🧠 Managed Bean <br> (DashboardBean)]
        SERVICE[🔧 Lógica de Agregação <br> (DashboardService)]
        FEIGN[📡 Clientes OpenFeign]
    end
    
    UI -- Interage com --> BEAN
    BEAN -- Solicita dados --> SERVICE
    SERVICE -- Chama --> FEIGN
    FEIGN -- Busca Saldo/Extrato --> API_A
    FEIGN -- Busca Saldo/Extrato --> API_B
```

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
git clone [https://github.com/SEU-USUARIO/meu-gestor-financeiro-app.git](https://github.com/SEU-USUARIO/meu-gestor-financeiro-app.git)
git clone [https://github.com/SEU-USUARIO/banco-a-api.git](https://github.com/SEU-USUARIO/banco-a-api.git)
git clone [https://github.com/SEU-USUARIO/banco-b-api.git](https://github.com/SEU-USUARIO/banco-b-api.git)
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