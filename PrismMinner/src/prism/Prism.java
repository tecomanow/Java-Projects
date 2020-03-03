/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prism;

import helper.Dados;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    static RulesList rl = new RulesList();

    public static void main(String[] args) throws IOException {
        TrainningSet trainningsetx = new TrainningSet();
        Dados d = new Dados();
        d.pegarAtributos(trainningsetx);
        for (Value v : trainningsetx.getListAtributos().classAttribute.values) {
            mine(v);
        }
        
        System.out.println("");
        System.out.println("");
        System.out.println("================AS REGRAS ENCONTRADAS FORAM: =============================");
        for (Rules r : rl.getRulesList()) {
            System.out.println(r.getRule());
        }
        System.out.println("==========================================================================");
    }

    public static RulesList mine(Value v) throws IOException {
        TrainningSet trainningset = new TrainningSet();
        Dados d = new Dados();
        d.pegarAtributos(trainningset);
        d.pegarDados(trainningset);

        Attribute at;
        TrainningSet tsAux = null;
        ArrayList<Attribute> listBestAtt = new ArrayList<Attribute>();

        //for (Value v : trainningset.getListAtributos().classAttribute.values) {
        //Value v = new Value("Yes");;
        System.out.println(v.name);
        do {
            Rules R = new Rules();
            tsAux = trainningset;

            do {
                //System.out.println(v.getName());
                at = trainningset.bestAtValue(v);
                listBestAtt.add(at);
                R.addCondition(at, v);

                trainningset = trainningset.selectSet(at);
            } while (at.values.get(0).probability != 1);
            if (!rl.getRulesList().contains(R)) {
                rl.addRules(R);
            }
            //System.out.println("Chamar o prune");
            trainningset = tsAux.pruneSet(listBestAtt);
            listBestAtt.clear();
            //System.out.println(R.getRule());
        } while (tsAux.hasClassValue(v));

        // }
        return null;

    }

}
