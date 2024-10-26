public class BinarySearchNoRecurse {
    public static int binarySearchNoRecurse(int[] arr, int target) {
        int start = 0;
        int end = arr.length - 1;

        while (start <= end) {
            // Calculate middle index
            int guess = start + (end - start) / 2;

            if (arr[guess] == target) {
                // Target found, return index
                return guess;
            } else if (arr[guess] < target) {
                // Move to the right subarray
                start = guess + 1;
            } else {
                // Move to the left subarray
                end = guess - 1;
            }
        }

        // Target not found, return -1 (or could return start for insertion point)
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(binarySearchNoRecurse(array, 1));
        System.out.println(binarySearchNoRecurse(array, 11));
    }
}
