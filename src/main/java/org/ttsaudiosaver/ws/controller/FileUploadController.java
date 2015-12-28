package org.ttsaudiosaver.ws.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FileUploadController {
	
	private static final Logger logger = Logger.getLogger(FileUploadController.class);
	private static final String OUTPUT_FOLDER_PATH = "/output/";
	
	@Autowired
    ServletContext context; 
	
	@RequestMapping(value = "/output/{path}", method = RequestMethod.GET)
	public void getFile(@PathVariable("path") String filePath, HttpServletRequest req, HttpServletResponse res) {
		res.addHeader("Access-Control-Allow-Origin", "*");
		try {
			try {
				byte[] bytes = Files.readAllBytes(Paths.get(context.getRealPath(OUTPUT_FOLDER_PATH + filePath + ".mp3")));
				res.setContentType("audio/mpeg");
				res.addHeader("Accept-Ranges", "bytes");
				res.addHeader("Access-Control-Allow-Headers", "range, accept-encoding");
				res.addHeader("Content-Length", String.valueOf(bytes.length));
				res.addHeader("Content-Range", String.format("bytes %d-%d/%d", 0, bytes.length - 1, bytes.length));
				res.getOutputStream().write(bytes);
			} catch (IOException e) {
				res.setStatus(500);
			}
			res.flushBuffer();
		} catch (IOException e) {
			logger.error("Error while closing response output stream", e);
		}
	}
}