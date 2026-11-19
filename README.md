# DojoSys
Sistema de Gestão para Dojô de Jiu-Jitsu - Projeto Final LP2

## Estrutura de pacotes

| Pacote | Responsabilidade |
| --- | --- |
| `dojosys.app` | Ponto de entrada e menu de console |
| `dojosys.modelo` | Praticantes e contrato de graduação |
| `dojosys.servico` | Gerenciamento dos membros do dojô |
| `dojosys.persistencia` | Conexão JDBC e teste de conexão |

## Executar o menu

Com um JDK 17 ou superior, rode na raiz do projeto:

```sh
javac -encoding UTF-8 -d out -sourcepath src src/dojosys/app/Main.java src/dojosys/persistencia/ConexaoDBTest.java
java -cp out dojosys.app.Main
```

O cadastro e a listagem do menu operam em memória. O teste de conexão pode ser executado separadamente com o driver PostgreSQL no classpath e um banco configurado pelo `schema.sql`.
