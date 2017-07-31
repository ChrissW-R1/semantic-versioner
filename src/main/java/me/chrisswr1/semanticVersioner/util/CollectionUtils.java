package me.chrisswr1.semanticVersioner.util;

import java.util.List;

public class CollectionUtils {
	public static <T> boolean identical(List<T> list1, List<T> list2) {
		if (list1.size() != list2.size()) {
			return false;
		}
		
		int size = list1.size();
		for (int i = 0; i < size; i++) {
			if (!(list1.get(i).equals(list2.get(i)))) {
				return false;
			}
		}
		
		return true;
	}
	
	public static String join(String delimiter, Iterable<?> collection) {
		StringBuilder builder = new StringBuilder();
		
		for (Object element : collection) {
			builder.append(element);
			builder.append(delimiter);
		}
		
		builder.setLength(Math.max(builder.length() - delimiter.length(), 0));
		
		return builder.toString();
	}
}
