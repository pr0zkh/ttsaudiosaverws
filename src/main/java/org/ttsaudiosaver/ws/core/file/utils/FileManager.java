package org.ttsaudiosaver.ws.core.file.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {
	
	public static byte[] mp3ToByteArray(String source) throws IOException, URISyntaxException {
		return Files.readAllBytes(
                Paths.get(FileManager.class.getClassLoader()
                        .getResource(source)
                        .toURI()));
	}
}
