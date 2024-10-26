class InsertionSort {
    public static void insertionSort(int[] arr){
        int temp;
        int length = arr.length;
        for (int i = 0; i < length; i++){
            for (int j = i; j > 0; j--){
                if (arr[j] < arr[j-1]) {
                    temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                }
                
            }
        }
    }
    public static void main(String[] args) {
        int[] arr = {4,6,100,123,64,87,1,2,3,8,10,5};
        insertionSort(arr);
        for (int num: arr) System.out.print(num+" ");
    }
}
