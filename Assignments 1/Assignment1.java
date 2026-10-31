import java.util.*;

public class Assignment1 {


    // PART A: Remove Duplicates from Sorted Array

 
    // Time Complexity: O(N), Auxiliary Space: O(N)
    public static List<Integer> removeDuplicatesRef(int[] arr) {
        List<Integer> uniqueList = new ArrayList<>();
        for (int val : arr) {
            // Only append if the list is empty or the current value is new
            if (uniqueList.isEmpty() || uniqueList.get(uniqueList.size() - 1) != val) {
                uniqueList.add(val);
            }
        }
        return uniqueList;
    }

    // Optimized Solution: In-place modification using slow and fast pointers
    // Time Complexity: O(N), Auxiliary Space: O(1)
    public static int removeDuplicates(int[] arr) {
        // Boundary check: empty array has 0 unique elements
        if (arr.length == 0) return 0;

        int slow = 0; // Tracks the index of the last confirmed unique element

        // Fast pointer scans the rest of the array
        for (int fast = 1; fast < arr.length; fast++) {
            // When a new distinct element is encountered
            if (arr[fast] != arr[slow]) {
                slow++;                 // Move slow pointer forward
                arr[slow] = arr[fast];  // Overwrite in-place with the new distinct value
            }
        }
        // Number of distinct elements is index + 1
        return slow + 1;
    }
    // PART B: Pair Sum in a Sorted Array
    // Time Complexity: O(N^2), Auxiliary Space: O(1)
    public static int[] pairSumBruteForce(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) {
                    return new int[]{i, j}; // Return matching 0-based indices
                }
            }
        }
        return new int[]{-1, -1}; // No pair found
    }

    // Optimized Solution: Opposite-end two pointers
    // Time Complexity: O(N), Auxiliary Space: O(1)
    public static int[] pairSumOptimized(int[] arr, int target) {
        int left = 0;               // Start pointer at beginning (smallest element)
        int right = arr.length - 1; // End pointer at end (largest element)

        // Ensure distinct elements (never pair an element with itself)
        while (left < right) {
            int sum = arr[left] + arr[right];

            if (sum == target) {
                return new int[]{left, right}; // Target found
            } else if (sum < target) {
                left++;  // Sum too small: move left pointer right to increase sum
            } else {
                right--; // Sum too large: move right pointer left to decrease sum
            }
        }
        return new int[]{-1, -1}; // No valid pair found
    }

    // Main Function: Run Required Test Cases

    public static void main(String[] args) {
        System.out.println("--- PART A: Remove Duplicates ---");
        int[][] dupTests = {
            {1, 1, 2, 2, 2, 3, 4, 4}, // Normal duplicate-heavy case
            {5},                       // Single-element boundary case
            {},                        // Empty array boundary case
            {-3, -3, -1, 0, 0}         // Negative numbers and zeros
        };

        for (int[] arr : dupTests) {
            System.out.print("Original: " + Arrays.toString(arr) + " -> ");
            int k = removeDuplicates(arr);
            System.out.print("k = " + k + ", Unique Prefix: [");
            for (int i = 0; i < k; i++) {
                System.out.print(arr[i] + (i + 1 < k ? ", " : ""));
            }
            System.out.println("]");
        }

        System.out.println("\n--- PART B: Pair Sum ---");
        int[][] sumArrays = {
            {1, 2, 4, 7, 11, 15}, // Pair found in middle/ends
            {1, 3, 5, 8},          // No pair equals 7 (returns -1, -1)
            {2, 4, 6},            // Target out of range
            {1, 2, 2, 3}           // Duplicate values forming target
        };
        int[] targets = {15, 7, 20, 4};

        for (int t = 0; t < sumArrays.length; t++) {
            int[] arr = sumArrays[t];
            int target = targets[t];
            int[] res = pairSumOptimized(arr, target);
            System.out.println("Target: " + target + " -> Indices: (" + res[0] + ", " + res[1] + ")");
        }
    }
}
