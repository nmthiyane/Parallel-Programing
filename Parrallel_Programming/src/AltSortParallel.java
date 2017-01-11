
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class AltSortParallel extends RecursiveAction {
	private int[] arr, tempLst;
    private int start, end;
    private int threshold;
    private boolean flag = true;

    public AltSortParallel(int[] arr, int start, int end, int threshold) {
            this.arr = arr;
            this.start = start;
            this.end = end;
            this.threshold = threshold;
            
    }

    @Override
    protected void compute() {
            if (end - start <= threshold) {
                    // sequential sort
                    Arrays.sort(arr, start, end);
                    return;
            }

            // Sort halves in parallel
            int mid = start + (end-start) / 2;
            invokeAll(
            		new AltSortParallel(arr, start, mid, threshold),
                    new AltSortParallel(arr, mid, end, threshold) );

            sequentialMerge(arr);
    }
    
    public int[] sequentialMerge(int[] arr){
        int temp;
        int i=0;
        while(flag){
        	flag = false;
            for(int j=1; j < arr.length-i; j++){
                if(arr[j-1] > arr[j]){
                    temp=arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = temp;
                    flag= true;
                }
            }
           // System.out.println((i+1)+"th iteration result: "+Arrays.toString(arr));
            i++;
        }
        return arr;
    }
    
   /* public static void main(String [] args){
    	int[] y=  {67, 65, 34, 20, 5, 99, 53, 37, 75, 97, 83, 70, 99, 23, 60, 31, 8, 6, 41, 94, 87, 1, 70, 79, 5, 79, 37, 9, 27, 19, 69, 53, 23, 52, 59, 87, 60, 53, 24, 22, 53, 2, 30, 29, 99, 45, 20, 32, 62, 59, 47, 84, 78, 39, 87, 34, 87, 74, 43, 8, 58, 58, 46, 86, 91, 33, 47, 14, 49, 29, 92, 38, 53, 5, 63, 5, 39, 99, 54, 89, 49, 5, 21, 44, 30, 14, 30, 24, 52, 38, 21, 29, 11, 7, 58, 50, 51, 11, 44, 75};
    	int SIZE = 100;
		int MAX = 100;
		
		
		
    	//y = {13,3,5,0,7,9,1,4,6,10,2,8};
    	/* y = new int[SIZE];
		    Random generator = new Random();
		    for (int i = 0; i < y.length; i++) {
		      y[i] = generator.nextInt(MAX);
		    }
		    System.out.print(Arrays.toString(y)+ "");
		    
		    System.out.println("");
    	
		    AltSortParallel x= new AltSortParallel(y,0,y.length,1);
    	ForkJoinPool.commonPool().invoke(x);
    	System.out.print(Arrays.toString(y));*/
    }
    
    
    
	
	
	
	
	
	/*
 
public class CrunchifyBubbleSortAsce {
 
	public static void main(String[] args) {
 
        int arrayList[] = { 5,3,9,7,1,8 };
        System.out.println("\nFinal result:"+Arrays.toString(CrunchifyBubbleSortAsceMethod(arrayList)));
	}
 
    public static int[] CrunchifyBubbleSortAsceMethod(int[] arr){
        int temp;
        for(int i=0; i < arr.length-1; i++){
 
            for(int j=1; j < arr.length-i; j++){
                if(arr[j-1] > arr[j]){
                    temp=arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = temp;
                }
            }
            System.out.println((i+1)+"th iteration result: "+Arrays.toString(arr));
        }
        return arr;
    }*/
//}
