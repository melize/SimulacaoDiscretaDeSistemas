package mapa;

import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * Use este código para criar seu grafo a partir do xml gerado na aba anterior
 * Passos: 
 *  1 - Salve o xml em um arquivo 
 *  2 - Exporte este código para seu projeto 
 *  3 - Altere o método mapaFromXML incluindo uma referência à 
 *      classe do seu grafo, conforme exemplificado no código.
 *      Apenas as linhas comentadas precisam ser alteradas.
 *  4 - Execute este código conforme demonstrado no método main, logo abaixo
 *  5 - Uma janela de seleção de arquivo abrirá, selecione o arquivo xml salvo no passo 1
 */

public class LeitorXMLAStar {
    
    public static Object mapaFromXML() {
        //Referencie a classe do seu grafo aqui, exemplo:
        Mapa mapa = new Mapa();
        int linhas;
        int colunas;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Arquivo XML", "xml");
        fileChooser.setFileFilter(extensionFilter);
        fileChooser.setDialogTitle("Selecione o Mapa");
        File file = null;
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            try {
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                file = removeComment(file);
                Document doc = dBuilder.parse(file);
                file.delete();
                doc.getDocumentElement().normalize();
             
                Node node;
                Element element;
                NodeList nodes = doc.getElementsByTagName("LINHAS");
                element = (Element)nodes.item(0);
                mapa.setLinhas(Integer.parseInt(element.getTextContent()));
                nodes = doc.getElementsByTagName("COLUNAS");
                element = (Element)nodes.item(0);
                mapa.setColunas(Integer.parseInt(element.getTextContent()));
                nodes = doc.getElementsByTagName("INICIAL");
                element = (Element)nodes.item(0);
                mapa.setOrigem(getPonto(element.getTextContent()));
                nodes = doc.getElementsByTagName("FINAL");
                element = (Element)nodes.item(0);
                mapa.setDestino(getPonto(element.getTextContent()));
                
                nodes = doc.getElementsByTagName("MURO");
                for (int i = 0; i < nodes.getLength(); i++) {
                    node = nodes.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        element = (Element) node;
                        mapa.addMuro(getPonto(element.getTextContent()));
                    }
                }
                return mapa;
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
        return null;
    }
    
    private static Ponto getPonto(String ponto) {
        String montaCoordenada = "";
        int j = 0;
        for (int i = 0; ponto.charAt(i) != ','; i++) {
            montaCoordenada = montaCoordenada.concat(String.valueOf(ponto.charAt(i)));
            j = i+1;
        }
        int x = Integer.parseInt(montaCoordenada);
 
        montaCoordenada = "";
        for (int i = j+1; i < ponto.length(); i++) {
            montaCoordenada = montaCoordenada.concat(String.valueOf(ponto.charAt(i)));
        }
        int y = Integer.parseInt(montaCoordenada);   
        
        return new Ponto(x, y);
    }
    
    private static File removeComment (File file) {
        File grafoSemComments = new File("grafoSemComments.xml");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedWriter bw = new BufferedWriter(new FileWriter(grafoSemComments));
            String linha;
            while((linha = br.readLine()) != null) {
                if (linha.startsWith("\t\t<!--")) {
                    linha = "";
                }
                
                bw.write(linha);
                bw.newLine();
            }
            bw.close();
            br.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return grafoSemComments;
    }
}
