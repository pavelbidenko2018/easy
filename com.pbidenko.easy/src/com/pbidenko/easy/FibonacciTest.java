package com.pbidenko.easy;

import java.util.stream.Stream;

public class FibonacciTest {

	void processing(int num) {
		
		Stream.iterate(new int[] {0,1},n->new int[]{n[1],n[1] + n[0]}).limit(num)
		.mapToInt(n->n[0]).forEach(System.out::println);;
		
		}

	public static void main(String[] args) {

		int num = 10;
		new FibonacciTest().processing(num);

	}

}
