package by.epamlab;

import java.util.Arrays;

public class MergeSort implements Runnable {
	private int[] array;
	private int firstIdx, lastIndex;

	private MergeSort(int[] array, int firstIdx, int lastIndex) {
		this.array = array;
		this.firstIdx = firstIdx;
		this.lastIndex = lastIndex;
	}

	public static void sort(int[] array) throws InterruptedException {
		sort(array, 0, array.length - 1);
	}

	private static void sort(int[] array, int firstIdx, int lastIndex)
			throws InterruptedException {

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

	@Override
	public void run() {
		try {
			sort(array, firstIdx, lastIndex);
		} catch (InterruptedException e) {
			System.out.println("Exception in "
					+ Thread.currentThread().getName());
		}
	}

	// ==============nested class================
	static class Branch {
		private static final int MAX_THREADS = 6;
		private static int threadStarted = 0;

		public Branch(int[] array, int firstIdx, int lastIndex)
				throws InterruptedException {
			sort(array, firstIdx, lastIndex);
		}

		private void sort(int[] array, int firstIdx, int lastIndex)
				throws InterruptedException {
			int totalSize = lastIndex - firstIdx + 1;
			int middle = totalSize / 2;
			int leftLastIdx = firstIdx + middle - 1;
			int rightFirstIdx = leftLastIdx + 1;

			int[] firstIdxs = { firstIdx, rightFirstIdx };
			int[] lastIdxs = { leftLastIdx, lastIndex };
			Thread[] threads = new Thread[2];
			for (int i = 0; i < 2; i++) {
				if (threadStarted < MAX_THREADS) {
					threadStarted++;
					threads[i] = new Thread(new MergeSort(array, firstIdxs[i],
							lastIdxs[i]));
					threads[i].start();
				} else {
					MergeSort.sort(array, firstIdxs[i], lastIdxs[i]);
				}
			}
			int threadsClosing = 0;
			for (int i = 0; i < 2; i++) {
				if (threads[i] != null) {
					threads[i].join();
					threadsClosing++;
				}
			}
			threadStarted -= threadsClosing;

			MergeSort.merge(array, firstIdx, leftLastIdx, rightFirstIdx,
					lastIndex);
		}
	}
}
