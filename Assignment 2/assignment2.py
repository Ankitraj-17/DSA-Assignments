import sys

import sys
# Reference Solution: Recalculates every K-element subarray sum from scratch
# Time Complexity: O(N * K), Auxiliary Space: O(1)
def max_sum_subarray_ref(a, k):
    n = len(a)
    if k < 1 or k > n:
        print(f"Error: Invalid window size K = {k} (must satisfy 1 <= K <= {n})", file=sys.stderr)
        return float('-inf')

    max_sum = float('-inf')

    for i in range(n - k + 1):
        # Plain loop accumulator avoids temporary slice allocation: O(1) auxiliary space
        current_sum = 0
        for j in range(i, i + k):
            current_sum += a[j]
        if current_sum > max_sum:
            max_sum = current_sum

    return max_sum


# Optimized Solution: Fixed-size sliding window
# Time Complexity: O(N), Auxiliary Space: O(1)
def max_sum_subarray(a, k):
    n = len(a)
    if k < 1 or k > n:
        print(f"Error: Invalid window size K = {k} (must satisfy 1 <= K <= {n})", file=sys.stderr)
        return float('-inf')

    # 1. Initialize sum of first window using loop for O(1) auxiliary space
    window_sum = 0
    for i in range(k):
        window_sum += a[i]

    max_sum = window_sum

    # 2. Slide the window from index K to n - 1
    # Add entering element a[i] and subtract leaving element a[i - k]
    for i in range(k, n):
        window_sum += a[i] - a[i - k]
        if window_sum > max_sum:
            max_sum = window_sum

    return max_sum


# ============================================================
# PART B: Longest Subarray with Sum at Most K (Variable Window)
# Precondition: Array elements must be non-negative (a[i] >= 0, limit >= 0)
# ============================================================

# Reference Solution: Enumerates every start index and extends subarray
# Time Complexity: O(N^2), Auxiliary Space: O(1)
def longest_subarray_at_most_k_ref(a, limit):
    n = len(a)
    best_len = 0
    best_start, best_end = -1, -1

    for i in range(n):
        current_sum = 0
        for j in range(i, n):
            current_sum += a[j]
            if current_sum <= limit:
                current_len = j - i + 1
                if current_len > best_len:
                    best_len = current_len
                    best_start, best_end = i, j
            else:
                # Elements are non-negative, so sum will only increase further
                break

    return best_len, best_start, best_end


# Optimized Solution: Variable-size sliding window (Expand & Contract)
# Time Complexity: O(N), Auxiliary Space: O(1)
def longest_subarray_at_most_k(a, limit):
    n = len(a)
    left = 0
    current_sum = 0
    best_len = 0
    best_start, best_end = -1, -1

    # Right pointer expands the window
    for right in range(n):
        current_sum += a[right]  # Add entering element on the right

        # Contract window from left while current sum exceeds limit
        while current_sum > limit and left <= right:
            current_sum -= a[left]
            left += 1

        # If window is valid, evaluate its length
        if left <= right and current_sum <= limit:
            current_len = right - left + 1
            if current_len > best_len:
                best_len = current_len
                best_start, best_end = left, right

    return best_len, best_start, best_end


# ============================================================
# Main Function: Run All Minimum Test Cases with Pass/Fail Verification
# ============================================================
if __name__ == "__main__":
    print("============================================================")
    print("PART A: Fixed-Size Window (Maximum Sum of Subarray of Size K)")
    print("============================================================")

    fixed_tests = [
        ([2, 1, 5, 1, 3, 2], 3, 9),    # Normal case
        ([-4, -2, -7], 2, -6),         # All-negative elements
        ([6], 1, 6),                   # Single element (K = 1)
        ([1, 2, 3], 3, 6)              # Entire array (K = n)
    ]

    for a, k, expected in fixed_tests:
        ref_ans = max_sum_subarray_ref(a, k)
        opt_ans = max_sum_subarray(a, k)
        passed = (ref_ans == expected) and (opt_ans == expected)

        print(f"Array: {a}, K = {k}")
        print(f"  Expected Max Sum : {expected}")
        print(f"  Reference Result : {ref_ans}")
        print(f"  Optimized Result : {opt_ans}")
        print(f"  Status           : [{'PASS' if passed else 'FAIL'}]\n")

    print("============================================================")
    print("PART B: Variable-Size Window (Longest Subarray with Sum <= K)")
    print("============================================================")

    var_tests = [
        ([1, 2, 1, 1, 3], 4, 3),  # Verified example
        ([5, 1, 1], 2, 2),        # Window at the end
        ([4, 2, 1], 0, 0),        # Zero limit edge case
        ([0, 0, 1, 0], 1, 4)      # Zeros contributing to length
    ]

    for a, limit, expected_len in var_tests:
        ref_len, ref_s, ref_e = longest_subarray_at_most_k_ref(a, limit)
        opt_len, opt_s, opt_e = longest_subarray_at_most_k(a, limit)
        passed = (ref_len == expected_len) and (opt_len == expected_len)

        print(f"Array: {a}, Limit = {limit}")
        print(f"  Expected Length : {expected_len}")
        ref_sub = f" (indices {ref_s} to {ref_e})" if ref_len > 0 else ""
        opt_sub = f" (indices {opt_s} to {opt_e})" if opt_len > 0 else ""
        print(f"  Reference Result: Length = {ref_len}{ref_sub}")
        print(f"  Optimized Result: Length = {opt_len}{opt_sub}")
        print(f"  Status          : [{'PASS' if passed else 'FAIL'}]\n")
