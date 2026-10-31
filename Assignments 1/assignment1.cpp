#include <iostream>
#include <vector>
using namespace std;

// PART A: Remove Duplicates from Sorted Array

// Time Complexity: O(N), Auxiliary Space: O(N)
vector<int> removeDuplicatesRef(const vector<int>& arr) {
    vector<int> uniqueList;
    for (int val : arr) {
        // Only append if the list is empty or the current value is new
        if (uniqueList.empty() || uniqueList.back() != val) {
            uniqueList.push_back(val);
        }
    }
    return uniqueList;
}

// Optimized Solution: In-place modification using slow and fast pointers
// Time Complexity: O(N), Auxiliary Space: O(1)
int removeDuplicates(vector<int>& arr) {
    // Boundary check: empty array has 0 unique elements
    if (arr.empty()) return 0;

    int slow = 0; // Tracks the index of the last confirmed unique element

    // Fast pointer scans the rest of the array
    for (int fast = 1; fast < arr.size(); fast++) {
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
// Brute-Force Solution: Check all pairs (i, j) with i < j
// Time Complexity: O(N^2), Auxiliary Space: O(1)
pair<int, int> pairSumBruteForce(const vector<int>& arr, int target) {
    int n = arr.size();
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (arr[i] + arr[j] == target) {
                return {i, j}; // Return 0-based indices of matching pair
            }
        }
    }
    return {-1, -1}; // No pair found
}

// Optimized Solution: Opposite-end two pointers
// Time Complexity: O(N), Auxiliary Space: O(1)
pair<int, int> pairSumOptimized(const vector<int>& arr, int target) {
    int left = 0;                  // Start pointer at beginning (smallest element)
    int right = (int)arr.size() - 1; // End pointer at end (largest element)

    // Ensure distinct elements (never pair an element with itself)
    while (left < right) {
        int sum = arr[left] + arr[right];

        if (sum == target) {
            return {left, right};  // Target found
        } else if (sum < target) {
            left++;  // Sum too small: move left pointer right to increase sum
        } else {
            right--; // Sum too large: move right pointer left to decrease sum
        }
    }
    return {-1, -1}; // No valid pair found
}


// Main Function: Run Required Test Cases

int main() {
    cout << "--- PART A: Remove Duplicates ---\n";
    vector<vector<int>> dupTests = {
        {1, 1, 2, 2, 2, 3, 4, 4}, // Normal duplicate-heavy case
        {5},                       // Single-element boundary case
        {},                        // Empty array boundary case
        {-3, -3, -1, 0, 0}         // Negative numbers and zeros
    };

    for (auto arr : dupTests) {
        cout << "Original: ";
        for (int x : arr) cout << x << " ";
        cout << "\n";

        int k = removeDuplicates(arr);
        cout << "k = " << k << ", Unique Prefix: ";
        for (int i = 0; i < k; i++) cout << arr[i] << " ";
        cout << "\n\n";
    }

    cout << "--- PART B: Pair Sum ---\n";
    vector<pair<vector<int>, int>> sumTests = {
        {{1, 2, 4, 7, 11, 15}, 15}, // Pair found in middle/ends
        {{1, 3, 5, 8}, 7},          // No pair equals 7 (returns -1, -1)
        {{2, 4, 6}, 20},            // Target out of range
        {{1, 2, 2, 3}, 4}           // Duplicate values forming target
    };

    for (auto& test : sumTests) {
        vector<int>& arr = test.first;
        int target = test.second;

        pair<int, int> res = pairSumOptimized(arr, target);
        cout << "Target: " << target << " -> Indices: (" << res.first << ", " << res.second << ")\n";
    }

    return 0;
}
