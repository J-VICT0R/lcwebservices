package br.com.juciano.victor.lcwebservices.enumeration;

public enum Genre {
	MALE("MALE"),
	FEMALE("FEMALE");
	
	private String value;
	
	private Genre(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
