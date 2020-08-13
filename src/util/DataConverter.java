package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.interfaces.BeanInterface;

public class DataConverter {

	/** Convert the given List to a HashMap. Objects of the list must implement BeanInterface. */
	public static <T extends BeanInterface> Map<String, T> listToMap(List<T> list) {
		Map<String, T> map = new HashMap<String, T>();
		if (list == null)
			return map;
		
		for (T element : list) {
			map.put(element.getKey(), element);
		}
		
		return map;
	}
	
	/** Populate the given map with values from the list. */
	public static <T extends BeanInterface> Map<String, T> listToMap(List<T> list, Map<String, T> map) {
		if (list == null)
			return map;
		
		for (T element : list) {
			map.put(element.getKey(), element);
		}
		
		return map;
	}
}
