import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.io.*;


public class DriverSort {
	
	static long startTime; //startTime = 0;  
	static int[] randomArray;
	 
	private static void tick(){      								// starting the timer 
		startTime = 0;
		startTime = System.currentTimeMillis();
	}
	
	private static float toc(){                                   // calculating the total time
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	
	private static int[] generateRandom(int size){
		int[] randomArray = new int[size];
		Random generator = new Random();
		for (int i = 0; i < size; i++) {
			randomArray[i] = generator.nextInt(size);
  	  	}	  
  	  	return randomArray;
	}
	
   public static void main(String [] args){
      
	  String sort = args[0].toLowerCase() , outFileName = "doc/" + args[4].toLowerCase() + ".txt" ; 
	  int arraySizeMin = Integer.parseInt(args[1]), arraySizeMax = Integer.parseInt(args[2]), arraySizeIncr = Integer.parseInt(args[3]) ;
	  
      int  counter, optimalThreads=0;
      int[] randomArray, randomArray1;
      float speedUp, parallelTime = 0, sequentialTime = 0, bestTime = 0, bestSpeedup = 0, bestSequentialTime=0;
      counter= arraySizeMin;
      
      DecimalFormat aaa= new DecimalFormat("#.00000");
      
      BufferedWriter output;

	
      while(counter<=arraySizeMax){
		
    	  bestSpeedup =0; bestTime = 4; bestSequentialTime= 4;
    	  
    	  randomArray =  new int[arraySizeMax];
    	  randomArray1 = new int[arraySizeMax];
    	  
    	  
    	  for(int counter2=0; counter2< 5; counter2++){
    		  
    		randomArray = generateRandom(arraySizeMax);  				     /** generating an array of random numbers of size counter*/	
    	    System.arraycopy(randomArray, 0, randomArray1, 0, randomArray.length);  	 /**Making a copy of the array*/
    	  	optimalThreads = Runtime.getRuntime().availableProcessors(); 				  /** Getting no of threads*/
    	  	System.gc();                                                             	 /** collects garbage*/ 
    	  
    	  	switch(sort){
    	  
    	  		case "mergesort":
    	  		
    	  			MergeSortParallel x= new MergeSortParallel(randomArray,0,randomArray.length, randomArray.length/optimalThreads);
    	  			tick();
    	  			ForkJoinPool.commonPool().invoke(x);
    	  			parallelTime = toc();
    	  			break;
			
    	  		case "quicksort":
    	  		
    	  			QuickSortParallel y= new QuickSortParallel(randomArray,0,randomArray.length,randomArray.length/optimalThreads);
    	  			tick(); 
    	  			ForkJoinPool.commonPool().invoke(y);
    	  			parallelTime = toc();
    	  			break;
			
    	  		case "altsort":
    	  		
    	  			AltSortParallel z= new AltSortParallel(randomArray,0,randomArray.length-1,randomArray.length/optimalThreads);
    	  			tick(); 
    	  			ForkJoinPool.commonPool().invoke(z);
    	  			parallelTime = toc();
    	  			break;
			
    	  		default:
    	  		
    	  			System.out.println("No sort seleted");          // if no sort has been selected break;
    	  			counter=arraySizeMax;
    	  			counter2=5;
    	  			break;	
				
    	  	}  // end of switch block*/  
    	  
    	  	tick();
    	  	Arrays.sort(randomArray1);
    	  	toc(); sequentialTime = toc();
    	  
    	  	if(parallelTime<bestTime){
    	  		bestTime=parallelTime;
    	  		bestSequentialTime=sequentialTime;
    	  	}
    	  	
    	  	speedUp = bestSequentialTime/bestTime;
    	  	
    	  	if(speedUp>bestSpeedup){
    	  		bestSpeedup=speedUp;
    	  		
    	  	}
    	  }  
    	  
    	  /**Writing to the text file*/
    	  try{                                
          	output = new BufferedWriter(new FileWriter(outFileName, true));
          	output.append(counter + " " + optimalThreads + " " + bestTime + " " + aaa.format(bestSpeedup) + "\n");        // append a newline character.
          	output.newLine();
          	output.close();                                                                                        // Closing file.
         }
         catch(IOException ex) {
      	   System.out.println("Error writing to file "+ outFileName + "");                                  // if file doesn't exist
         }
    	  
    	 counter+=arraySizeIncr;
    	 
      } // end of while loop
   } // main  
} // class
