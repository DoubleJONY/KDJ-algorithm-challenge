import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    private static final int MAX_COUNT = 10;

    private static final int DIR_NONE       = 0;
    private static final int DIR_RIGHT      = 1;
    private static final int DIR_UP         = 2;
    private static final int DIR_LEFT       = 3;
    private static final int DIR_DOWN       = 4;
    private static final int[] REVERSED_DIR = { DIR_NONE,
            DIR_LEFT, DIR_DOWN, DIR_RIGHT, DIR_UP
            };

    private static final int[] DIR_OFFSET_Y = {  0,  0, -1,  0,  1 };
    private static final int[] DIR_OFFSET_X = {  0,  1,  0, -1,  0 };
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Board board = inputBoard(scan);
        
        int answer = solve(board, MAX_COUNT);
        System.out.println(answer);
        
        scan.close();
    }

    private static int solve(Board board, int maxCount) {
        Queue<BoardState> queue = new LinkedList<>();
        queue.add(new BoardState(0, board, DIR_NONE));

        while (!queue.isEmpty()) {
            BoardState state = queue.remove();
            if (state.count > maxCount) {
                return -1;
            }

            boolean redBallIsGoalIn = state.redBall.isGoalIn(board);
            boolean blueBallIsGoalIn = state.blueBall.isGoalIn(board);
            if (blueBallIsGoalIn) {
                continue;
            }
            if (redBallIsGoalIn) {
                return state.count;
            }
            
            for (int dir = DIR_RIGHT; dir <= DIR_DOWN; dir++) {
                // 최적화: 가지치기
                if (dir == state.lastDir) {
                    continue;
                }
                if (dir == REVERSED_DIR[state.lastDir]) {
                    continue;
                }

                state.apply(board);
                board.move(dir);

                queue.add(new BoardState(state.count + 1, board, dir));
            }
        }
        
        return -1;
    }

    private static Board inputBoard(Scanner scan) {
        int n = scan.nextInt();
        int m = scan.nextInt();
        char[][] cells = new char[n][m];
        
        for (int i = 0; i < n; i++) {
            String line = scan.next();
            for (int j = 0; j < m; j++) {
                cells[i][j] = line.charAt(j);
            }
        }
        
        return new Board(n, m, cells);
    }
    
    
    private static class BoardState {
        public int count;
        public Ball redBall;
        public Ball blueBall;
        public int lastDir;
        
        public BoardState(int count, Board board, int lastDir) {
            this.count = count;
            this.redBall = board.redBall.clone();
            this.blueBall = board.blueBall.clone();
            this.lastDir = lastDir;
        }
        
        public void apply(Board board) {
            board.redBall = redBall.clone();
            board.blueBall = blueBall.clone();
        }
    }
    
    
    private static class Board {
        public int nRows;
        public int nCols;
        public char[][] cells;
        public Ball redBall;
        public Ball blueBall;
        
        public Board(int nRows, int nCols, char[][] cells) {
            this.nRows = nRows;
            this.nCols = nCols;
            this.cells = cells;
            
            initBalls();
        }

        public void move(int dir) {
            // 원래 생각해둔 알고리즘: 빨간공이나 파란공 중에 길막이 없는 공을 먼저 이동
            // 하지만 귀찮으므로, 그냥 빨간공-파란공-빨간공 이동시킴
            moveRedBall(dir);
            moveBlueBall(dir);
            moveRedBall(dir);
        }

        private void moveBall(int dir, Ball target, Ball other) {
            int dy = DIR_OFFSET_Y[dir];
            int dx = DIR_OFFSET_X[dir];
            
            while (true) {
                // 이미 골인한 공인 경우
                if (target.isGoalIn(this)) {
                    break;
                }
                
                int nextRow = target.row + dy;
                int nextCol = target.col + dx;
                
                if (!isValidPosition(nextRow, nextCol)) {
                    break;
                }
                
                // 벽이나 공에 막힌 경우
                if (!other.isGoalIn(this) && nextRow == other.row && nextCol == other.col) {
                    break;
                }
                if (cells[nextRow][nextCol] == '#') {
                    break;
                }

                // 뚫린 경우
                target.row = nextRow;
                target.col = nextCol;
            }
        }

        private boolean isValidPosition(int row, int col) {
            if (row < 0 || row >= nRows) return false;
            if (col < 0 || col >= nCols) return false;
            return true;
        }

        private void moveRedBall(int dir) {
            moveBall(dir, redBall, blueBall);
        }

        private void moveBlueBall(int dir) {
            moveBall(dir, blueBall, redBall);
        }

        private void initBalls() {
            for (int i = 0; i < nRows; i++) {
                for (int j = 0; j < nCols; j++) {
                    char cell = cells[i][j];
                    if (cell == 'R') {
                        redBall = new Ball(i, j);
                        cells[i][j] = '.';
                    }
                    else if (cell == 'B') {
                        blueBall = new Ball(i, j);
                        cells[i][j] = '.';
                    }
                }
            }
        }
    }
    
    
    private static class Ball {
        public int row;
        public int col;
        
        public Ball(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        public boolean isGoalIn(Board board) {
            return (board.cells[row][col] == 'O');
        }

        @Override
        protected Ball clone() {
            return new Ball(row, col);
        }
    }
}
