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
        AttribList attrListTotal = ts.pegarAtributos("trainningSet");
        Attribute attr;
        Value value;

        Attribute teste1;
        Attribute teste2;
        Attribute teste3;

        for (Value v : attrListTotal.getClassValues()) {
            
        }

        return null;
    }

}
