# DojôSys | Diagrama de Classes UML (v1)

Responsável: João Gabriel

Esta é a primeira versão do diagrama. Ela mostra as classes que já estão na branch `main`, mais o `Menu` e o `Main`. O GitHub desenha o diagrama automaticamente a partir do bloco abaixo (sintaxe Mermaid).

```mermaid
classDiagram
    direction TB

    class Graduavel {
        <<interface>>
        +promoverFaixa(novaFaixa: String) void
        +adicionarGrau() void
    }

    class Praticante {
        <<abstract>>
        #nome: String
        #faixa: String
        #graus: int
        +Praticante(nome: String, faixa: String)
        +getNome() String
        +getFaixa() String
        +getGraus() int
        +exibirResumo()* void
    }

    class Aluno {
        -mensalidadeEmDia: boolean
        +Aluno(nome: String, faixa: String, mensalidadeEmDia: boolean)
        +promoverFaixa(novaFaixa: String) void
        +adicionarGrau() void
        +exibirResumo() void
    }

    class Instrutor {
        -salario: double
        +Instrutor(nome: String, faixa: String, salario: double)
        +promoverFaixa(novaFaixa: String) void
        +adicionarGrau() void
        +exibirResumo() void
    }

    class DojoManager {
        <<Singleton>>
        -instanciaUnica: DojoManager$
        -membros: List~Praticante~
        -DojoManager()
        +getInstancia()$ DojoManager
        +cadastrarMembro(p: Praticante) void
        +listarTodos() void
    }

    class ConexaoDB {
        -URL: String$
        -USUARIO: String$
        -SENHA: String$
        +conectar()$ Connection
    }

    class Menu {
        -scanner: Scanner
        -dojo: DojoManager
        +iniciar() void
        -cadastrarAluno() void
        -cadastrarInstrutor() void
        -listarMembros() void
    }

    class Main {
        +main(args: String[])$ void
    }

    Graduavel <|.. Praticante : implementa
    Praticante <|-- Aluno : herda
    Praticante <|-- Instrutor : herda
    DojoManager "1" o-- "0..*" Praticante : membros
    Main ..> Menu : cria
    Menu --> DojoManager : usa
    Menu ..> Aluno : cria
    Menu ..> Instrutor : cria
    DojoManager ..> ConexaoDB : usará (persistência)
```

## Conceitos de POO no diagrama

| Conceito | Onde aparece |
|---|---|
| Herança | `Aluno` e `Instrutor` herdam de `Praticante` |
| Classe abstrata | `Praticante` (não pode ser instanciada; `exibirResumo()` é abstrato) |
| Interface | `Graduavel`, implementada por `Praticante` |
| Polimorfismo | `DojoManager.listarTodos()` chama `exibirResumo()` e cada subclasse responde do seu jeito |
| Encapsulamento | atributos `private`/`protected` com acesso por getters |
| Singleton | `DojoManager` (construtor privado + `getInstancia()`) |

## Legenda

- `+` público, `-` privado, `#` protegido
- `$` membro estático, `*` método abstrato
- Seta com triângulo cheio: herança. Seta tracejada com triângulo: implementação de interface
- Losango vazio: agregação (o dojô tem praticantes)
- Seta tracejada simples: dependência (uma classe usa ou cria a outra)

## Próximas versões (planejado)

Itens que ainda não existem no código e entram no diagrama quando forem implementados:

- Datas de graduação em `Praticante` (ex.: `dataUltimaPromocao`), para validar o tempo mínimo em cada faixa
- Regras de promoção (ordem das faixas, máximo de 4 graus, tempo mínimo) dentro de `promoverFaixa()`
- Busca de praticantes no `DojoManager` (ex.: `buscarPorNome()`), para o menu poder promover e dar grau
- Classe ou registros de mensalidade (valor, vencimento, status de pagamento)
- Classes de acesso ao banco (ex.: `PraticanteDAO`) usando `ConexaoDB`
