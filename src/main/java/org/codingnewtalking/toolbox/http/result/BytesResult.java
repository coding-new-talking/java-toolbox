package org.codingnewtalking.toolbox.http.result;

import org.codingnewtalking.toolbox.http.HttpResult;

/**
 * @author lixinjie
 * @since 2018-07-19
 */
public class BytesResult extends HttpResult {

	public byte[] getBytesBody() {
		return getBytesBody(getBody());
	}
}
