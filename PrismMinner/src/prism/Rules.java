package prism;

import trainningSet.Attribute;
import trainningSet.TrainningSet;
import trainningSet.Value;

public class Rules {
	
	public String rule;
        public Value classValue;
        
        public Rules(Value classValue){
            this.classValue = classValue;
        }

	public String getRule() {
		return this.rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	
	public void addCondition (Value bestAtValue, TrainningSet ts){
            String condition = new String();
            
            for (Attribute a : ts.attrListDados.attributes)
                for (Value va : a.values)
                    if (bestAtValue.name==va.name)
                        condition = "(" + a.name + "=" + bestAtValue.name + ")";
            
            if (this.rule == null)
                this.rule = condition; 
            else
                this.rule = " and " + condition;
        }            
}
