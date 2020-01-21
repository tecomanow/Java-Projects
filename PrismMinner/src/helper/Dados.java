/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import trainningSet.AttribList;
import trainningSet.Attribute;
import trainningSet.TrainningSet;
import static trainningSet.TrainningSet.removeParenteses;
import trainningSet.Value;

/**
 *
 * @author MateusR
 */
public class Dados {

    AttribList attrList = new AttribList();
    AttribList attrListDados = new AttribList();
    //TrainningSet ts = new TrainningSet();
    private Attribute attr;
    private Value value;

    public AttribList pegarDados(TrainningSet ts) throws IOException {

        String local = "src/arquivos/TrainningSet.txt";
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
                if (!attrListDados.attributes.contains(attr)) {
                    attrListDados.attributes.add(attr);
                }

            } else if (palavra.contains("@data") && palavra.contains("Class")) {

                picotada = palavra.split(" ");
                attrListDados.classAttribute = new Attribute(picotada[1]);
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
                        attrListDados.classAttribute.values.add(value);
                    }
                }
                if (!attrListDados.attributes.contains(attr)) {
                    attrListDados.attributes.add(attr);
                }
            }

        }

        //verificarAtributos(AttribListDados);
        reader.close();
        ts.setListDados(attrListDados);
        return attrListDados;

    }

    public AttribList pegarAtributos(TrainningSet ts) throws IOException {

        String local = "src/arquivos/TrainningSet.txt";
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
        ts.setListAtributos(attrList);
        return attrList;

    }
}
