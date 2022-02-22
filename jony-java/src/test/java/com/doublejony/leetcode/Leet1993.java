package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.doublejony.common.AssertResolve.resolve;

class LockingTree {

    private int[]       parents;
    private int[]       users;
    private ArrayList[] childs;


    public LockingTree() {

    }

    public LockingTree(int[] parent) {

//        int CHILD_LENGTH = parent[parent.length - 1] + 1;
        int CHILD_LENGTH = parent.length;

        parents = parent;
        users = new int[parent.length];
        childs = new ArrayList[CHILD_LENGTH];

        for (int i = 0; i < CHILD_LENGTH; i++) {
            childs[i] = new ArrayList<>();
        }
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == -1) {
                continue;
            }
            childs[parent[i]].add(i);
        }
    }

    public boolean lock(int num, int user) {

        if (isUnlocked(num)) {
            return false;
        }
        users[num] = user;
        return true;
    }

    public boolean unlock(int num, int user) {

        if (users[num] != user) {
            return false;
        }
        users[num] = 0;
        return true;
    }

    public boolean upgrade(int num, int user) {

        if (isUnlocked(num)) {
            return false;
        }

        if (isParentsUnlocked(num)) {
            return false;
        }

        List<Integer> childQueue = getChildQueue(num);
        if (childQueue == null) {
            return false;
        }

        //LETS DO THIS
        childQueue.forEach(r -> users[r] = 0);
        users[num] = user;

        return true;
    }

    private List<Integer> getChildQueue(int num) {

        List<Integer> childQueue = new ArrayList<>();
        boolean hasLockedChild = false;
        for (int i = 0; i < parents.length; i++) {
            if (parents[i] == num) {
                childQueue.add(i);
            }
        }
        for (int i = 0; i < childQueue.size(); i++) {
            int c = childQueue.get(i);
            if (users[c] != 0) {
                hasLockedChild = true;
            }
            for (int j = 0; j < parents.length; j++) {
                if (parents[j] == c) {
                    childQueue.add(j);
                }
            }
        }

        return hasLockedChild ? childQueue : null;
    }

    private boolean isParentsUnlocked(int num) {

        int p = num;
        while (p != 0) {
            p = parents[p];
            if (users[p] != 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isUnlocked(int num) {

        return users[num] != 0;
    }
}

@RunWith(DataProviderRunner.class)
public class Leet1993 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{"LockingTree", "lock", "unlock", "unlock", "lock", "upgrade", "lock"},
                        new int[][]{{-1, 0, 0, 1, 1, 2, 2}, {2, 2}, {2, 3}, {2, 2}, {4, 5}, {0, 1}, {0, 1}},
                        new Boolean[]{null, true, false, true, true, true, false}
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] commands, int[][] parents, Boolean[] expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().setLockingTree(commands, parents), timer.stop());
    }

    class Solution {
        public Boolean[] setLockingTree(String[] commands, int[][] parents) {

            LockingTree lockingTree = new LockingTree();
            List<Boolean> result = new ArrayList<>();

            for (int i = 0; i < commands.length; i++) {
                switch (commands[i]) {
                    case "LockingTree":
                        lockingTree = new LockingTree(parents[i]);
                        result.add(null);
                        break;
                    case "lock":
                        result.add(lockingTree.lock(parents[i][0], parents[i][1]));
                        break;
                    case "unlock":
                        result.add(lockingTree.unlock(parents[i][0], parents[i][1]));
                        break;
                    case "upgrade":
                        result.add(lockingTree.upgrade(parents[i][0], parents[i][1]));
                        break;
                }
            }

            return result.toArray(new Boolean[0]);
        }
    }
}
