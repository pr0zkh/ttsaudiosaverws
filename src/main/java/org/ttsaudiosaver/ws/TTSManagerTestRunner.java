package org.ttsaudiosaver.ws;

import java.util.HashMap;
import java.util.Map;

import org.ttsaudiosaver.ws.core.translation.common.Language;
import org.ttsaudiosaver.ws.core.tts.manager.TTSManager;
import org.ttsaudiosaver.ws.core.tts.provider.IvonaTTSProvider;


public class TTSManagerTestRunner {
	
	private static final String OUTPUT_FOLDER_PATH = "./output/";
	
	public static void main(String[] args) {
		TTSManager manager = new TTSManager();
//		GoogleTTSProvider provider = new GoogleTTSProvider();
//		manager.setProvider(provider);
//		manager.saveTTSToFile(OUTPUT_FOLDER_PATH + "sample.mp3", "hello", Language.ENGLISH);
//		
//		Map<String, String> pairs = new HashMap<String, String>();
//		pairs.put("hello", "привет");
//		pairs.put("apple", "яблоко");
//		pairs.put("bus", "автобус");
//		pairs.put("transactional", "транзакционный");
//		
//		manager.saveTTSToFile(OUTPUT_FOLDER_PATH + "sample_pairs.mp3", pairs, Language.ENGLISH, Language.RUSSIAN);
		
//		IvonaTTSProvider provider = new IvonaTTSProvider();
//		manager.setProvider(provider);
//		manager.saveTTSToFile(OUTPUT_FOLDER_PATH + "sample.mp3", "This is a sample text to be synthesized.", Language.ENGLISH);
		
		
//		Map<String, String> pairs = new HashMap<String, String>();
//		pairs.put("hello", "привет");
//		pairs.put("apple", "яблоко");
//		pairs.put("bus", "автобус");
//		pairs.put("transactional", "транзакционный");
//		
//		IvonaTTSProvider provider = new IvonaTTSProvider();
//		manager.setProvider(provider);
//		manager.saveTTSToFile(OUTPUT_FOLDER_PATH + "sample_pairs.mp3", pairs, Language.ENGLISH, Language.RUSSIAN);
		
	}
}
