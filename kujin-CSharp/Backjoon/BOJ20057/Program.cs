using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TestApp1
{
    public class Program
    {
        public static int N, x, y, ans;
        public static int[,] map;
        public static int[] dr = new int[] {-1, 0, 1, -0};//1
        public static int[] dc = new int[] {0, 1, 0, -1};//0
        public static int[,] dia = {{1, -1}, {1, 1}, {-1, 1}, {-1, -1}};

        public static StringBuilder sb = new StringBuilder();
        public static StreamWriter sw = new StreamWriter(new BufferedStream(Console.OpenStandardOutput()));
        public static StreamReader sr = new StreamReader(new BufferedStream(Console.OpenStandardInput()));

        public static void Main()
        {
            string[] tmp = sr.ReadLine().Split(' ');
            N = Convert.ToInt32(tmp[0]);

            map = new int[N, N];

            for (int i = 0; i < N; i++)
            {
                tmp = sr.ReadLine().Split(' ');
                for (int j = 0; j < N; j++)
                {
                    map[i, j] = Convert.ToInt32(tmp[j]);
                }
            }

            x = y = N / 2;

            int d = 0;
            int temp = 1;

            bool isEnd = false;
            while (true)
            {
                // 이동 1
                for (int j = 0; j < temp; j++)
                {
                    x += dc[d];
                    y += dr[d];
                    Tornado(x, y, d);

                    if (x == 0 && y == 0)
                    {
                        isEnd = true;
                        break;
                    }
                }

                if (isEnd)
                {
                    break;
                }
                
                d++;
                if (d == 4) d = 0;


                // 이동 2
                for (int j = 0; j < temp; j++)
                {
                    x += dc[d];
                    y += dr[d];
                    Tornado(x, y, d);

                    if (x == 0 && y == 0)
                    {
                        isEnd = true;
                        break;
                    }
                }

                d++;
                if (d == 4) d = 0;

                temp++;

                if (isEnd)
                {
                    break;
                }
            }


            Console.WriteLine(ans);
        }

        public static void Tornado(int r, int c, int direct)
        {
            int value = map[r, c];
            int nx, ny;
            int per1 = (int) (value * 0.01);
            int per2 = (int) (value * 0.02);
            int per5 = (int) (value * 0.05);
            int per7 = (int) (value * 0.07);
            int per10 = (int) (value * 0.1);

            for (int i = 0; i < 8; i++)
            {
                if (i == 4) continue;

                if (i % 2 == 0)
                {
                    if (i == 0)
                    {
                        // a
                        nx = r + dc[direct];
                        ny = c + dr[direct];

                        int a = value - (2 * (per1 + per2 + per7 + per10) + per5);
                        if (IsInside(nx, ny))
                        {
                            map[nx, ny] += a;
                        }
                        else ans += a;

                        // 5%
                        nx = r + 2 * dc[direct];
                        ny = c + 2 * dr[direct];

                        if (IsInside(nx, ny)) map[nx, ny] += per5;
                        else ans += per5;
                    }
                    else
                    {
                        //7%
                        nx = r + dc[direct];
                        ny = c + dr[direct];

                        if (IsInside(nx, ny)) map[nx, ny] += per7;
                        else ans += per7;

                        //2%
                        nx = r + 2 * dc[direct];
                        ny = c + 2 * dr[direct];

                        if (IsInside(nx, ny)) map[nx, ny] += per2;
                        else ans += per2;
                    }
                }
                else
                {
                    if (i == 1 || i == 7)
                    {
                        // 10%
                        nx = r + dia[direct, 0];
                        ny = c + dia[direct, 1];

                        if (IsInside(nx, ny)) map[nx, ny] += per10;
                        else ans += per10;
                        
                    }
                    else
                    {
                        // 1%
                        nx = r + dia[direct, 0];
                        ny = c + dia[direct, 1];

                        if (IsInside(nx, ny)) map[nx, ny] += per1;
                        else ans += per1;
                    }

                    direct++;
                    if (direct == 4) direct = 0;
                }
            }

            map[r, c] = 0;
        }

        public static bool IsInside(int x, int y)
        {
            return x >= 0 && y >= 0 && x < N && y < N;
        }
    }
}