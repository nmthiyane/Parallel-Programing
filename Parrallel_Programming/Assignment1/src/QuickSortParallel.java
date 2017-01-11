import java.util.Arrays;
import java.util.concurrent.*;
import java.util.*;

public class QuickSortParallel extends RecursiveAction
{
    private int lowerIndex, higherIndex;
	public int[] array;
	private int threshold;   

    public QuickSortParallel(int[] array,int lowerIndex,int higherIndex, int threshold)
    {	//initialize
        this.array = array;
        this. lowerIndex= lowerIndex;
        this.higherIndex = higherIndex;
        this.threshold = threshold;
    }
    
    
    protected void compute()
    {    
       if(lowerIndex-higherIndex<=threshold)
       {
             Arrays.sort(array, lowerIndex, higherIndex);
       }
       else
       {   
          int partition = seqentialQuickSort(array, lowerIndex, higherIndex);
          if (partition==-1)
          {   
             return;
          }
          else
             {
               QuickSortParallel left = new  QuickSortParallel (array, lowerIndex, partition-1, threshold); // quicksort the left partition    
               QuickSortParallel right = new QuickSortParallel (array,partition+1, higherIndex, threshold); // quicksort the right partitio
               invokeAll(left,right);
             }
                 
         }
         
    }
    
    public int seqentialQuickSort(int[] array,int lowerIndex , int higherIndex)
    {
        int i = lowerIndex;                          
        int j = higherIndex;                            

        if (higherIndex - lowerIndex >= 1)                  
        {
                int pivot = array[lowerIndex];       

                while (j > i)                   
                {
                  while (array[i] <= pivot && i <= higherIndex && j > i)  
                     i++;                                    
                  while (array[j] > pivot && j >= lowerIndex && j >= i) 
                     j--;                                       
                  if (i < j)                                       
                     exchange(array, i, j);                      
                }
                exchange(array, lowerIndex, j);          
                                                 

        }
        else    
        {
                return -1;                     
        }
        return j;
	}
    
    private void exchange(int[] array, int a,int b)
    {	
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
   
}