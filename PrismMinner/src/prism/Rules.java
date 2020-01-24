package prism;

import java.util.ArrayList;
import trainningSet.Attribute;
import trainningSet.Value;

public class Rules {

    private String rule;
    public String condition;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    void addCondition(Attribute bestValue, Value v) {
        String condition = bestValue.name + "=" + bestValue.values.get(0).getName();

        if (this.condition == null) {
            this.condition = condition;
        } else {
            this.condition += " and " + condition;
        }

        if(bestValue.values.get(0).getProbability() == 1){
            rule = this.condition + " -> " + v.getName();
        }
    }

}
