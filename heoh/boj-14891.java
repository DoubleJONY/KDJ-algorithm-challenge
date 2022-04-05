import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    List<Gear> gears;

    private void solve(Scanner input) {
        gears = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            gears.add(new Gear(i, input.next()));
        }

        int K = input.nextInt();
        for (int i = 0; i < K; i++) {
            int gearId = input.nextInt() - 1;
            int direction = input.nextInt();
            rotateAndPropagate(gearId, direction);
        }

        int score = getScore();
        System.out.println(score);
    }

    private void rotateAndPropagate(int mid, int direction) {
        int left = mid;
        int right = mid;

        for (int i = mid - 1; i >= 0; i--) {
            if (!isRotatableLeft(i + 1, i)) break;
            left = i;
        }
        for (int i = mid + 1; i < gears.size(); i++) {
            if (!isRotatableRight(i - 1, i)) break;
            right = i;
        }

        for (int i = left; i <= right; i++) {
            int d = (Math.abs(mid - i) % 2 == 0) ? direction : -direction;
            rotate(i, d);
        }
    }

    private boolean isRotatableLeft(int id, int leftId) {
        Gear me = gears.get(id);
        Gear left = gears.get(leftId);
        return me.getLeft() != left.getRight();
    }

    private boolean isRotatableRight(int id, int rightId) {
        Gear me = gears.get(id);
        Gear right = gears.get(rightId);
        return me.getRight() != right.getLeft();
    }

    private void rotate(int id, int direction) {
        Gear me = gears.get(id);
        if (direction == 1) {
            me.rotateCW();
        } else {
            me.rotateCCW();
        }
    }

    private int getScore() {
        int score = 0;
        for (int i = 0; i < gears.size(); i++) {
            score |= (gears.get(i).getTop()) << i;
        }
        return score;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        new Solution().solve(input);
        input.close();
    }
}

class Gear {
    int id;
    LinkedList<Integer> cogs;

    public Gear(int id, String state) {
        this.id = id;
        this.cogs = parseState(state);
    }

    private LinkedList<Integer> parseState(String state) {
        return state.chars()
                .map(c -> c - '0')
                .boxed()
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public void rotateCW() {
        cogs.addFirst(cogs.pollLast());
    }

    public void rotateCCW() {
        cogs.addLast(cogs.pollFirst());
    }

    public int getTop() {
        return cogs.get(0);
    }

    public int getLeft() {
        return cogs.get(6);
    }

    public int getRight() {
        return cogs.get(2);
    }

    @Override
    public String toString() {
        return "Gear{" +
                "cogs=" + cogs +
                '}';
    }
}
