unsorted = [9,8,7,6,45,3,2,1]
def partition(arr,l,r,pivot):
    while l <= r:
            
        while arr[r] > pivot:
            #then arr[r] is on the correct side of the pivot
            r -= 1
            
        while arr[l] < pivot:
            #then arr[l] is on the correct side of the pivot
            l += 1
            
        if l <= r:
            arr[l], arr[r] = arr[r], arr[l] #swap
            l += 1
            r -= 1

    return l #return the starting index of the right sub-array

def quickSort(arr, s, e):
    if s < e:
        mid = (s+e)//2
        pivot = arr[mid] #uses middle of array as the pivot
        index = partition(arr,s,e,pivot) #return the starting index of the right sub-array
        quickSort(arr,s,index-1)
        quickSort(arr,index,e)
        
quickSort(unsorted, 0, len(unsorted) - 1)
print(unsorted)
