package org.ttsaudiosaver.ws.core.tts.provider;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.ttsaudiosaver.ws.core.translation.common.Language;

@Service
public class GoogleTTSProvider implements TTSProvider {
	
	private static final String QUERY_URI = "https://translate.google.com/translate_tts";
	private static final String PARAM_QUERY = "q";
	private static final String PARAM_ENCODING = "ie";
	private static final String PARAM_LANGUAGE = "tl";
	private static final String PARAM_TOTAL = "total";
	private static final String PARAM_IDX = "idx";
	private static final String PARAM_TEXTLEN = "textlen";
	private static final String PARAM_TOKEN = "tk";
	private static final String PARAM_CLIENT = "client";
	private static final String DEFAULT_TOKEN = "378142.254180";
	private static final String PARAM_USER_AGENT = "User-Agent";
	private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36";
	private static final Integer BUFFER_SIZE = 8 * 1024;
	
	private static final Logger logger = Logger.getLogger(GoogleTTSProvider.class);
	
	private String token;
	
	private String userAgent;

	public byte[] getTTSAudio(String query, Language language) {
		byte[] result = null;
		InputStream is = null;
		try {
			try {
				URL url = new URL(buildURL(query, language));
				URLConnection conn = url.openConnection();
				conn.addRequestProperty(PARAM_USER_AGENT, StringUtils.defaultString(getUserAgent(), DEFAULT_USER_AGENT));
				is = conn.getInputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead;
				while((bytesRead = is.read(buffer)) != -1) {
					if(result == null) {
						result = Arrays.copyOf(buffer, bytesRead);
					} else {
						int currentSize = result.length;
						result = Arrays.copyOf(result, currentSize + bytesRead);
						System.arraycopy(buffer, 0, result, currentSize, bytesRead);
					}
				}
			} catch (MalformedURLException e) {
				logger.error("Google TTS API query URL is corrupted.", e);
			} finally {
				if(is != null) {
					is.close();
				}
			}
		} catch (IOException e) {
			logger.error("I/O exception while processing Google TTS input stream.", e);
		}
		return result;
	}
	
	private String buildURL(String query, Language language) {
		String result = null;
		try {
			URIBuilder builder = new URIBuilder(QUERY_URI);
			builder.addParameter(PARAM_QUERY, query);
			builder.addParameter(PARAM_ENCODING, CharEncoding.UTF_8);
			builder.addParameter(PARAM_LANGUAGE, language.getValue());
			builder.addParameter(PARAM_TOTAL, "1"); //total number of chunks - in our case, it won't be changed as we're going to process only one word per request
			builder.addParameter(PARAM_IDX, "0"); //id of a chunk - in our case we have only one chunk with id 0
			builder.addParameter(PARAM_TEXTLEN, String.valueOf(query.length()));
			builder.addParameter(PARAM_TOKEN, StringUtils.defaultString(getToken(), DEFAULT_TOKEN));
			builder.addParameter(PARAM_CLIENT, "t"); //client of the Google TTS service (supposedly stands for Translate)
			result = builder.toString();
		} catch (URISyntaxException e) {
			logger.error("Error while building Google TTS API query URL.", e);
		} 
		return result;
	}


	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getUserAgent() {
		return userAgent;
	}
	
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}
