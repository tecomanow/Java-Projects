package trainningSet;

import java.io.IOException;
import java.util.ArrayList;

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
        AttribList possiveisAttrValues = ts.pegarAtributos("trainningSet");
        int index = 0;
        Attribute attr;
        Value value;
        int vezesQueApareceu = 0;

        /*for (Value v : possiveisAttrValues.getClassValues()) {

            for (Attribute attribute : attrListDados.attributes) {
                //System.out.println("----ATRIBUTO----");
                for (Value values : attribute.getValues()) {
                    //System.out.println(attribute.name + " Valores: " + values.name);
                    if (v.name.equals(values.name)) {

                    }

                }

            }

        }*/
        for (Value v : possiveisAttrValues.getClassValues()) {

            //System.out.println(att.values.size());
            //System.out.println(attrListDados.classAttribute.values.size());
            for (int i = 0; i < 24; i++) {
                Attribute att = attrListDados.attributes.get(index);
                Value attAttributeValue = att.values.get(i);
                Value attClassValue = attrListDados.classAttribute.values.get(i);
                //System.out.println("Atributo: " + att.name + " /" + " Valor: " + attAttributeValue.name +  " /" + " Class: " + attClassValue.name);

                if (v.name.equalsIgnoreCase(attClassValue.name)) {
                    System.out.println(att.name + " Valor: " + attAttributeValue.name + " Class: " + attClassValue.name);
                }
            }
            index++;
        }

        return null;
    }

}
