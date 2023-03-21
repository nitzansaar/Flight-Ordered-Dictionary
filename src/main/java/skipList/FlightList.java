package skipList;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The class that stores flights in a skip list.
 */
public class FlightList {
    final String NEGATIVE_INF = "\u0000";
    final String POSITIVE_INF = "\uFFFF";
    private FlightNode head;
    private FlightNode tail;
    private int skipHeight = 0;
    Random random = new Random();

    /**
     * Constructor.
     * Reads flights from the file and inserts each flight into the skip list.
     *
     * @param filename the name of the file
     */
    public FlightList(String filename) {
        initializeHeadAndTail();
        readFile(filename);
    }

    // initialize head and tail with negative and positive infinity
    private void initializeHeadAndTail() {
        // start with dummy list on top
        head = new FlightNode(new FlightKey(NEGATIVE_INF, NEGATIVE_INF, NEGATIVE_INF, NEGATIVE_INF), null);
        tail = new FlightNode(new FlightKey(POSITIVE_INF, POSITIVE_INF, POSITIVE_INF, POSITIVE_INF), null);
        head.next = tail;
        tail.prev = head;
    }

    public void readFile(String filename) {
        File file = new File(filename);
        try {
            FileReader freader = new FileReader(file);
            BufferedReader reader = new BufferedReader(freader);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                FlightKey tempKey = new FlightKey(parts[0], parts[1], parts[2], parts[3]);
                FlightData tempData = new FlightData(parts[4], Double.parseDouble(parts[5]));
                insert(tempKey, tempData);
            }
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
        FlightNode current = head;
        while (current.down != null) { // assuming there is a dummy list on top level
            current = current.down;
            while (key.compareTo(current.next.getKey()) >= 0) { // if key is greater
//                System.out.println(current.getKey());
                current = current.next;
            }
        }
//        System.out.println("Found: " + key.toString());
        return current.getKey().compareTo(key) == 0;
    }

    public FlightNode search(FlightKey key) {
        FlightNode node = head;
        while (node.down != null) {
            node = node.down;
            while (key.compareTo(node.next.getKey()) >= 0) { // stop when key is smaller than current
                node = node.next;
            }
        }
        return node; // either the same node or the one right before
    }

    public void addNewLevel() {
        // create new head and tail
        FlightNode newHead = new FlightNode(new FlightKey(NEGATIVE_INF, NEGATIVE_INF, NEGATIVE_INF, NEGATIVE_INF),
                null);
        FlightNode newTail = new FlightNode(new FlightKey(POSITIVE_INF, POSITIVE_INF, POSITIVE_INF, POSITIVE_INF),
                null);
        // make sure everything is referenced correctly
        newHead.down = head;
        newTail.down = tail;
        newHead.next = newTail;
        newTail.prev = newHead;
        head.up = newHead;
        tail.up = newTail;
        head = newHead;
        tail = newTail;
        // now head is pointing to the top left node
    }

    /**
     * Insert a (key, value) pair to the skip list. Returns true if it was able
     * to insert it successfully.
     *
     * @param key  flight key
     * @param data associated flight data
     * @return true if insertion was successful
     */
    public boolean insert(FlightKey key, FlightData data) {
        // get position right before where node should be inserted
        FlightNode position = search(key);
        FlightNode insertPosition;

        // if key already exists, insertion is unsuccessful
        if (position.getKey().compareTo(key) == 0) {
            return false;
        }
        int towerHeight = 0;
        // tower will have height of at least 1
        do {
            towerHeight++;
            // if towerHeight exceeds current skipHeight we need to increase skipHeight and add new level
            if (towerHeight > skipHeight) {
                skipHeight++;
                addNewLevel();
            }
            // store the insertion point at current level
            insertPosition = position;
            // move pos until there is a node above
            while (position.up == null) {
                position = position.prev;
            }
            // move position to next level
            position = position.up;
            // create new node with the given parameters
            FlightNode newNode = new FlightNode(key, data);
            // insert newNode after insertPosition
            newNode.next = insertPosition.next;
            newNode.prev = insertPosition;
            insertPosition.next.prev = newNode;
            insertPosition.next = newNode;
            // check lower levels
            if (position.down != null) {
                FlightNode nodeBeforeNew = position.down.down;
                while (nodeBeforeNew != null && nodeBeforeNew.next.getKey().compareTo(key) < 0) {
                    nodeBeforeNew = nodeBeforeNew.next;
                }
                if (nodeBeforeNew != null && nodeBeforeNew.next.getKey().compareTo(key) == 0) {
                    newNode.down = nodeBeforeNew.next;
                    nodeBeforeNew.next.up = newNode;
                }
            }
        } while (random.nextBoolean()); // coin flip

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
        List<FlightNode> arrayList = new ArrayList<>();
        FlightNode current = search(key);
        while (current.next != null && sameKey(current.next.getKey(), key)) {
            arrayList.add(current.next);
            current = current.next;
        }
        return arrayList;
    }

    private boolean sameKey(FlightKey key1, FlightKey key2) {
        return key1.getOrigin().compareTo(key2.getOrigin()) == 0 &&
                key1.getDest().compareTo(key2.getDest()) == 0 &&
                key1.getDate().compareTo(key2.getDate()) == 0;
    }

    /**
     * Returns the list of nodes that are predecessors of a given key
     * (that corresponds to flights that are earlier than the given flight).
     * Refer to the project pdf for a detailed description of the method.
     *
     * @param key flight key
     * @return predecessors of the given key
     */
    public List<FlightNode> predecessors(FlightKey key, int timeFrame) {
        int low = key.getHour() - timeFrame;
        List<FlightNode> arr = new ArrayList<>();
        FlightNode current = search(key);
        if (current.getKey().compareTime(key) < 0) {
            arr.add(current);
        }
        while (current.prev != null && sameKey(current.prev.getKey(), key) && (current.getKey().getHour() >= low)) {
            arr.add(current.prev);
            current = current.prev;
        }

        Collections.reverse(arr);
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
        StringBuilder sb = new StringBuilder();

        FlightNode startNode = head;

        FlightNode topLevelNode = startNode;
        int level = skipHeight;

        while (topLevelNode != null) {
            sb.append("\nLevel: " + level + "\n");
            while (startNode != null) {
                sb.append(startNode.getKey());
                if (startNode.next != null) {
                    sb.append(" - ");
                }

                startNode = startNode.next;
            }

            topLevelNode = topLevelNode.down;
            startNode = topLevelNode;
            level--;
        }
        return sb.toString();
    }

    /**
     * Outputs the SkipList to a file
     * (prints the elements on all levels starting at the top.
     * Each level should be printed on a separate line.
     *
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
     * @param key       flight key
     * @param timeFrame interval of time
     * @return list of flight nodes that have the same origin, destination and date
     * as the key, and whose departure time is within a given timeframe
     */
    public List<FlightNode> findFlights(FlightKey key, int timeFrame) {
        int low = key.getHour() - timeFrame;
        int high = key.getHour() + timeFrame;
        List<FlightNode> resFlights = new ArrayList<>();
        FlightNode current = search(key);
        while (current != null && sameKey(current.getKey(), key) && (current.getKey().getHour() >= low)) {
            resFlights.add(current);
            current = current.prev;
        }
        Collections.reverse(resFlights);

        List<FlightNode> successors = successors(key);

        for (FlightNode successor : successors) {
            if (successor.getKey().getHour() <= high) {
                resFlights.add(successor);
            }
        }
        return resFlights;
    }

}
