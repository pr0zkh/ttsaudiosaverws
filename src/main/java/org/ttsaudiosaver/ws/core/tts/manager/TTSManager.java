package org.ttsaudiosaver.ws.core.tts.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.ttsaudiosaver.ws.core.file.utils.FileManager;
import org.ttsaudiosaver.ws.core.translation.common.Language;
import org.ttsaudiosaver.ws.core.tts.provider.TTSProvider;

@Service
public class TTSManager {
	
	private static final String OUTPUT_FOLDER_PATH = "/output/";
	private static final String ONE_SEC_GAP_FILE_PATH = "1sec.mp3";
	private static final String ONE_AND_A_HALF_SEC_GAP_FILE_PATH = "1point5sec.mp3";
	
	private static final Logger logger = Logger.getLogger(TTSManager.class);
	
	@Autowired
	@Qualifier("IvonaTTSProvider")
	private TTSProvider provider;
	
	@Autowired
    ServletContext context; 
	
	public void saveTTSToFile(String filePath, String query, Language language) {
		OutputStream out = null;
		try {
			try {		
				File f = new File(filePath);
				out = new FileOutputStream(f);
				out.write(provider.getTTSAudio(query, language));
			} catch (FileNotFoundException e) {
				logger.error("Couldn't save TTS to the file with path " + filePath + ".", e);
			} finally {
				if(out != null) {
					out.close();
				}
			}
		}catch (IOException e) {
			logger.error("I/O exception while processing TTS byte array to file output stream.", e);
		}
	}
	
	public void saveTTSToFile(Map<String, String> queries, Language from, Language to) {
		OutputStream out = null;
		try {
			String filePath = context.getRealPath(OUTPUT_FOLDER_PATH + UUID.randomUUID() + ".mp3");
			try {		
				byte[] oneSecGap = FileManager.mp3ToByteArray(ONE_SEC_GAP_FILE_PATH);
				byte[] oneAndAHalfSecGap = FileManager.mp3ToByteArray(ONE_AND_A_HALF_SEC_GAP_FILE_PATH);
				File f = new File(filePath);
				out = new FileOutputStream(f, true);
				out.write(oneSecGap);
				for(Map.Entry<String, String> translationPair : queries.entrySet()) {
					out.write(provider.getTTSAudio(translationPair.getKey(), from));
					out.write(oneSecGap);
					out.write(provider.getTTSAudio(translationPair.getValue(), to));
					out.write(oneAndAHalfSecGap);
				}
			} catch (FileNotFoundException e) {
				logger.error("Couldn't save TTS to the file with path " + filePath + ".", e);
			} catch (URISyntaxException e) {
				logger.error("Couldn't read file", e);
			} finally {
				if(out != null) {
					out.close();
				}
			}
		}catch (IOException e) {
			logger.error("I/O exception while processing TTS byte array to file output stream.", e);
		}
	}

	public TTSProvider getProvider() {
		return provider;
	}

	public void setProvider(TTSProvider provider) {
		this.provider = provider;
	}
}