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
        public static int N, Q, ans, mapSize;
        public static int[,] map = null;
        public static int[,] tempMap = null;
        public static bool[,] vi = null;

        public static List<int> l = null;

        public static int[] dr = new int[] {-1, 0, 1, -0}; //1
        public static int[] dc = new int[] {0, 1, 0, -1}; //0

        public static StringBuilder sb = new StringBuilder();
        public static StreamWriter sw = new StreamWriter(new BufferedStream(Console.OpenStandardOutput()));
        public static StreamReader sr = new StreamReader(new BufferedStream(Console.OpenStandardInput()));

        public static void Main()
        {
            string[] tmp = sr.ReadLine().Split(' ');
            N = Convert.ToInt32(tmp[0]);
            Q = Convert.ToInt32(tmp[1]);
            mapSize =  1 << N;
            map = new int[mapSize, mapSize];
            tempMap = new int[mapSize, mapSize];
            vi = new bool[mapSize, mapSize];

            for (int i = 0; i < mapSize; i++)
            {
                tmp = sr.ReadLine().Split(' ');
                for (int j = 0; j < mapSize; j++)
                {
                    map[i, j] = Convert.ToInt32(tmp[j]);
                    vi[i, j] = false;
                }
            }

            tmp = sr.ReadLine().Split(' ');
            l = new List<int>();
            for (int i = 0; i < Q; i++)
            {
                l.Add(Convert.ToInt32(tmp[i]));
            }

            //파이어스톰!!
            for (int i = 0; i < Q; i++)
            {
                if (l[i] != 0)
                    rotate(l[i]);
                
                updateIce();
            }

            // 남아있는 얼음의 합
            int remains = 0;
            for (int i = 0; i < mapSize; i++)
            {
                for (int j = 0; j < mapSize; j++)
                {
                    remains += map[i, j];
                }
            }

            Console.WriteLine(remains.ToString());


            // 남아있는 얼음 중 가장 큰 덩어리가 차지하는 칸의 개수
            int ans = 0;
            for (int i = 0; i < mapSize; i++)
            {
                for (int j = 0; j < mapSize; j++)
                {
                    if (vi[i, j] == false && map[i, j] > 0)
                        ans = Math.Max(ans, bfs(i, j));
                    
                }
            }

            if (ans < 2) Console.WriteLine('0'); // 한칸 집합 예외처리
            else Console.WriteLine(ans);
        }


        public static void updateIce()
        {
            List<(int, int)> v = new List<(int, int)>();
            for (int i = 0; i < mapSize; i++)
            {
                for (int j = 0; j < mapSize; j++)
                {
                    if (map[i, j] == 0) continue;
                    int adjIce = 0;
                    for (int k = 0; k < 4; k++)
                    {
                        int ny = i + dr[k];
                        int nx = j + dc[k];
                        if (ny < 0 || nx < 0 || ny >= mapSize || nx >= mapSize || map[ny, nx] == 0) continue;
                        adjIce++;
                    }

                    if (adjIce < 3)
                        v.Add((i, j));
                }
            }

            foreach (var tuple in v)
            {
                map[tuple.Item1, tuple.Item2] -= 1;
            }
        }


// 하나의 회전 단위의 시작점 탐색 후

        public static void rotate(int L)
        {
            int n = 1 << L; //bit\
            for (int mapPosY = 0; mapPosY < mapSize; mapPosY += n)
            {
                for (int mapPosX = 0; mapPosX < mapSize; mapPosX += n)
                {
                    // 시계방향으로 90도 회전
                    {
                        for (int j = mapPosX, ii = 0; j < mapPosX + n; j++, ii++)
                        {
                            for (int i = mapPosY + n - 1, jj = 0; i >= mapPosY; i--, jj++)
                            {
                                tempMap[ii, jj] = map[i, j];
                            }
                        }

                        for (int i = mapPosY, ii = 0; i < mapPosY + n; i++, ii++)
                        {
                            for (int j = mapPosX, jj = 0; j < mapPosX + n; j++, jj++)
                            {
                                map[i, j] = tempMap[ii, jj];
                            }
                        }
                    }
                }
            }
        }

        private static int bfs(int x, int y)
        {
            Queue<(int, int)> queue = new Queue<(int, int)>();
        
            int ret = 1;
        
            queue.Enqueue((x, y));
            vi[x, y] = true;
        
            while (queue.Count != 0)
            {
                var item = queue.Dequeue();
                int cy = item.Item1;
                int cx = item.Item2;
        
        
                for (int i = 0; i < 4; i++)
                {
                    int ny = cy + dr[i];
                    int nx = cx + dc[i];
                    if (ny < 0 || nx < 0 || ny >= mapSize || nx >= mapSize) continue;
                    if (vi[ny, nx] == false && map[ny, nx] > 0)
                    {
                        queue.Enqueue((ny, nx));
                        vi[ny, nx] = true;
                        ret++;
                    }
                }
            }
        
            return ret;
        }
        
        //출처 : https://nato-blog.tistory.com/142
        public static int dfs(int x, int y) {
            int cnt=1;
            vi[x,y]=true;
            if(x>0 && !vi[x-1,y] && map[x-1,y]>0)cnt+=dfs(x-1,y);
            if(y>0 && !vi[x,y-1] && map[x,y-1]>0)cnt+=dfs(x,y-1);
            if(x<mapSize-1 && !vi[x+1,y] && map[x+1,y]>0)cnt+=dfs(x+1,y);
            if(y<mapSize-1 && !vi[x,y+1] && map[x,y+1]>0)cnt+=dfs(x,y+1);
		
            return cnt;
        }
    }
   
}