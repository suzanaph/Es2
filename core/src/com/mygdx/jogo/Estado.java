/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.jogo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manue_000
 */
public class Estado {

    int[][] matriz;
    int qtdPecaJ1;
    int qtdPecaJ2;
    //Valores para representar os tipos de objetos que poderão estar nas casas do tabuleiro
    public final static int PECAJOGADOR1 = 1;
    public final static int DAMAJOGADOR1 = 2;
    public final static int PECAJOGADOR2 = 3;
    public final static int DAMAJOGADOR2 = 4;
    public final static int VAZIA = 0;
    //diz qual o estado que chegou naquele após o movimento de uma peça
    

    public Estado() {
        // tabuleiro inicial
        matriz = new int[][]{
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 3, 0, 3, 0, 3, 0, 3},
            {3, 0, 3, 0, 3, 0, 3, 0},
            {0, 3, 0, 3, 0, 3, 0, 3}
        };
        qtdPecaJ1 = 12;
        qtdPecaJ2 = 12;
    }

       /**Método construtor do estado apartir de uma matriz que representa as posições das peças.
        * e conta q auntidade de peças de cada jogador.
    * @param matriz recebe de posições.  
    */
    public Estado(int[][] matriz) {
        this.matriz = matriz;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] == PECAJOGADOR1 || matriz[i][j] == DAMAJOGADOR1) {
                    qtdPecaJ1++;
                } else if (matriz[i][j] == PECAJOGADOR2 || matriz[i][j] == DAMAJOGADOR2) {
                    qtdPecaJ2++;
                }
            }
        }
    }

   
     /**Método utilizado para descobrir a diferença de peças entrem os dois jogadores (custo usado no mimmax),
    * @return inteiro que respresenta o custo daquele estado.
    */
    public int saldoJ1() {
        return qtdPecaJ1 - qtdPecaJ2;
    }
    /**Método utilizado para descobrir a diferença de peças entrem os dois jogadores (custo usado no mimmax),
    * @return inteiro que respresenta o custo daquele estado.
    */
    public int saldoJ2() {
        return qtdPecaJ2 - qtdPecaJ1;
    }

    /**Método utilizado para criar uma cópia da matriz do estado do tabuleiro para que a copia possa ser editada
    * sem alaterar a original.
    * @return matrizCopia copia da matriz pertencente a este estado.
    */
    public int[][] copia() {
        int[][] matrizCopia = new int[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matrizCopia[i][j] = matriz[i][j];
            }
        }

        return matrizCopia;
    }

    /** Método utilizado para verificar todos os movimentos possiveis 
    * onde cada movimento gera uma tupla casa-estado. 
    * @param lin linha da casa selecionada pelo jogador
    * @param  col coluna da casa selecionada pelo jogador
    * @param capturou limita a verificação de movimentos a apenas capturas
    * @return estados lista contendo todas as tuplas casa-estado geradas pelos movimentos realizados com sucesso.
    */
    public List<MovimentoEstado> movimentosPossiveis(int lin, int col, boolean capturou) {
        List<MovimentoEstado> estados = new ArrayList<MovimentoEstado>();
        MovimentoEstado temp = null;
        MovimentoEstado anterior = new MovimentoEstado(Jogo.getInstance().getTabuleiro().matrizCasas[lin][col],this);

        //indica qual tipo de peça pode se capturar
        int eliminar = 0;
        System.out.println("lin : " + lin + " col:" + col);
        //limitar o jogador 2 a não comer pra trás
        if (matriz[lin][col] == PECAJOGADOR2) {
            System.out.println("jogador2");
            if (!capturou) {

                temp = moverDID(lin, col);
                if (temp != null) {
                    estados.add(temp);
                    temp.anterior = anterior;

                }
                temp = moverDIE(lin, col);
                if (temp != null) {
                    estados.add(temp);
                    temp.anterior = anterior;
                }
            }
            eliminar = PECAJOGADOR1;
            //limitar o jogador 2 a não comer pra trás
        } else if (matriz[lin][col] == PECAJOGADOR1) {
            System.out.println("jogador1");
            if (!capturou) {
                temp = moverDSD(lin, col);
                if (temp != null) {
                    estados.add(temp);
                    temp.anterior = anterior;
                }
                temp = moverDSE(lin, col);
                if (temp != null) {
                    estados.add(temp);
                    temp.anterior = anterior;
                }
            }
            eliminar = PECAJOGADOR2;
        }
        
        // tentar realizar movimento de captura na diagonal superior direita caso não consiga retorna null
        temp = capturaDSD(lin, col, eliminar);
        if (temp != null) {
            estados.add(temp);
            temp.anterior = anterior;
            //caso haja captura de peça, checar todos os movimentos que a peça pode realizar após se deslocar
            estados.addAll(temp.t.movimentosPossiveis(lin + 2, col + 2, true));
        }
        // tentar realizar movimento de captura na diagonal superior esquerda caso não consiga retorna null
        temp = capturaDSE(lin, col, eliminar);
        if (temp != null) {
            
            estados.add(temp);
            temp.anterior = anterior;
            //caso haja captura de peça, checar todos os movimentos que a peça pode realizar após se deslocar
            estados.addAll(temp.t.movimentosPossiveis(lin + 2, col - 2, true));
        }
        // tentar realizar movimento de captura na diagonal inferior direita caso não consiga retorna null
        temp = capturaDID(lin, col, eliminar);
        if (temp != null) {
            estados.add(temp);
            temp.anterior = anterior;
            //caso haja captura de peça, checar todos os movimentos que a peça pode realizar após se deslocar
            estados.addAll(temp.t.movimentosPossiveis(lin - 2, col + 2, true));
        }
        // tentar realizar movimento de captura na diagonal inferior esquerda caso não consiga retorna null
        temp = capturaDIE(lin, col, eliminar);
        if (temp != null) {
   
            estados.add(temp);
            temp.anterior = anterior;
            //caso haja captura de peça, checar todos os movimentos que a peça pode realizar após se deslocar
            estados.addAll(temp.t.movimentosPossiveis(lin - 2, col - 2, true));
        }
        return estados;
    }

    // método captura usando peça normal na diagonal superior direita
    public MovimentoEstado capturaDSD(int lin, int col, int jogador) {
        try {
            int[][] cop = copia();
            //checa se a casa possui uma peça do jogador inimigo para ser capturada e se a casa após essa esta vazia.
            if ((cop[lin + 1][col + 1] == jogador || cop[lin + 1][col + 1] == jogador + 1) && cop[lin + 2][col + 2] == 0) {
                // mover peça da casa que ela estava para a nova
                cop[lin + 2][col + 2] = cop[lin][col];
                cop[lin][col] = 0;
                //deletar peça capturada
                cop[lin + 1][col + 1] = 0;
                Estado novo = new Estado(cop);
                return new MovimentoEstado(Jogo.getInstance().getTabuleiro().matrizCasas[lin + 2][col + 2], novo);
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    // método captura usando peça normal na diagonal superior esquerda
    public MovimentoEstado capturaDSE(int lin, int col, int jogador) {
        try {
            int[][] cop = copia();
            //checa se a casa possui uma peça do jogador inimigo para ser capturada e se a casa após essa esta vazia.
            if ((cop[lin + 1][col - 1] == jogador || cop[lin + 1][col - 1] == jogador + 1) && cop[lin + 2][col - 2] == 0) {
                // mover peça da casa que ela estava para a nova
                cop[lin + 2][col - 2] = cop[lin][col];
                cop[lin][col] = 0;
                //deletar peça capturada
                cop[lin + 1][col - 1] = 0;
                Estado novo = new Estado(cop);
                return new MovimentoEstado(Jogo.getInstance().getTabuleiro().matrizCasas[lin + 2][col - 2], novo);
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    // método captura usando peça normal na diagonal inferior direita
    public MovimentoEstado capturaDID(int lin, int col, int jogador) {
        try {
            int[][] cop = copia();
            //checa se a casa possui uma peça do jogador inimigo para ser capturada e se a casa após essa esta vazia.
            if ((cop[lin - 1][col + 1] == jogador || cop[lin - 1][col + 1] == jogador + 1) && cop[lin - 2][col + 2] == 0) {
                // mover peça da casa que ela estava para a nova
                cop[lin - 2][col + 2] = cop[lin][col];
                cop[lin][col] = 0;
                //deletar peça capturada
                cop[lin - 1][col + 1] = 0;
                Estado novo = new Estado(cop);
               return new MovimentoEstado(Jogo.getInstance().getTabuleiro().matrizCasas[lin - 2][col + 2], novo);
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    // método captura usando peça normal na diagonal inferior esquerda
    public MovimentoEstado capturaDIE(int lin, int col, int jogador) {
        try {
            int[][] cop = copia();
            //checa se a casa possui uma peça do jogador inimigo para ser capturada e se a casa após essa esta vazia.
            if ((cop[lin - 1][col - 1] == jogador || cop[lin - 1][col - 1] == jogador + 1) && cop[lin - 2][col - 2] == 0) {
                // mover peça da casa que ela estava para a nova
                cop[lin - 2][col - 2] = cop[lin][col];
                cop[lin][col] = 0;
                //deletar peça capturada
                cop[lin - 1][col - 1] = 0;
                Estado novo = new Estado(cop);
                return new MovimentoEstado(Jogo.getInstance().getTabuleiro().matrizCasas[lin - 2][col - 2], novo);
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
// movimento simples de peça normal para diagonal superior direita

    public MovimentoEstado moverDSD(int lin, int col) {
        try {
            int[][] cop = copia();
            if (cop[lin + 1][col + 1] == 0) {
                // mover peça da casa que ela estava para a nova 
                cop[lin + 1][col + 1] = cop[lin][col];
                cop[lin][col] = 0;
                Estado novo = new Estado(cop);
                return new MovimentoEstado(Jogo.getInstance().getTabuleiro().matrizCasas[lin + 1][col + 1], novo);

            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
// movimento simples de peça normal para diagonal superior esquerda

    public MovimentoEstado moverDSE(int lin, int col) {
        try {
            int[][] cop = copia();
            if (cop[lin + 1][col - 1] == 0) {
                // mover peça da casa que ela estava para a nova 
                cop[lin + 1][col - 1] = cop[lin][col];
                cop[lin][col] = 0;
                Estado novo = new Estado(cop);
                return new MovimentoEstado(Jogo.getInstance().getTabuleiro().matrizCasas[lin + 1][col - 1], novo);

            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
// movimento simples de peça normal para diagonal inferior direita

    public MovimentoEstado moverDID(int lin, int col) {
        try {
            int[][] cop = copia();
            if (cop[lin - 1][col + 1] == 0) {
                // mover peça da casa que ela estava para a nova 
                cop[lin - 1][col + 1] = cop[lin][col];
                cop[lin][col] = 0;
                Estado novo = new Estado(cop);
                return new MovimentoEstado(Jogo.getInstance().getTabuleiro().matrizCasas[lin - 1][col + 1], novo);
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
// movimento simples de peça normal para diagonal inferior esquerda

    public MovimentoEstado moverDIE(int lin, int col) {
        try {
            int[][] cop = copia();
            if (cop[lin - 1][col - 1] == 0) {
                // mover peça da casa que ela estava para a nova 
                cop[lin - 1][col - 1] = cop[lin][col];
                cop[lin][col] = 0;
                Estado novo = new Estado(cop);
                return new MovimentoEstado(Jogo.getInstance().getTabuleiro().matrizCasas[lin - 1][col - 1], novo);

            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    //retorna o inicial diferente do estado atual do tabuleiro para descobrir qual movimento foi usado para chegar no mesmo 
    public List<MovimentoEstado> ordenar(MovimentoEstado other) {
        MovimentoEstado inicial = other;
        List<MovimentoEstado> movimentos = new ArrayList<MovimentoEstado>();
        movimentos.add(other);
        while (inicial.anterior != null && !inicial.anterior.t.equals(this)) {
            inicial = inicial.anterior;
            movimentos.add(0,inicial);
        }
        return movimentos;
    }
 /** Método utilizado para verificar dos estados encontrado pelos movimentos possives quais possuem o maior valor de custo
  * *para o jogador , de acordo com a regra da lei da maioria para que limite seus movimentos disponiveis aos movimentos que capturam mais peças
    * @param lin linha da casa selecionada pelo jogador
    * @param  col coluna da casa selecionada pelo jogador
    * @param estados lista dos estados gerados pelos movimentos possiveis.
    * @return melhor lista contendo todas as tuplas casa-estado ,cujo estado possui o maior custo.
    */
    public List<MovimentoEstado> melhorCusto(int lin, int col, List<MovimentoEstado> estados) {
        List<MovimentoEstado> melhor = new ArrayList<MovimentoEstado>();
        int custo = Integer.MIN_VALUE;
        if (matriz[lin][col] == PECAJOGADOR1 || matriz[lin][col] == DAMAJOGADOR1) {
            for (MovimentoEstado estado : estados) {
                if (custo < estado.getCustoJ1()) {
                    custo = estado.getCustoJ1();
                }
            }
            for (MovimentoEstado estado : estados) {
                if (custo == estado.getCustoJ1()) {
                    melhor.add(estado);
                }
            }
        } else if (matriz[lin][col] == PECAJOGADOR2 || matriz[lin][col] == DAMAJOGADOR2) {
             for (MovimentoEstado estado : estados) {
                if (custo < estado.getCustoJ2()) {
                    custo = estado.getCustoJ2();
                }
            }
            for (MovimentoEstado estado : estados) {
                if (custo == estado.getCustoJ2()) {
                    melhor.add(estado);
                }
            }
        }

        return melhor;
    }

  

    //por questão de teste exibir o 
    public void exibir() {
        for (int i = 0; i < matriz.length; i++) {
            System.out.println("");
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
        }
        System.out.println("");
    }
}
