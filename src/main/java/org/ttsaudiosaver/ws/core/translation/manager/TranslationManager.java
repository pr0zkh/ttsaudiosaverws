package org.ttsaudiosaver.ws.core.translation.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.ttsaudiosaver.ws.core.translation.common.Language;
import org.ttsaudiosaver.ws.core.translation.provider.TranslationProvider;

@Service
public class TranslationManager {
	
	@Autowired
	@Qualifier("googleTranslateTranslationProvider")
	private TranslationProvider translationProvider;
	
	public String getTranslation(String from, Language fromLanguage, Language toLanguage) {
		return translationProvider.translate(from, fromLanguage, toLanguage);
	}
	
	public Map<String, String> getTranslations(List<String> from, Language fromLanguage, Language toLanguage) {
		if(from == null || fromLanguage == null || toLanguage == null) {
			throw new IllegalArgumentException("One of required params is null: from = " + from + ", fromLanguage = " + fromLanguage + ", toLanguage = " + toLanguage);
		}
		Map<String, String> results = new HashMap<String, String>();
		
		for(String s : from) {
			String translation = translationProvider.translate(s, fromLanguage, toLanguage);
			results.put(s, translation);
		}
		
		return results;
	}
}
