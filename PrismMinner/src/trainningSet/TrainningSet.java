package trainningSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TrainningSet {

    AttribList attrList;
    AttribList attrListDados;
    Map<String, Integer> mapOcorrencia = new HashMap<String, Integer>();
    Map<String, Integer> mapFrequencia = new HashMap<String, Integer>();


    /**
     * Create a constructor for trainningSet, in this case when we instantiate
     * the class, we already have the array in the object
     */
    public TrainningSet() {
        //this.trainningSet = TrainningSet.lerArquivos();
    }
    
    public void setListDados(AttribList attrListDados){
        this.attrListDados = attrListDados;
    }
    
    public void setListAtributos(AttribList attrList){
        this.attrList = attrList;
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

    public Attribute bestAtValue(Value value) throws IOException {

        TrainningSet ts = new TrainningSet();
        //AttribList attrListDados = ts.pegarDados("trainningSet");
         //= ts.pegarAtributos("trainningSet");
        int index = 0;
        int frequenciaAtt = 0;

        //for (Value v : possiveisAttrValues.getClassValues()) {
        Value v = value;
        mapFrequencia.clear();
        for (Attribute att : attrListDados.attributes) {
            index = 0;
            frequenciaAtt = 0;

            ArrayList<Value> valoresClasse = attrListDados.classAttribute.values;

            for (Value valueAtribute : att.values) {

                Value valorClasse = valoresClasse.get(index);

                if (valorClasse.name.equals(v.name)) {

                    if (mapFrequencia.containsKey(valueAtribute.name)) {
                        mapFrequencia.put(valueAtribute.name, mapFrequencia.get(valueAtribute.name) + 1);
                    } else {
                        mapFrequencia.put(valueAtribute.name, 1);
                    }
                    //System.out.println(att.name + ": " + valueAtribute.name + ": " + valorClasse.name + "=" + v.name);
                }
                //System.out.println(att.name + "=" + valueAtribute.name + "=" + valorClasse.name);
                index++;
            }

        }
        //System.out.println("=============VERIFICANDO MAP - FREQUENCIA " + v.name.toUpperCase() + "================");
        for (Map.Entry<String, Integer> entry : mapFrequencia.entrySet()) {
            //System.out.println("Valor: " + entry.getKey() + " Freq: " + entry.getValue());

        }
        // }

        contarFrequencia();

        Attribute bestAtValue = calcularProbabilidade(mapFrequencia, mapOcorrencia);

        return bestAtValue;
    }

    public void contarFrequencia() throws IOException {

        TrainningSet ts = new TrainningSet();
        //AttribList attrListDados = ts.pegarDados("trainningSet");

        for (int n = 0; n < attrListDados.attributes.size(); n++) {

            Attribute atributo = attrListDados.attributes.get(n);

            for (Value val : atributo.values) {

                //System.out.println(attAttributeValue.name + "==" + valores.get(l).name);
                if (mapOcorrencia.containsKey(val.name)) {
                    mapOcorrencia.put(val.name, mapOcorrencia.get(val.name) + 1);
                } else {
                    mapOcorrencia.put(val.name, 1);
                }
            }

        }

        //System.out.println("=============VERIFICANDO MAP - OCORRENCIA================");
        for (Map.Entry<String, Integer> entry : mapOcorrencia.entrySet()) {
            //System.out.println("Valor: " + entry.getKey() + " Freq: " + entry.getValue());

        }

    }

    public Attribute calcularProbabilidade(Map<String, Integer> frequencia, Map<String, Integer> ocorrencia) throws IOException {

        TrainningSet ts = new TrainningSet();
        //AttribList attrListAtributos = ts.pegarAtributos("trainningSet");
        Attribute atributo = null;
        ArrayList<Attribute> atributosCandidatos = new ArrayList<Attribute>();
        Value valor = null;

        for (Map.Entry<String, Integer> entry : frequencia.entrySet()) {

            for (Map.Entry<String, Integer> entry2 : ocorrencia.entrySet()) {

                if (entry.getKey().equals(entry2.getKey())) {
                    String nomeAtributoValor = entry.getKey();
                    String nomeAtributoValor2 = entry2.getKey();
                    //System.out.println(nomeAtributoValor + " = " + nomeAtributoValor2);

                    int a = entry.getValue();
                    int b = entry2.getValue();

                    float probabilidade = (float) a / b;

                    for (Attribute attrAtual : attrList.attributes) {
                        for (Value valorAtualAttr : attrAtual.values) {
                            if (valorAtualAttr.name.equals(nomeAtributoValor)) {
                                //System.out.println(attrAtual.name + valorAtualAttr.name);
                                atributo = new Attribute(attrAtual.name);
                                valor = new Value(valorAtualAttr.name);
                                valor.setProbability(probabilidade);
                                atributo.values.add(valor);
                                System.out.println("Atributo: " + attrAtual.name + " Valor: " + valorAtualAttr.name + " Probabilidade: " + valor.getProbability());
                            }
                        }
                    }
                    atributosCandidatos.add(atributo);
                    //System.out.println("Atributo: " + entry.getKey() + " "+ "Frequencia: " + a + " = " + entry2.getKey() + "/" + " Ocorrencia: " + b + " Probabilidade: " + probabilidade);
                }
            }
        }

        Attribute bestAtValue = null;
        float probabilidade = 0;
        for (Attribute attrAtual2 : atributosCandidatos) {
            for (Value valorAtualAttr2 : attrAtual2.values) {
                if (valorAtualAttr2.getProbability() > probabilidade) {
                    bestAtValue = attrAtual2;
                    probabilidade = valorAtualAttr2.getProbability();
                    //System.out.println(probabilidade);
                    System.out.println("===============");
                    System.out.println("O melhor atributo-valor é: ");
                    System.out.println("Atributo: " + bestAtValue.name + " - " + valorAtualAttr2.name + " | Probabilidade: " + valorAtualAttr2.getProbability());
                    System.out.println("===============");
                }
            }
        }

        //System.out.println(bestAtValue.name + " = " + bestAtValue.values.get(0).name);
        return bestAtValue;
    }

    public void pruneSet(Attribute bestAtValue, AttribList attribListDados) throws IOException {

        int index = 0;

        for (int i = 0; i < attrListDados.attributes.size(); i++) {
            Attribute atual = attrListDados.attributes.get(i);
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

        Attribute atualClass = attrListDados.classAttribute;

        for (int i = 0; i < attrListDados.attributes.size(); i++) {
            Attribute atual = attrListDados.attributes.get(i);

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
