#include <iostream>
#include <vector>
#include <climits>
#include <string>
using namespace std;

// Result structure to hold subarray information for Part B
struct SubarrayResult {
    int length;
    int start;
    int end;
};
// Reference Solution: Recalculates every K-element subarray sum from scratch
// Time Complexity: O(N * K), Auxiliary Space: O(1)
int maxSumSubarrayRef(const vector<int>& a, int k) {
    int n = (int)a.size();
    if (k < 1 || k > n) {
        cerr << "Error: Invalid window size K = " << k << " (must satisfy 1 <= K <= " << n << ")\n";
        return INT_MIN;
    }

    int maxSum = INT_MIN;

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
int maxSumSubarray(const vector<int>& a, int k) {
    int n = (int)a.size();
    if (k < 1 || k > n) {
        cerr << "Error: Invalid window size K = " << k << " (must satisfy 1 <= K <= " << n << ")\n";
        return INT_MIN;
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
SubarrayResult longestSubarrayAtMostKRef(const vector<int>& a, int limit) {
    int n = (int)a.size();
    SubarrayResult best = {0, -1, -1};

    for (int i = 0; i < n; i++) {
        int currentSum = 0;
        for (int j = i; j < n; j++) {
            currentSum += a[j];
            if (currentSum <= limit) {
                int len = j - i + 1;
                if (len > best.length) {
                    best = {len, i, j};
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
SubarrayResult longestSubarrayAtMostK(const vector<int>& a, int limit) {
    int n = (int)a.size();
    int left = 0;
    int currentSum = 0;
    SubarrayResult best = {0, -1, -1};

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
                best = {currentLength, left, right};
            }
        }
    }
    return best;
}

// Helper to format an array as string [x, y, ...]
string formatArray(const vector<int>& a) {
    string s = "[";
    int n = (int)a.size();
    for (int i = 0; i < n; i++) {
        s += to_string(a[i]) + (i + 1 < n ? ", " : "");
    }
    s += "]";
    return s;
}

// ============================================================
// Main Function: Run All Minimum Test Cases with Pass/Fail Verification
// ============================================================
int main() {
    cout << "============================================================\n";
    cout << "PART A: Fixed-Size Window (Maximum Sum of Subarray of Size K)\n";
    cout << "============================================================\n";

    struct FixedTest {
        vector<int> a;
        int k;
        int expected;
    };

    vector<FixedTest> fixedTests = {
        {{2, 1, 5, 1, 3, 2}, 3, 9},   // Normal case
        {{-4, -2, -7}, 2, -6},        // All-negative elements
        {{6}, 1, 6},                  // Single element (K = 1)
        {{1, 2, 3}, 3, 6}             // Entire array (K = n)
    };

    for (const auto& test : fixedTests) {
        int refAns = maxSumSubarrayRef(test.a, test.k);
        int optAns = maxSumSubarray(test.a, test.k);
        bool passed = (refAns == test.expected) && (optAns == test.expected);

        cout << "Array: " << formatArray(test.a) << ", K = " << test.k << "\n";
        cout << "  Expected Max Sum : " << test.expected << "\n";
        cout << "  Reference Result : " << refAns << "\n";
        cout << "  Optimized Result : " << optAns << "\n";
        cout << "  Status           : [" << (passed ? "PASS" : "FAIL") << "]\n\n";
    }

    cout << "============================================================\n";
    cout << "PART B: Variable-Size Window (Longest Subarray with Sum <= K)\n";
    cout << "============================================================\n";

    struct VarTest {
        vector<int> a;
        int limit;
        int expectedLen;
    };

    vector<VarTest> varTests = {
        {{1, 2, 1, 1, 3}, 4, 3}, // Verified example
        {{5, 1, 1}, 2, 2},       // Window at the end
        {{4, 2, 1}, 0, 0},       // Zero limit edge case
        {{0, 0, 1, 0}, 1, 4}     // Zeros contributing to length
    };

    for (const auto& test : varTests) {
        SubarrayResult refAns = longestSubarrayAtMostKRef(test.a, test.limit);
        SubarrayResult optAns = longestSubarrayAtMostK(test.a, test.limit);
        bool passed = (refAns.length == test.expectedLen) && (optAns.length == test.expectedLen);

        cout << "Array: " << formatArray(test.a) << ", Limit = " << test.limit << "\n";
        cout << "  Expected Length : " << test.expectedLen << "\n";
        cout << "  Reference Result: Length = " << refAns.length;
        if (refAns.length > 0) cout << " (indices " << refAns.start << " to " << refAns.end << ")";
        cout << "\n";
        cout << "  Optimized Result: Length = " << optAns.length;
        if (optAns.length > 0) cout << " (indices " << optAns.start << " to " << optAns.end << ")";
        cout << "\n";
        cout << "  Status          : [" << (passed ? "PASS" : "FAIL") << "]\n\n";
    }

    return 0;
}
