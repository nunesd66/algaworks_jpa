# AlgaWorks - Curso Especialista JPA

> Curso com foco no mapeamento e desenvolvimento da persistência de dados com JPA. 
> Repositório de referência.

## :receipt:Sumário

0. [Resumo do Curso](#resumo)
1. [Introdução](#intro)
2. [Iniciando com JPA](#iniciando)
3. [Mapeamento Básico](#basico)

## :book: 0. Resumo do Curso <a id="resumo"></a>

### O que é persistência de dados?

> É armazenar informação pelo período de tempo desejado de forma que ela possa ser recuperada ou apagada.

### O que é JPA?

> Jakarta persistence API é uma API padrão da linguagem Java que descreve uma interface comum para frameworks de persistência de dados. A JPA define um meio de mapeamento objeto-relacional para objetos Java simples e comuns, denominados beans de entidade.

### O que é Mapeamento Objeto-Relacional (ORM)?

> Object-Relational Mapping (ORM) é uma técnica para aproximar(traduzir) o paradigma de desenvolvimento de aplicações orientadas a objetos ao paradigma do banco de dados relacional.

### Anotações de teste

#### @Test

> Na classe de testes, onde estiver a anotação @Test sob o método, indica que o método será testado.

#### @BeforeAll

> Executada antes de qualquer método de teste, na ordem em que foram declarados.

#### @AfterAll

> Executada depois de todos os métodos de teste, na ordem em que foram declarados.

#### @BeforeEach

> Usado para ser executado antes de cada método de testes, na ordem em que foram declarados.

#### @AfterEach

> Usado para ser executado depois de cada método de testes, na ordem em que foram declarados.

### persistence.xml

> Documento usado para configurar as propriedades de conexão com o banco de dados.

```
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence https://jakarta.ee/xml/ns/persistence/persistence_3_1.xsd"
             version="3.1">

    <!-- nome usado para ter uma referência da unidade de persistência --->
    <persistence-unit name="Ecommerce-PU">
        <properties>

            <!-- url do banco de dados --->
            <property name="jakarta.persistence.jdbc.url"
                      value="jdbc:mysql://localhost/algaworks_ecommerce?createDatabaseIfNotExist=true&amp;useTimezone=true&amp;serverTimezone=UTC" />

            <!-- nome do usuário do banco de dados --->
            <property name="jakarta.persistence.jdbc.user" value="user" />

            <!-- senha do usuário no banco de dados --->
            <property name="jakarta.persistence.jdbc.password" value="password" />

            <!-- driver do banco de dados --->
            <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver" />

            <!-- configuração de geração de tabelas do banco, onde o valor indica que sempre que a aplicação subir, antes as tabelas serão limpas --->
            <property name="jakarta.persistence.schema-generation.database.action" value="drop-and-create"/>

            <!-- roda um arquivo de comandos sql --->
            <property name="jakarta.persistence.sql-load-script-source" value="META-INF/banco-de-dados/dados-iniciais.sql"/>

            <!-- dialetos específicos de cada banco, nessa propriedade estamos indicando que vamos usar o dialeto do MySQL --->
            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect" />

            <!-- mostra no console todos os comandos SQL gerados --->
            <property name="hibernate.show_sql" value="true" />

            <!-- formata de forma visualmente agradável os scripts SQL --->
            <property name="hibernate.format_sql" value="true" />
        </properties>
    </persistence-unit>
</persistence>
```

### Anotações Hibernate (Jakarta)

#### @Entity

> Especifica que a classe é uma entidade. O ORM traduz a classe em uma tabela do banco de dados.

#### @Table

> Especifica a tabela primária da entidade anotada. Se nenhuma anotação for especificada para uma entidade class, os valores padrão se aplicam.

#### @Id

> Especifica a chave primária de uma entidade. A coluna mapeada para a chave primária da entidade é assumida para ser a chave primária da tabela primária.

#### @GeneratedValue(strategy = GenerationType.AUTO)

> Estratégia de geração de valores de chaves primárias. Apenas pode ser usado em chaves primárias simples.
> O GenerationType, quando não informado a strategy, é por padrão AUTO. Os valores disponíveis são:
> - AUTO - Indica que o provedor de persistência deve escolher um estratégia apropriada para o banco de dados específico.
> - IDENTITY - Indica que o provedor de persistência deve atribuir chaves primárias para a entidade usando uma coluna de identidade de banco de dados.
> - SEQUENCE - Indica que o provedor de persistência deve atribuir chaves primárias para a entidade usando uma sequência de banco de dados.
> - TABLE - Indica que o provedor de persistência deve atribuir chaves primárias para a entidade usando um subjacente tabela de banco de dados para garantir exclusividade.
> - UUID - Indica que o provedor de persistência deve atribuir chaves primárias para a entidade gerando uma RFC 4122 IDentifier universalmente exclusivo.

#### @SequenceGenerator

> Define um gerador de chave primária que pode ser referenciado por nome quando um elemento gerador é especificado para a anotação @GeneratedValue. Um gerador de sequência pode ser especificado na entidade ou no campo ou propriedade da chave primária. O âmbito de aplicação do nome do gerador é global para a unidade de persistência (em todos os tipos de geradores).

#### @TableGenerator

> Define um gerador de chave primária que pode ser referenciado pelo nome quando um elemento gerador é especificado para a anotação @GeneratedValue. Um gerador de tabela pode ser especificado na classe de entidade ou na chave primária campo ou propriedade. O escopo do nome do gerador é global para a unidade de persistência (em todos os tipos de gerador).

#### @Column

> Especifica a coluna mapeada para uma propriedade ou campo persistente. Se nenhuma anotação for especificada, os valores padrão serão aplicados. A propriedade mais usada é o "name", alterando o nome da coluna referente ao atributo em questão.

#### @Enumerated(EnumType.STRING)

> Especifica que uma propriedade ou campo persistente deve ser persistido como um tipo enumerado. A anotação pode ser usado especificando o tipo da enumeração, quando o valor da coleção de elementos é do tipo básico. Se o tipo enumerado não é especificado ou a anotação não é usado, o valor é assumido como sendo ordinal.

#### @Embedded

> Especifica um campo ou propriedade persistente de uma entidade cujo valor é uma instância de uma classe incorporável. Ou seja, uma extensão da classe, que não terá mapeamento, porém no banco, a tabela incorpora os atributos da classe incorporável na classe pai.

## :clipboard: 1. Introdução <a id="intro"></a>

### Versões utilizadas

- Java JDK 17
- MySQL 8
- Hibernate 6
- JUnit 5
- IntelliJ IDEA 2023.1

## :man_technologist: 2. Iniciando com JPA <a id="iniciando"></a>

### Dependências(Maven 3.8.1) adicionadas

- Lombok 1.18.30 - biblioteca Java focada em produtividade e redução de código boilerplate
- Hibernate 6.2.2 -  framework ORM(mapeamento objeto-relacional)
- MySQL 8.0.33 - conector do banco de dados
- Junit 5.9.3 - framework com suporte de testes unitários

### Classes adicionadas

- src/main/java/com/nunesd66/ecommerce/model/
  - Cliente (nome)
  - Produto (nome, descricao, preco)

### Arquivos de execução adicionados
  - IniciarUnidadeDePersistencia - testa se a aplicação roda corretamente

### Arquivos de configuração adicionados

- src/main/resouces/META-INF/
  - persistence.xml - arquivo de configuração do banco de dados

- src/main/resouces/META-INF/banco-de-dados/
  - dados-iniciais.sql - registros adicionados para testes

### Arquivos de testes adicionados

- src/test/java/com/nunesd66/ecommerce/
  - EntityManagerTest - classe genérica de testes

- src/test/java/com/nunesd66/ecommerce/iniciandocomjpa/
  - ConsultandoRegistrosTest - primeiro exemplo de testes
  - OperacoesComTransacaoTest - exemplos de testes um pouco mais abrangentes
  - PrimeiroCrudTest - exercício de testes
  
## :world_map: 3. Mapeamento Básico <a id="basico"></a>

### Módelo de domínio

![Mapa do modelo de domínio](./asserts/EJPA-Domain-Model.jpg)

### Classea alteradas
- src/main/java/com/nunesd66/ecommerce/model/
  - Cliente (nome, sexo)

### Classes adicionadas

- src/main/java/com/nunesd66/ecommerce/model/
  - Categoria (nome, categoriaPaiId)
  - EnderecoEntregaPedido (cep, logradouro, numero, complemento, bairro, cidade, estado)
  - Estoque (produtoId, quantidade)
  - ItemPedido (pedidoId, produtoId, precoProduto, quantidade)
  - NotaFiscal (pedidoId, xml, dataEmissao)
  - PagamentoBoleto (pedidoId, status, codigoBarras)
  - PagamentoCartao (pedidoId, status, numero)
  - Pedido (dataPedido, dataConclusao, notaFiscalId, total, status, enderecoEntrega)

### Arquivos de testes adicionados

- src/test/java/com/nunesd66/ecommerce/mapeamentobasico/
  - EstrategiaChavePrimariaTest - testar diferentes tipos de @GeneratedValue
  - MapeamentoObjetoEmbutidoTest - testar objeto @Embedded em Pedido.EnderecoEntregaPedido
  - MapeandoEnumeracoesTest - testar salvamentos de @Enumerated no banco de dados
