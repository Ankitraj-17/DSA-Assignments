# PART A: Remove Duplicates from Sorted Array


# Reference Solution: Builds a separate list and adds distinct values
# Time Complexity: O(N), Auxiliary Space: O(N)
def remove_duplicates_ref(arr):
    unique_list = []
    for val in arr:
        # Only append if the list is empty or the current value is new
        if not unique_list or unique_list[-1] != val:
            unique_list.append(val)
    return unique_list

# Optimized Solution: In-place modification using slow and fast pointers
# Time Complexity: O(N), Auxiliary Space: O(1)
def remove_duplicates(arr):
    # Boundary check: empty array has 0 unique elements
    if not arr:
        return 0

    slow = 0  # Tracks the index of the last confirmed unique element

    # Fast pointer scans the rest of the array
    for fast in range(1, len(arr)):
        # When a new distinct element is encountered
        if arr[fast] != arr[slow]:
            slow += 1
            arr[slow] = arr[fast]  # Overwrite in-place with new distinct value

    # Number of distinct elements is index + 1
    return slow + 1


# PART B: Pair Sum in a Sorted Array


# Brute-Force Solution: Check all pairs (i, j) with i < j
# Time Complexity: O(N^2), Auxiliary Space: O(1)
def pair_sum_brute_force(arr, target):
    n = len(arr)
    for i in range(n):
        for j in range(i + 1, n):
            if arr[i] + arr[j] == target:
                return (i, j)  # Return matching 0-based indices
    return (-1, -1)  # No pair found

# Optimized Solution: Opposite-end two pointers
# Time Complexity: O(N), Auxiliary Space: O(1)
def pair_sum_optimized(arr, target):
    left, right = 0, len(arr) - 1  # Opposite end pointers

    # Ensure distinct elements (never pair an element with itself)
    while left < right:
        current_sum = arr[left] + arr[right]
        if current_sum == target:
            return (left, right)  # Target found
        elif current_sum < target:
            left += 1   # Sum too small: move left pointer right to increase sum
        else:
            right -= 1  # Sum too large: move right pointer left to decrease sum

    return (-1, -1)  # No valid pair found



# Main Function: Run Required Test Cases

if __name__ == "__main__":
    print("--- PART A: Remove Duplicates ---")
    dup_tests = [
        [1, 1, 2, 2, 2, 3, 4, 4],  # Normal duplicate-heavy case
        [5],                        # Single-element boundary case
        [],                         # Empty array boundary case
        [-3, -3, -1, 0, 0]          # Negative numbers and zeros
    ]

    for arr in dup_tests:
        original = list(arr)
        k = remove_duplicates(arr)
        print(f"Original: {original} -> k = {k}, Unique Prefix: {arr[:k]}")

    print("\n--- PART B: Pair Sum ---")
    sum_tests = [
        ([1, 2, 4, 7, 11, 15], 15),  # Pair found in middle/ends
        ([1, 3, 5, 8], 7),           # No pair equals 7 (returns -1, -1)
        ([2, 4, 6], 20),             # Target out of range
        ([1, 2, 2, 3], 4)            # Duplicate values forming target
    ]

    for arr, target in sum_tests:
        indices = pair_sum_optimized(arr, target)
        print(f"Array: {arr}, Target: {target} -> Indices: {indices}")
