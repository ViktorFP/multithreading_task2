package by.epamlab;

import java.util.Arrays;

public class Sorter {
	private Sorter() {
	}

	public static void sort(int[] array) {
		final int BRANCH_AMOUNT = 10;// notional amount
		Branch[] branchs = new Branch[BRANCH_AMOUNT];
		int size = array.length;
		int range = size / BRANCH_AMOUNT;
		for (int i = 0; i < BRANCH_AMOUNT; i++) {
			int from = i * range;
			int to = (i + 1) * range;
			if (i + 1 == BRANCH_AMOUNT) {
				to = size + 1;
			}
		branchs[i] = new Branch(Arrays.copyOfRange(array, from, to));
		}
		int count = 0;// ---
		// wait closing all Branch
		for (Branch branch : branchs) {
			try {
				(branch.getThread()).join();
			} catch (InterruptedException e) {
				System.out.println("Exception of branch: " + e.getMessage());
			}
		}
		
	}

	// ==============nected class================
	static class Branch implements Runnable {
		private Thread thread;
		private int[] array;
		private int index = 0;

		public Branch(int[] array) {
			this.array = array;
			thread = new Thread(this);
			thread.start();
		}

		@Override
		public void run() {
			Arrays.sort(array);			
		}

		public Thread getThread() {
			return thread;
		}

		public Integer getNext() {
			if (index + 1 < array.length) {
				return array[index++];
			} else {
				return null;
			}
		}
	}
}
