package org.ttsaudiosaver.ws.core.file.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileManager {
	
	public static byte[] mp3ToByteArray(File source) throws IOException {
		return Files.readAllBytes(source.toPath());
	}
}
