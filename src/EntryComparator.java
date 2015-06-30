
import java.util.Comparator;

public class EntryComparator implements Comparator<MapEntry<String, Long>> {
	@Override
	public int compare(MapEntry<String, Long> o1, MapEntry<String, Long> o2) {
		return Long.compare(o1.getValue(),o2.getValue());
	}
}
