/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prism;

import helper.Dados;
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
        Dados d = new Dados();
        d.pegarAtributos(trainningset);
        d.pegarDados(trainningset);
        Value v = new Value("None");
        trainningset.bestAtValue(v);
        //AttribList attrList = trainningset.pegarDados("trainningSet");
        //AttribList attrListTotal = trainningset.pegarAtributos("trainningSet");
        //Attribute bestAtValue = trainningset.bestAtValue();
        //trainningset.pruneSet(bestAtValue, attrList);
        //String[][] aa = trainningset.recoverDate();
        //trainningset.getColumnTarget();

    }

    public RulesList mine(AttribList attrList, TrainningSet set) {
        RulesList rulesList = new RulesList();
        //Arqui vou percorrer a lista de valores de classe dos atributos
        //e armazenar temporariamente em v para fazer pegar alguma regra
        //uma vez que depois eu tenho que fazer as regras para todos os valores
        //de classes
        
        /*for (Value v : attrList.getClassValues()) {
            TrainningSet setOriginal = set.createClone();
            do{
                Rules R = new Rules();
                tsAux = set;
                do{
                    Value bestValue = set.bestAtValue();
                    R.addCondition(v);
                    set = set.select(v);                    
                }while(!v.enoughProbability);
                rulesList.addRules(R);
                set = tsAux.pruneSet(R);
            }while(tsAux.hasNoClassValue(v));
            
        }*/
        return null;

    }

}
