package feiraShop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StandList {
	
	private List<Stand> standList;
	
	public StandList(){
		standList = new ArrayList<Stand>();
	}

	public List<Stand> getStandList() {
		return standList;
	}

	public void setStandList(List<Stand> standList) {
		this.standList = standList;
	}

	public void add(int index, Stand element) {
		standList.add(index, element);
	}

	public boolean add(Stand e) {
		return standList.add(e);
	}

	public void clear() {
		standList.clear();
	}

	public Stand get(int index) {
		return standList.get(index);
	}

	public Iterator<Stand> iterator() {
		return standList.iterator();
	}

	public Stand remove(int index) {
		return standList.remove(index);
	}

	public boolean remove(Object o) {
		return standList.remove(o);
	}

	public int size() {
		return standList.size();
	}

	public Object[] toArray() {
		return standList.toArray();
	}
	
	
}
