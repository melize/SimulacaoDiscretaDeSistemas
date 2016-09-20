package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Linha extends JPanel {

    private int posXInicial;
    private int posYInicial;
     private int posXFinal;
    private int posYFinal;

    public Linha(int posXInicial, int posYInicial, int posXFinal, int posYFinal) {
        this.posXInicial = posXInicial;
        this.posYInicial = posYInicial;
        this.posXFinal = posXFinal;
        this.posYFinal = posYFinal;
    }
    
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawLine(posXInicial, posYInicial, posXFinal, posYFinal);
    }

    @Override
    public void paintComponent(Graphics g) {

        doDrawing(g);
    }
}
