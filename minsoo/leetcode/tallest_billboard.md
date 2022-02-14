## Do:

$\forall \{A, B \subseteq Rods\}$ where $A \cap B = \emptyset$, update a dictionary which its keys are represented by $h_{\text{diff}} = \left | \sum_{a \in A} a - \sum_{b \in B} b \right |$.



#### 1. Add rod to the taller one

In this case, the shorter counterpart doesn't change at all so we should update using that counterpart.

```python
_dp[h_diff + h] = max(_dp[h_diff + h], _h)
```



#### 2. Add rod to the shorter one

There are two cases in this scenario:

1. If $h_{\text{diff}} \ge h_{\text{rod}}$, the height difference will shrink to $h_{\text{diff}} := h_{\text{diff}} - h_{\text{rod}}$ while shorter one remains the shorter position. Therefore we should update with the present height of the shorter rod.
2. If $h_{\text{diff}} \le h_{\text{rod}}$, two rods exchange there positions; taller to shorter, shorter to taller. We therefore update with the height of the previous taller rod: previous shorter rod + height difference

```python
if h_diff >= h:
	_dp[h_diff - h] = max(_dp[h_diff - h], _h + h)
else:
	_dp[h - h_diff] = max(_dp[h - h_diff], _h + h_diff)
```



## Code

```python
def tallestBillboard(self, rods: List[int]) -> int:
	dp = defaultdict(int)
	dp[0] = 0

	for h in rods:
		_dp = dp.copy()
		for h_diff, _h in dp.items():
			_dp[h_diff + h] = max(_dp[h_diff + h], _h)
            if h_diff >= h:
                _dp[h_diff - h] = max(_dp[h_diff - h], _h + h)
            else:
                _dp[h - h_diff] = max(_dp[h - h_diff], _h + h_diff)
		dp = _dp
	return dp[0]
```

https://leetcode.com/problems/tallest-billboard/
