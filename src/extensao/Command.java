package extensao;

import http.*;

public interface Command {
	void execute(Request req, Response resp) throws Exception;
}