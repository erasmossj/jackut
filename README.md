# Jackut

## Arquitetura do Projeto Jackut

### Visão Geral

O projeto Jackut é uma aplicação que simula uma rede social, com gerenciamento de usuários, amizades e envio de recados, desenvolvida em Java. A arquitetura segue padrões de boas práticas de desenvolvimento, separando responsabilidades em camadas distintas e bem definidas.

---

### Estrutura de Pastas e Justificativas

#### `exceptions/`

Separa todas as exceções em uma pasta dedicada.

**Justificativa:**

* Centralizar o tratamento de erros, para facilitar a manutenção e visualização de todos os possíveis erros do sistema.
* Cada exceção é específica para um erro do negócio, como `LoginOuSenhaInvalidoException`, `AmizadeJaAdicionadaException`, `AutoRecadoException`.
* Facilita a reutilização das exceções em múltiplas classes sem repetição de trechos do código.
* Melhora a legibilidade ao centralizar toda a estratégia de tratamento de erros.

**Exemplos de Exceções:**

* Validação/Login: `LoginInvalidoException`, `SenhaInvalidaException`, `LoginOuSenhaInvalidoException`
* Negócio: `AmizadeJaAdicionadaException`, `AmizadePendenteException`, `AutoAmizadeException`, `AutoRecadoException`
* Sistema: `SessaoInvalidaException`, `FalhaAoSalvarException`

---

#### `models/`

Modelagem das classes do negócio de forma separada da lógica do serviço.

**Justificativa:**

* `models/` contém as classes que representam as entidades principais do sistema: `Usuario`, `Amizade`, `Recado`, `Session`.
* Estas classes encapsulam os dados e a estrutura do negócio da rede social.

---

#### `repository/`

Abstrai a persistência de dados em uma camada de repositório.

**Justificativa:**

* `repository/` é responsável pela interação com a persistência de dados para as entidades principais.
* Facilita a escalabilidade e a manutenção do armazenamento, permitindo trocar a forma de persistência sem afetar o resto do código.

---

#### `services/`

Centraliza toda a lógica de operações e regras de negócio em serviços.

**Justificativa:**

* `services/` contém as classes responsáveis por orquestrar as operações do sistema:

  * `UsuarioService`: gerencia criação, busca e validação de usuários
  * `AmizadeService`: gerencia solicitações, aceitação e listagem de amizades
  * `RecadoService`: gerencia envio e leitura de recados entre os usuários
  * `SessionService`: gerencia a sessão de usuários autenticados no sistema

**Por que não usar um único Manager com todos os serviços do sistema?**

* Um único Manager se tornaria uma classe gigante, difícil de manter e entender.
* Torna o código confuso e propenso a erros.
* Dificulta a escalabilidade, pois qualquer mudança em um único serviço poderia afetar todos os outros na classe.

---

#### `utils/`

Centraliza as funções e utilitários reutilizáveis em um único lugar.

**Justificativa:**

* `utils/` contém classes com métodos que seriam repetidos em múltiplos lugares, evitando trechos de código repetidos.
* No projeto atual, a classe `Validador` é responsável por todas as validações de dados e regras genéricas.
* Segue o DRY (Don't Repeat Yourself).
* Melhor manutenção e legibilidade.

---

## Padrões de Projeto Utilizados

### **Facade Pattern**

* Fornece uma interface simplificada para o cliente, como a classe principal ou os testes.
* Reduz a complexidade ao fatorar a coordenação entre múltiplos services.

### **Repository Pattern**

* Abstrai a persistência de dados.
* Facilita a escalabilidade e manutenção do armazenamento.

### **Service Layer Pattern**

* Centraliza a lógica de negócio.
* Promove reutilização, escalabilidade e testabilidade.

### **Guard Clauses**

* Validações e verificações no início dos métodos.
* Evita aninhamento profundo de condicionais, tornando o código mais limpo e legível.

---

## Fluxo de Execução do Projeto

```text
Main.java
  ↓
Facade.java
  ↓
Services (UsuarioService, AmizadeService, RecadoService, SessionService)
  ↓
Models (Usuario, Amizade, Recado, Session)
  ↓
Repository
  ↓
Exceptions (Tratamento de erros)
```

---

## Importante Para a Execução do Projeto

### Use a SDK ou JDK compatível e a Codificação Correta (ISO 8859-1)

* O projeto utiliza a codificação **ISO 8859-1**. Certifique-se de configurar a sua IDE para utilizar esse encoding na compilação e leitura dos arquivos, do contrário caracteres especiais podem ficar corrompidos.
* Por favor, não use a `openjdk20/openjdk25` sugerida automaticamente pelas IDEs e não execute fora da raiz do projeto.

### Possíveis erros se a SDK/JDK correta ou a codificação não for utilizada, ou se o projeto for executado fora da pasta raiz:

* Falha de compilação
* Falha de execução
* Problemas com caracteres acentuados

---

Cada camada tem um propósito claro e bem definido, visando facilitar a legibilidade, manutenção e escalabilidade do sistema para as entregas das próximas Milestones.
