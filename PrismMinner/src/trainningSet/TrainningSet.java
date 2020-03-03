package trainningSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import prism.Rules;

public class TrainningSet implements Cloneable {

    public AttribList attrList;
    AttribList attrListDados;
    AttribList attrListNovo = new AttribList();

    Map<String, Integer> mapOcorrencia;
    Map<String, Integer> mapFrequencia;

    public TrainningSet(){
        
    }
    

    public void setListDados(AttribList attrListDados) {
        this.attrListDados = attrListDados;
    }

    public void setListAtributos(AttribList attrList) {
        this.attrList = attrList;
    }

    public AttribList getListAtributos() {
        return attrList;
    }

    public Attribute bestAtValue(Value value) throws IOException {

        mapFrequencia = new HashMap<String, Integer>();


        int index = 0;
        int frequenciaAtt = 0;

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
        mapOcorrencia = new HashMap<String, Integer>();

        TrainningSet ts = new TrainningSet();

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

                }
            }
        }

        System.out.println("=============================================");
        System.out.println("O melhor atributo-valor é: ");
        System.out.println("Atributo: " + bestAtValue.name + " - " + bestAtValue.values.get(0).name + " | Probabilidade: " + bestAtValue.values.get(0).getProbability());
        System.out.println("=============================================");

        return bestAtValue;
    }

    public TrainningSet selectSet(Attribute bestAtValue) throws IOException {

        int index = 0;
        List<Integer> listIndex = new ArrayList<Integer>();
        TrainningSet novoSet = new TrainningSet();

        for (int i = 0; i < attrListDados.attributes.size(); i++) {
            Attribute atual = attrListDados.attributes.get(i);
            for (int j = 0; j < atual.values.size(); j++) {
                Value atualValue = atual.values.get(j);
                if (atualValue.name.equals(bestAtValue.values.get(0).name)) {
                    index = atual.values.indexOf(atualValue);
                    listIndex.add(index);
                }

            }
        }

        novoSet.setListDados(criaNovaLista(listIndex));
        novoSet.setListAtributos(attrList);

        return novoSet;
    }

    private AttribList criaNovaLista(List<Integer> listIndex) throws IOException {

        Attribute atributo = null;
        Value valorAtributo;

        attrListNovo = new AttribList();
        attrListNovo.classAttribute = new Attribute("Class");
        Attribute atualClass = attrListDados.classAttribute;
        attrListNovo.classAttribute = new Attribute(atualClass.name);

        for (Attribute atual : attrListDados.attributes) {
            atributo = new Attribute(atual.name);
            for (Integer inte : listIndex) {
                Value atualValue = atual.values.get(inte);
                Value atualClassValue = atualClass.values.get(inte);

                atributo.values.add(atualValue);

                if (!attrListNovo.classAttribute.values.contains(atualClassValue)) {
                    attrListNovo.classAttribute.addValue(atualClassValue);
                }

                if (!attrListNovo.attributes.contains(atributo)) {
                    attrListNovo.attributes.add(atributo);
                }
                //System.out.println(attrListNovo.attributes.size());
                //}
            }
        }

        return attrListNovo;
    }

    public TrainningSet pruneSet(ArrayList<Attribute> listBestAtt) throws IOException {
        int index = 0;
        List<Integer> listIndex = new ArrayList<Integer>();
        Map<Integer, Integer> contagem = new HashMap<Integer, Integer>();
        AttribList attrLista = new AttribList();

        Attribute atualClass = attrListDados.classAttribute;

        for (Attribute atributoArgumento : listBestAtt) {
            Value valorAtributoArgumento = atributoArgumento.values.get(0);

            for (Attribute atributo : attrListDados.attributes) {
                for (Value valor : atributo.values) {

                    if (valorAtributoArgumento.name.equals(valor.name)) {
                        index = atributo.values.indexOf(valor);
                        //System.out.println(index);
                        if (contagem.containsKey(index)) {
                            contagem.put(index, contagem.get(index) + 1);
                        } else {
                            contagem.put(index, 1);
                        }
                    }

                }
            }

        }

        for (Map.Entry<Integer, Integer> entry : contagem.entrySet()) {
            //System.out.println(entry.getKey() + " = " + entry.getValue());
            if (entry.getValue() == listBestAtt.size()) {                
                listIndex.add(entry.getKey());
            }
        }

        attrLista = attrListDados;
        Collections.sort(listIndex);
        Collections.reverse(listIndex);
        for (int i = 0; i < attrListDados.attributes.size(); i++) {

            for (Integer inte : listIndex) {
                int x = inte;
                attrLista.attributes.get(i).values.remove(x);
            }
        }

        for (Integer inte : listIndex) {
            Value ve = atualClass.values.get(inte);
            int x = inte;
            attrLista.classAttribute.values.remove(x);
        }

        TrainningSet ts = new TrainningSet();
        ts.setListDados(attrLista);
        ts.setListAtributos(attrList);

        return ts;
    }

    public boolean hasClassValue(Value classValue) {
        for (Value v : this.attrListDados.classAttribute.values) {
            if (v.name.equalsIgnoreCase(classValue.name)) {
                return true;
            }
        }

        return false;
    }

    public static String removeParenteses(String palavra) {

        String palavraAux = "";

        palavraAux = palavra.replace("(", "");
        palavraAux = palavraAux.replace(")", "");

        return palavraAux;
    }

}
