package com.alientation.chess.stats;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.alientation.chess.game.init.Game;

public class Logger {
	
	public final static String PATH = Game.BASEPATH + "\\src\\com\\alientation\\chess\\stats\\logs";
	
	public final static int OBJECT_CREATION = 01;
	public final static int METHOD_CALL = 02;
	public final static int GRAPHICS_CALL = 03;
	public final static int LIGHT_ERROR = 04;
	public final static int HEAVY_ERROR = 05;
	public final static int TEST_CASE = 98;
	public final static int MISCELLANEOUS = 99;
	
	
	public static File dataFile;
	
	public Logger() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		
		File dataFile = new File(PATH + "\\" + dtf.format(LocalDateTime.now()));
		try {
			if (dataFile.createNewFile()) {
				Logger.dataFile = dataFile;
			} else {
				//File exists already
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean createLog(String title, String description, String codeLocation, int type) {
		
		
		return false;
	}
}
