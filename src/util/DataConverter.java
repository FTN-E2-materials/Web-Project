package util;

import java.util.ArrayList;
import java.util.HashMap;

import beans.interfaces.BeanInterface;

public class DataConverter {

	public static <T extends BeanInterface> HashMap<String, T> listToMap(ArrayList<T> list) {
		HashMap<String, T> map = new HashMap<String, T>();
		for (T element : list) {
			map.put(element.getKey(), element);
		}
		
		return map;
	}
}
