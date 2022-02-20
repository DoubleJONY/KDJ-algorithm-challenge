import collections


class LockingTree:

    def __init__(self, parent: List[int]):
        n_nodes = len(parent)
        self.parent = parent
        self.children = self.make_children_map(parent)
        self.locked = [0] * n_nodes

    def lock(self, num: int, user: int) -> bool:
        if self.locked[num]:
            return False
        self.locked[num] = user
        return True

    def unlock(self, num: int, user: int) -> bool:
        if self.locked[num] != user:
            return False
        self.locked[num] = 0
        return True

    def upgrade(self, num: int, user: int) -> bool:
        if self.locked[num]:
            return False
        if self.has_locked_ancestors(num):
            return False
        locked_nums = self.get_locked_descendant(num)
        if not locked_nums:
            return False
        
        self.locked[num] = user
        for i in locked_nums:
            self.locked[i] = 0
        return True

    def make_children_map(self, parent: List[int]) -> dict[int, list[int]]:
        children = collections.defaultdict(list)
        for i, p in enumerate(parent):
            children[p].append(i)
        return children

    def has_locked_ancestors(self, num: int) -> bool:
        parent = self.parent[num]
        if parent == -1:
            return False
        if self.locked[parent]:
            return True
        return self.has_locked_ancestors(parent)

    def get_locked_descendant(self, num: int) -> list[int]:
        locked_descendant = []
        for c in self.children[num]:
            if self.locked[c]:
                locked_descendant.append(c)
            locked_descendant += self.get_locked_descendant(c)
        return locked_descendant

# Your LockingTree object will be instantiated and called as such:
# obj = LockingTree(parent)
# param_1 = obj.lock(num,user)
# param_2 = obj.unlock(num,user)
# param_3 = obj.upgrade(num,user)
