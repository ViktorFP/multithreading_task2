import java.util.Arrays;
import java.util.Date;

import by.epamlab.IntegerGenerator;
import by.epamlab.MergeSort;

public class Runner {
	public static void main(String[] args) {
		final int ARRAY_SIZE=1_000_000;
		final int ARRAY_MAX_RANGE =1_000;
		
		int[] array = IntegerGenerator.generateArrayData(ARRAY_SIZE, ARRAY_MAX_RANGE);

		// simpl sorting
		long time = (new Date()).getTime();
		Arrays.sort(array);
		System.out.println("Arrays.sort: " + ((new Date()).getTime() - time)
				+ "ms");
		
		// parallel sorting
		array = IntegerGenerator.generateArrayData(ARRAY_SIZE, ARRAY_MAX_RANGE);
		
//		String del="=======================";
//		System.out.println(del);
//		for(int i: array){
//			System.out.println(i);
//		}
		
		time = (new Date()).getTime();
		MergeSort.sort(array);
		System.out.println("MergeSort.sort: " + ((new Date()).getTime() - time)
				+ "ms");
//		System.out.println(del+"final");
//		for(int i: array){
//			System.out.println(i);
//		}
	}
}
