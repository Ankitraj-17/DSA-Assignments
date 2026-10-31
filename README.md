# DSA2_assignments

DSA-II Programming Assignments — Sliding Window & Two-Pointer Techniques.

## Contents

| Folder | Topic |
|---|---|
| [`Assignment1/`](./Assignment1/) | Two-Pointer Technique (Remove Duplicates · Pair Sum) |
| [`Assignment2/`](./Assignment2/) | Sliding Window (Max Sum of K Elements · Longest Subarray ≤ K) |

Each assignment contains implementations in **C++17**, **Java**, and **Python 3**.

---

## Structure & Overview

### 1. [Assignment 1: Two-Pointer Technique](./Assignment1/)
- **Part A — Remove Duplicates from Sorted Array**:
  - Reference Solution: $\mathcal{O}(N)$ time, $\mathcal{O}(N)$ auxiliary space (separate list).
  - Optimized Solution: $\mathcal{O}(N)$ time, $\mathcal{O}(1)$ auxiliary space (in-place slow and fast pointers).
- **Part B — Pair Sum in a Sorted Array**:
  - Brute-Force Solution: $\mathcal{O}(N^2)$ time, $\mathcal{O}(1)$ space (nested loops).
  - Optimized Solution: $\mathcal{O}(N)$ time, $\mathcal{O}(1)$ space (opposite-end two pointers).

### 2. [Assignment 2: Sliding Window Technique](./Assignment2/)
- **Part A — Maximum Sum of Subarray of Size K (Fixed Window)**:
  - Reference Solution: $\mathcal{O}(N \cdot K)$ time, $\mathcal{O}(1)$ space (recalculates from scratch).
  - Optimized Solution: $\mathcal{O}(N)$ time, $\mathcal{O}(1)$ space (`sum += entering - leaving`).
  - Edge cases: $1 \le K \le n$, handles all-negative arrays, $K = 1$, and $K = n$.
- **Part B — Longest Subarray with Sum at Most K (Variable Window)**:
  - Reference Solution: $\mathcal{O}(N^2)$ time, $\mathcal{O}(1)$ space (enumerates all start indices).
  - Optimized Solution: $\mathcal{O}(N)$ time, $\mathcal{O}(1)$ space (expands right, contracts left while `sum > limit`).
  - Precondition: Non-negative array elements ($a[i] \ge 0$). Handles zero limit edge case.

---

## Quick Run Instructions

Navigate into either assignment folder (`Assignment1/` or `Assignment2/`):

### C++ (C++17)
```bash
g++ -std=c++17 -Wall -Wextra assignment1.cpp -o assignment1 && ./assignment1
# or for Assignment 2:
g++ -std=c++17 -Wall -Wextra assignment2.cpp -o assignment2 && ./assignment2
```

### Java
```bash
javac Assignment1.java && java Assignment1
# or for Assignment 2:
javac Assignment2.java && java Assignment2
```

### Python (Python 3)
```bash
python3 assignment1.py
# or for Assignment 2:
python3 assignment2.py
```

