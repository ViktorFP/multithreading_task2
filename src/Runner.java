import by.epamlab.IntegerGenerator;
import by.epamlab.MergeSort;

public class Runner {
	public static void main(String[] args) {
		final int ARRAY_SIZE = 30_000_000;
		final int ARRAY_MAX_RANGE = 10_000;

		int[] array = IntegerGenerator.generateArrayData(ARRAY_SIZE,
				ARRAY_MAX_RANGE);

		long time = System.currentTimeMillis();
		try {
			MergeSort.sort(array);
		} catch (InterruptedException e) {
			System.out.println("Sorting was interrupted: " + e.getMessage());
		}
		System.out.println("MergeSort.sort: "
				+ (System.currentTimeMillis() - time) + "ms");
	}
}
