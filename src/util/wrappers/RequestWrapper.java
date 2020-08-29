package util.wrappers;

import java.util.List;

/** Wrapper class for processing requests with primitive datatypes in payload. 
 *  Intended use: login, search by words, etc. */
public class RequestWrapper {
	public String stringKey;
	public double doubleKey;
	public long longKey;
	public List<String> stringArgs;
}
