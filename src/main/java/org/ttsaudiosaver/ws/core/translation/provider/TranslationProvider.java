package org.ttsaudiosaver.ws.core.translation.provider;

import org.ttsaudiosaver.ws.core.translation.common.Language;

public interface TranslationProvider {
	
	public String translate(String from, Language fromLanguage, Language toLanguage);

}
