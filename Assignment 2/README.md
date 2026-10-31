# DSA-II | Programming Assignment 2: Sliding Window Technique

**Course:** DSA-II (SFT AY 2026-27 B.Tech CSE)  
**Topic:** Sliding Window Technique (Fixed-Size and Variable-Size)  
**Student Name:** [Your Name]  
**Roll Number:** [Your Roll No]  
**Division / Batch:** [Your Division]  
**Date:** October 31, 2026  

---

## 1. Assignment Overview

This assignment implements, analyzes, and traces two fundamental sliding-window algorithms:

1. **Part A — Maximum Sum of a Subarray of Size K (Fixed Window)**:
   - Validates $1 \le K \le n$.
   - Handles arrays with all-negative numbers, $K = 1$, and $K = n$.
   - Compares an $\mathcal{O}(N \cdot K)$ reference solution with an $\mathcal{O}(N)$ optimized sliding window (`sum += entering - leaving`).
2. **Part B — Longest Subarray with Sum at Most K (Variable Window)**:
   - Requires non-negative array values ($a[i] \ge 0$) and limit $K \ge 0$.
   - Handles edge cases such as zero limit and all-zero subarrays.
   - Compares an $\mathcal{O}(N^2)$ reference solution with an $\mathcal{O}(N)$ optimized variable-size sliding window (expand with `right`, contract with `left` while `sum > limit`).

Implementations are provided across three languages:
- **C++**: [`assignment2.cpp`](./assignment2.cpp)
- **Java**: [`Assignment2.java`](./Assignment2.java)
- **Python**: [`assignment2.py`](./assignment2.py)

---

## 2. Algorithm Design & Pseudocode

### Part A: Maximum Sum of a Subarray of Size K (Fixed Window)

#### 1. Reference Approach
- **Mechanism**: Recalculates the sum of every $K$-element subarray from scratch using nested loops.
- **Complexity**: Time $\mathcal{O}(N \cdot K)$, Auxiliary Space $\mathcal{O}(1)$.

#### 2. Optimized Approach (Fixed-Size Sliding Window)
- **Mechanism**:
  1. Validate $1 \le K \le N$. If invalid, print an error and return an error flag (`INT_MIN`).
  2. Compute the sum of the first window $[0 \dots K-1]$ once. Initialize `max_sum = window_sum`.
  3. Slide the window from index $K$ to $N - 1$:
     - Add entering element $a[i]$.
     - Subtract leaving element $a[i - K]$.
     - Update `max_sum = max(max_sum, window_sum)`.
- **Complexity**: Time $\mathcal{O}(N)$, Auxiliary Space $\mathcal{O}(1)$.

```text
Algorithm: maxSumSubarray(a, K)
Input: array a of size N, integer K (1 <= K <= N)
Output: integer maximum sum

1. if K < 1 or K > N then:
2.     print "Error: Invalid window size K"
3.     return -infinity
4. window_sum <- 0
5. for i <- 0 to K - 1 do:
6.     window_sum <- window_sum + a[i]
7. max_sum <- window_sum
8. for i <- K to N - 1 do:
9.     window_sum <- window_sum + a[i] - a[i - K]
10.    if window_sum > max_sum then:
11.        max_sum <- window_sum
12. return max_sum
```

---

### Part B: Longest Subarray with Sum at Most K (Variable Window)

#### Precondition & Non-Negative Requirement
All array values must be **non-negative** ($a[i] \ge 0$) and $\text{limit} \ge 0$.  
*Explanation:* If negative values were present, expanding the window could decrease the sum, destroying the monotonic relationship needed for the two-pointer approach (which would instead require prefix sums with a hash map).

#### 1. Reference Approach
- **Mechanism**: Loops over each starting index $i$ and extends the subarray to index $j$, breaking early when the sum exceeds the limit.
- **Complexity**: Time $\mathcal{O}(N^2)$, Auxiliary Space $\mathcal{O}(1)$.

#### 2. Optimized Approach (Variable-Size Sliding Window)
- **Mechanism**:
  1. Initialize `left = 0`, `current_sum = 0`, `best_len = 0`.
  2. Expand: For each `right` from $0$ to $N - 1$, add $a[\text{right}]$ to `current_sum`.
  3. Contract: While `current_sum > limit` and `left <= right`:
     - Subtract $a[\text{left}]$ from `current_sum` and increment `left++`.
  4. If window $[left, right]$ is valid, compare `(right - left + 1)` with `best_len` and record boundaries.
- **Complexity**: Time $\mathcal{O}(N)$, Auxiliary Space $\mathcal{O}(1)$.

