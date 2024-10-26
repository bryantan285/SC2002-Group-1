import math

def mergeSort(arr):
    if len(arr) <= 1:
        return arr
    
    array_length = len(arr)
    left_half = mergeSort(arr[:math.ceil(array_length/2)])
    right_half = mergeSort(arr[math.ceil(array_length/2):])

    return merge(left_half,right_half)

def merge(arr1, arr2):
    arr1_length = len(arr1)
    arr2_length = len(arr2)
    merged_arr = []

    i = j = 0

    while i < arr1_length and j < arr2_length:
        if arr1[i] <= arr2[j]:
            merged_arr.append(arr1[i])
            i += 1
        else:
            merged_arr.append(arr2[j])
            j += 1
            
    # Append remaining elements of arr1, if any
    while i < arr1_length:
        merged_arr.append(arr1[i])
        i += 1
    
    # Append remaining elements of arr2, if any
    while j < arr2_length:
        merged_arr.append(arr2[j])
        j += 1
    
    return merged_arr

array = [12,8,9,3,11,5,4]
sorted = mergeSort(array)
print(sorted)