public class ArvoreAVL {

    // No acessivel apenas internamente pela arvore.
    private static class No {
        int valor;
        int altura;
        No esquerda;
        No direita;

        No(int valor) {
            this.valor = valor;
            this.altura = 1;
        }
    }

    private No raiz;
    private long rotacoes;

    // Retorna a altura de um no.
    private int altura(No no) {
        return no == null ? 0 : no.altura;
    }

    // Metodo publico para consultar a altura da arvore.
    public int altura() {
        return altura(raiz);
    }

    // Retorna a quantidade de rotacoes realizadas.
    public long getRotacoes() {
        return rotacoes;
    }

    // Calcula o fator de balanceamento de um no.
    private int fatorBalanceamento(No no) {
        return no == null
                ? 0
                : altura(no.esquerda) - altura(no.direita);
    }

    // Atualiza a altura de um no.
    private void atualizarAltura(No no) {
        no.altura = 1 + Math.max(
                altura(no.esquerda),
                altura(no.direita));
    }

    // Rotacao simples a direita.
    private No rotacaoDireita(No y) {
        No x = y.esquerda;
        No subArvore = x.direita;

        x.direita = y;
        y.esquerda = subArvore;

        atualizarAltura(y);
        atualizarAltura(x);

        rotacoes++;
        return x;
    }

    // Rotacao simples a esquerda.
    private No rotacaoEsquerda(No x) {
        No y = x.direita;
        No subArvore = y.esquerda;

        y.esquerda = x;
        x.direita = subArvore;

        atualizarAltura(x);
        atualizarAltura(y);

        rotacoes++;
        return y;
    }

    // Corrige o balanceamento usando os casos LL, LR, RR e RL.
    private No balancear(No no) {
        if (no == null) {
            return null;
        }

        atualizarAltura(no);

        int fator = fatorBalanceamento(no);

        // Caso LL ou LR: subarvore esquerda pesada.
        if (fator > 1) {
            if (fatorBalanceamento(no.esquerda) < 0) {
                no.esquerda = rotacaoEsquerda(no.esquerda);
            }

            return rotacaoDireita(no);
        }

        // Caso RR ou RL: subarvore direita pesada.
        if (fator < -1) {
            if (fatorBalanceamento(no.direita) > 0) {
                no.direita = rotacaoDireita(no.direita);
            }

            return rotacaoEsquerda(no);
        }

        return no;
    }

    // Insere um valor. Retorna false se for duplicado.
    public boolean inserir(int valor) {
        if (buscar(valor)) {
            return false;
        }

        raiz = inserir(raiz, valor);
        return true;
    }

    private No inserir(No no, int valor) {
        if (no == null) {
            return new No(valor);
        }

        if (valor < no.valor) {
            no.esquerda = inserir(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = inserir(no.direita, valor);
        }

        return balancear(no);
    }

    // Busca um valor na arvore.
    public boolean buscar(int valor) {
        No atual = raiz;

        while (atual != null) {
            if (valor == atual.valor) {
                return true;
            }

            if (valor < atual.valor) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }

        return false;
    }

    // Localiza o menor no de uma subarvore.
    private No menorNo(No no) {
        No atual = no;

        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }

        return atual;
    }

    // Remove um valor. Retorna false se nao existir.
    public boolean remover(int valor) {
        if (!buscar(valor)) {
            return false;
        }

        raiz = remover(raiz, valor);
        return true;
    }

    private No remover(No no, int valor) {
        if (no == null) {
            return null;
        }

        if (valor < no.valor) {
            no.esquerda = remover(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = remover(no.direita, valor);
        } else {
            // No sem filho esquerdo.
            if (no.esquerda == null) {
                return no.direita;
            }

            // No sem filho direito.
            if (no.direita == null) {
                return no.esquerda;
            }

            // No com dois filhos: utiliza o sucessor.
            No sucessor = menorNo(no.direita);
            no.valor = sucessor.valor;
            no.direita = remover(no.direita, sucessor.valor);
        }

        return balancear(no);
    }

    // Exibe os valores em ordem crescente.
    public void imprimirEmOrdem() {
        if (raiz == null) {
            System.out.println("Arvore vazia.");
            return;
        }

        imprimirEmOrdem(raiz);
        System.out.println();
    }

    private void imprimirEmOrdem(No no) {
        if (no == null) {
            return;
        }

        imprimirEmOrdem(no.esquerda);
        System.out.print(no.valor + " ");
        imprimirEmOrdem(no.direita);
    }

    // Exibe a estrutura da arvore.
    public void imprimirEstrutura() {
        if (raiz == null) {
            System.out.println("Arvore vazia.");
            return;
        }

        imprimirEstrutura(raiz, "", true);
    }

    private void imprimirEstrutura(
            No no, String prefixo, boolean ultimo) {

        if (no == null) {
            return;
        }

        System.out.println(prefixo
                + (ultimo ? "`-- " : "|-- ")
                + no.valor
                + " [altura=" + no.altura
                + ", FB=" + fatorBalanceamento(no) + "]");

        String novoPrefixo = prefixo + (ultimo ? "    " : "│   ");

        if (no.esquerda != null || no.direita != null) {
            if (no.esquerda != null) {
                imprimirEstrutura(
                        no.esquerda,
                        novoPrefixo,
                        no.direita == null);
            }

            if (no.direita != null) {
                imprimirEstrutura(
                        no.direita,
                        novoPrefixo,
                        true);
            }
        }
    }

    // Valida a ordenacao, as alturas e o balanceamento AVL.
    public boolean validar() {
        return validar(raiz, Long.MIN_VALUE, Long.MAX_VALUE) >= 0;
    }

    // Retorna a altura calculada; -1 indica violacao.
    private int validar(No no, long min, long max) {
        if (no == null) {
            return 0;
        }

        if (no.valor <= min || no.valor >= max) {
            return -1;
        }

        int alturaEsquerda = validar(
                no.esquerda, min, no.valor);

        int alturaDireita = validar(
                no.direita, no.valor, max);

        if (alturaEsquerda < 0 || alturaDireita < 0) {
            return -1;
        }

        int alturaCalculada = 1 + Math.max(
                alturaEsquerda, alturaDireita);

        if (no.altura != alturaCalculada) {
            return -1;
        }

        if (Math.abs(alturaEsquerda - alturaDireita) > 1) {
            return -1;
        }

        return alturaCalculada;
    }
}