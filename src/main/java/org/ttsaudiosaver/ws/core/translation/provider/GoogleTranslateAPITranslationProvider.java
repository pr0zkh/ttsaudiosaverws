package org.ttsaudiosaver.ws.core.translation.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.ttsaudiosaver.ws.core.translation.common.Language;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service(value = "googleTranslateTranslationProvider")
public class GoogleTranslateAPITranslationProvider implements TranslationProvider {
	
	private static final String PARAM_API_KEY = "key";
	private static final String PARAM_FROM_LANGUAGE = "source";
	private static final String PARAM_TO_LANGUAGE = "target";
	private static final String PARAM_QUERY = "q";
	private static final String HTTP_GET_METHOD_NAME = "GET";
	private static final String PARAM_USER_AGENT = "User-Agent";
	private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36";
	private static final String QUERY_URI = "https://www.googleapis.com/language/translate/v2";
	private static final String API_KEY = "AIzaSyAdMJ2C1dOqyCxDdPOtIOzLUisuMn-t_XA";
	
	private static final Logger logger = Logger.getLogger(GoogleTranslateAPITranslationProvider.class);

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
			con.setRequestProperty(PARAM_USER_AGENT, DEFAULT_USER_AGENT);
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
		} catch (IOException e) {
			logger.error("I/O exception while performing request to Google Translate API", e);
		}
		String result = getTranslaitonFromReponse(translation);
		logger.info("***********************************");
		try {
			logger.info(new String(result.getBytes(), "UTF-8"));
			result = new String(result.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("***********************************");
		return result;
	}
	
	/**
	 * URL example: "https://www.googleapis.com/language/translate/v2?key=[key]&source=[source_lang]&target=[target_lang]&q=[UTF-8_encoded_query]
	 * 
	 * @param query
	 * @param fromLanguage
	 * @param toLanguage
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws InvalidAPIKeyException
	 */
	private String buildURL(String query, Language fromLanguage, Language toLanguage) throws UnsupportedEncodingException {
		String url = null;
//		URIBuilder builder = new URIBuilder();
		try {
			URIBuilder builder = new URIBuilder(QUERY_URI);
			builder.addParameter(PARAM_API_KEY, API_KEY);
			builder.addParameter(PARAM_FROM_LANGUAGE, fromLanguage.getValue());
			builder.addParameter(PARAM_TO_LANGUAGE, toLanguage.getValue());
			builder.addParameter(PARAM_QUERY, query);
			url = builder.toString();
		} catch (URISyntaxException e) {
			logger.error("Error while building Google Translate API query URL.", e);
		}
		return url;
	}
	
	private String getTranslaitonFromReponse(String response) {
		if(response == null) {
			return null;
		}
		JsonElement jelement = new JsonParser().parse(response);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    jobject = jobject.getAsJsonObject("data");
	    JsonArray jarray = jobject.getAsJsonArray("translations");
	    jobject = jarray.get(0).getAsJsonObject();
	    String result = jobject.get("translatedText").toString();
	    return result.replace("\"", "");
	}
}