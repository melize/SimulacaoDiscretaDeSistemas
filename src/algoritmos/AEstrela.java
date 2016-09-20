package algoritmos;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import mapa.Aberto;
import mapa.Mapa;
import mapa.Ponto;
import view.MapaGrafico;

public class AEstrela extends Thread {

    private Mapa mapa;
    private MapaGrafico mapaGrafico;
    private List<Aberto> abertos;
    private List<Ponto> fechados;
    private int[][] matrizMapa;

    public AEstrela(Mapa mapa, MapaGrafico mapaGrafico) {
        this.mapa = mapa;
        this.mapaGrafico = mapaGrafico;
        this.abertos = new LinkedList<>();
        this.fechados = new LinkedList<>();
        this.matrizMapa = mapa.gerarMatrizMapa();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            buscarDestino(mapa.getOrigem(), mapa.getDestino());
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Deu Ruim!");
        }
    }

    private void buscarDestino(Ponto atual, Ponto destino) throws InterruptedException {
        Thread.sleep(500);
        if (!((atual.getX() == destino.getX()) && (atual.getY() == destino.getY()))) {
            Ponto ponto = proximoPonto(atual, destino);
            if (ponto != null) {
                fechados.add(atual);
                matrizMapa[atual.getX()][atual.getY()] = 4;
                mapaGrafico.getComponent(atual.getY() + (mapa.getColunas() * atual.getX())).setBackground(Color.yellow);
                mapaGrafico.getComponent(atual.getY() + (mapa.getColunas() * atual.getX())).repaint();
                matrizMapa[ponto.getX()][ponto.getY()] = 2;
                mapaGrafico.getComponent(ponto.getY() + (mapa.getColunas() * ponto.getX())).setBackground(Color.green);
                mapaGrafico.getComponent(ponto.getY() + (mapa.getColunas() * ponto.getX())).repaint();
                buscarDestino(ponto, destino);
            } else {
                if (!fechados.isEmpty()) {
                    matrizMapa[atual.getX()][atual.getY()] = 4;
                    mapaGrafico.getComponent(atual.getY() + (mapa.getColunas() * atual.getX())).setBackground(Color.yellow);
                    mapaGrafico.getComponent(atual.getY() + (mapa.getColunas() * atual.getX())).repaint();
                    mapaGrafico.getComponent(fechados.get(fechados.size() - 1).getY() + (mapa.getColunas() * 
                            fechados.get(fechados.size() - 1).getX())).setBackground(Color.green);
                    mapaGrafico.getComponent(fechados.get(fechados.size() - 1).getY() + (mapa.getColunas() * 
                            fechados.get(fechados.size() - 1).getX())).repaint();
                    buscarDestino(fechados.remove(fechados.size() - 1), destino);
                }
            }
        }
    }

    private Ponto proximoPonto(Ponto atual, Ponto destino) {
        abertos.clear();

        int x;
        int y;
        int g;
        Aberto aberto;

        /*
         CALCULANDO POSIÇÕES MARCADAS COM "x"
         [x][x][x]
         [x][ ][x]
         [x][x][x]
         */
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                // SE NÃO FOR A MESMA POSIÇÃO
                if (!((i == 0) && (j == 0))) {
                    x = atual.getX() + i;
                    y = atual.getY() + j;
                    // SE NÃO FOR 1 = OBSTÁCULO, 4 = VISITADO, 2 = ORIGEM
                    if (((x >= 0) && (x < mapa.getLinhas())) && ((y >= 0) && (y < mapa.getColunas()))) {
                        if ((matrizMapa[x][y] != 1) && (matrizMapa[x][y] != 4) && (matrizMapa[x][y] != 2)) {
                            g = ((i == 0) || (j == 0)) ? 10 : 14; // 10 PARA HORIZONTAL E VERTICAL, E 14 PARA DIAGONAL
                            aberto = new Aberto(g, destino, new Ponto(x, y));
                            abertos.add(aberto);
                        }
                    }
                }
            }
        }
        if (abertos.isEmpty()) {
            return null;
        }
        return menosCusto();
    }

    private Ponto menosCusto() {
        int menor = 999999999;
        Ponto menorID = null;
        for (Aberto aberto : abertos) {
            if (aberto.getF() < menor) {
                menor = aberto.getF();
                menorID = aberto.getVizinho();
            }
        }
        return menorID;
    }
}
