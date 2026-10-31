import java.util.*;

public class Assignment2 {

    // Helper class to store result for Part B
    public static class SubarrayResult {
        public int length;
        public int start;
        public int end;

        public SubarrayResult(int length, int start, int end) {
            this.length = length;
            this.start = start;
            this.end = end;
        }
    }

    // ============================================================
    // PART A: Maximum Sum of a Subarray of Size K (Fixed Window)
    // Constraint: 1 <= K <= n (Array can contain negative numbers)
    // ============================================================

    // Reference Solution: Recalculates every K-element subarray sum from scratch
    // Time Complexity: O(N * K), Auxiliary Space: O(1)
    public static int maxSumSubarrayRef(int[] a, int k) {
        int n = a.length;
        if (k < 1 || k > n) {
            System.err.println("Error: Invalid window size K = " + k + " (must satisfy 1 <= K <= " + n + ")");
            return Integer.MIN_VALUE;
        }

        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i <= n - k; i++) {
            int currentSum = 0;
            for (int j = i; j < i + k; j++) {
                currentSum += a[j];
            }
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
        }
        return maxSum;
    }

    // Optimized Solution: Fixed-size sliding window
    // Time Complexity: O(N), Auxiliary Space: O(1)
    public static int maxSumSubarray(int[] a, int k) {
        int n = a.length;
        if (k < 1 || k > n) {
            System.err.println("Error: Invalid window size K = " + k + " (must satisfy 1 <= K <= " + n + ")");
            return Integer.MIN_VALUE;
        }

        // 1. Initialize sum of the first window of size K
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += a[i];
        }

        int maxSum = windowSum;

        // 2. Slide the window from index K to n - 1
        // Add entering element a[i] and subtract leaving element a[i - k]
        for (int i = k; i < n; i++) {
            windowSum += a[i] - a[i - k];
            if (windowSum > maxSum) {
                maxSum = windowSum;
            }
        }
        return maxSum;
    }

    // ============================================================
    // PART B: Longest Subarray with Sum at Most K (Variable Window)
    // Precondition: Array elements must be non-negative (a[i] >= 0, limit >= 0)
    // ============================================================

    // Reference Solution: Enumerates every start index and extends subarray
    // Time Complexity: O(N^2), Auxiliary Space: O(1)
    public static SubarrayResult longestSubarrayAtMostKRef(int[] a, int limit) {
        int n = a.length;
        SubarrayResult best = new SubarrayResult(0, -1, -1);

        for (int i = 0; i < n; i++) {
            int currentSum = 0;
            for (int j = i; j < n; j++) {
                currentSum += a[j];
                if (currentSum <= limit) {
                    int len = j - i + 1;
                    if (len > best.length) {
                        best = new SubarrayResult(len, i, j);
                    }
                } else {
                    // Non-negative array elements ensure sum only increases
                    break;
                }
            }
        }
        return best;
    }

    // Optimized Solution: Variable-size sliding window (Expand & Contract)
    // Time Complexity: O(N), Auxiliary Space: O(1)
    public static SubarrayResult longestSubarrayAtMostK(int[] a, int limit) {
        int n = a.length;
        int left = 0;
        int currentSum = 0;
        SubarrayResult best = new SubarrayResult(0, -1, -1);

        // Right pointer expands the window
        for (int right = 0; right < n; right++) {
            currentSum += a[right]; // Add entering element on the right

            // Contract window from left while current sum exceeds limit
            while (currentSum > limit && left <= right) {
                currentSum -= a[left];
                left++;
            }

            // If window is valid, evaluate its length
            if (left <= right && currentSum <= limit) {
                int currentLength = right - left + 1;
                if (currentLength > best.length) {
                    best = new SubarrayResult(currentLength, left, right);
                }
            }
        }
        return best;
    }

    // ============================================================
    // Main Function: Run All Minimum Test Cases with Pass/Fail Verification
    // ============================================================
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("PART A: Fixed-Size Window (Maximum Sum of Subarray of Size K)");
        System.out.println("============================================================");

        int[][] fixedArrays = {
            {2, 1, 5, 1, 3, 2},
            {-4, -2, -7},
            {6},
            {1, 2, 3}
        };
        int[] kValues = {3, 2, 1, 3};
        int[] expectedFixed = {9, -6, 6, 6};

        for (int i = 0; i < fixedArrays.length; i++) {
            int[] a = fixedArrays[i];
            int k = kValues[i];
            int exp = expectedFixed[i];

            int refAns = maxSumSubarrayRef(a, k);
            int optAns = maxSumSubarray(a, k);
            boolean passed = (refAns == exp) && (optAns == exp);

            System.out.println("Array: " + Arrays.toString(a) + ", K = " + k);
            System.out.println("  Expected Max Sum : " + exp);
            System.out.println("  Reference Result : " + refAns);
            System.out.println("  Optimized Result : " + optAns);
            System.out.println("  Status           : [" + (passed ? "PASS" : "FAIL") + "]\n");
        }

        System.out.println("============================================================");
        System.out.println("PART B: Variable-Size Window (Longest Subarray with Sum <= K)");
        System.out.println("============================================================");

        int[][] varArrays = {
            {1, 2, 1, 1, 3},
            {5, 1, 1},
            {4, 2, 1},
            {0, 0, 1, 0}
        };
        int[] limits = {4, 2, 0, 1};
        int[] expectedVar = {3, 2, 0, 4};

        for (int i = 0; i < varArrays.length; i++) {
            int[] a = varArrays[i];
            int limit = limits[i];
            int exp = expectedVar[i];

            SubarrayResult refAns = longestSubarrayAtMostKRef(a, limit);
            SubarrayResult optAns = longestSubarrayAtMostK(a, limit);
            boolean passed = (refAns.length == exp) && (optAns.length == exp);

            System.out.println("Array: " + Arrays.toString(a) + ", Limit = " + limit);
            System.out.println("  Expected Length : " + exp);
            System.out.print("  Reference Result: Length = " + refAns.length);
            if (refAns.length > 0) System.out.print(" (indices " + refAns.start + " to " + refAns.end + ")");
            System.out.println();
            System.out.print("  Optimized Result: Length = " + optAns.length);
            if (optAns.length > 0) System.out.print(" (indices " + optAns.start + " to " + optAns.end + ")");
            System.out.println();
            System.out.println("  Status          : [" + (passed ? "PASS" : "FAIL") + "]\n");
        }
    }
}
