# BlackFridayAmazon

Projeto desenvolvido em Java para a disciplina Estruturas de Dados Básicas II. O objetivo é implementar e comparar duas estruturas de árvores binárias de busca balanceadas: Árvore AVL e Árvore Rubro-Negra.

As estruturas são utilizadas como protótipo para o gerenciamento de IDs de pedidos durante a Black Friday, permitindo inserir, remover, buscar e visualizar os pedidos armazenados.

## Funcionalidades

- Escolha entre Árvore AVL e Árvore Rubro-Negra pelo menu principal.
- Inserção de IDs de pedidos.
- Remoção de IDs existentes.
- Busca de IDs.
- Exibição dos IDs em ordem crescente.
- Visualização da estrutura da árvore.
- Exibição das cores dos nós na árvore rubro-negra.
- Consulta da altura da árvore e da quantidade de rotações registradas.
- Validação das propriedades de balanceamento.
- Tratamento de entradas inválidas, IDs duplicados e IDs não encontrados.

## Tecnologias utilizadas

- Java — linguagem de programação.
- Visual Studio Code — editor de código.
- Git e GitHub — controle de versão e hospedagem do código-fonte.

## Pré-requisitos

Antes de executar o projeto, instale:

1. JDK (Java Development Kit): necessário para compilar e executar os arquivos Java. Recomenda-se o JDK 17 ou superior.
   - Download: https://adoptium.net/

2. Visual Studio Code:
   - Download: https://code.visualstudio.com/

3. Extension Pack for Java: extensão recomendada para desenvolver Java no VS Code.
   - Abra a aba de extensões do VS Code (`Ctrl + Shift + X`).
   - Pesquise por `Extension Pack for Java`.
   - Instale a extensão publicada pela Microsoft.

### Verificar a instalação do Java

Abra um terminal e execute:

```bash
java -version
javac -version
```

Os dois comandos devem apresentar a versão instalada. Se algum deles não for reconhecido, verifique se o JDK está instalado e configurado no `PATH`.

## Como executar no Visual Studio Code

### 1. Obter o projeto

Clone o repositório:

```bash
git clone https://github.com/syndekemylle/BlackFridayAmazon
```

Se preferir, baixe o código pelo GitHub e extraia a pasta do projeto.

### 2. Abrir a pasta no VS Code

1. Abra o Visual Studio Code.
2. Clique em File → Open Folder (Arquivo → Abrir Pasta).
3. Selecione a pasta `BlackFridayAmazon`.
4. Confira se os três arquivos Java estão presentes.

### 3. Abrir o terminal integrado

No VS Code, selecione Terminal → New Terminal (Terminal → Novo Terminal).

Verifique se o terminal está na pasta que contém os arquivos `ArvoreAVL.java`, `ArvoreRubroNegra.java` e `Main.java`.

### 4. Compilar o projeto

Execute o seguinte comando:

```bash
javac ArvoreAVL.java ArvoreRubroNegra.java Main.java
```

Se não houver erros, o compilador gerará os arquivos `.class` necessários para a execução.

### 5. Executar o programa

No mesmo terminal, execute:

```bash
java Main
```

O menu principal será exibido:

```text
========================================
 BLACK FRIDAY AMAZON
 Arvores Balanceadas
========================================
1 - Utilizar Arvore AVL
2 - Utilizar Arvore Rubro-Negra
0 - Sair

Escolha uma opcao:
```

Digite a opção desejada e pressione `Enter`.

## Como utilizar

Depois de escolher uma árvore, o programa apresenta as operações disponíveis:

| Opção | Operação |
|---|---|
| 1 | Inserir ID de pedido |
| 2 | Remover ID de pedido |
| 3 | Buscar ID de pedido |
| 4 | Exibir IDs em ordem crescente |
| 5 | Visualizar a estrutura da árvore |
| 6 | Exibir altura, rotações e validação |
| 0 | Voltar ao menu principal |

