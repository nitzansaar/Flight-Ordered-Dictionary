package skipList;

import junit.framework.TestCase;

import java.io.*;
import java.util.Random;


public class FlightListTest extends TestCase {

    public void testReadFile() {
        File file = new File("flights");
        try {
            FileReader freader = new FileReader(file);
            BufferedReader reader = new BufferedReader(freader);
            String line;
            // read file and create key/data then create node
            while ((line = reader.readLine()) != null) {
                String [] parts = line.split(" ");
                if (parts.length != 6) {
                    throw new IllegalArgumentException();
                }
                FlightKey tempKey = new FlightKey(parts[0], parts[1], parts[2], parts[3]);
                FlightData tempData = new FlightData(parts[4], Double.parseDouble(parts[5]));
                // determine tower height and insert node at each level
                System.out.println(tempKey.toString() + " " + tempData.toString());

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void testGetRandom() {
        Random random = new Random();
        int count = 0;
        int flip;

        while (true) {
            flip = random.nextInt(2);
            if (flip == 1) {
                count++;
            } else {
                break;
            }
        }

        System.out.println(count);

    }
}