package org.ttsaudiosaver.ws.core.translation.common;

public enum Language {
	
	RUSSIAN("ru"), ENGLISH("en");
	
	private String value;
	
	Language(String language) {
		this.value = language;
	}
	
	public String getValue() {
		return this.value;
	}
}