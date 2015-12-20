package org.ttsaudiosaver.ws.core.tts.provider;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.ttsaudiosaver.ws.core.translation.common.Language;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.ivona.services.tts.IvonaSpeechCloudClient;
import com.ivona.services.tts.model.CreateSpeechRequest;
import com.ivona.services.tts.model.CreateSpeechResult;
import com.ivona.services.tts.model.Input;
import com.ivona.services.tts.model.ListLexiconsResult;
import com.ivona.services.tts.model.ListVoicesRequest;
import com.ivona.services.tts.model.ListVoicesResult;
import com.ivona.services.tts.model.Voice;

@Service(value = "IvonaTTSProvider")
public class IvonaTTSProvider implements TTSProvider {
	
	private static final Logger logger = Logger.getLogger(IvonaTTSProvider.class);
	
	private static final String IVONA_ENDPOINT = "https://tts.eu-west-1.ivonacloud.com";
	private static final Integer BUFFER_SIZE = 2 * 1024;
	
	private IvonaSpeechCloudClient speechCloud;
	
	private Map<Language, String> langToVoiceMap;
	
	@PostConstruct
	private void init() {
		speechCloud = new IvonaSpeechCloudClient(
                new ClasspathPropertiesFileCredentialsProvider("/IvonaCredentials.properties"));
        speechCloud.setEndpoint(IVONA_ENDPOINT);

    	langToVoiceMap = new HashMap<Language, String>();
    	langToVoiceMap.put(Language.ENGLISH, "Amy");
    	langToVoiceMap.put(Language.RUSSIAN, "Tatyana");
	}
		
		
	@Override
	public byte[] getTTSAudio(String query, Language language) {
		byte[] result = null;
		CreateSpeechRequest createSpeechRequest = new CreateSpeechRequest();
		Input input = new Input();
        Voice voice = new Voice();
        voice.setName(langToVoiceMap.get(language));
        input.setData(query);
        createSpeechRequest.setInput(input);
        createSpeechRequest.setVoice(voice);
        InputStream in = null;
        try {
            CreateSpeechResult createSpeechResult = speechCloud.createSpeech(createSpeechRequest);
            in = createSpeechResult.getBody();
            byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead;
			while((bytesRead = in.read(buffer)) != -1) {
				if(result == null) {
					result = Arrays.copyOf(buffer, bytesRead);
				} else {
					int currentSize = result.length;
					result = Arrays.copyOf(result, currentSize + bytesRead);
					System.arraycopy(buffer, 0, result, currentSize, bytesRead);
				}
			}
        } catch (IOException e) {
        	logger.error("Could not read response from TTS engine", e);
		} finally {
            if (in != null) {
                try {
					in.close();
				} catch (IOException e) {
		        	logger.error("Exception occured while trying to close input stream from TTS engine", e);
				}
            }
        }
        return result;
	}
	
	public void getVoices() {
		ListVoicesRequest allVoicesRequest = new ListVoicesRequest();
        ListVoicesResult allVoicesResult = speechCloud.listVoices(allVoicesRequest);
        System.out.println("All voices: " + allVoicesResult);

        ListVoicesRequest enUsVoicesRequest = new ListVoicesRequest();
        Voice voice = new Voice();
        voice.setLanguage("en-US");
        enUsVoicesRequest.setVoice(voice);
        ListVoicesResult enUsVoiceResult = speechCloud.listVoices(enUsVoicesRequest);
        System.out.println("en-US voices: " + enUsVoiceResult);
        
        ListLexiconsResult listLexiconsResult = speechCloud.listLexicons();
        System.out.println("List of lexicons: " + listLexiconsResult);
	}
}