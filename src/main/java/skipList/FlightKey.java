package skipList;

/**
 * Represents the key in the FlightNode. Stores origin, destination, date and
 * time. Implements Comparable<FlightKey>.
 */
public class FlightKey implements Comparable<FlightKey> {
	// Each key is a tuple: origin, destination, date, time
	private String origin;
	private String dest;
	private String date;
	private String time;


	/**
     *  FlightKey constructor
	 * @param or origin
	 * @param dest destination
	 * @param date date
	 * @param time time
	 */
    public FlightKey(String or, String dest, String date, String time) {
		this.origin = or;
		this.dest = dest;
		this.date = date;
		this.time = time;
	}

	public int compareTime(FlightKey other) {
		return this.time.compareTo(other.time);
	}

	public int getHour() {
		String[] timeParts = this.time.split(":");
		return Integer.parseInt(timeParts[0]);
	}

	/**
     * Compares a given flight key with the one given as a parameter.
	 * @param other
     * @return -1, if this key is < other, > -1 if the opposite, and 0 if equal.  </>
	 */
	public int compareTo(FlightKey other) {
		int originCompare = this.origin.compareTo(other.origin);
		if (originCompare != 0) {
			return originCompare;
		}
		int destCompare = this.dest.compareTo(other.dest);
		if (destCompare != 0) {
			return destCompare;
		}
		int dateCompare = this.date.compareTo(other.date);
		if (dateCompare != 0) {
			return dateCompare;
		}
		return this.time.compareTo(other.time);
	}

	/**
     * Returns a string representation of the key
	 * @return String
	 */
	public String toString() {
		// FILL IN CODE
		return "(" + origin + ", " + dest + ", " + date  + ", " + time + ")";
	}

	/**
	 * getOrigin
	 * @return origin as a String
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * getDest
	 * @return destination as a String
	 */
	public String getDest() {
		return dest;
	}

	/**
	 * getDate
	 * @return date as a String (format:  "01/03/2019")
	 */
	public String getDate() {
		return date;
	}

	/**
	 * getTime
	 * @return time as a String (format: "16:00")
	 */
	public String getTime() {
		return time;
	}
}
