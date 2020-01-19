package trainningSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import prism.Prism;

public class TrainningSet {

    private  AttribList attrList = new AttribList();
    private  AttribList AttribListDados = new AttribList();
    private  Attribute attr;
    private  Value value;

    /**
     * Create a constructor for trainningSet, in this case when we instantiate
     * the class, we already have the array in the object
     */
    public TrainningSet() {
        //this.trainningSet = TrainningSet.lerArquivos();
    }

    /**
     * This method creates a training set identical to this and returns it
     *
     * @return
     */
    public TrainningSet createClone() {
        TrainningSet myClone = new TrainningSet();
        return myClone;
    }

    public  AttribList pegarDados(String arq) throws IOException {

        String local = "src/arquivos/" + arq + ".txt";
        String palavra = "";
        String atributoListAux = "";
        String[] picotada = null;
        String atributos[] = null;

        File arquivo = new File(local);
        FileReader fileReader = new FileReader(arquivo);
        BufferedReader reader = new BufferedReader(fileReader);

        int k = 0;
        while (reader.ready()) {

            palavra = reader.readLine();
            if (palavra.contains("@data") && !palavra.contains("Class")) {
                picotada = palavra.split(" ");
                attr = new Attribute(picotada[1]);
                //System.out.println(picotada[1]);
                for (int i = 2; i < picotada.length; i++) {
                    //System.out.println(picotada[i]);
                    atributoListAux = picotada[i];
                    //System.out.println(aux);
                    atributoListAux = removeParenteses(atributoListAux);
                    //System.out.println(k);
                    //System.out.println(atributoListAux);
                    atributos = atributoListAux.split(",");
                    for (int j = 0; j < atributos.length; j++) {
                        //System.out.println(atributos[j]);
                        value = new Value(atributos[j]);
                        attr.values.add(value);
                    }
                }
                if (!AttribListDados.attributes.contains(attr)) {
                    AttribListDados.attributes.add(attr);
                }

            } else if (palavra.contains("@data") && palavra.contains("Class")) {

                picotada = palavra.split(" ");
                AttribListDados.classAttribute = new Attribute(picotada[1]);
                //System.out.println(picotada[1]);
                for (int i = 2; i < picotada.length; i++) {
                    //System.out.println(picotada[i]);
                    atributoListAux = picotada[i];
                    //System.out.println(aux);
                    atributoListAux = removeParenteses(atributoListAux);
                    //System.out.println(k);
                    //System.out.println(atributoListAux);
                    atributos = atributoListAux.split(",");
                    for (int j = 0; j < atributos.length; j++) {
                        //System.out.println(atributos[j]);
                        value = new Value(atributos[j]);
                        AttribListDados.classAttribute.values.add(value);
                    }
                }
                if (!AttribListDados.attributes.contains(attr)) {
                    AttribListDados.attributes.add(attr);
                }
            }

        }

        //verificarAtributos(AttribListDados);
        reader.close();
        return AttribListDados;

    }

    public  AttribList pegarAtributos(String arq) throws IOException {

        String local = "src/arquivos/" + arq + ".txt";
        String palavra = "";
        String atributoListAux = "";
        String[] picotada = null;
        String atributos[] = null;

        File arquivo = new File(local);
        FileReader fileReader = new FileReader(arquivo);
        BufferedReader reader = new BufferedReader(fileReader);

        int k = 0;
        while (reader.ready()) {

            palavra = reader.readLine();
            if (palavra.contains("@attribute") && !palavra.contains("Class")) {
                picotada = palavra.split(" ");
                attr = new Attribute(picotada[1]);
                //System.out.println(picotada[1]);
                for (int i = 2; i < picotada.length; i++) {
                    //System.out.println(picotada[i]);
                    atributoListAux = picotada[i];
                    //System.out.println(aux);
                    atributoListAux = removeParenteses(atributoListAux);
                    //System.out.println(k);
                    //System.out.println(atributoListAux);
                    atributos = atributoListAux.split(",");
                    for (int j = 0; j < atributos.length; j++) {
                        //System.out.println(atributos[j]);
                        value = new Value(atributos[j]);
                        attr.values.add(value);
                    }

                    //System.out.println();
                }
            } else if (palavra.contains("@attribute") && palavra.contains("Class")) {

                picotada = palavra.split(" ");
                attrList.classAttribute = new Attribute(picotada[1]);
                //System.out.println(picotada[1]);
                for (int i = 2; i < picotada.length; i++) {
                    //System.out.println(picotada[i]);
                    atributoListAux = picotada[i];
                    //System.out.println(aux);
                    atributoListAux = removeParenteses(atributoListAux);
                    //System.out.println(k);
                    //System.out.println(atributoListAux);
                    atributos = atributoListAux.split(",");
                    for (int j = 0; j < atributos.length; j++) {
                        //System.out.println(atributos[j]);
                        value = new Value(atributos[j]);
                        attrList.classAttribute.values.add(value);
                    }
                }

            }

            attrList.attributes.add(attr);
        }

        //verificarAtributos(attrList);
        reader.close();
        return attrList;

    }

    public void verificarAtributos(AttribList attrList) {

        int tamanhoValorClass = attrList.classAttribute.values.size();
        System.out.println("O tamanho da ClassValue é: " + tamanhoValorClass);

        for (int k = 0; k < attrList.attributes.size(); k++) {

            Attribute teste = attrList.attributes.get(k);

            System.out.println("=========ATRIBUTO============");
            System.out.println("Atributo: " + teste.name);
            System.out.println("Valores:");
            for (int i = 0; i < teste.values.size(); i++) {
                System.out.println(teste.values.get(i).name);
            }
        }

        Attribute teste = attrList.classAttribute;

        System.out.println("=========ATRIBUTO CLASSE============");
        System.out.println("Atributo: " + teste.name);
        System.out.println("Valores:");
        for (int i = 0; i < teste.values.size(); i++) {
            System.out.println(teste.values.get(i).name);
        }

    }

    public static String removeParenteses(String palavra) {

        String palavraAux = "";

        palavraAux = palavra.replace("(", "");
        palavraAux = palavraAux.replace(")", "");

        return palavraAux;
    }
}
