package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import beans.interfaces.BeanInterface;

public class DataConverter {

	public static <T extends BeanInterface> HashMap<String, T> listToMap(List<T> list) {
		HashMap<String, T> map = new HashMap<String, T>();
		if (list == null)
			return map;
		
		for (T element : list) {
			map.put(element.getKey(), element);
		}
		
		return map;
	}
}
