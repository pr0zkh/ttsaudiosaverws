package org.ttsaudiosaver.ws;

import org.ttsaudiosaver.ws.core.translation.common.Language;
import org.ttsaudiosaver.ws.core.translation.provider.GoogleTranslateAPITranslationProvider;

public class GoogleTranslateAPITestRunner {
	
	public static void main(String[] args) {
		GoogleTranslateAPITranslationProvider provider = new GoogleTranslateAPITranslationProvider();
		System.out.println(provider.translate("hello", Language.ENGLISH, Language.RUSSIAN));
		System.out.println(provider.translate("здравствуйте", Language.RUSSIAN, Language.ENGLISH));
	}
}
