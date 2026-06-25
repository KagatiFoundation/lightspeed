# Register Allocation Using Graph Coloring

## 1. Introduction
<p style="text-align: justify">
	Register allocation is one of the most critical phases of a compiler's backend. Its primary goal
	is to solve a fundamental resource conflict: how do we map an infinite number of temporary variables (virtual registers) down to a strictly limited number of actual physical hardware
	registers?
	<br>
	<br>
	To solve this systematically, modern production compilers treat register allocation as a Graph
	Coloring problem: an algorithmic approach where variables are treated as nodes, conflicts are
	treated as edges, and physical registers are treated as colors. If two variables need to exist at
	the same time, they connect; our job is to color the entire web so that no two connected variables
	share the same color.
</p>

## 2. Implementation
The entire allocation pipeline breaks down into distinct, sequential phases:

### 2.1. Phase 1: Backward Liveness Analysis
<p style="text-align: justify">
	Before we can build a conflict graph, we must establish a precise timeline of existence for every
	variable in our program. This initial phase utilizes <b>Backward Liveness Analysis</b> to scan our
	intermediate representation from the bottom up. By tracing usage in reverse, we map the exact birth
	(definition) and death (last use) coordinates for every virtual register, compiling them into a
	definitive list of Live Intervals. These intervals serve as the raw chronological data that
	dictates exactly when a variable must be kept alive in memory.
</p>


<p style="text-align: justify">
	For explaining the algorithm we first need to setup a scenario. I am going to use the following Three-Address Code (3AC) block.
</p>

```
v0 = 10
v1 = 20
v2 = v0 + v1
v3 = 30
v4 = v3 * 2
v5 = v4 + 5
v6 = v1 - 1
```

<p style="text-align: justify">
	Before diving into the algorithm, let's look at the raw sequence of instructions. We will assign a permanent Line Index (0 through 6) to each instruction to act as our timeline:
</p>

Line 0: `v0 = 10` (Defines `v0`)

Line 1: `v1 = 20` (Defines `v1`)

Line 2: `v2 = v0 + v1` (Uses `v0`, `v1`; Defines `v2`)

Line 3: `v3 = 30` (Defines `v3`)

Line 4: `v4 = v3 * 2` (Uses `v3`; Defines `v4`)

Line 5: `v5 = v4 + 5` (Uses `v4`; Defines `v5`)

Line 6: `v6 = v1 - 1` (Uses `v1`; Defines `v6`)

Instead of reading from Line 0 down, our algorithm starts at the very bottom (Line 6) and marches upward to Line 0. We maintain a running checklist of variables called ```currently_live```. ```currently_live``` stores variables which are alive in the moment when a certain line is being analysed. We also maintain a map called ```intervals_map``` from variable to its starting and ending position (which are basically just instructions' positions in the instructions array).

#### 2.1.1: Running Backward Analysis Algorithm

<b>Line 6:</b> ```v6 = v1 - 1```

Uses: `v1`
Defines: `v6`

We analyze **uses** and **definitions** separately. We begin with the definition analysis.

Since each definition corresponds to a unique variable in the instruction list, we must create a new interval mapping for every definition encountered. The first step is to determine whether the defined variable is currently live. We use the `currently_live` set for this purpose.

Because `currently_live` contains all variables that are active at the current point of analysis, `v6` would be present in the set if it had been encountered previously during the reverse traversal. However, for the final instruction, this can never be the case because no instructions have been processed yet. Consequently, `currently_live` is initially empty.

Since `v6` is not present in `currently_live`, a new entry is created in `intervals_map` with both the start and end positions set to the current instruction index.

If `v6` had already been present in `currently_live`, it would indicate that a later use of `v6` had already been encountered during the backward traversal. In that case, the current definition would mark the beginning of `v6`'s live range. We would therefore update the interval's start position to the current instruction index and remove `v6` from `currently_live`, since its lifetime cannot extend beyond its defining instruction.

Next, we process the **uses** list. For each used variable, we check whether an interval has already been created. If no interval exists, we create a new interval whose start and end positions are both initialized to the current instruction index, and add the variable to `currently_live`. If an interval already exists, we simply update its start position to the current instruction index, extending the live range backward.

In this example, `v1` has not been encountered previously, so a new interval is created for it and `v1` is added to `currently_live`.

**State after processing:**

* `currently_live`: `{v1}`
* `intervals_map`: `{ v6: (6, 6), v1: (6, 6) }`