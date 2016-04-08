import java.util.Arrays;
import java.util.Date;

import by.epamlab.IntegerGenerator;
import by.epamlab.Sorter;

public class Runner {
	public static void main(String[] args) {
		int[] array = IntegerGenerator.generateArrayData(20_000_000, 10_000);

		// simpl sorting
		long time = (new Date()).getTime();
		Arrays.sort(array);
		System.out.println("Arrays.sort: " + ((new Date()).getTime() - time)
				+ "ms");
		// parallel sorting
		array = IntegerGenerator.generateArrayData(20_000_000, 10_000);
		time = (new Date()).getTime();
		Sorter.sort(array);
		System.out.println("Sorter.sort: " + ((new Date()).getTime() - time)
				+ "ms");		
	}
}
