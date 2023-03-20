package skipList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** The class that stores flights in a skip list. */
public class FlightList {

	private final int NEG_INFINITY = Integer.MIN_VALUE;
	private final int POS_INFINITY = Integer.MAX_VALUE;
	final String NEGATIVE_INF = "\u0000";
	final String POSITIVE_INF = "\uFFFF";
	private FlightNode head;
	private FlightNode tail;
	private int heightOfSkipList = 0;
	Random random = new Random();

	/**
	 * Constructor.
	 * Reads flights from the file and inserts each flight into the skip list.
	 * @param filename the name of the file
	 */
	public FlightList(String filename) {
		// set head to negative infinity and tail to positive infinity
		head = new FlightNode(new FlightKey(NEGATIVE_INF, NEGATIVE_INF, NEGATIVE_INF, NEGATIVE_INF),
				new FlightData(NEGATIVE_INF, NEG_INFINITY));
		tail = new FlightNode(new FlightKey(POSITIVE_INF, POSITIVE_INF, POSITIVE_INF, POSITIVE_INF),
				new FlightData(POSITIVE_INF, POS_INFINITY));
		head.next = tail;
		tail.prev = head;
		readFile(filename);

	}
	public void readFile(String filename) {
		File file = new File(filename);
		try {
			FileReader freader = new FileReader(file);
			BufferedReader reader = new BufferedReader(freader);
			String line;
			// read file and create key/data then create node
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(" ");
				if (parts.length != 6) {
					throw new IllegalArgumentException();
				}
				FlightKey tempKey = new FlightKey(parts[0], parts[1], parts[2], parts[3]);
				FlightData tempData = new FlightData(parts[4], Double.parseDouble(parts[5]));
				// determine tower height and insert node at each level
				insert(tempKey, tempData);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns true if the node with the given key exists in the skip list,
	 * false otherwise. This method needs to be efficient.
	 * 
	 * @param key flight key
	 * @return true if the key is in the skip list, false otherwise
	 */
	public boolean find(FlightKey key) {
		FlightNode current = head.next;
		while (current != null && current != tail) {
			if (current.getKey().compareTo(key) == 0) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	public FlightNode getPosition(FlightKey key) {
		FlightNode node = head;
		while (node.down != null) {
			node = node.down;
			while (key.compareTo(node.next.getKey()) > 0) {
				node = node.next;
			}
		}
		return node;
	}

	/**
	 * Insert a (key, value) pair to the skip list. Returns true if it was able
	 * to insert it successfully.
	 *
	 * @param key flight key
	 * @param data associated flight data
	 * @return true if insertion was successful
	 */
	public boolean insert(FlightKey key, FlightData data) {
		FlightNode insertion = getPosition(key);
		// already in the skip list means we can't insert
		if (insertion.getKey().compareTo(key) == 0) {
			return false;
		}
		// determine height of tower
		int numTails = 0;// represents the height
		while (random.nextBoolean() == true) {
			numTails++;
			if (numTails >= heightOfSkipList) {
				heightOfSkipList++;
			}
		}
		return true;
	}

	/**
	 * Returns the list of nodes that are successors of a given key. Refer to
	 * the project pdf for a description of the method.
	 * 
	 * @param key flight key
	 * @return successors of the given key
	 */
	public List<FlightNode> successors(FlightKey key) {
		List<FlightNode> arr = new ArrayList<>();
		// FILL IN CODE

		return arr;
	}

	/**
	 * Returns the list of nodes that are predecessors of a given key
	 * (that corresponds to flights that are earlier than the given flight).
	 *  Refer to the project pdf for a detailed description of the method.
	 * 
	 * @param key flight key
	 * @return predecessors of the given key
	 */
	public List<FlightNode> predecessors(FlightKey key, int timeFrame) {
		List<FlightNode> arr = new ArrayList<>();

		// FILL IN CODE
		return arr;

	}

	/**
	 * Returns the string representing the SkipList (contains the elements on all levels starting at the
	 * top. Each level should be on a separate line, for instance:
	 * (SFO, PVD, 03/14, 09:15)
	 * (SFO, JFK, 03/15, 06:30), (SFO, PVD, 03/14, 09:15)
	 * (SFO, JFK, 03/15, 06:30),   (SFO, JFK, 03/15, 7:15), (SFO, JFK, 03/20, 5:00), (SFO, PVD, 03/14, 09:15)
	 */
	public String toString() {
		// FILL IN CODE

		return ""; // don't forget to change it
	}

	/**
	 * Outputs the SkipList to a file
	 * (prints the elements on all levels starting at the top.
	 * Each level should be printed on a separate line.
	 * @param filename the name of the file
	 */
	public void print(String filename) {
		// FILL IN CODE

	}

	/**
	 * Returns a list of nodes that have the same origin and destination cities
	 * and the same date as the key, with departure times within the given time
	 * frame of the departure time of the key.
	 *
	 * @param key flight key
	 * @param timeFrame interval of time
	 * @return list of flight nodes that have the same origin, destination and date
	 * as the key, and whose departure time is within a given timeframe
	 */
	public List<FlightNode> findFlights(FlightKey key, int timeFrame) {
		List<FlightNode> resFlights = new ArrayList<>();
		// FILL IN CODE

		return resFlights;
	}

}
