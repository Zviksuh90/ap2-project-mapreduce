
import java.util.Map.Entry;

class MapEntry<String, Long> implements Entry<String, Long> {

	private final String key;
	private Long val;

	public MapEntry(final String k, final Long v) {
		super();
		if (k == null)
			throw new NullPointerException(); // key shall never be null
		this.key = k;
		this.val = v;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public Long getValue() {
		return this.val;
	}

	@Override
	public Long setValue(final Long value) {
		final Long r = this.val;
		this.val = value;
		return r;
	}

	@Override
	public int hashCode() {
		int r = 31;
		r *= this.key == null ? 5 : this.key.hashCode();
		r *= this.val == null ? 7 : this.val.hashCode();
		return r;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof MapEntry))
			return false;
		final MapEntry<?, ?> other = (MapEntry<?, ?>) obj;
		// key is ensured to not be null on the constructor
		// if (this.key == null && other.key != null)
		// return false;
		if (this.val == null && other.val != null)
			return false;
		return this.key.equals(other.key) && this.val.equals(other.val);
	}

}
