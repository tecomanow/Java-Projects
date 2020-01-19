package trainningSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttribList {

    ArrayList<Attribute> attributes;
    Attribute classAttribute;
    Map<String, Integer> mapOcorrencia = new HashMap<String, Integer>();
    Map<String, Integer> mapFrequencia = new HashMap<String, Integer>();

    AttribList() {

        attributes = new ArrayList<Attribute>();
    }

    /**
     * This method returns the list of all class atrribute values
     *
     * @return
     */
    public ArrayList<Value> getClassValues() {
        return classAttribute.getValues();
    }

    /**
     * This method adds a new attribute into the list
     *
     * @param attr
     */
    public void newAttribute(Attribute attr) {
        attributes.add(attr);
    }

    /**
     * This method sets the class attribute NOT TESTED!!!!!
     *
     * @param attr
     */
    public void setClassAttribute(Attribute attr) {
        classAttribute = attr;
    }

    public Attribute bestAtValue() throws IOException {

        TrainningSet ts = new TrainningSet();
        AttribList attrListDados = ts.pegarDados("trainningSet");
        AttribList possiveisAttrValues = ts.pegarAtributos("trainningSet");
        int index = 0;
        int frequenciaAtt = 0;

        //for (Value v : possiveisAttrValues.getClassValues()) {
        Value v = new Value("Hard");
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
        // System.out.println("=============VERIFICANDO MAP - FREQUENCIA " + v.name.toUpperCase() + "================");
        for (Map.Entry<String, Integer> entry : mapFrequencia.entrySet()) {
            //System.out.println("Valor: " + entry.getKey() + " Freq: " + entry.getValue());

        }
        // }

        contarFrequencia();

        calcularProbabilidade(mapFrequencia, mapOcorrencia);
        
        return null;
    }

    public void contarFrequencia() throws IOException {

        TrainningSet ts = new TrainningSet();
        AttribList attrListDados = ts.pegarDados("trainningSet");

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

    public void calcularProbabilidade(Map<String, Integer> frequencia, Map<String, Integer> ocorrencia) throws IOException {

        TrainningSet ts = new TrainningSet();
        AttribList attrListDados = ts.pegarDados("trainningSet");
        Attribute atributo = null;
        ArrayList<Attribute> atributosCandidatos = new ArrayList<Attribute>();
        Value valor = null;

        for (Map.Entry<String, Integer> entry : frequencia.entrySet()) {

            for (Map.Entry<String, Integer> entry2 : ocorrencia.entrySet()) {

                if (entry.getKey().equals(entry2.getKey())) {
                    String nomeAtributoValor = entry.getKey();
                    int a = entry.getValue();
                    int b = entry2.getValue();

                    float probabilidade = (float) a / b;

                    for (Attribute attrAtual : attrListDados.attributes) {
                        for (Value valorAtualAttr : attrAtual.values) {
                            if (valorAtualAttr.name.equals(nomeAtributoValor)) {
                                    atributo = new Attribute(attrAtual.name);
                                    valor = new Value(valorAtualAttr.name);
                                    valor.setProbability(probabilidade);
                                    //System.out.println("Atributo: " + attrAtual.name + " Valor: " + valorAtualAttr.name + " Probabilidade: " + valor.getProbability());
                                    atributosCandidatos.add(atributo);
                            }
                        }

                    }

                    //System.out.println("Atributo: " + entry.getKey() + " "+ "Frequencia: " + a + " = " + entry2.getKey() + "/" + " Ocorrencia: " + b + " Probabilidade: " + probabilidade);
                }

            }

        }
        
    }
}
