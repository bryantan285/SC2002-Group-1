if (array[indexOfMiddle] == target) {
            return indexOfMiddle;
        } 
        else if (array[indexOfMiddle] < target) {
            return binarySearch(array, target, indexOfMiddle + 1, endIndex);
        } 
        else {
            return binarySearch(array, target, startIndex, indexOfMiddle - 1);
        }