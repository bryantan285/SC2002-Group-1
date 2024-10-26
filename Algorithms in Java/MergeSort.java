import java.lang.Math;
import java.util.Arrays;

public class MergeSort {

    public static int[] mergeSort(int[] arr){
        if (arr.length <= 1) return arr;
        
        int mid = (int) Math.floor(arr.length/2);
        int[] left_half = mergeSort(Arrays.copyOfRange(arr,0,mid));
        int[] right_half = mergeSort(Arrays.copyOfRange(arr,mid,arr.length));

        return merge(left_half, right_half);
    }

    public static int[] merge(int[] arr1, int[] arr2){
        //create new array by comparing each element in the 2 (already sorted)
        //arrays and putting it in a new array
        int i = 0, j = 0, s = 0;
        int[] sorted = new int[arr1.length + arr2.length];
        
        while (i < arr1.length && j < arr2.length){
            if (arr1[i] <= arr2[j]) sorted[s++] += arr1[i++];
            else sorted[s++] += arr2[j++];
        }

        while (i < arr1.length) sorted[s++] += arr1[i++];

        while (j < arr2.length) sorted[s++] += arr2[j++];

        return sorted;
    }

    public static void main(String[] args) {
        int[] unsorted = {5,9,10,7,1,3,2,4,6,8};
        int[] sortedArray = mergeSort(unsorted);
        for (int num : sortedArray) System.out.print(num + " ");

    }
}
