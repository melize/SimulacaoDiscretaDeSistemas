package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import mapa.Ponto;

public class MapaGrafico extends JPanel {
    private int linhas;
    private int colunas;
    private Ponto origem;
    private Ponto destino;
    private List<Ponto> barreiras;

    public MapaGrafico(int linhas, int colunas, Ponto origem, Ponto destino, List<Ponto> barreiras) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.origem = origem;
        this.destino = destino;
        this.barreiras = barreiras;
        desenharMapa();
    }
    
    private void desenharMapa() {
        this.setLayout(new GridLayout(linhas, colunas));

        // DESENHAR MAPA
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                JLabel ponto = new JLabel();
                ponto.setBorder(BorderFactory.createLineBorder(Color.black));
                ponto.setSize(15, 20);
                ponto.setOpaque(true);
                ponto.setBackground(Color.white);
                this.add(ponto);
            }
        }
        
        // MARCAR ORIGEM
        this.getComponent(origem.getY()+(colunas*origem.getX())).setBackground(Color.green);
        
        // MARCAR DESTINO
        this.getComponent(destino.getY()+(colunas*destino.getX())).setBackground(Color.blue);
        
        // MARCAR BARREIRAS
        for (Ponto barreira : barreiras) {
            this.getComponent(barreira.getY()+(colunas*barreira.getX())).setBackground(Color.black);
        }
    }
}
