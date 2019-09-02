package org.codingnewtalking.toolbox.http.invoker;

import org.codingnewtalking.toolbox.http.HttpParam;
import org.codingnewtalking.toolbox.http.HttpResult;

/**
 * @author lixinjie
 * @since 2017-12-19
 */
public interface IHttpInvoker {

	HttpResult invoke(HttpParam httpParam);
}
