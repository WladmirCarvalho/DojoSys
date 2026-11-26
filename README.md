# DojôSys — Sistema de Gestão para Dojô de Jiu-Jitsu

Sistema de gestão em console desenvolvido em Java com persistência em banco de dados relacional PostgreSQL para o controle de alunos, instrutores, graduações (faixas e graus) e mensalidades de uma academia de Jiu-Jitsu.

Projeto final da disciplina **Linguagem de Programação II (IMD0040)** — Instituto Metrópole Digital (IMD/UFRN).

---

## Equipe
* **Wladmir Luiz de Carvalho**
* **João Gabriel**
* **Davi Summer**

---

## Funcionalidades
1. **Cadastrar aluno** (nome, faixa inicial e situação da mensalidade)
2. **Cadastrar instrutor** (nome, faixa e salário)
3. **Listar membros** (exibição polimórfica de alunos e instrutores cadastrados no banco)
4. **Promover faixa** (atualiza a faixa do praticante e zera automaticamente os graus)
5. **Adicionar grau** (incrementa o grau do praticante respeitando o limite máximo de 4 graus)
6. **Controle de mensalidades** (atualiza o status de adimplência dos alunos)

---

## Tecnologias e Requisitos
* **Linguagem:** Java (JDK 27)
* **Banco de Dados:** PostgreSQL
* **Driver JDBC:** `postgresql-42.7.14.jar` (incluso na raiz do repositório)
* **Documentação:** Markdown e diagramas UML em Mermaid (`docs/`)

---

## Estrutura de Pacotes

```text
DojoSys/
├── docs/
│   ├── diagrama-classes.md     # Diagrama de Classes UML (Mermaid)
│   ├── diagrama-sequencia.md   # Diagrama de Sequência UML (Mermaid)
│   └── escopo-e-fluxos.md      # Escopo e fluxos do sistema
├── src/
│   ├── database/
│   │   └── ConexaoDB.java      # Conexão JDBC com o PostgreSQL
│   ├── model/
│   │   ├── Graduavel.java      # Interface para regras de graduação
│   │   ├── Praticante.java     # Classe abstrata base
│   │   ├── Aluno.java          # Subclasse de Praticante
│   │   └── Instrutor.java      # Subclasse de Praticante
│   ├── service/
│   │   └── DojoManager.java    # Singleton com regras de negócio e CRUD JDBC
│   ├── ui/
│   │   ├── Main.java           # Ponto de entrada da aplicação
│   │   └── Menu.java           # Interface de console e validação de entradas
│   │   ├── DojoSysGUI.java     # Interface gráfica (Swing)
│   └── test/
│       ├── ConexaoDBTest.java  # Teste unitário de conexão com o banco
│       └── DojoSysTest.java    # Testes unitários de POO, Singleton e exceções
├── postgresql-42.7.14.jar      # Driver JDBC do PostgreSQL
├── schema.sql                  # Script de criação da tabela praticantes
└── README.md
```

---

## Arquitetura, POO e Padrão de Projeto

* **Abstração e Herança:** `Praticante` define os atributos e comportamentos comuns (`nome`, `faixa`, `graus`), enquanto `Aluno` e `Instrutor` especializam a entidade com atributos próprios (`mensalidadeEmDia` e `salario`).
* **Polimorfismo:** Implementado por meio da interface `Graduavel` (`promoverFaixa` e `adicionarGrau`) e da sobrescrita do método abstrato `exibirResumo()`.
* **Encapsulamento e Tratamento de Exceções:** Validação de entradas no `Menu` (`NumberFormatException`), regras de domínio nas classes de modelo (`IllegalArgumentException`, `IllegalStateException`) e tratamento de erros de banco de dados (`SQLException`).
* **Padrão de Projeto — Singleton (`DojoManager`):** Utilizado para garantir que exista uma única instância centralizada responsável por coordenar as regras de negócio e a comunicação com o banco de dados durante toda a execução do programa, evitando conflitos de estado e múltiplas instâncias desnecessárias do gerenciador.

---

## Como Configurar e Executar

### 1. Banco de Dados (PostgreSQL)
1. Crie um banco de dados chamado `dojosys` no PostgreSQL.
2. Execute o script `schema.sql` dentro do banco `dojosys` para criar a tabela `praticantes`.
3. *(Opcional)* Caso a senha do seu usuário `postgres` seja diferente de `1234`, defina a variável de ambiente `DOJOSYS_DB_PASS`:

**Windows (PowerShell):**
```powershell
$env:DOJOSYS_DB_PASS="sua_senha"
```

**Linux / macOS:**
```bash
export DOJOSYS_DB_PASS="sua_senha"
```

### 2. Compilação
Na raiz do projeto, execute:
```bash
javac -encoding UTF-8 -d bin src/database/*.java src/model/*.java src/service/*.java src/ui/*.java src/test/*.java
```

### 3. Execução do Sistema

**Windows (PowerShell):**
```powershell
java -cp "bin;postgresql-42.7.14.jar" ui.Main
```

**Linux / macOS:**
```bash
java -cp "bin:postgresql-42.7.14.jar" ui.Main
```


### 3.1 Interface Gráfica (Swing)
Além do menu de console, o sistema tem uma interface gráfica com tabela de membros e botões para cadastrar alunos e instrutores, promover faixa, adicionar e remover graus e atualizar mensalidades.

Depois de compilar (passo 2), execute:

**Windows (PowerShell):**
```powershell
java -cp "bin;postgresql-42.7.14.jar" ui.DojoSysGUI
```

**Linux / macOS:**
```bash
java -cp "bin:postgresql-42.7.14.jar" ui.DojoSysGUI
```

> O PostgreSQL precisa estar rodando com o banco `dojosys` configurado (passo 1), pois a interface carrega os membros direto do banco.


### 4. Execução dos Testes Unitários
Os testes utilizam `assert` nativo do Java (flag `-ea`):

**Windows (PowerShell):**
```powershell
java -ea -cp "bin;postgresql-42.7.14.jar" test.DojoSysTest
java -ea -cp "bin;postgresql-42.7.14.jar" test.ConexaoDBTest
```

**Linux / macOS:**
```bash
java -ea -cp "bin:postgresql-42.7.14.jar" test.DojoSysTest
java -ea -cp "bin:postgresql-42.7.14.jar" test.ConexaoDBTest
```

---

## Documentação UML
* [Diagrama de Classes](docs/diagrama-classes.md)
* [Diagrama de Sequência](docs/diagrama-sequencia.md)
* [Escopo e Fluxos](docs/escopo-e-fluxos.md)
