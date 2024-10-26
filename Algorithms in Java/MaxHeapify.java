public class MaxHeapify {
    /*
    if i is the heap index of the child, then:
        parent = floor(i/2)
        left child = 2i
        right child = 2i+1
    */

    public static void maxHeapify(int[] arr, int heapIndex){

        //heap index starts from 1, arr index starts from 0
        int arrIndex = heapIndex - 1; 

        //BASE CASE: if node has no children (is a leaf node), return to parent
        if (arrIndex >= arr.length) return; 

        maxHeapify(arr, heapIndex * 2); //heapify left child
        maxHeapify(arr, heapIndex * 2 + 1); //heapify right child

        int parentValue = arr[arrIndex]; //arr[arrIndex] = parent's index
        
        int leftChildIndex = heapIndex * 2 - 1; // converting heap index to array index
        int rightChildIndex = heapIndex * 2; // converting heap index to array index

        //check for and valid indexes
        if (leftChildIndex >= arr.length) return;
        int leftChildValue = arr[leftChildIndex];

        //if rightChildIndex is invalid, assignn it the minimum value -2^31, to ensure it doesnt affect comparisons
        int rightChildValue = (rightChildIndex < arr.length) ? arr[rightChildIndex] : Integer.MIN_VALUE;
        

        //if left child > parent, and left child > right child, swap left child with parent
        if (leftChildValue > parentValue && leftChildValue > rightChildValue){
            arr[leftChildIndex] = parentValue; 
            arr[arrIndex] = leftChildValue;
            //maxHeapify(arr, heapIndex * 2);
            maxHeapify(arr, heapIndex * 2); //re-heapify left descendents after switching
        }

        //if right child > parent, and right child > left child, swap right child with parent
        else if (rightChildValue > parentValue && rightChildValue > leftChildValue){
            arr[rightChildIndex] = parentValue; 
            arr[arrIndex] = rightChildValue;
            maxHeapify(arr, heapIndex * 2 + 1); //re-heapify right descendents after switching
        }
    }
    
    public static void main(String[] args) {
        int[] array = {900,2,8,5,1,6,3,4,192,34,456,678,0,1000000};
        maxHeapify(array, 1); //array starts from index 1
        for (int num: array) System.out.print(num + " "); //print array
    }
}