package http;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Response {
	private String responseLine;
	private Map<String, String> headers;
	private Map<String, Cookie> cookies;
	private ByteArrayOutputStream byteArrayOutput;
	private OutputStream output;

	public Response(OutputStream output) {
		this.responseLine = "HTTP/1.1 200 OK";
		this.headers = new HashMap<>();
		this.cookies = new HashMap<>();
		this.byteArrayOutput = new ByteArrayOutputStream();
		this.output = output;		

		headers.put("Content-Type", "text/html");
		headers.put("Connection"  , "close"    );
	}

	public void setCookie(String k, String v) {
		Cookie c = new Cookie(k, v);
		cookies.put(k, c);
	}

	public Map<String, Cookie> getCookies() {
		return cookies;
	}

	public void setResponseLine(String code, String description) {
		responseLine = "HTTP/1.1 " + code + " " + description;
	}

	public void setHeader(String key, String value) {
		headers.put(key, value);
	}

	public OutputStream getOutputStream() {
		//return output;
		return byteArrayOutput;
	}

	public PrintStream getPrintStream() {
		//return new PrintStream(output);
		return new PrintStream(byteArrayOutput);
	}

	void flush() throws Exception {
		headers.put(
			"Content-Length", 
			String.valueOf(byteArrayOutput.size())
		);

		PrintStream outputPS = new PrintStream(output);
		outputPS.println(responseLine);
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			outputPS.println(
				entry.getKey() + 
				": " + 
				entry.getValue()
			);
		}
		for (Cookie c : cookies.values()) {
			outputPS.println(c);
		}
		outputPS.println();
		outputPS.flush();

		output.write(byteArrayOutput.toByteArray());
		output.flush();		
	}
}









