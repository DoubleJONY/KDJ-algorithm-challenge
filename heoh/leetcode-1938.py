class Solution:
    def maxGeneticDifference(self, parents: List[int], queries: List[List[int]]) -> List[int]:
        def values(index: int):
            while index != -1:
                yield index
                index = parents[index]

        output = []
        for query in queries:
            node, val = query
            best = max([v ^ val for v in values(node)])
            output.append(best)

        return output
