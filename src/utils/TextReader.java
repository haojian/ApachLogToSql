package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextReader {
	public static String readFile(String _url) throws Exception {
		StringBuffer buf = new StringBuffer();
		String s = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(_url));
			while ((s = reader.readLine()) != null) {
				buf.append(s + "\n");
			}
			reader.close();

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}

		return buf.toString();
	}

}
