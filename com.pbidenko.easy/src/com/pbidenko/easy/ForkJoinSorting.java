package com.pbidenko.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

class Processing extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	
	private Integer[] a;
	private int start, end;

	public Processing(Integer[] array) {

		this(array, 0, array.length - 1);
	}

	public Processing(Integer[] array, int start, int end) {

		this.a = array;
		this.start = start;
		this.end = end;
	}

	@Override
	protected void compute() {

		if (start < end)
			RecursiveAction.invokeAll(getListOfTasks());
		else
			return;

	}

	private List<Processing> getListOfTasks() {

		List<Processing> list = new ArrayList<>();

		int i = start;
		int j = end;
		int middle = start + (end - start) / 2;

		int threshold = a[middle];

		while (i < j) {
			while (i < middle && a[i] < threshold)
				i++;

			while (j > middle && a[j] >= threshold)
				j--;

			int z = a[i];
			a[i] = a[j];
			a[j] = z;

			if (i == middle)
				middle = j;
			else if (j == middle)
				middle = i;

			list.add(new Processing(a, start, middle));
			list.add(new Processing(a, middle + 1, end));
		}

		return list;
	}

}

public class ForkJoinSorting {

	public static void main(String[] args) {

		int length = 100;

		Integer[] arr = new Integer[length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new Random().nextInt(100);
		}

		long point = System.currentTimeMillis();
		ForkJoinTask<?> task = new Processing(arr);
		ForkJoinPool pool = new ForkJoinPool();

		pool.invoke(task);

		System.out.println(Arrays.toString(arr));
		System.out.println("Millis: " + (System.currentTimeMillis() - point));
	}

}
