package br.com.juciano.victor.lcwebservices.enumeration;

public enum ProductCategory {
	T_SHIRT("T_SHIRT"),
	SNEAKERS("SNEAKERS");
	
	private String value;

	ProductCategory(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
