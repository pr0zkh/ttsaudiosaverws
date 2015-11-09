package org.ttsaudiosaver.ws.core.translation.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ttsaudiosaver.ws.core.translation.common.Language;



public class GoogleTranslateAPITranslationProvider implements TranslationProvider {
	
	private static final String PARAM_API_KEY = "key";
	private static final String PARAM_FROM_LANGUAGE = "source";
	private static final String PARAM_TO_LANGUAGE = "target";
	private static final String PARAM_QUERY = "q";
	private static final String HTTP_GET_METHOD_NAME = "GET";
	private static final String PARAM_USER_AGENT = "User-Agent";
	private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36";
	private static final String QUERY_URI = "https://www.googleapis.com/language/translate/v2";
	
	private static final Logger logger = LogManager.getLogger(GoogleTranslateAPITranslationProvider.class);
	
	private String googleTranslateAPIKey;
	
	private String userAgent;

	public String translate(String query, Language fromLanguage, Language toLanguage) {
		String translation = null;
		if(StringUtils.isBlank(query) || fromLanguage == null || toLanguage == null) {
			String errorMsg = "Some of required params are null or empty: query = " + query + ", fromLanguage = " + fromLanguage + ", toLanguage = " + toLanguage + ".";
			logger.error(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
		try {
			URL url = new URL(buildURL(query, fromLanguage, toLanguage));
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod(HTTP_GET_METHOD_NAME);
			con.setRequestProperty(PARAM_USER_AGENT, StringUtils.defaultString(getUserAgent(), DEFAULT_USER_AGENT));
			//TODO create DTO/bean for translation and include response code as one of the fields
			//int responseCode = con.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer response = new StringBuffer();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			translation = response.toString();
			
		} catch (MalformedURLException e) {
			logger.error("Google Translate API query URL is corrupted.", e);
		} catch (InvalidAPIKeyException e) {
			logger.error("Couldn't perform request to Google Translate API - Google Translate API key is null or empty.");
		} catch (IOException e) {
			logger.error("I/O exception while performing request to Google Translate API", e);
		}
		return translation;
	}
	
	/**
	 * URL example: "https://www.googleapis.com/language/translate/v2?key=[key]&source=[source_lang]&target=[target_lang]&q=[UTF-8_encoded_query]
	 * 
	 * @param query
	 * @param fromLanguage
	 * @param toLanguage
	 * @return
	 * @throws InvalidAPIKeyException
	 */
	private String buildURL(String query, Language fromLanguage, Language toLanguage) throws InvalidAPIKeyException {
		if(StringUtils.isBlank(getGoogleTranslateAPIKey())) {
			throw new InvalidAPIKeyException("Google Translate API key is null or empty");
		} 
		String url = null;
//		URIBuilder builder = new URIBuilder();
		try {
			URIBuilder builder = new URIBuilder(QUERY_URI);
			builder.addParameter(PARAM_API_KEY, getGoogleTranslateAPIKey());
			builder.addParameter(PARAM_FROM_LANGUAGE, fromLanguage.getValue());
			builder.addParameter(PARAM_TO_LANGUAGE, toLanguage.getValue());
			builder.addParameter(PARAM_QUERY, query);
			url = builder.toString();
		} catch (URISyntaxException e) {
			logger.error("Error while building Google Translate API query URL.", e);
		}
		return url;
	}
	
	private class InvalidAPIKeyException extends Exception {

		private static final long serialVersionUID = -1393526897414108335L;
		
		InvalidAPIKeyException(String message) {
			super(message);
		}
		
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getGoogleTranslateAPIKey() {
		return googleTranslateAPIKey;
	}

	public void setGoogleTranslateAPIKey(String googleTranslateAPIKey) {
		this.googleTranslateAPIKey = googleTranslateAPIKey;
	}
	
}
