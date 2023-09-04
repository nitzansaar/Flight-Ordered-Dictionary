# Flight Ordered Dictionary Project

This project implements a skip list data structure to store and manage flight information. It includes classes for representing flight data, flight keys, and flight nodes, as well as a class to manage the skip list itself.

## FlightData Class
The `FlightData` class represents the data associated with a flight. It includes the flight number and the price of the flight.

## FlightKey Class
The `FlightKey` class represents the key used for identifying flights. It includes information such as origin, destination, date, and time. The class implements the `Comparable` interface to enable comparison between flight keys.

## FlightNode Class
The `FlightNode` class represents a node in the skip list. It contains references to the flight key, flight data, and various pointers to other nodes: next, previous, down, and up. This class is used to build the skip list structure.

## FlightList Class
The `FlightList` class manages the skip list of flights. It provides methods for inserting flights, finding flights, retrieving successors and predecessors of flight keys, and printing the skip list. It also reads flight data from a file to populate the skip list upon initialization.

## What I Learned
Throughout this project, I learned several important concepts related to data structures and algorithms, including:

- **Skip List**: I learned how a skip list is a probabilistic data structure that allows for efficient searching, insertion, and deletion of elements. It consists of multiple levels of linked lists, where elements are inserted into multiple levels to provide quicker access.

- **Comparing and Ordering**: The importance of implementing the `Comparable` interface for custom classes to enable sorting and comparison. This is crucial for maintaining the order of elements in the skip list.

- **Efficient Searching**: I learned how to perform efficient searching in a skip list by navigating through different levels and narrowing down the search range based on comparisons between keys.

- **Balancing and Height**: The skip list automatically balances itself during insertions to maintain a reasonable height, ensuring that search operations remain efficient.

- **Randomization and Probabilistic Structures**: Skip lists utilize coin flips (randomization) to determine the height of new nodes, which contributes to their logarithmic time complexity.

- **Linked List Manipulation**: Understanding how to manipulate linked lists by adjusting pointers and references to maintain the skip list's structure and order.

- **File Input and Output**: Reading and processing data from files, as well as outputting the skip list's contents to a file.

- **Algorithm Design**: Designing algorithms to efficiently perform tasks such as finding successors and predecessors within a specific time frame.

By working on this project, I gained practical experience in implementing a complex data structure and learned valuable skills that are applicable to various programming and software development tasks.
