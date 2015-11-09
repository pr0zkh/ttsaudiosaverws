package org.ttsaudiosaver.ws;

import org.ttsaudiosaver.ws.core.translation.common.Language;
import org.ttsaudiosaver.ws.core.translation.provider.GoogleTranslateAPITranslationProvider;

public class GoogleTranslateAPITestRunner {
	
	private static final String API_KEY = "AIzaSyAdMJ2C1dOqyCxDdPOtIOzLUisuMn-t_XA";
	
	public static void main(String[] args) {
		GoogleTranslateAPITranslationProvider provider = new GoogleTranslateAPITranslationProvider();
		provider.setGoogleTranslateAPIKey(API_KEY);
		System.out.println(provider.translate("hello", Language.ENGLISH, Language.RUSSIAN));
		System.out.println(provider.translate("здравствуйте", Language.RUSSIAN, Language.ENGLISH));
	}
}
