package org.codingnewtalking.toolbox.http.result;

import java.io.InputStream;

import org.codingnewtalking.toolbox.http.HttpResult;

/**
 * @author lixinjie
 * @since 2018-07-19
 */
public class StreamResult extends HttpResult {

	public InputStream getStreamBody() {
		return getStreamBody(getBody());
	}
}
