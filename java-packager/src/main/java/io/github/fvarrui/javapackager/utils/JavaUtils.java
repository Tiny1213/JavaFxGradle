package io.github.fvarrui.javapackager.utils;

public class JavaUtils {
	
	public static int getJavaMajorVersion() {
		String [] version = System.getProperty("java.version").split("\\.");
		int major = Integer.parseInt(version[0]);
		if (major >= 2) return major;
		return Integer.parseInt(version[1]);
	}

}
