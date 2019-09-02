package org.codingnewtalking.toolbox.http.param;

import org.codingnewtalking.toolbox.http.HttpParam;
import org.codingnewtalking.toolbox.http.RequestBodyType;
import org.codingnewtalking.toolbox.http.ResponseBodyType;

/**
 * @author lixinjie
 * @since 2018-07-19
 */
public class XmlParam extends HttpParam {

	public XmlParam() {
		super();
		setRequestBodyType(RequestBodyType.XML);
		setResponseBodyType(ResponseBodyType.XML);
	}
	
	public void setXmlBody(String xml) {
		setBody(xml);
	}
}
