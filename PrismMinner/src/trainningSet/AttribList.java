package trainningSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttribList {

    ArrayList<Attribute> attributes;
    Attribute classAttribute;

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
        //ts.verificarAtributos(attrListDados);
        AttribList possiveisAttrValues = ts.pegarAtributos("trainningSet");
        Map<String, Integer> mapFrequencia = new HashMap<String, Integer>();
        int index = 0;
        int frequenciaAtt = 0;

        for (Value v : possiveisAttrValues.getClassValues()) {
            //Value v = new Value("Hard");
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

                        //frequenciaAtt++;
                        //mapFrequencia.put(valueAtribute.name, frequenciaAtt);
                        //System.out.println(att.name + ": " + valueAtribute.name + ": " + valorClasse.name + "=" + v.name);
                    }

                    //System.out.println(att.name + "=" + valueAtribute.name + "=" + valorClasse.name);
                    index++;
                }

            }
            System.out.println("VERIFICANDO MAP");
            for (Map.Entry<String, Integer> entry : mapFrequencia.entrySet()) {
                System.out.println("Valor: " + entry.getKey() + " Freq: " + entry.getValue());

            }
        }


        /*int frequencia = 0;
        int igual = 0;
        for (Value v : possiveisAttrValues.getClassValues()) {

            Attribute att = attrListDados.attributes.get(index);
            for (int i = 0; i < attrListDados.classAttribute.values.size(); i++) {
                Value attAttributeValue = att.values.get(i);
                Value attClassValue = attrListDados.classAttribute.values.get(i);

                frequencia = 0;
                for (int k = 0; k < attrListDados.attributes.size(); k++) {

                    Attribute atributo = attrListDados.attributes.get(k);
                    ArrayList<Value> valores = atributo.values;

                    frequencia = valores.get(i).getOcorrencia();
                    igual = valores.get(i).getIgual();
                    valores.get(i).setIgual(0);
                    valores.get(i).setOcorrencia(0);

                    for (int l = 0; l < valores.size(); l++) {
                        Value classesValores = attrListDados.classAttribute.values.get(l);
                        if (attAttributeValue.name.equals(valores.get(l).name)) {
                            //System.out.println(attAttributeValue.name + "==" + valores.get(l).name);
                            frequencia++;
                            valores.get(i).setOcorrencia(frequencia);
                            //System.out.println(valores.get(l).getOcorrencia());
                            if (classesValores.name.equals(v.name)) {
                                igual++;
                                valores.get(i).setIgual(igual);
                            }
                        }

                    }
                }

                System.out.println("Atributo: " + att.name + " /" + " Valor: " 
                        + attAttributeValue.name + " /" + " Class: " 
                        + attClassValue.name + " Freq: " + attAttributeValue.getOcorrencia() 
                        + " Igual:" + attAttributeValue.getIgual());
            }
            index++;
        }*/
        return null;
    }

}
