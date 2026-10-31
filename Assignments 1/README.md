# DSA-II | Programming Assignment 1: Two-Pointer Technique

**Course:** DSA-II (SFT AY 2026-27 B.Tech CSE)  
**Topic:** Two-Pointer Technique (Slow/Fast Pointers & Opposite-End Pointers)  
**Student Name:** [Your Name]  
**Roll Number:** [Your Roll No]  
**Division / Batch:** [Your Division]  
**Date:** October 31, 2026  

---

## 1. Assignment Overview

This assignment implements and analyzes two foundational array problems using both brute-force/reference and optimized two-pointer algorithms:

1. **Part A — Remove Duplicates from a Sorted Array**:
   - In-place duplicate removal using slow and fast pointers.
   - Reference approach using extra memory.
2. **Part B — Pair Sum in a Sorted Array**:
   - Finding indices $(i, j)$ such that $arr[i] + arr[j] = T$ ($i < j$) using opposite-end two pointers.
   - Brute-force approach using nested loops.

Implementations are provided across three languages:
- **C++**: [`assignment1.cpp`](./assignment1.cpp)
- **Java**: [`Assignment1.java`](./Assignment1.java)
- **Python**: [`assignment1.py`](./assignment1.py)

---

## 2. Algorithm Design & Pseudocode

### Part A: Remove Duplicates from Sorted Array

#### Problem Interpretation
Given a non-decreasing sorted array, modify it in place such that the first $k$ elements contain unique values in their original relative order. Return $k$.

#### 1. Reference Approach
- **Mechanism**: Iterate through the array. Check if the element has already been added to a new result list. If not, append it.
- **Complexity**: Time $\mathcal{O}(N)$, Auxiliary Space $\mathcal{O}(N)$.

#### 2. Optimized Approach (Slow & Fast Pointers)
- **Mechanism**:
  - `slow` pointer starts at index `0` (tracks index of the last confirmed unique element).
  - `fast` pointer iterates from index `1` to `N - 1`.
  - When `arr[fast] != arr[slow]`, a new distinct value is found: increment `slow` by 1 and overwrite `arr[slow] = arr[fast]`.
  - At termination, unique count $k = \text{slow} + 1$.
- **Complexity**: Time $\mathcal{O}(N)$, Auxiliary Space $\mathcal{O}(1)$.

```text
Algorithm: removeDuplicates(arr)
Input: sorted array arr of size N
Output: integer k

1. if N == 0 return 0
2. slow <- 0
3. for fast <- 1 to N - 1 do:
4.     if arr[fast] != arr[slow] then:
5.         slow <- slow + 1
6.         arr[slow] <- arr[fast]
7. return slow + 1
```

---

### Part B: Pair Sum in a Sorted Array

#### Problem Interpretation
Given a sorted array of integers and a target sum $T$, find two distinct 0-based indices $i$ and $j$ ($i < j$) such that $arr[i] + arr[j] = T$. If no such pair exists, return $(-1, -1)$.

#### 1. Brute-Force Approach
- **Mechanism**: Two nested loops testing all pairs $(i, j)$ where $0 \le i < j < N$.
- **Complexity**: Time $\mathcal{O}(N^2)$, Auxiliary Space $\mathcal{O}(1)$.

#### 2. Optimized Approach (Opposite-End Two Pointers)
- **Mechanism**:
  - `left` initialized to `0` (smallest element).
  - `right` initialized to `N - 1` (largest element).
  - While `left < right`:
    - Calculate `sum = arr[left] + arr[right]`.
    - If `sum == target`: Return `(left, right)`.
    - If `sum < target`: Increment `left++` (moves to a larger value to increase sum).
    - If `sum > target`: Decrement `right--` (moves to a smaller value to decrease sum).
  - If pointers meet/cross without finding a match: Return `(-1, -1)`.
- **Complexity**: Time $\mathcal{O}(N)$, Auxiliary Space $\mathcal{O}(1)$.

```text
Algorithm: pairSumOptimized(arr, target)
Input: sorted array arr of size N, integer target
Output: pair of indices (left, right) or (-1, -1)

1. left <- 0, right <- N - 1
2. while left < right do:
3.     sum <- arr[left] + arr[right]
4.     if sum == target then:
5.         return (left, right)
6.     else if sum < target then:
7.         left <- left + 1
8.     else:
9.         right <- right - 1
10. return (-1, -1)
```

---

## 3. Pointer Movement Analysis

### Why does sorted order enable $\mathcal{O}(1)$ space in Duplicate Removal?
Because duplicate elements are always contiguous in a sorted array. We only need to check the current element against `arr[slow]`. There is no need for a hash set or extra memory.

### Why do pointers move in Pair Sum?
1. **`left++` when `sum < target`:**
   - Because the array is sorted in ascending order, `arr[left + 1] >= arr[left]`.
   - Incrementing `left` is the only way to increase the pair sum.
2. **`right--` when `sum > target`:**
   - Because the array is sorted in ascending order, `arr[right - 1] <= arr[right]`.
   - Decrementing `right` is the only way to decrease the pair sum.
3. **Correctness:**
   - At each step, either the current left element cannot form a pair with any remaining candidate, or the current right element cannot form a pair with any remaining candidate. No potential valid pairs are ever skipped.

---

## 4. Dry-Run Tables

