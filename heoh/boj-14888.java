import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        int N = scanner.nextInt();      // 수열의 길이
        int[] numbers = new int[N];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = scanner.nextInt();
        }
        int[] opCounts = new int[Calculator.NUMBER_OF_OPS];
        for (int i = 0; i < opCounts.length; i++) {
            opCounts[i] = scanner.nextInt();
        }
        
        Calculator calculator = new Calculator(numbers, opCounts);
        calculator.calculate();
        int maxAnswer = calculator.getMaxAnswer();
        int minAnswer = calculator.getMinAnswer();

        System.out.println(maxAnswer);
        System.out.println(minAnswer);
        
        scanner.close();
    }
    
    
    private static class Calculator {
        public  static final int NUMBER_OF_OPS  = 4;
        private static final int OP_SUM         = 0;    // '+'
        private static final int OP_SUB         = 1;    // '-'
        private static final int OP_MUL         = 2;    // '*'
        private static final int OP_DIV         = 3;    // '/'
        
        private int[] _numbers;
        private int[] _opCounts;
        
        private int _minAnswer;
        private int _maxAnswer;
        
        public Calculator(int[] numbers, int[] opCounts) {
            this._numbers = numbers;
            this._opCounts = opCounts;
            this._minAnswer = Integer.MAX_VALUE;
            this._maxAnswer = Integer.MIN_VALUE;
        }

        public int getMaxAnswer() {
            return this._maxAnswer;
        }

        public int getMinAnswer() {
            return this._minAnswer;
        }

        public void calculate() {
            int[] opCounts = this._opCounts.clone();
            this.calculate(opCounts, 0, this._numbers[0]);
        }

        private void calculate(int[] opCounts, int index, int answer) {
            if (index >= this._numbers.length) {
                return;
            }
            else if (index == this._numbers.length - 1) {
                this._minAnswer = Math.min(this._minAnswer, answer);
                this._maxAnswer = Math.max(this._maxAnswer, answer);
                return;
            }
            
            int nextIndex = index + 1;
            int nextNumber = this._numbers[nextIndex];
            if (opCounts[OP_SUM] > 0) {
                opCounts[OP_SUM]--;
                this.calculate(opCounts, nextIndex, answer + nextNumber);
                opCounts[OP_SUM]++;
            }
            if (opCounts[OP_SUB] > 0) {
                opCounts[OP_SUB]--;
                this.calculate(opCounts, nextIndex, answer - nextNumber);
                opCounts[OP_SUB]++;
            }
            if (opCounts[OP_MUL] > 0) {
                opCounts[OP_MUL]--;
                this.calculate(opCounts, nextIndex, answer * nextNumber);
                opCounts[OP_MUL]++;
            }
            if (opCounts[OP_DIV] > 0) {
                opCounts[OP_DIV]--;
                this.calculate(opCounts, nextIndex, answer / nextNumber);
                opCounts[OP_DIV]++;
            }
        }
    }
    
}
