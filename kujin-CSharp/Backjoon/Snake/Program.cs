using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;



namespace TestApp1
{
    class Program
    {
        
        static int N, K, L;
        static Queue<Tuple<int, int>> dq = new Queue<Tuple<int, int>>();
        static List<int> vtime = new List<int>();
        static List<char> vdir = new List<char>();
        static int[,] map = new int[100, 100];
        static int[] dx = new int[] { 1, 0, -1, 0 };
        static int[] dy = new int[] { 0, 1, 0, -1 };
        

        public static void Main()
        {
            N = int.Parse(Console.ReadLine());
            K = int.Parse(Console.ReadLine());

            
            string[] command = null;
            
            for (int i = 0; i < K; i++)
            {
                command =  Console.ReadLine().Split(' ');

                map[int.Parse(command[0]), int.Parse(command[1])] = 1;
            }
            
            L = int.Parse(Console.ReadLine());
            
            for (int i = 0; i < L; i++)
            {
                
                command =  Console.ReadLine().Split(' ');
                vtime.Add( int.Parse(command[0]));
                vdir.Add((command[1].ToCharArray())[0]);
            }
            
            Console.WriteLine(dummy(0,0));
        }

        static int dummy(int sx, int sy)
        {
            int dir = 0;
            int cnt = 0;
            int x, y;
            int idx = 0;
            dq.Enqueue(Tuple.Create(sx, sy));
            while (true)
            {
                x = dq.ToArray()[0].Item1;
                y = dq.ToArray()[0].Item2;
                map[y,x] = 2;
                cnt++;
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                //게임 끝
                if (nx < 0 || ny < 0 || nx >= N || ny >= N || map[ny,nx] == 2)
                {
                    return cnt;
                }
                else
                {
                    dq.Enqueue(Tuple.Create(nx, ny));
                    // 사과 있으면 그대로, 없으면 dq 끝에서 pop 해주기
                    if (map[ny,nx] != 1)
                    {
                        int xend, yend;
                        xend = dq.Peek().Item1;
                        yend = dq.Peek().Item2;
                        map[yend,xend] = 0;
                        dq.Dequeue();
                    }
                    //시간 확인 후 방향 변경
                    if (cnt == vtime[idx])
                    {
                        if (vdir[idx] == 'L')
                        {
                            dir += 3;
                            dir %= 4;
                        }
                        else
                        {
                            dir += 1;
                            dir %= 4;
                        }
                        if (idx < vtime.Count() - 1)
                        {
                            idx++;
                        }
                    }
                }
            }
        }
    }
}