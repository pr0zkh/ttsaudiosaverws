package org.ttsaudiosaver.ws.core.translation.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ttsaudiosaver.ws.core.translation.common.Language;
import org.ttsaudiosaver.ws.core.translation.provider.TranslationProvider;

public class TranslationManager {
	
	private TranslationProvider translationProvider;
	
	public Map<String, String> getTranslations(List<String> from, Language fromLanguage, Language toLanguage) {
		if(from == null || fromLanguage == null || toLanguage == null) {
			throw new IllegalArgumentException("One of required params is null: from = " + from + ", fromLanguage = " + fromLanguage + ", toLanguage = " + toLanguage);
		}
		Map<String, String> results = new HashMap<String, String>();
		
		
		return results;
	}

	public TranslationProvider getTranslationProvider() {
		return translationProvider;
	}

	public void setTranslationProvider(TranslationProvider translationProvider) {
		this.translationProvider = translationProvider;
	}

}
