package http;

import java.io.IOException;
import java.io.InputStream;

public class Util {
	public static String readLine(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		int b;
		do {
			b = in.read();			
			if (b == -1) throw new IOException("Invalid input data");
			if (b != '\r' && b != '\n') {
				sb.append((char) b);
			}		
		} while (b != '\n');
		return sb.toString();
	}
	
	public static String readString(InputStream in, int length) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int b = in.read();	
			if (b == -1) throw new IOException("Invalid input data");
			sb.append((char) b);
		}
		return sb.toString();
	}
}