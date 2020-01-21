/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prism;

import java.io.IOException;
import trainningSet.AttribList;
import trainningSet.Attribute;
import trainningSet.TrainningSet;
import trainningSet.Value;

/**
 *
 * @author MateusR
 */
public class Prism {

    public static void main(String[] args) throws IOException {
        TrainningSet trainningset = new TrainningSet();
        AttribList attrList = trainningset.pegarDados("trainningSet");
        AttribList attrListTotal = trainningset.pegarAtributos("trainningSet");
        Attribute bestAtValue = attrList.bestAtValue();
        trainningset.pruneSet(bestAtValue, attrList);
        //String[][] aa = trainningset.recoverDate();
        //trainningset.getColumnTarget();
        
    }

    public RulesList mine(AttribList attrList, TrainningSet set) {
        RulesList rulesList = new RulesList();
        TrainningSet setOriginal = set.createClone();
        //Arqui vou percorrer a lista de valores de classe dos atributos
        //e armazenar temporariamente em v para fazer pegar alguma regra
        //uma vez que depois eu tenho que fazer as regras para todos os valores
        //de classes
        for (Value v : attrList.getClassValues()) {
            System.out.println(v);
        }
        return null;

    }

}
