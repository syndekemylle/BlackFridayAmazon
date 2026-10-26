public class ArvoreRubroNegra {

    private static final boolean VERMELHO = true;
    private static final boolean PRETO = false;

    private class No {
        int valor;
        boolean cor;
        No esquerda;
        No direita;
        No pai;

        No(int valor, boolean cor) {
            this.valor = valor;
            this.cor = cor;
        }
    }

    private final No NIL;
    private No raiz;
    private long rotacoes;

    public ArvoreRubroNegra() {
        NIL = new No(0, PRETO);
        NIL.esquerda = NIL;
        NIL.direita = NIL;
        NIL.pai = NIL;
        raiz = NIL;
    }

    public long getRotacoes() {
        return rotacoes;
    }

    public boolean estaVazia() {
        return raiz == NIL;
    }

    // Busca um valor na arvore.
    public boolean buscar(int valor) {
        return buscarNo(valor) != NIL;
    }

    private No buscarNo(int valor) {
        No atual = raiz;

        while (atual != NIL) {
            if (valor == atual.valor) {
                return atual;
            }

            if (valor < atual.valor) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }

        return NIL;
    }

    // Insere um valor. Retorna false se ele ja existir.
    public boolean inserir(int valor) {
        if (buscar(valor)) {
            return false;
        }

        No novo = new No(valor, VERMELHO);
        novo.esquerda = NIL;
        novo.direita = NIL;

        No pai = NIL;
        No atual = raiz;

        while (atual != NIL) {
            pai = atual;

            if (novo.valor < atual.valor) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }

        novo.pai = pai;

        if (pai == NIL) {
            raiz = novo;
        } else if (novo.valor < pai.valor) {
            pai.esquerda = novo;
        } else {
            pai.direita = novo;
        }

        corrigirInsercao(novo);
        return true;
    }

    // Restaura as propriedades rubro-negras apos a insercao.
    private void corrigirInsercao(No z) {
        while (z.pai.cor == VERMELHO) {
            if (z.pai == z.pai.pai.esquerda) {
                No tio = z.pai.pai.direita;

                if (tio.cor == VERMELHO) {
                    // Caso 1: pai e tio vermelhos.
                    z.pai.cor = PRETO;
                    tio.cor = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.direita) {
                        // Caso 2: transforma em caso de linha.
                        z = z.pai;
                        rotacaoEsquerda(z);
                    }

                    // Caso 3: pai preto e avo vermelho.
                    z.pai.cor = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    rotacaoDireita(z.pai.pai);
                }
            } else {
                // Casos simetricos.
                No tio = z.pai.pai.esquerda;

                if (tio.cor == VERMELHO) {
                    z.pai.cor = PRETO;
                    tio.cor = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.esquerda) {
                        z = z.pai;
                        rotacaoDireita(z);
                    }

                    z.pai.cor = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    rotacaoEsquerda(z.pai.pai);
                }
            }
        }

        raiz.cor = PRETO;
        raiz.pai = NIL;
    }

    private void rotacaoEsquerda(No x) {
        No y = x.direita;
        x.direita = y.esquerda;

        if (y.esquerda != NIL) {
            y.esquerda.pai = x;
        }

        y.pai = x.pai;

        if (x.pai == NIL) {
            raiz = y;
        } else if (x == x.pai.esquerda) {
            x.pai.esquerda = y;
        } else {
            x.pai.direita = y;
        }

        y.esquerda = x;
        x.pai = y;
        rotacoes++;
    }

    private void rotacaoDireita(No y) {
        No x = y.esquerda;
        y.esquerda = x.direita;

        if (x.direita != NIL) {
            x.direita.pai = y;
        }

        x.pai = y.pai;

        if (y.pai == NIL) {
            raiz = x;
        } else if (y == y.pai.direita) {
            y.pai.direita = x;
        } else {
            y.pai.esquerda = x;
        }

        x.direita = y;
        y.pai = x;
        rotacoes++;
    }

    // Remove um valor. Retorna false se ele nao existir.
    public boolean remover(int valor) {
        No z = buscarNo(valor);

        if (z == NIL) {
            return false;
        }

        No y = z;
        boolean corOriginal = y.cor;
        No x;

        if (z.esquerda == NIL) {
            x = z.direita;
            transplantar(z, z.direita);
        } else if (z.direita == NIL) {
            x = z.esquerda;
            transplantar(z, z.esquerda);
        } else {
            // Usa o sucessor em ordem.
            y = minimo(z.direita);
            corOriginal = y.cor;
            x = y.direita;

            if (y.pai == z) {
                x.pai = y;
            } else {
                transplantar(y, y.direita);
                y.direita = z.direita;
                y.direita.pai = y;
            }

            transplantar(z, y);
            y.esquerda = z.esquerda;
            y.esquerda.pai = y;
            y.cor = z.cor;
        }

        if (corOriginal == PRETO) {
            corrigirRemocao(x);
        }

        if (raiz != NIL) {
            raiz.cor = PRETO;
            raiz.pai = NIL;
        }

        return true;
    }

    private void transplantar(No antigo, No novo) {
        if (antigo.pai == NIL) {
            raiz = novo;
        } else if (antigo == antigo.pai.esquerda) {
            antigo.pai.esquerda = novo;
        } else {
            antigo.pai.direita = novo;
        }

        // Inclusive para NIL: necessario durante a correcao.
        novo.pai = antigo.pai;
    }

    private No minimo(No no) {
        while (no.esquerda != NIL) {
            no = no.esquerda;
        }

        return no;
    }

    // Restaura as propriedades rubro-negras apos a remocao.
    private void corrigirRemocao(No x) {
        while (x != raiz && x.cor == PRETO) {
            if (x == x.pai.esquerda) {
                No irmao = x.pai.direita;

                // Caso 1: irmao vermelho.
                if (irmao.cor == VERMELHO) {
                    irmao.cor = PRETO;
                    x.pai.cor = VERMELHO;
                    rotacaoEsquerda(x.pai);
                    irmao = x.pai.direita;
                }

                // Caso 2: irmao e filhos pretos.
                if (irmao.esquerda.cor == PRETO
                        && irmao.direita.cor == PRETO) {
                    if (irmao != NIL) {
                        irmao.cor = VERMELHO;
                    }
                    x = x.pai;
                } else {
                    // Caso 3: filho direito preto.
                    if (irmao.direita.cor == PRETO) {
                        irmao.esquerda.cor = PRETO;

                        if (irmao != NIL) {
                            irmao.cor = VERMELHO;
                        }

                        rotacaoDireita(irmao);
                        irmao = x.pai.direita;
                    }

                    // Caso 4: empresta o preto do pai.
                    irmao.cor = x.pai.cor;
                    x.pai.cor = PRETO;
                    irmao.direita.cor = PRETO;
                    rotacaoEsquerda(x.pai);
                    x = raiz;
                }
            } else {
                // Casos simetricos.
                No irmao = x.pai.esquerda;

                if (irmao.cor == VERMELHO) {
                    irmao.cor = PRETO;
                    x.pai.cor = VERMELHO;
                    rotacaoDireita(x.pai);
                    irmao = x.pai.esquerda;
                }

                if (irmao.direita.cor == PRETO
                        && irmao.esquerda.cor == PRETO) {
                    if (irmao != NIL) {
                        irmao.cor = VERMELHO;
                    }
                    x = x.pai;
                } else {
                    if (irmao.esquerda.cor == PRETO) {
                        irmao.direita.cor = PRETO;

                        if (irmao != NIL) {
                            irmao.cor = VERMELHO;
                        }

                        rotacaoEsquerda(irmao);
                        irmao = x.pai.esquerda;
                    }

                    irmao.cor = x.pai.cor;
                    x.pai.cor = PRETO;
                    irmao.esquerda.cor = PRETO;
                    rotacaoDireita(x.pai);
                    x = raiz;
                }
            }
        }

        x.cor = PRETO;
    }

    // Imprime os valores em ordem crescente.
    public void imprimirEmOrdem() {
        if (raiz == NIL) {
            System.out.println("Arvore vazia.");
            return;
        }

        imprimirEmOrdem(raiz);
        System.out.println();
    }

    private void imprimirEmOrdem(No no) {
        if (no == NIL) {
            return;
        }

        imprimirEmOrdem(no.esquerda);
        System.out.print(no.valor + "("
                + (no.cor == VERMELHO ? "V" : "P") + ") ");
        imprimirEmOrdem(no.direita);
    }

    // Imprime a estrutura de forma lateral.
    public void imprimirEstrutura() {
        if (raiz == NIL) {
            System.out.println("Arvore vazia.");
            return;
        }

        imprimirEstrutura(raiz, "", true);
    }

    private void imprimirEstrutura(
            No no, String prefixo, boolean ultimo) {

        if (no == NIL) {
            return;
        }

        System.out.println(prefixo
                + (ultimo ? "`-- " : "|-- ")
                + no.valor
                + (no.cor == VERMELHO ? " [V]" : " [P]"));

        String novoPrefixo = prefixo + (ultimo ? "    " : "│   ");

        if (no.esquerda != NIL || no.direita != NIL) {
            if (no.esquerda != NIL) {
                imprimirEstrutura(no.esquerda, novoPrefixo,
                        no.direita == NIL);
            }

            if (no.direita != NIL) {
                imprimirEstrutura(no.direita, novoPrefixo, true);
            }
        }
    }

    // Altura em numero de nos no maior caminho.
    public int altura() {
        return altura(raiz);
    }

    private int altura(No no) {
        if (no == NIL) {
            return 0;
        }

        return 1 + Math.max(
                altura(no.esquerda),
                altura(no.direita));
    }

    // Valida ordenacao, cores e altura negra.
    public boolean validar() {
        if (NIL.cor != PRETO) {
            return false;
        }

        if (raiz == NIL) {
            return true;
        }

        if (raiz.cor != PRETO || raiz.pai != NIL) {
            return false;
        }

        return validar(raiz, Long.MIN_VALUE, Long.MAX_VALUE) >= 0;
    }

    // Retorna altura negra; -1 indica alguma violacao.
    private int validar(No no, long min, long max) {
        if (no == NIL) {
            return 1;
        }

        if (no.valor <= min || no.valor >= max) {
            return -1;
        }

        if (no.esquerda != NIL && no.esquerda.pai != no) {
            return -1;
        }

        if (no.direita != NIL && no.direita.pai != no) {
            return -1;
        }

        if (no.cor == VERMELHO
                && (no.esquerda.cor == VERMELHO
                || no.direita.cor == VERMELHO)) {
            return -1;
        }

        int alturaEsquerda = validar(no.esquerda, min, no.valor);
        int alturaDireita = validar(no.direita, no.valor, max);

        if (alturaEsquerda < 0
                || alturaDireita < 0
                || alturaEsquerda != alturaDireita) {
            return -1;
        }

        return alturaEsquerda + (no.cor == PRETO ? 1 : 0);
    }
}