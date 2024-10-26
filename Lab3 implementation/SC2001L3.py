def knapsack(w, p, C, n):

    # n is the number of objects
    # w is the array of weights of the corresponding object
    # p is the array of profits of the corresponding object
    # C is the capacity of the knapsack
    
    # Initialize a 2D array with (C+1) rows and (n+1) columns, filled with zeros
    profit = [[0 for _ in range(n + 1)] for _ in range(C + 1)]

    for r in range(1, C + 1):  
        for c in range(1, n + 1): 
            profit[r][c] = profit[r][c - 1]
            if w[c - 1] <= r:
                profit[r][c] = max(profit[r][c], profit[r - w[c - 1]][c - 1] + p[c - 1])

    print("Table:")
    for row in range(C+1):
        print(profit[row]) # Show working

    #print the objects needed for the max profit
    #start from the bottom right

    selected_items = []
    cap = C
    itemNum = n

    while cap > 0 and itemNum > 0:
        if profit[cap][itemNum] != profit[cap][itemNum-1]:  # Change in profit = the item is included
            selected_items.append(itemNum-1)  # Add the index of the selected item
            cap -= w[itemNum-1]  # Reduce the capacity by the weight of the included item

        itemNum -= 1  # Move to the previous item

    print("Selected item indices (0-based):", selected_items[::-1])  # Reverse to get original order
    return profit[C][n]

weights = [4,6,8,6]
profits = [7,6,9,5]
capacity = 20
numObjects = len(weights)

#column = capacity of knapsack
#row = item number
print(knapsack(weights, profits, capacity, numObjects))
