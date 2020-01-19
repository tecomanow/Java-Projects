package trainningSet;

import java.util.ArrayList;

public class Attribute {
	
	String name;
	ArrayList<Value> values = new ArrayList<Value>();
        
	public Attribute(String name) {
		super();
		this.name = name;
	}

	/**
	 * This method returns all possible values
	 * @return
	 */
	public ArrayList<Value> getValues() {
		return values;
	}

	/**
	 * This method creates new attribute value
	 * NOT TESTED!!!!!!
	 * @param value
	 */
	public void addValue(Value value) {
		values.add(value);
	}

}
