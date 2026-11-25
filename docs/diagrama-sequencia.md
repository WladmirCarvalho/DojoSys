# DojôSys | Diagrama de Sequência UML

Fluxo de cadastro e persistência de um praticante (Aluno ou Instrutor) no sistema.

```mermaid
sequenceDiagram
    actor Utilizador
    participant Menu as Menu (ui)
    participant Dojo as DojoManager (Singleton)
    participant DB as ConexaoDB (database)
    participant PG as PostgreSQL

    Utilizador->>Menu: Escolhe opção 1 (Cadastrar aluno)
    Menu->>Utilizador: Solicita nome, faixa e mensalidade
    Utilizador-->>Menu: Informa os dados validados
    Menu->>Dojo: getInstancia()
    Menu->>Dojo: cadastrarMembro(Aluno)
    Dojo->>DB: conectar()
    DB->>PG: DriverManager.getConnection()
    PG-->>DB: Connection
    DB-->>Dojo: Connection
    Dojo->>PG: INSERT INTO praticantes (...)
    PG-->>Dojo: Registo inserido com sucesso
    Dojo-->>Menu: Retorno sem erros
    Menu-->>Utilizador: "Aluno cadastrado com sucesso!"
    ```