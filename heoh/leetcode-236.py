# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def lowestCommonAncestor(self, root: 'TreeNode', p: 'TreeNode', q: 'TreeNode') -> 'TreeNode':
        self.route_p = None
        self.route_q = None

        self.dfs(root, p, q, [])

        lca = None
        for a, b in zip(self.route_p, self.route_q):
            if a == b:
                lca = a
            else:
                break
        return lca

    def dfs(self, node: 'TreeNode', p: 'TreeNode', q: 'TreeNode', route: List['TreeNode']):
        route.append(node)
        
        if p is node:
            self.route_p = route.copy()
            p = None
        if q is node:
            self.route_q = route.copy()
            q = None
        
        if p == q:
            return
        
        if node.left:
            self.dfs(node.left, p, q, route)
        if node.right:
            self.dfs(node.right, p, q, route)
        
        route.pop()
