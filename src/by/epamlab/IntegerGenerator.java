package by.epamlab;

public class IntegerGenerator {

	private IntegerGenerator() {
	}
	
	public static int[] generateArrayData(int size, int maxRange){
		int array[]=new int[size];
		for(int i=0;i<size;i++){
			array[i]=(int)(Math.random()*maxRange);
		}
		return array;
	}
}
