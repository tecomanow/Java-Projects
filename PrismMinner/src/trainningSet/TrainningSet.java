package trainningSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import prism.Rules;

public class TrainningSet {

    AttribList attrList;
    AttribList attrListDados;
    AttribList attrListNovo = new AttribList();
    
    Map<String, Integer> mapOcorrencia;
    Map<String, Integer> mapFrequencia;

    /**
     * Create a constructor for trainningSet, in this case when we instantiate
     * the class, we already have the array in the object
     */
    public TrainningSet() {
        //this.trainningSet = TrainningSet.lerArquivos();
    }

    public void setListDados(AttribList attrListDados) {
        this.attrListDados = attrListDados;
    }

    public void setListAtributos(AttribList attrList) {
        this.attrList = attrList;
    }

    public AttribList getListAtributos(){
        return attrList;
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

        mapFrequencia = new HashMap<String, Integer>();

        //TrainningSet ts = new TrainningSet();
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
                    //System.out.println(valueAtribute.name);

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
        System.out.printf("%s -  %s\n",mapFrequencia,mapOcorrencia);
        if(!mapOcorrencia.isEmpty()){
            Attribute bestAtValue = calcularProbabilidade(mapFrequencia, mapOcorrencia);
            return bestAtValue;
        }
        
        return null;
    }

    public void contarFrequencia() throws IOException {
        mapOcorrencia = new HashMap<String, Integer>();

        TrainningSet ts = new TrainningSet();
        //AttribList attrListDados = ts.pegarDados("trainningSet");

        for (int n = 0; n < attrListDados.attributes.size(); n++) {

            Attribute atributo = attrListDados.attributes.get(n);

            for (Value val : atributo.values) {
                //System.out.println(val.name);
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
                    //System.out.println(valorAtualAttr2.getProbability());
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

        //System.out.println(bestAtValue.name + " = " + bestAtValue.values.get(0).name);
        return bestAtValue;
    }

    public TrainningSet selectSet(Attribute bestAtValue, int k) throws IOException {
        //System.out.println(attrListDados.attributes.size());
        int index = 0;
        int count = 0;
        List<Integer> listIndex = new ArrayList<Integer>();
        TrainningSet novoSet = new TrainningSet();
        attrListNovo = new AttribList();
        
        // Esse primeiro if checa se os atributos são o mesmo da tabela original
        // Caso nãos seja ele entra nesse primeiro if, que é o caso de ocorrer uma regra 100% única
        if(attrListDados.attributes.size() != k)
        {
            for (Attribute at : attrListDados.attributes){
                for(Value va : at.values){
                    //System.out.printf("%s -> %s\n", at.name, va.name);
                    if(bestAtValue.name.equals(at.name)){
                        if(va.name.equals(bestAtValue.values.get(0).name)){
                            //System.out.println("x");
                            index = at.values.indexOf(va);
                            listIndex.add(index);
                        }
                    }
                }
            }

            if (listIndex.isEmpty()){
                return null;
            }
            
            attrListNovo = criaNovaLista(listIndex);
            //novoSet.setListDados(criaNovaLista(listIndex));
            novoSet.setListDados(CriaNovaListaPrune(bestAtValue.name, attrListNovo));
            
        }
        // Caso sejam iguais ele entra no else
        // tratando as linhas de outra maneira
        else
        {
            for (int i = 0; i < attrListDados.attributes.size(); i++) {
                Attribute atual = attrListDados.attributes.get(i);
                for (int j = 0; j < atual.values.size(); j++) {
                    Value atualValue = atual.values.get(j);
                    if (atualValue.name.equals(bestAtValue.values.get(0).name)) {
                        //System.out.println(atualValue.name);
                        //System.out.println(atual.name + " == " + atualValue.name);
                        //System.out.println(j);
                        index = atual.values.indexOf(atualValue);
                        //Manda por exemplo o index 2, na função cria lista vai criar um atributo
                        //Onde o index for dois, depois vai criar o mesmo atributo novamente, mas não adicionar o value
                        //há um que já foi adicionado
                        listIndex.add(index);
                        //criaNovaLista(index);
                    }

                }
            }
            novoSet.setListDados(criaNovaLista(listIndex));
        }
        
        /*for(Attribute attr : attrListNovo.attributes){
            for(Value valu : attr.values){
                System.out.printf("%s -> %s\n",attr.name,valu.name);
            }
        }*/
        novoSet.setListAtributos(attrList);
        

        /*       for (int i = 0; i < aux.attributes.size(); i++) {
            Attribute atual = aux.attributes.get(i);
            for (int j = 0; j < atual.values.size(); j++) {
                Value atualValue = atual.values.get(j);
                System.out.println(atual.name + " " + atualValue.name);
            }
        }*/
        return novoSet;
    }

    public static String removeParenteses(String palavra) {

        String palavraAux = "";

        palavraAux = palavra.replace("(", "");
        palavraAux = palavraAux.replace(")", "");

        return palavraAux;
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
                attrListNovo.classAttribute.addValue(atualClassValue);

                if (!attrListNovo.attributes.contains(atributo)) {
                    attrListNovo.attributes.add(atributo);
                    for(Value vv : atributo.getValues()){
                        //System.out.println(vv.name);
                    }
                }
                //System.out.println(attrListNovo.attributes.size());
                //}
            }
        }

 /*for (int i = 0; i < attrListNovo.attributes.size(); i++) {
            Attribute atualx = attrListNovo.attributes.get(i);
            System.out.println(i + "° Atributo: " + atualx.name);
            //System.out.println();
            for (int j = 0; j < atualx.values.size(); j++) {
                Value atualValuex = atualx.values.get(j);
                System.out.println(atualValuex.name + " == " + attrListNovo.classAttribute.values.get(j).name);
            }

        }*/
        

        return attrListNovo;
    }
    