```text
Algorithm: longestSubarrayAtMostK(a, limit)
Input: non-negative array a of size N, non-negative integer limit
Output: (length, start_idx, end_idx)

1. left <- 0, current_sum <- 0, best_len <- 0, best_start <- -1, best_end <- -1
2. for right <- 0 to N - 1 do:
3.     current_sum <- current_sum + a[right]
4.     while current_sum > limit and left <= right do:
5.         current_sum <- current_sum - a[left]
6.         left <- left + 1
7.     if left <= right and current_sum <= limit then:
8.         current_len <- right - left + 1
9.         if current_len > best_len then:
10.            best_len <- current_len
11.            best_start <- left
12.            best_end <- right
13. return (best_len, best_start, best_end)
```

---

## 3. Window Movement Analysis

### Fixed Window: Reusing Overlapping Subarrays
Adjacent windows of size $K$ share $K - 1$ elements in common:
$$\text{Window}_i = [a_{i}, a_{i+1}, \dots, a_{i+K-1}]$$
$$\text{Window}_{i+1} = [a_{i+1}, \dots, a_{i+K-1}, a_{i+K}]$$
Instead of summing all $K$ elements from scratch ($\mathcal{O}(K)$ per window), we compute:
$$\text{Sum}_{i+1} = \text{Sum}_i + a[i+K] - a[i]$$
This reduces the per-step cost to $\mathcal{O}(1)$.

### Variable Window: Expand & Contract Logic
- **Right Boundary Expands:** Greedily includes new elements to extend the current valid subarray as long as possible.
- **Left Boundary Contracts:** Because all elements are non-negative, once `current_sum > limit`, expanding further to the right will never satisfy the condition. The only way to restore validity is by shrinking the window from the left (`left++`).
- **Amortized $\mathcal{O}(N)$ Time:** Each element enters the window once (via `right`) and leaves the window at most once (via `left`). Thus, the `left` pointer moves at most $N$ times across the entire algorithm.

---

## 4. Trace Tables

### Table 1A: Fixed-Window Normal Trace ($K = 3$)
**Input Array:** $a = [2, 1, 5, 1, 3, 2]$, $K = 3$, $N = 6$

| Step | Boundaries `[start, end]` | Elements | Operation | Running Sum | Max Sum So Far |
| :---: | :---: | :---: | :---: | :---: | :---: |
| **Init** | `[0, 2]` | `[2, 1, 5]` | Initial window: $2 + 1 + 5$ | $8$ | **8** |
| **1** | `[1, 3]` | `[1, 5, 1]` | Enter $a[3]=1$, Leave $a[0]=2$: $8 + 1 - 2$ | $7$ | **8** |
| **2** | `[2, 4]` | `[5, 1, 3]` | Enter $a[4]=3$, Leave $a[1]=1$: $7 + 3 - 1$ | $9$ | **9** (New Max!) |
| **3** | `[3, 5]` | `[1, 3, 2]` | Enter $a[5]=2$, Leave $a[2]=5$: $9 + 2 - 5$ | $6$ | **9** |

**Result:** Maximum Sum = **9** (Subarray `[5, 1, 3]`).

---

### Table 1B: Fixed-Window All-Negative Trace ($K = 2$)
**Input Array:** $a = [-4, -2, -7]$, $K = 2$, $N = 3$

| Step | Boundaries `[start, end]` | Elements | Operation | Running Sum | Max Sum So Far |
| :---: | :---: | :---: | :---: | :---: | :---: |
| **Init** | `[0, 1]` | `[-4, -2]` | Initial window: $(-4) + (-2)$ | $-6$ | **-6** |
| **1** | `[1, 2]` | `[-2, -7]` | Enter $a[2]=-7$, Leave $a[0]=-4$: $-6 + (-7) - (-4)$ | $-9$ | **-6** |

**Result:** Maximum Sum = **-6** (Correctly avoids returning 0).

---

### Table 1C: Fixed-Window Boundary Cases ($K = 1$ and $K = n$)
- **For $a = [6], K = 1$:**  
  Initial window `[0, 0]` has sum $6$. No slide needed. **Max Sum = 6**.
- **For $a = [1, 2, 3], K = 3$ ($K = n$):**  
  Initial window `[0, 2]` has sum $1 + 2 + 3 = 6$. No slide needed. **Max Sum = 6**.

---

### Table 2A: Variable-Window Trace Table ($\text{Limit } K = 4$)
**Input Array:** $a = [1, 2, 1, 1, 3]$, $\text{Limit } K = 4$, $N = 5$

