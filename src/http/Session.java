package http;

import java.util.HashMap;
import java.util.Map;

public class Session {
	private static final long TIMEOUT = 5 * 60 * 1000;

	private static Map<String, Session> sessions = new HashMap<>();

	public static void put(String k, Session s) {
		sessions.put(k, s);
	}

	public static Session get(String k) {
		return sessions.get(k);
	}

	public static void touch(String k) {
		Session s = get(k);
		if (s != null) s.touch();
	}

	private String id;	
	private long time;
	private Map<String, Object> attributes;
	private Response response;

	public Session(Response response) {
		time = System.currentTimeMillis();
		id = String.valueOf(time);
		attributes = new HashMap<>();
		this.response = response;

		Session.put(id, this);
		response.setCookie("MyServerSSID", id);
	}

	public void touch() {
		if (!expired()) {
			time = System.currentTimeMillis();
		} else {
			invalidate();
		}
	}

	public String getID() {
		return id;
	}

	public void setAttribute(String k, Object v) {
		attributes.put(k, v);
	}

	public Object getAttribute(String k) {
		return attributes.get(k);
	}

	public boolean expired() {
		return System.currentTimeMillis() - time > TIMEOUT;
	}

	public void invalidate() {
		attributes.clear();
		sessions.remove(id);
		response.setCookie("MyServerSSID", null);
	}
}