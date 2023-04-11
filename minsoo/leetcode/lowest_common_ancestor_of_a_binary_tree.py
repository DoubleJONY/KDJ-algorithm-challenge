# https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/description/

# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def lowestCommonAncestor(self, root: 'TreeNode', p: 'TreeNode', q: 'TreeNode') -> 'TreeNode':
        if root is None or root == p or root == q:
            return root

        lnode = self.lowestCommonAncestor(root.left, p, q)
        rnode = self.lowestCommonAncestor(root.right, p, q)

        if lnode is not None and rnode is not None:
            return root
        return lnode or rnode