| Step (`right`) | `a[right]` | Boundaries `[left, right]` | Current Sum | Contraction Steps (`while sum > 4`) | Current Valid Length | Best Length So Far | Best Boundaries |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **0** | $1$ | `[0, 0]` | $1$ | None ($1 \le 4$) | $1$ | **1** | `[0, 0]` |
| **1** | $2$ | `[0, 1]` | $3$ | None ($3 \le 4$) | $2$ | **2** | `[0, 1]` |
| **2** | $1$ | `[0, 2]` | $4$ | None ($4 \le 4$) | $3$ | **3** | `[0, 2]` |
| **3** | $1$ | `[0, 3]` $\rightarrow$ `[1, 3]` | $5 \rightarrow 4$ | Contract: subtract $a[0]=1$, `left = 1` | $3$ | **3** | `[0, 2]` |
| **4** | $3$ | `[1, 4]` $\rightarrow$ `[3, 4]` | $7 \rightarrow 5 \rightarrow 4$ | Contract: subtract $a[1]=2$ (`left=2`), subtract $a[2]=1$ (`left=3`) | $2$ | **3** | `[0, 2]` |

**Result:** Longest Length = **3** (Subarray `[1, 2, 1]` at indices 0–2 or `[2, 1, 1]` at indices 1–3).

---

### Table 2B: Variable-Window Contraction & Zero-Limit Cases
- **Contraction Case ($a = [5, 1, 1], \text{limit} = 2$):**  
  - $right = 0$: $a[0]=5 > 2 \rightarrow$ contracts to empty (`left = 1`), length = 0.
  - $right = 1$: $a[1]=1 \le 2 \rightarrow$ valid window `[1, 1]`, length = 1.
  - $right = 2$: $a[2]=1$, sum $1 + 1 = 2 \le 2 \rightarrow$ valid window `[1, 2]`, length = 2.  
  **Result:** Length = **2** (Subarray `[1, 1]`).
- **Zero-Limit Case ($a = [4, 2, 1], \text{limit} = 0$):**  
  Every element is $> 0$. Window contracts to empty at every step.  
  **Result:** Length = **0** (Handles zero-limit without crash).

---

## 5. Complexity Comparison Table

| Problem | Method | Time Complexity | Auxiliary Space | Justification |
| :--- | :--- | :---: | :---: | :--- |
| **Part A: Fixed Window** | Reference | $\mathcal{O}(N \cdot K)$ | $\mathcal{O}(1)$ | Recalculates sum of $K$ elements for all $N - K + 1$ windows. |
| **Part A: Fixed Window** | Optimized | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | First window takes $\mathcal{O}(K)$, remaining $N - K$ steps take $\mathcal{O}(1)$ each. |
| **Part B: Variable Window** | Reference | $\mathcal{O}(N^2)$ | $\mathcal{O}(1)$ | Tests up to $N$ starting points and extends each subarray. |
| **Part B: Variable Window** | Optimized | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Two pointers move strictly left-to-right; at most $2N$ pointer advances. |

---

## 6. Test Cases Verification Table

| Problem | Test Input | Parameter | Expected Output | Reference Output | Optimized Output | Status |
| :--- | :--- | :---: | :--- | :--- | :--- | :---: |
| **Fixed Window** | `[2, 1, 5, 1, 3, 2]` | $K = 3$ | Maximum sum = 9 | Maximum sum = 9 | Maximum sum = 9 | **PASS** |
| **Fixed Window** | `[-4, -2, -7]` | $K = 2$ | Maximum sum = -6 | Maximum sum = -6 | Maximum sum = -6 | **PASS** |
| **Fixed Window** | `[6]` | $K = 1$ | Maximum sum = 6 | Maximum sum = 6 | Maximum sum = 6 | **PASS** |
| **Fixed Window** | `[1, 2, 3]` | $K = 3$ | Maximum sum = 6 | Maximum sum = 6 | Maximum sum = 6 | **PASS** |
| **Variable Window** | `[1, 2, 1, 1, 3]` | $\text{limit} = 4$ | Longest length = 3 | Length = 3 (0 to 2) | Length = 3 (0 to 2) | **PASS** |
| **Variable Window** | `[5, 1, 1]` | $\text{limit} = 2$ | Longest length = 2 | Length = 2 (1 to 2) | Length = 2 (1 to 2) | **PASS** |
| **Variable Window** | `[4, 2, 1]` | $\text{limit} = 0$ | Length = 0 | Length = 0 | Length = 0 | **PASS** |
| **Variable Window** | `[0, 0, 1, 0]` | $\text{limit} = 1$ | Longest length = 4 | Length = 4 (0 to 3) | Length = 4 (0 to 3) | **PASS** |

---

## 7. How to Compile and Run

### C++
```bash
# Compile using C++17 with strict warnings enabled
g++ -std=c++17 -Wall -Wextra assignment2.cpp -o assignment2

# Run executable
./assignment2
```

### Java
```bash
# Compile
javac Assignment2.java

# Run class
java Assignment2
```

### Python
```bash
# Run with Python 3
python3 assignment2.py
```
