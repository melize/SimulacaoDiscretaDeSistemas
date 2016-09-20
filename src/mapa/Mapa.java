package mapa;

import java.util.LinkedList;
import java.util.List;

public class Mapa {
    private int linhas;
    private int colunas;
    private Ponto origem;
    private Ponto destino;
    private List<Ponto> barreiras;

    public Mapa() {
        this.barreiras = new LinkedList<>();
    }
    
    public Mapa(int linhas, int colunas, Ponto origem, Ponto destino) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.origem = origem;
        this.destino = destino;
        this.barreiras = new LinkedList<>();
    }

    public Mapa(int linhas, int colunas, Ponto origem, Ponto destino, List<Ponto> barreiras) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.origem = origem;
        this.destino = destino;
        this.barreiras = barreiras;
    }
    
    /*
        0 - Livre
        1 - Obstáculo
        2 - Origem
        3 - Destino
        4 - Visitado
    */
    public int[][] gerarMatrizMapa() {
        int[][] matriz = new int[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matriz[i][j] = 0;
            }
        }
        matriz[origem.getX()][origem.getY()] = 2;
        matriz[destino.getX()][destino.getY()] = 3;
        for (Ponto muro : barreiras) {
            matriz[muro.getX()][muro.getY()] = 1;
        }
        return matriz;
    }
    
    public void addMuro(Ponto muro) {
        barreiras.add(muro);
    }
    
    public void addMuro(int x, int y) {
        Ponto muro = new Ponto(x, y);
        barreiras.add(muro);
    }
    
    public Ponto removeMuro(int i) {
        return barreiras.remove(i);
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

    public void setColunas(int colunas) {
        this.colunas = colunas;
    }

    public void setOrigem(Ponto origem) {
        this.origem = origem;
    }

    public void setDestino(Ponto destino) {
        this.destino = destino;
    }
    
    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Ponto getOrigem() {
        return origem;
    }

    public Ponto getDestino() {
        return destino;
    }

    public List<Ponto> getBarreiras() {
        return barreiras;
    }
}