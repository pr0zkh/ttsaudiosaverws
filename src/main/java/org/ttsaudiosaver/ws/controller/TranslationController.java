package org.ttsaudiosaver.ws.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ttsaudiosaver.ws.core.translation.common.Language;
import org.ttsaudiosaver.ws.core.translation.manager.TranslationManager;
import org.ttsaudiosaver.ws.core.tts.manager.TTSManager;

import com.google.gson.JsonObject;

@RestController
public class TranslationController {
	
	@Autowired
	private TranslationManager translationManager;
	
	@Autowired
	private TTSManager ttsManager;
	
	@RequestMapping(value = "/translate", method = RequestMethod.GET)
	public String translate(@RequestParam("from") String from, @RequestParam("fromLang") String fromLang, @RequestParam("toLang") String toLang) {
		JsonObject response = new JsonObject();
		response.addProperty("from", from);
		response.addProperty("translation", translationManager.getTranslation(from, Language.getLanguage(fromLang), Language.getLanguage(toLang)));
		response.addProperty("fromLang", fromLang);
		response.addProperty("toLang", toLang);
		
		return response.toString(); 
	}
	
	@RequestMapping(value = "/translate_tts", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public String translateTTS(@RequestParam("from") String from, @RequestParam("fromLang") String fromLang, @RequestParam("toLang") String toLang) {
		Map<String, String> toSave = new HashMap<>();
		String translation = translationManager.getTranslation(from, Language.getLanguage(fromLang), Language.getLanguage(toLang));
		toSave.put(from, translation);
		String fileId = ttsManager.saveTTSToFile(toSave, Language.getLanguage(fromLang), Language.getLanguage(toLang));
		
		JsonObject response = new JsonObject();
		response.addProperty("from", from);
		response.addProperty("translation", translation);
		response.addProperty("fromLang", fromLang);
		response.addProperty("toLang", toLang);
		response.addProperty("fileId", fileId);
		
		return response.toString(); 
	}
	
	@RequestMapping(value = "/compile_tts", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public String compileTranslations(@RequestParam("fileIds") String[] fileIds) {
		String compiledFileId = ttsManager.saveTTSToFile(fileIds);
		
		JsonObject response = new JsonObject();
		response.addProperty("compiledFileId", compiledFileId);
		return response.toString(); 
	}
}