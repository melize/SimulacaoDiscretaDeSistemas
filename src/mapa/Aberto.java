package mapa;

public class Aberto {
    private int G;
    private int F;
    private Ponto vizinho;
    private Ponto destino;

    public Aberto(int G, Ponto destino, Ponto vizinho) {
        this.G = G;
        this.destino = destino;
        this.vizinho = vizinho;
        this.F = calcularF();
    }

    private int calcularF() {
        return (G + 10*(Math.abs(vizinho.getX()-destino.getX())+Math.abs(vizinho.getY()-destino.getY())));
    }
    
    public int getG() {
        return G;
    }

    public int getF() {
        return F;
    }

    public Ponto getVizinho() {
        return vizinho;
    }
}
