# BibliotecaPostgre

Sistema de gerenciamento de biblioteca desenvolvido em **Java**, utilizando **PostgreSQL** para persistência dos dados e **JDBC** para conexão com o banco de dados.


## Estrutura do projeto

```text
BibliotecaPostgre/
├── src/
├── lib/
├── sql/
├── backup/
└── README.md
```

## Banco de dados

O sistema utiliza um banco PostgreSQL chamado:

```text
biblioteca
```

O projeto disponibiliza o backup do banco em:

```text
backup/biblioteca.backup
```

Para restaurar o banco, crie o banco `biblioteca` e utilize o backup:

```bash
pg_restore -U postgres -d biblioteca backup/biblioteca.backup
```
tambem é possivel apagar e inserir as informaçães atraves do comandos:

```bash
   psql -U postgres -d biblioteca -f sql/01_schema.sql
   psql -U postgres -d biblioteca -f sql/02_dados_iniciais.sql
```

## Configuração da aplicação

A conexão com o banco utiliza os seguintes parâmetros:

```text
Host: localhost
Porta: 5432
Banco: biblioteca
Usuário: postgres
Senha: 123456
```

A senha deve corresponder à configurada para o usuário `postgres` no ambiente em que a aplicação será executada.

## Compilação

Com o terminal aberto na pasta raiz do projeto, execute:

### Windows

```cmd
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -d bin -cp lib\postgresql.jar @sources.txt
```

### Linux/macOS

```bash
find src -name "*.java" > sources.txt
javac -encoding UTF-8 -d bin -cp lib/postgresql.jar @sources.txt
```

## Execução

### Windows

```cmd
java -Dfile.encoding=UTF-8 -cp bin;lib\postgresql.jar Main
```

### Linux/macOS

```bash
java -Dfile.encoding=UTF-8 -cp "bin:lib/postgresql.jar" Main
```

## Funcionalidades

O sistema implementa operações de gerenciamento e consulta relacionadas às entidades da biblioteca, incluindo:

* Autores
* Editoras
* Gêneros
* Usuários
* Funcionários
* Livros
* Exemplares
* Empréstimos

Também são disponibilizados relatórios e operações relacionadas ao processo de empréstimos.




Projeto desenvolvido para a disciplina, como parte da implementação de um sistema de gerenciamento de biblioteca.
