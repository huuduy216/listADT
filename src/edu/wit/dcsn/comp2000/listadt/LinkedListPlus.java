
package edu.wit.dcsn.comp2000.listadt;

import java.util.Iterator;
import java.util.Random;

/**
 * A class that implements the ADT list by using a chain of nodes that has both
 * a head reference and a tail reference.
 * <p>
 * Revised: 2018-07-11 by David M Rosenberg
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 4.0
 * 
 * @param <T>
 *            the class (or an ancestor class) of objects to be stored in the
 *            list
 */
public class LinkedListPlus<T extends Comparable<? super T>> implements ListInterface<T>, Iterable<T> {

    private Node firstNode; // Head reference to first node
    private Node lastNode; // Tail reference to last node
    private int numberOfEntries;

    public LinkedListPlus() {
	initializeDataFields();
    } // end default constructor

    @Override
    public void clear() {
	initializeDataFields();
    } // end clear

    @Override
    public void add(T newEntry) {
	Node newNode = new Node(newEntry);

	if (isEmpty())
	    firstNode = newNode;
	else
	    lastNode.setNextNode(newNode);

	lastNode = newNode;
	numberOfEntries++;
    } // end add

    @Override
    public void add(int newPosition, T newEntry) {
	if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
	    Node newNode = new Node(newEntry);

	    if (isEmpty()) {
		firstNode = newNode;
		lastNode = newNode;
	    } else if (newPosition == 0) {
		newNode.setNextNode(firstNode);
		firstNode = newNode;
	    } else if (newPosition == numberOfEntries) {
		lastNode.setNextNode(newNode);
		lastNode = newNode;
	    } else {
		Node nodeBefore = getNodeAt(newPosition - 1);
		Node nodeAfter = nodeBefore.getNextNode();
		newNode.setNextNode(nodeAfter);
		nodeBefore.setNextNode(newNode);
	    } // end if
	    numberOfEntries++;
	} else
	    throw new IndexOutOfBoundsException("Illegal position given to add operation.");
    } // end add

    @Override
    public T remove(int givenPosition) {
	T result = null; // Return value

	if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
	    assert !isEmpty();
	    if (givenPosition == 0) // Case 1: Remove first entry
	    {
		result = firstNode.getData(); // Save entry to be removed
		firstNode = firstNode.getNextNode();
		if (numberOfEntries == 1)
		    lastNode = null; // Solitary entry was removed
	    } else // Case 2: Not first entry
	    {
		Node nodeBefore = getNodeAt(givenPosition - 1);
		Node nodeToRemove = nodeBefore.getNextNode();
		Node nodeAfter = nodeToRemove.getNextNode();
		nodeBefore.setNextNode(nodeAfter);// Disconnect the node to be removed
		result = nodeToRemove.getData(); // Save entry to be removed

		if (givenPosition == numberOfEntries)
		    lastNode = nodeBefore; // Last node was removed
	    } // end if

	    numberOfEntries--;
	} else
	    throw new IndexOutOfBoundsException("Illegal position given to remove operation.");

	return result; // Return removed entry
    } // end remove

    @Override
    public T replace(int givenPosition, T newEntry) {
	if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
	    assert !isEmpty();

	    Node desiredNode = getNodeAt(givenPosition);
	    T originalEntry = desiredNode.getData();
	    desiredNode.setData(newEntry);
	    return originalEntry;
	} else
	    throw new IndexOutOfBoundsException("Illegal position given to replace operation.");
    } // end replace

    @Override
    public T getEntry(int givenPosition) {
	if ((givenPosition >= 0) && (givenPosition <= numberOfEntries)) {
	    assert !isEmpty();
	    return getNodeAt(givenPosition).getData();
	} else
	    throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
    } // end getEntry

    public T[] toArray() {
	// The cast is safe because the new array contains null entries
	@SuppressWarnings("unchecked")
	T[] result = (T[]) new Comparable[numberOfEntries];

	int index = 0;
	Node currentNode = firstNode;
	while ((index < numberOfEntries) && (currentNode != null)) {
	    result[index] = (T)currentNode.getData();
	    currentNode = currentNode.getNextNode();
	    index++;
	} // end while

	return result;
    } // end toArray

    @Override
    public boolean contains(T anEntry) {
	boolean found = false;
	Node currentNode = firstNode;

	while (!found && (currentNode != null)) {
	    if (anEntry.equals(currentNode.getData()))
		found = true;
	    else
		currentNode = currentNode.getNextNode();
	} // end while

	return found;
    } // end contains

    @Override
    public int getLength() {
	return numberOfEntries;
    } // end getLength

    @Override
    public boolean isEmpty() {
	boolean result;

	if (numberOfEntries == 0) // Or getLength() == 0
	{
	    assert (firstNode == null) && (lastNode == null);
	    result = true;
	} else {
	    assert (firstNode != null) && (lastNode != null);
	    result = false;
	} // end if

	return result;
    } // end isEmpty

    /*
     * (non-Javadoc)
     * 
     * @see edu.wit.dcsn.comp2000.listadt.ListInterface#sort()
     */
    @Override
    public void sort() {
	this.firstNode = _sort(this.firstNode);
	resetLastNode();
    }

    private void resetLastNode() {
	Node node = this.firstNode;
	while (node.next != null) {
	    node = node.next;
	}
	this.lastNode = node;
    }

    private Node _sort(Node node) {
	if (node == null || node.next == null)
	    return node;

	// get the middle of the list
	Node mid = traverseHalf(node);
	Node temp = mid.next;
	// set the next of middle node to null
	mid.next = null;

	// Apply mergeSort on left list
	Node l = _sort(node);

	// Apply mergeSort on right list
	Node r = _sort(temp);

	// Merge the left and right lists
	Node sorted = new Node(null);

	sorted = sortedMerge(l, r);

	return sorted;
    }

    private Node traverseHalf(Node node) {
	if (node == null || node.next == null)
	    return node;
	Node fast = node.next;
	Node slow = node;

	while (fast != null) {
	    fast = fast.next;
	    if (fast != null) {
		slow = slow.next;
		fast = fast.next;
	    }
	}
	return slow;
    }

    private Node sortedMerge(Node a, Node b) {
	Node result = null;
	if (a == null)
	    return b;
	if (b == null)
	    return a;

	if (a.data.compareTo(b.data) <= 0) {
	    result = a;
	    result.next = sortedMerge(a.next, b);
	} else {
	    result = b;
	    result.next = sortedMerge(a, b.next);
	}
	return result;

    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.wit.dcsn.comp2000.listadt.ListInterface#shuffle()
     */
    @Override
    public void shuffle() {
	Random rand = new Random();
	_shuffle();
	for(int i = 0;  i < rand.nextInt(5); i++) {
	    _shuffle();
	}
    }
    private void _shuffle() {
	Node mid = traverseHalf(this.firstNode);
	Node temp = mid.next;
	mid.next = null;
	this.firstNode = shuffleMerge(this.firstNode, temp);
	resetLastNode();
    }
    private Node shuffleMerge(Node first, Node second) {
	if (first == null)
	    return second;
	else if (second == null)
	    return first;
	Node result, temp = null;
	temp = shuffleMerge(first.next, second.next);
	result = first;
	first.next = second;
	second.next = temp;
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<T> iterator() {
	// TODO Auto-generated method stub
	return null;
    } // end iterator()

    // Initializes the class's data fields to indicate an empty list.
    private void initializeDataFields() {
	firstNode = null;
	lastNode = null;
	numberOfEntries = 0;
    } // end initializeDataFields

    // Returns a reference to the node at a given position.
    // Precondition: List is not empty; 1 <= givenPosition <= numberOfEntries.
    private Node getNodeAt(int givenPosition) {
	assert (firstNode != null) && (0 <= givenPosition) && (givenPosition <= numberOfEntries);
	Node currentNode = firstNode;

	if (givenPosition == numberOfEntries)
	    currentNode = lastNode;
	else if (givenPosition > 0) {
	    assert givenPosition < numberOfEntries;
	    // Traverse the chain to locate the desired node
	    for (int counter = 0; counter < givenPosition; counter++)
		currentNode = currentNode.getNextNode();
	} // end if

	assert currentNode != null;
	return currentNode;
    } // end getNodeAt

    private class Node {

	private T data; // Data portion
	private Node next; // Next to next node

	private Node(T dataPortion)// PRIVATE or PUBLIC is OK
	{
	    data = dataPortion;
	    next = null;
	} // end constructor

	private Node(T dataPortion, Node nextNode)// PRIVATE or PUBLIC is OK
	{
	    data = dataPortion;
	    next = nextNode;
	} // end constructor

	private T getData() {
	    return data;
	} // end getData

	private void setData(T newData) {
	    data = newData;
	} // end setData

	private Node getNextNode() {
	    return next;
	} // end getNextNode

	private void setNextNode(Node nextNode) {
	    next = nextNode;
	} // end setNextNode
    } // end inner class Node

    @SuppressWarnings("javadoc")
    public static void main(String[] args) {
	System.out.println("LinkedList Plus' main()");
    } // end main()

} // end LListWithTail
