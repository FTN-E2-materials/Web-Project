package util;

import java.util.HashMap;

import com.sun.javafx.collections.ObservableMapWrapper;

import beans.interfaces.BeanInterface;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import storage.Storage;


/** Currently not in use */
public class ObservableHashMap<T extends BeanInterface> extends HashMap<String, T> implements ObservableMap<String, T>{

	private Storage<T> storage;
	ObservableMapWrapper<String, T> map;
	
	public ObservableHashMap(Storage<T> storage) {
		this.storage = storage;
	}
	
	/** Create a listener which saves the database to a file each time the database (hashmap) changes */
	public void startObservingChanges() {
		this.addListener(new MapChangeListener<String, T>() {

			@Override
			public void onChanged(Change<? extends String, ? extends T> change) {
				storage.writeAll(ObservableHashMap.this);
				System.out.println("Something is happening");
			}
		});
	}
	
	@Override
	public void addListener(InvalidationListener listener) {
		// TODO Something
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Do something
		
	}

	@Override
	public void addListener(MapChangeListener<? super String, ? super T> listener) {
		System.out.println("Listening to map changes");
	}

	@Override
	public void removeListener(MapChangeListener<? super String, ? super T> listener) {
		// TODO Auto-generated method stub
		
	}
}
