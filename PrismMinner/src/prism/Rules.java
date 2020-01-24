package prism;

import trainningSet.Attribute;
import trainningSet.TrainningSet;
import trainningSet.Value;

public class Rules {
	
	private String rule;

	public String getRule() {
		return this.rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	
	public void addCondition (Value v, TrainningSet ts){
            String condition = new String();
            
            for (Attribute a : ts.attrListDados.attributes)
                for (Value va : a.values)
                    if (v.name==va.name)
                        condition = "(" + a.name + "=" + v.name + ")";
            
            if (this.rule == null)
                this.rule = condition; 
            else
                this.rule = " and " + condition;
        }            
}
