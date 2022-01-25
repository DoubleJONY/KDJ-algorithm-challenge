using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;


namespace TestApp1
{
    class Program
    {
        static int N, M,K, L, X, Y;
     
        static int[,] map = null;


        public static void Main()
        {
            string[] command = null;
            command = Console.ReadLine().Split(' ');
            
            N = int.Parse(command[0]);
            M = int.Parse(command[1]);
            X = int.Parse(command[2]);
            Y = int.Parse(command[3]);
            K = int.Parse(command[4]);

            map = new int[N, M];
            int[] dice = new int[6]; // 주사위(0: 윗면, 1: 아랫면, 2: 앞면, 3: 뒷면, 4: 왼쪽 면, 5: 오른쪽 면)

            for (int i = 0; i < N; i++) {
                command = Console.ReadLine().Split(' ');
                for (int j = 0; j < M; j++)
                {
                    map[i, j] = int.Parse(command[j]);
                }
            }
            command = Console.ReadLine().Split(' ');
            for (int i = 0; i < command.Length; i++) {
                int dir = int.Parse(command[i]);

                // 주사위 굴리기
                if (dir == 1) { // 동쪽
                    if (Y + 1 >= M)
                        continue;
                    Y++;
                    int temp = dice[1];
                    dice[1] = dice[5];
                    dice[5] = dice[0];
                    dice[0] = dice[4];
                    dice[4] = temp;

                } else if (dir == 2) { // 서쪽
                    if (Y - 1 < 0)
                        continue;
                    Y--;
                    int temp = dice[1];
                    dice[1] = dice[4];
                    dice[4] = dice[0];
                    dice[0] = dice[5];
                    dice[5] = temp;

                } else if (dir == 3) { // 북쪽
                    if (X - 1 < 0)
                        continue;
                    X--;
                    int temp = dice[1];
                    dice[1] = dice[3];
                    dice[3] = dice[0];
                    dice[0] = dice[2];
                    dice[2] = temp;

                } else if (dir == 4) { // 남쪽
                    if (X + 1 >= N)
                        continue;
                    X++;
                    int temp = dice[1];
                    dice[1] = dice[2];
                    dice[2] = dice[0];
                    dice[0] = dice[3];
                    dice[3] = temp;
                }

                // 수 업데이트
                if (map[X,Y] == 0) {
                    map[X,Y] = dice[1];
                } else {
                    dice[1] = map[X,Y];
                    map[X,Y] = 0;
                }
                Console.WriteLine(dice[0]);
            }
        }

       
    }
}