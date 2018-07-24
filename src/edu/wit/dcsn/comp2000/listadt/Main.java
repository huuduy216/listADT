package edu.wit.dcsn.comp2000.listadt;

public class Main {
    public static void main(String args[]) {
	LinkedListPlus<Integer> test = new LinkedListPlus<Integer>();
	for (int i = 10; i > 0; i--) {
	    test.add(i);
	}
//	Integer[] arr = test.toArray();
//	for (int i = 0; i < arr.length; i++) {
//	    System.out.print(arr[i] + " ");
//	}

	System.out.println(" ");
	test.sort();
	for (int i = 0; i < test.getLength(); i++) {
	    System.out.print(test.getEntry(i) + " ");
	}

	System.out.println(" ");
	test.add(4);
	test.add(15);
	test.sort();
	System.out.println("Length: " + test.getLength());
	for (int i = 0; i < test.getLength(); i++) {
	    System.out.print(test.getEntry(i) + " ");
	}
	System.out.println(" ");
	test.shuffle();
	for (int i = 0; i < test.getLength(); i++) {
	    System.out.print(test.getEntry(i) + " ");
	}
	
    }
}