    private AttribList CriaNovaListaPrune(String at, AttribList al) throws IOException {

        Attribute atributo = null;
        Value valorAtributo;
        int count;
        
        attrListNovo = new AttribList();
        attrListNovo.classAttribute = new Attribute("Class");
        Attribute atualClass = al.classAttribute;
        attrListNovo.classAttribute = new Attribute(atualClass.name);

        for (Attribute atual : al.attributes) {
            atributo = new Attribute(atual.name);
            if(!atual.name.equals(at)){
                for (count = 0; count < atual.values.size(); count++) {
                    Value atualValue = atual.values.get(count);
                    //System.out.printf("%s -> %s -> %s\n", atual.name, atual.values.get(count).name, atualClass.values.get(count).name);
                    Value atualClassValue = atualClass.values.get(count);
                    atributo.values.add(atualValue);
                    attrListNovo.classAttribute.addValue(atualClassValue);
                    
                    if (!attrListNovo.attributes.contains(atributo)) {
                    attrListNovo.attributes.add(atributo);
                    }
                    //System.out.println(attrListNovo.attributes.size());
                    //}
                }
                
            }
            
        }
        
        for (Attribute attrAtual : attrListNovo.attributes) {
            //System.out.println(attrAtual.name);
            for(Value valueAtual : attrAtual.values){
                //System.out.printf("%s -> %s\n", attrAtual.name, valueAtual.name);
            }
        }
        

 /*for (int i = 0; i < attrListNovo.attributes.size(); i++) {
            Attribute atualx = attrListNovo.attributes.get(i);
            System.out.println(i + "° Atributo: " + atualx.name);
            //System.out.println();
            for (int j = 0; j < atualx.values.size(); j++) {
                Value atualValuex = atualx.values.get(j);
                System.out.println(atualValuex.name + " == " + attrListNovo.classAttribute.values.get(j).name);
            }

        }*/

        return attrListNovo;
        
    }

    public boolean hasNoClassValue(Value v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public TrainningSet pruneSet(Attribute bestAtValue) throws IOException{
        TrainningSet novoSet = new TrainningSet();
        AttribList attrListx;
        Attribute atributo = null;
        int count = 0;
        Value valoratributo;
        
        
        novoSet.setListDados(CriaNovaListaPrune(bestAtValue.name, attrListDados));
        if(novoSet.attrListDados.attributes.size() == 0){
            return null;
        }
        //System.out.println(attrList.attributes.get(3).name);
        //System.out.println(attrList.attributes.get(3).values.get(1).name);
        
        
        attrListNovo = new AttribList();
        attrListNovo.classAttribute = new Attribute("Class");
        Attribute atualClass = attrListDados.classAttribute;
        
        //System.out.println(attrList.classAttribute.values.get(2).name);
        //System.out.println(attrList.classAttribute.name);
        //System.out.println(attrList.attributes.get(3).name);
        //System.out.println(attrList.attributes.get(0).values.get(0).name);
        
        /*for (Attribute attrAtual : attrList.attributes) {
            if(!attrAtual.name.equals(bestAtValue.name)){
                attrListNovo.attributes.add(attrAtual);
            }
        }
        
        for (Value valueclass : attrList.classAttribute.getValues()) {
            attrListNovo.classAttribute.addValue(attrList.classAttribute.getValues().get(count));
            count++;
        }*/
        
        
        //System.out.println(attrList.getClassValues().get(3).name);
        //System.out.println(attrListNovo.attributes.get(0).name);
        //System.out.println(attrListNovo.attributes.get(0).values);
        //System.out.println(attrListNovo.attributes.get(1).name);
        //System.out.println(attrListNovo.attributes.get(2).name);
        //System.out.println(attrListNovo.classAttribute.name);
        //System.out.println(attrListNovo.classAttribute.values.get(0).name);
        //System.out.println(attrListNovo.classAttribute.values.get(1).name);
        //System.out.println(attrListNovo.classAttribute.values.get(2).name);
        
        
        novoSet.setListAtributos(attrList);
        //novoSet.setListAtributos(attrListNovo);
        
        count++;
           /* for (int i = 0; i < aux.attributes.size(); i++) {
            Attribute atual = aux.attributes.get(i);
            for (int j = 0; j < atual.values.size(); j++) {
                Value atualValue = atual.values.get(j);
                System.out.println(atual.name + " " + atualValue.name);
            }
        }*/
        return novoSet;
       
    }

    public boolean hasClassValue(Value classValue){
        for(Value v : this.attrListDados.classAttribute.values)
            if(v.name==classValue.name)
                return true;

        return false;
    }

}
