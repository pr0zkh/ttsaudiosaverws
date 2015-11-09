package org.ttsaudiosaver.ws.core.tts.provider;

import org.ttsaudiosaver.ws.core.translation.common.Language;

public interface TTSProvider {

	public byte[] getTTSAudio(String query, Language language);
	
}