### Exemplo de utilização

Selecione uma das árvores e insira os seguintes IDs:

```text
30
20
10
25
```

Em seguida:

1. Escolha a opção `4` para visualizar os IDs em ordem crescente.
2. Escolha a opção `5` para visualizar a estrutura da árvore.
3. Escolha a opção `6` para consultar a altura, as rotações e o resultado da validação.
4. Tente inserir novamente o ID `20` para verificar o tratamento de duplicatas.
5. Tente buscar ou remover o ID `999` para verificar o tratamento de valores inexistentes.

Na árvore rubro-negra, os nós são identificados pelas letras `[V]` (vermelho) e `[P]` (preto).

## Testes recomendados

Para avaliar o comportamento das estruturas, execute os seguintes cenários em ambas as árvores:

- Inserção de IDs em ordem crescente.
- Inserção de IDs em ordem decrescente.
- Inserção de IDs em ordem aleatória.
- Busca de IDs existentes e inexistentes.
- Tentativa de inserir IDs duplicados.
- Remoção de nós folha, nós com um filho e nós com dois filhos.
- Tentativa de remover IDs inexistentes.
- Testes com árvore vazia e árvore contendo apenas um nó.
- Verificação do balanceamento após inserções e remoções.

Utilize as mesmas sequências de operações nas duas implementações para permitir uma comparação justa.

## Comparação entre as árvores

A implementação permite observar características diferentes das duas estruturas:

- Árvore AVL: mantém um balanceamento mais rigoroso por meio das alturas e dos fatores de balanceamento dos nós.
- Árvore Rubro-Negra: utiliza regras de cores, rotações e ajustes após inserções e remoções para manter suas propriedades.

Ambas permitem operações de busca, inserção e remoção em tempo \(O(\log n)\) no pior caso, desde que suas propriedades de balanceamento sejam preservadas.

A altura e a quantidade de rotações registradas podem ser utilizadas como parte da análise experimental. Para uma comparação rigorosa, registre os resultados das mesmas sequências de operações em ambas as estruturas.

## Decisões de implementação

- IDs duplicados são rejeitados; o valor existente não é inserido novamente.
- A busca por um ID inexistente informa que o elemento não foi encontrado.
- A remoção de um ID inexistente não modifica a árvore e informa que o elemento não foi encontrado.
- A impressão em ordem utiliza o percurso em ordem simétrica, produzindo IDs em ordem crescente.
- A árvore rubro-negra utiliza um nó sentinela preto (`NIL`) para representar folhas vazias.
- As árvores são criadas novamente quando uma opção é selecionada no menu principal. Portanto, os dados não são persistidos ao trocar de implementação ou sair da árvore.

## Possíveis problemas

### O comando `javac` não é reconhecido

Verifique se o JDK está instalado e se o diretório `bin` do JDK foi adicionado ao `PATH`. Reinicie o terminal do VS Code após alterar as variáveis de ambiente.

### O programa não encontra uma classe

Confirme que os três arquivos estão na mesma pasta e que os nomes das classes correspondem aos nomes dos arquivos. Execute a compilação na pasta correta.

### O programa informa que a validação falhou

Revise a sequência de inserções e remoções e investigue a implementação de balanceamento correspondente. A opção de validação deve retornar `SIM` após cada operação válida.

## Autor

- Nome: Synde Kemylle Ferreira Filgueira de Souza
- Disciplina: Estruturas de Dados Básicas II
- Instituição: UFRN
- Repositório: https://github.com/syndekemylle/BlackFridayAmazon

## Referências

- Documentação oficial do Java: https://docs.oracle.com/en/java/
- Visual Studio Code — Java: https://code.visualstudio.com/docs/languages/java
- Adoptium — Eclipse Temurin JDK: https://adoptium.net/
- Este README foi criado com o auxílio da Inteligência Artificial CHATGPT.

---
