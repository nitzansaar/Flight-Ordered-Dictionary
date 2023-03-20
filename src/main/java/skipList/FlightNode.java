package skipList;

/**
 * The class that represents a node in a flight skip list. 
 * Contains the key of type FlightKey and the data of type FlightData. 
 * Also stores the following pointers to FlightNode(s): next, down, prev and up.
 */
public class FlightNode {
	private FlightKey key;
	private FlightData data;
	// FILL IN CODE: add other variables: next, prev, down, up
	FlightNode next;
	FlightNode prev;
	FlightNode down;
	FlightNode up;


	/**
     * FlightNode Constructor
	 * @param key flight key
	 * @param data fight data
	 */
	public FlightNode(FlightKey key, FlightData data) {
		this.key = key;
		this.data = data;
		this.next = null;
		this.prev = null;
		this.down = null;
		this.up = null;

	}

	/**
     * A getter for the key
	 * @return key
	 */
	public FlightKey getKey() {
		return key;
	}

}
