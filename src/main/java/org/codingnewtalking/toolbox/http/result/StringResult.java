package org.codingnewtalking.toolbox.http.result;

import org.codingnewtalking.toolbox.http.HttpResult;

/**
 * @author lixinjie
 * @since 2018-07-19
 */
public class StringResult extends HttpResult {

	public String getStringBody() {
		return getStringBody(getBody());
	}
}
