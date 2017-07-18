package http;

public class Cookie {
	private String key;
	private String value;

	public Cookie(String k, String v) {
		key = k;
		value = v;
	}

	public void setValue(String v) {
		value = v;
	}

	public String getValue() {
		return value;
	}

	public String toString() {
		return 
			"Set-Cookie: " +
			key +
			"=" +
			(value != null 
				? value 
				: "deleted; expires=Thu, 01 Jan 1970 00:00:00 GMT");
	}
}




















