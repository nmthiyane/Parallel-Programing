import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MergeSortParallel extends RecursiveAction {
	private int[] arr, tempLst;
    private int start, end;
    private int threshold;

    public MergeSortParallel(int[] arr, int start, int end, int threshold) {
            this.arr = arr;
            this.start = start;
            this.end = end;
            this.threshold = threshold;
            
    }

    @Override
    protected void compute() {
            if ((end - start) <= threshold) {
                    Arrays.sort(arr, start, end);
                    return;
            }

            // Sort halves in parallel
            int mid = start + (end-start) / 2;
            invokeAll(
            		new MergeSortParallel(arr, start, mid, threshold),
                    new MergeSortParallel(arr, mid, end, threshold) );

            sequentialMerge(mid);
    }

    private void sequentialMerge(int mid) {
    	
    	tempLst = new int[arr.length];
    	
    	System.arraycopy(arr,0 , tempLst, 0, arr.length);                 // making a copy of the original array
    	int i = start, j = mid , cuurentPos = start;
    	    
    	while (i < mid && j < end) {// Copy the smallest values from either the left or the right side back to the original array
    	     
    	    if (tempLst[i] <= tempLst[j]) {
    	    	arr[cuurentPos] = tempLst[i];
    	    	i++;
    	    }     	      
    	    else {
    	    	arr[cuurentPos] = tempLst[j];
    	    	j++;
    	    }
    	    cuurentPos++;
    	}
    	    
    	while (i < mid) {    // Copy the rest of the left side of the array into the target array
    		arr[cuurentPos] = tempLst[i];
    	    cuurentPos++;
    	    i++;
    	}
    }
}

