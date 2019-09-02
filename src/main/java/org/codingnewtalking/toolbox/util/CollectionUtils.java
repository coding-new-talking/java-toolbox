package org.codingnewtalking.toolbox.util;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * @author lixinjie
 * @since 2019-08-31
 */
public class CollectionUtils {

	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(Collection<T> collection, Class<T> componentType) {
		return collection.toArray((T[])Array.newInstance(componentType, collection.size()));
	}
}
