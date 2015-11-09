package org.ttsaudiosaver.ws;

import java.util.HashMap;
import java.util.Map;

import org.ttsaudiosaver.ws.core.translation.common.Language;
import org.ttsaudiosaver.ws.core.tts.manager.TTSManager;
import org.ttsaudiosaver.ws.core.tts.provider.GoogleTTSProvider;


public class TTSManagerTestRunner {
	
	public static void main(String[] args) {
		TTSManager manager = new TTSManager();
		GoogleTTSProvider provider = new GoogleTTSProvider();
		manager.setProvider(provider);
		manager.saveTTSToFile("sample.mp3", "hello", Language.ENGLISH);
		
		Map<String, String> pairs = new HashMap<String, String>();
		pairs.put("hello", "привет");
		pairs.put("apple", "яблоко");
		pairs.put("bus", "автобус");
		pairs.put("transactional", "транзакционный");
		
		manager.saveTTSToFile("sample_pairs.mp3", pairs, Language.ENGLISH, Language.RUSSIAN);
	}
}