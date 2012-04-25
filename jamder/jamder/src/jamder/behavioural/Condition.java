package jamder.behavioural;

import jamder.structural.Property;

public class Condition extends Property {

	// Default constructor
	public Condition() {}
	
	// Constructor with all data
	public Condition(String name, String type, String value) {
		setName(name);
		setType(type);
		setValue(value);
	}
}
