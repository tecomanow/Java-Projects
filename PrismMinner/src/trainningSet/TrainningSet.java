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

    private AttribList attrList = new AttribList();
    private AttribList AttribListDados = new AttribList();
    private Attribute attr;
    private Value value;

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

    public void pruneSet(Attribute bestAtValue, AttribList attribListDados) throws IOException {

        int index = 0;

        for (int i = 0; i < AttribListDados.attributes.size(); i++) {
            Attribute atual = AttribListDados.attributes.get(i);
            for (int j = 0; j < atual.values.size(); j++) {
                Value atualValue = atual.values.get(j);
                if (atualValue.name.equals(bestAtValue.values.get(0).name)) {
                    //System.out.println(atual.name + " == " + atualValue.name);
                    //System.out.println(j);
                    index = atual.values.indexOf(atualValue);
                    //System.out.println(index);
                    criaNovaLista(index, attribListDados);
                }

            }
        }

        /*       for (int i = 0; i < aux.attributes.size(); i++) {
            Attribute atual = aux.attributes.get(i);
            for (int j = 0; j < atual.values.size(); j++) {
                Value atualValue = atual.values.get(j);
                System.out.println(atual.name + " " + atualValue.name);
            }
        }*/
    }

    public AttribList pegarDados(String arq) throws IOException {

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

    public AttribList pegarAtributos(String arq) throws IOException {

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
                    if (!attrList.attributes.contains(attr)) {
                        attrList.attributes.add(attr);

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
                if (!attrList.attributes.contains(attr)) {
                    attrList.attributes.add(attr);

                }
            }

        }

        //verificarAtributos(attrList);
        reader.close();
        return attrList;

    }

    public static String removeParenteses(String palavra) {

        String palavraAux = "";

        palavraAux = palavra.replace("(", "");
        palavraAux = palavraAux.replace(")", "");

        return palavraAux;
    }

    private void criaNovaLista(int index, AttribList attribListDados) throws IOException {

        Attribute atributo;
        Value valorAtributo;
        AttribList attribList = new AttribList();

        Attribute atualClass = AttribListDados.classAttribute;

        for (int i = 0; i < AttribListDados.attributes.size(); i++) {
            Attribute atual = AttribListDados.attributes.get(i);

            for (int j = 0; j < atual.values.size(); j++) {
                Value atualValue = atual.values.get(j);
                Value atualClassValue = atualClass.values.get(j);

                if (index == j) {
                    atributo = new Attribute(atual.name);
                    valorAtributo = new Value(atualValue.name);
                    atributo.values.add(valorAtributo);
                    attribList.classAttribute = new Attribute(atualClass.name);
                    attribList.classAttribute.values.add(atualClassValue);
                    attribList.attributes.add(atributo);
                }
            }
        }

        for (int i = 0; i < attribList.attributes.size(); i++) {
            Attribute atualx = attribList.attributes.get(i);
            for (int j = 0; j < atualx.values.size(); j++) {
                Value atualValuex = atualx.values.get(j);
                System.out.println(atualx.name + " == " + atualValuex.name + " == " + attribList.classAttribute.values.get(j).name);
            }

        }

        //Attribute bav = attribList.bestAtValue(attribList);
        // System.out.println(bav.name + " == " + bav.values.get(0).name);
    }

}
