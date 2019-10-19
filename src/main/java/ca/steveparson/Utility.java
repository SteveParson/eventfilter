package ca.steveparson;

import java.io.File;

public class Utility {
	public static String getFileExtension(String f) {
		int i = f.lastIndexOf(".");
		String ext = "";
		if (i > 0) {
			return f.substring(i+1);
		}
		return "";
	}
	
	public static boolean doesFileExist(String f) {
		File fn = new File(f);
		if(fn.exists() && fn.isFile()) { return true; }
		System.out.printf("File '%s' does not exist\n", f);
		return false;
	}
}
