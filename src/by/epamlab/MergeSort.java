package by.epamlab;

import java.util.Arrays;

public class MergeSort {
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
			if (array[firstIdx] > array[lastIndex]) {
				int temp = array[firstIdx];
				array[firstIdx] = array[lastIndex];
				array[lastIndex] = temp;
			}
			break;
		default:
			new Branch(array, firstIdx, lastIndex);
		}
	}

	private static void merge(int[] array, int leftFirstIdx, int leftLastIndex,
			int rightFirstIdx, int rightLastIndex) {
		int[] temp = Arrays.copyOfRange(array, leftFirstIdx, leftLastIndex + 1);
		int tempCursor = 0;
		int rightCursor = rightFirstIdx;
		int cell;
		for (cell = leftFirstIdx; cell < rightLastIndex + 1; cell++) {
			if (tempCursor >= temp.length) {
				return;
			}
			if (rightCursor > rightLastIndex) {
				break;
			}
			if (temp[tempCursor] < array[rightCursor]) {
				array[cell] = temp[tempCursor];
				tempCursor++;
			} else {
				array[cell] = array[rightCursor];
				rightCursor++;
			}
		}
		for (int i = tempCursor; i < temp.length; i++) {
			array[cell] = temp[i];
			cell++;
		}
	}

	// ==============nested class================
	static class Branch implements Runnable {
		private static final int MAX_THREADS = 10;
		private static int threadStarted = 0;

		private Thread thread;
		private int[] array;
		private int firstIdx, lastIndex;

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
			} else {
				sort(array, firstIdx, lastIndex);
			}
		}

		@Override
		public void run() {
			sort(array, firstIdx, lastIndex);
		}

		private void sort(int[] array, int firstIdx, int lastIndex) {
			int totalSize = lastIndex - firstIdx + 1;
			int middle = totalSize / 2;
			int leftLastIdx = firstIdx + middle - 1;
			int rightFirstIdx = leftLastIdx + 1;

			MergeSort.sort(array, firstIdx, leftLastIdx);
			MergeSort.sort(array, rightFirstIdx, lastIndex);

			MergeSort.merge(array, firstIdx, leftLastIdx, rightFirstIdx,
					lastIndex);
		}
	}
}
