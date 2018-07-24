package edu.wit.dcsn.comp2000.listadt;

public class Main {
	public static void main(String args[]) {
		LinkedListPlus<Integer> test = new LinkedListPlus<Integer>();
		for(int i = 10; i > 0; i--) {
			test.add(i);
		}
		for(int i = 0 ; i< test.getLength();i++) {
			System.out.print(test.getEntry(i)+" ");
		}
		System.out.println(" ");
		
		test.sort();
		for(int i = 0 ; i< test.getLength();i++) {
			System.out.print(test.getEntry(i)+" ");
		}
		
		System.out.println(" ");
		test.shuffle();
		for(int i = 0 ; i< test.getLength();i++) {
			System.out.print(test.getEntry(i)+" ");
		}
	}
}