### Dry Run 1: Remove Duplicates (Optimized)
**Input Array:** `arr = [1, 1, 2, 2, 2, 3, 4, 4]`, $N = 8$  
**Initial State:** `slow = 0`, `arr[slow] = 1`

| Step | `fast` | `arr[fast]` | `arr[slow]` | Comparison (`arr[fast] != arr[slow]`) | Action Taken | Array State (first $k$ elements) |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| 1 | 1 | 1 | 1 | False ($1 == 1$) | Duplicate; skip | `[1, ...]` |
| 2 | 2 | 2 | 1 | True ($2 \ne 1$) | `slow++` (1), `arr[1] = 2` | `[1, 2, ...]` |
| 3 | 3 | 2 | 2 | False ($2 == 2$) | Duplicate; skip | `[1, 2, ...]` |
| 4 | 4 | 2 | 2 | False ($2 == 2$) | Duplicate; skip | `[1, 2, ...]` |
| 5 | 5 | 3 | 2 | True ($3 \ne 2$) | `slow++` (2), `arr[2] = 3` | `[1, 2, 3, ...]` |
| 6 | 6 | 4 | 3 | True ($4 \ne 3$) | `slow++` (3), `arr[3] = 4` | `[1, 2, 3, 4, ...]` |
| 7 | 7 | 4 | 4 | False ($4 == 4$) | Duplicate; skip | `[1, 2, 3, 4, ...]` |

**Result:** Loop terminates. $k = \text{slow} + 1 = 4$. Unique prefix: `[1, 2, 3, 4]`.

---

### Dry Run 2: Pair Sum (Optimized)
**Input Array:** `arr = [1, 2, 4, 7, 11, 15]`, $\text{Target} = 15$, $N = 6$

| Step | `left` | `right` | `arr[left]` | `arr[right]` | `sum` | Comparison | Action Taken |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| 1 | 0 | 5 | 1 | 15 | 16 | $16 > 15$ | Sum too large $\rightarrow$ `right--` (4) |
| 2 | 0 | 4 | 1 | 11 | 12 | $12 < 15$ | Sum too small $\rightarrow$ `left++` (1) |
| 3 | 1 | 4 | 2 | 11 | 13 | $13 < 15$ | Sum too small $\rightarrow$ `left++` (2) |
| 4 | 2 | 4 | 4 | 11 | 15 | $15 == 15$ | Target match found! Return indices `(2, 4)` |

**Result:** Indices `(2, 4)` returned corresponding to values $4 + 11 = 15$.

---

## 5. Complexity Comparison Table

| Problem | Method | Time Complexity | Auxiliary Space | Justification |
| :--- | :--- | :---: | :---: | :--- |
| **Part A: Duplicates** | Reference | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | Single pass; accumulates unique values in a separate list. |
| **Part A: Duplicates** | Optimized | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Single pass using two pointers modifying the array in place. |
| **Part B: Pair Sum** | Brute Force | $\mathcal{O}(N^2)$ | $\mathcal{O}(1)$ | Tests all $\frac{N(N-1)}{2}$ index pairs with nested loops. |
| **Part B: Pair Sum** | Optimized | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Pointers start at opposite ends and converge in at most $N$ steps. |

---

## 6. Test Cases Verification Table

| Problem | Test Input | Target | Expected Output | Actual Output | Status |
| :--- | :--- | :---: | :--- | :--- | :---: |
| **Duplicates** | `[1, 1, 2, 2, 2, 3, 4, 4]` | — | $k = 4$, prefix `[1, 2, 3, 4]` | $k = 4$, prefix `[1, 2, 3, 4]` | **PASS** |
| **Duplicates** | `[5]` | — | $k = 1$, prefix `[5]` | $k = 1$, prefix `[5]` | **PASS** |
| **Duplicates** | `[]` | — | $k = 0$, no invalid access | $k = 0$, prefix `[]` | **PASS** |
| **Duplicates** | `[-3, -3, -1, 0, 0]` | — | $k = 3$, prefix `[-3, -1, 0]` | $k = 3$, prefix `[-3, -1, 0]` | **PASS** |
| **Pair Sum** | `[1, 2, 4, 7, 11, 15]` | $15$ | Indices `(2, 4)` ($4+11$) | `(2, 4)` | **PASS** |
| **Pair Sum** | `[1, 3, 5, 8]` | $7$ | `(-1, -1)` *(Note below)* | `(-1, -1)` | **PASS** |
| **Pair Sum** | `[2, 4, 6]` | $20$ | `(-1, -1)` | `(-1, -1)` | **PASS** |
| **Pair Sum** | `[1, 2, 2, 3]` | $4$ | Two different indices | `(0, 3)` ($1+3$) | **PASS** |

> **Note on `[1, 3, 5, 8]` with $T = 7$:**  
> The assignment prompt lists indices `(0, 2)` as the expected output, but `arr[0] + arr[2] = 1 + 5 = 6`, not $7$. In fact, no pair in `[1, 3, 5, 8]` sums to $7$. Returning `(-1, -1)` is mathematically correct. If target $T = 6$, the algorithm outputs `(0, 2)`.

---

## 7. How to Compile and Run

### C++
```bash
# Compile with C++17
g++ -std=c++17 assignment1.cpp -o assignment1

# Run executable
./assignment1
```

### Java
```bash
# Compile
javac Assignment1.java

# Run class
java Assignment1
```

### Python
```bash
# Run with Python 3
python3 assignment1.py
```

