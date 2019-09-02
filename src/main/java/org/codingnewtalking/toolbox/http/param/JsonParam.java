package org.codingnewtalking.toolbox.http.param;

import org.codingnewtalking.toolbox.http.HttpParam;
import org.codingnewtalking.toolbox.http.RequestBodyType;
import org.codingnewtalking.toolbox.http.ResponseBodyType;

/**
 * @author lixinjie
 * @since 2018-07-19
 */
public class JsonParam extends HttpParam {

	public JsonParam() {
		super();
		setRequestBodyType(RequestBodyType.JSON);
		setResponseBodyType(ResponseBodyType.JSON);
	}
	
	public void setJsonBody(String json) {
		setBody(json);
	}
}
