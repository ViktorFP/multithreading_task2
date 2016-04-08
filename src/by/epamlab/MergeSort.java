package by.epamlab;

import java.util.Arrays;

public class MergeSort {
	// private int[] sorted;

	private MergeSort() {

	}

	public static void sort(int[] array) {

		sort(array, 0, array.length - 1);
	}

	private static void sort(int[] array, int firstIdx, int lastIndex) {
		switch (lastIndex - firstIdx + 1) {
		case 1:
			break;
		case 2:
			if (array[0] > array[1]) {
				int temp = array[0];
				array[0] = array[1];
				array[1] = temp;
			}
			// swap(array, firstIdx, lastIndex);
			break;
		default:
			new Branch(array, firstIdx, lastIndex);
		}
		// if (array.length > 1) {
		// new Branch(array);
		// }
	}

	private static void merge(int[] array, int leftFirstIdx, int leftLastIndex,
			int rightFirstIdx, int rightLastIndex) {
		int[] temp = Arrays.copyOfRange(array, leftFirstIdx, leftLastIndex);
		for (int l = 0; l < temp.length; l++) {
			// int temp=array[l];
			int left = temp[l];
			for (int r = rightFirstIdx; r < rightLastIndex + 1; r++) {
				int right = array[r];
				if (left > right) {
					array[leftFirstIdx + l] = right;
				} else {
					array[leftFirstIdx + l] = left;
					break;
				}
			}
		}
		// for (int l = leftFirstIdx; l < leftLastIndex + 1; l++) {
		// for (int r = rightFirstIdx; r < rightLastIndex + 1; r++) {
		// if (swap(array, l, r)) {
		// break;
		// }
		// }
		// }
	}

	// private static boolean swap(int[] array, int lIdx, int rIdx) {
	// if (array[lIdx] > array[rIdx]) {
	// int temp = array[lIdx];
	// array[lIdx] = array[rIdx];
	// array[rIdx] = temp;
	// return true;
	// }
	// return false;
	// }

	// ==============nested class================
	static class Branch implements Runnable {
		private static final int MAX_THREADS = 4;
		private static int threadStarted = 0;

		private Thread thread;
		private int[] array;
		private int firstIdx, lastIndex;

		int nT = threadStarted + 1;

		public Branch(int[] array, int firstIdx, int lastIndex) {
			this.array = array;
			this.firstIdx = firstIdx;
			this.lastIndex = lastIndex;

			if (threadStarted < MAX_THREADS) {
				threadStarted++;
				thread = new Thread(this);
				thread.start();
				try {
					thread.join();
					threadStarted--;
				} catch (InterruptedException e) {
					System.out
							.println("Exception of branch: " + e.getMessage());
				}
				System.out.println("финишировал поток " + nT);// ---
			} else {
				sort(array, firstIdx, lastIndex);
			}
		}

		@Override
		public void run() {
			System.out.println("запущен поток " + nT); // ---
			sort(array, firstIdx, lastIndex);
		}

		private void sort(int[] array, int firstIdx, int lastIndex) {
			int totalSize = lastIndex - firstIdx + 1;
			int middle = totalSize / 2;
			int leftLastIdx = firstIdx + middle - 1;
			int rightFirstIdx = leftLastIdx + 1;

			MergeSort.sort(array, firstIdx, leftLastIdx);
			MergeSort.sort(array, rightFirstIdx, lastIndex);

			print(array, firstIdx, leftLastIdx);
			print(array, rightFirstIdx, lastIndex);

			MergeSort.merge(array, firstIdx, leftLastIdx, rightFirstIdx,
					lastIndex);

			System.out.println("after merge");
			print(array, firstIdx, lastIndex);
			// System.arraycopy(array, 0, left, 0, middle);
			// System.arraycopy(array, middle, right, 0, rest);

		}

		void print(int[] array, int firstIdx, int lastIndex) {
			System.out.println("==============================");
			for (int i = firstIdx; i < lastIndex + 1; i++) {
				System.out.println(array[i]);
			}
		}
	}
}
