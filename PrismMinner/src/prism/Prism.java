/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prism;

import helper.Dados;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
        //Value v = new Value("Hard");

        Attribute at;
        TrainningSet trainningSetOriginal = trainningset;
        RulesList rl = new RulesList();
        TrainningSet tsAux = null;
        ArrayList<Attribute> listBestAtt = new ArrayList<Attribute>();

        //for (Value v : trainningSetOriginal.getListAtributos().classAttribute.values) {           
            Value v = new Value("Yes");
            System.out.println(v.name);
            do {
                Rules R = new Rules();
                tsAux = trainningset;
                
                do {
                    //System.out.println(v.getName());
                    at = trainningset.bestAtValue(v);
                    listBestAtt.add(at);
                    R.addCondition(at, v);
                    if (!rl.getRulesList().contains(R)) {
                        rl.addRules(R);
                    }
                    trainningset = trainningset.selectSet(at);
                } while (at.values.get(0).probability != 1);
                //System.out.println("Chamar o prune");
                trainningset = tsAux.pruneSet(listBestAtt);
                listBestAtt.clear();
                //System.out.println(R.getRule());
            } while (tsAux.hasClassValue(v));
            trainningset = trainningSetOriginal;
            
        //}
        
        for (Rules r : rl.getRulesList()) {
                System.out.println(r.getRule());
            }

    }

    public RulesList mine(AttribList attrList, TrainningSet set) throws IOException {
        RulesList rulesList = new RulesList();
        //Arqui vou percorrer a lista de valores de classe dos atributos
        //e armazenar temporariamente em v para fazer pegar alguma regra
        //uma vez que depois eu tenho que fazer as regras para todos os valores
        //de classes
        Attribute bestValue;
        TrainningSet tsAux;
        for (Value v : attrList.getClassValues()) {
            TrainningSet setOriginal = set.createClone();
            do {
                Rules R = new Rules();
                tsAux = set;
                do {
                    bestValue = set.bestAtValue(v);
                    R.addCondition(bestValue, v);
                    set = set.selectSet(bestValue);
                } while (bestValue.values.get(0).probability != 1);
                rulesList.addRules(R);
                set = tsAux.pruneSet(null);
            } while (tsAux.hasClassValue(v));
            set = setOriginal;
        }
        return null;

    }

}
