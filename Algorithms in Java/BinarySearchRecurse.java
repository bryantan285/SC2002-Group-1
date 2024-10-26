public class BinarySearchRecurse {

    public static int binarySearch(int[] array, int target, int startIndex, int endIndex) {
        if (startIndex > endIndex) {
            // Target not found
            return -1;
        }

        int indexOfMiddle = startIndex + (endIndex - startIndex) / 2;
        System.out.println("Start: " + startIndex + ", End: " + endIndex + ", Middle: " + indexOfMiddle);

        if (array[indexOfMiddle] == target) {
            return indexOfMiddle;
        } 
        else if (array[indexOfMiddle] < target) {
            return binarySearch(array, target, indexOfMiddle + 1, endIndex);
        } 
        else {
            return binarySearch(array, target, startIndex, indexOfMiddle - 1);
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 9, 10, 14, 19, 31};
        System.out.println(binarySearch(arr, 19, 0, arr.length - 1));
    }
}
