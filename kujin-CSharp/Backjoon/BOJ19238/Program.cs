using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

//https://bcp0109.tistory.com/211

namespace TestApp1
{
    public class Program
    {
        public class Taxi
        {
            public int x;
            public int y;
            public int move;

            public Taxi(int x, int y, int move)
            {
                this.x = x;
                this.y = y;
                this.move = move;
            }
        }

        public class Passenger
        {
            public int id;
            public int sx;
            public int sy;
            public int ex;
            public int ey;
        }

        public static int N, M, fuel;
        public static int[,] arr = new int[21, 21];
        public static Taxi taxi;
        public static Passenger taken = null;
        public static Dictionary<int, Passenger> passMap = new Dictionary<int, Passenger>();

        static StringBuilder sb = new StringBuilder();
        static StreamWriter sw = new StreamWriter(new BufferedStream(Console.OpenStandardOutput()));
        static StreamReader sr = new StreamReader(new BufferedStream(Console.OpenStandardInput()));

        public static void Main()
        {
            sw.AutoFlush = true;

            string[] tmp = sr.ReadLine().Split(' ');
            N = Convert.ToInt32(tmp[0]);
            M = Convert.ToInt32(tmp[1]);
            fuel = Convert.ToInt32(tmp[2]);

            for (int i = 1; i < N + 1; i++)
            {
                tmp = sr.ReadLine().Split(' ');

                for (int j = 1; j < N + 1; j++)
                {
                    arr[i, j] = Convert.ToInt32(tmp[j - 1]);
                }
            }

            tmp = sr.ReadLine().Split(' ');
            taxi = new Taxi(Convert.ToInt32(tmp[0]), Convert.ToInt32(tmp[1]), 0);

            for (int i = 0; i < M; i++)
            {
                tmp = sr.ReadLine().Split(' ');

                Passenger p = new Passenger();
                p.id = i + 2; // 벽이 1 이라서 2 부터 넘버링
                p.sx = Convert.ToInt32(tmp[0]);
                p.sy = Convert.ToInt32(tmp[1]);
                p.ex = Convert.ToInt32(tmp[2]);
                p.ey = Convert.ToInt32(tmp[3]);

                passMap.Add(p.id, p);

                // 출발지는 겹치지 않기 때문에 맵에 기록
                arr[p.sx, p.sy] = p.id;
            }

            // solution
            solution();
        }

        // 모든 승객을 데려다 줄때까지 BFS 를 반복하며 연료의 양을 확인한다.
        static void solution()
        {
            while (passMap.Count() != 0)
            {
                int toStartFuel = bfs();
                fuel -= toStartFuel;

                if (fuel < 0) break;

                int toDestFuel = bfs();
                fuel -= toDestFuel;

                if (fuel < 0) break;

                fuel += toDestFuel * 2;
            }

            Console.WriteLine(fuel < 0 ? -1 : fuel);
        }

        static int bfs()
        {
            Queue<Taxi> q = new Queue<Taxi>();
            Queue<Passenger> candidates = new Queue<Passenger>();
            bool[,] visited = new bool[21, 21];
            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};

            int prevMove = taxi.move;
            visited[taxi.x, taxi.y] = true;
            q.Enqueue(taxi);

            while (q.Count() != 0)
            {
                Taxi now = q.Dequeue();

                // 이동 중에 연료가 떨어지면 종료
                if (fuel - now.move < 0)
                {
                    return Int32.MaxValue;
                }

                // 택시 이동 시간대가 다르고 candidates 가 이미 존재하면 break
                if (prevMove != now.move && candidates.Count != 0)
                {
                    break;
                }

                prevMove = now.move;

                if (taken == null)
                {
                    // 택시가 비어있는 경우 가장 가까운 승객 후보를 만나면 candidates 에 추가
                    int id = arr[now.x, now.y];

                    if (id > 1)
                    {
                        Passenger p = passMap[id];
                        candidates.Enqueue(p);
                    }
                }
                else
                {
                    // 택시에 승객이 있는 경우 도착지를 만나면 종료
                    if (now.x == taken.ex && now.y == taken.ey)
                    {
                        passMap.Remove(taken.id);
                        taken = null;
                        taxi = new Taxi(now.x, now.y, 0);
                        return prevMove;
                    }
                }

                // 동서남북 이동
                for (int i = 0; i < 4; i++)
                {
                    int nx = now.x + dx[i];
                    int ny = now.y + dy[i];

                    if (0 < nx && nx < N + 1 && 0 < ny && ny < N + 1)
                    {
                        if (arr[nx, ny] != 1 && visited[nx, ny] == false)
                        {
                            q.Enqueue(new Taxi(nx, ny, now.move + 1));
                            visited[nx, ny] = true;
                        }
                    }
                }
            }

            // while 문이 끝났는데 candidates 에 아무것도 없으면 벽에 막혀서 도달못함
            if (candidates.Count == 0)
            {
                return int.MaxValue;
            }

            taken = findNearest(candidates);
            taxi = new Taxi(taken.sx, taken.sy, 0);
            arr[taken.sx, taken.sy] = 0;
            return prevMove;
        }

        // 같은 거리면 x 가 작고, y 가 작은 사람으로
        static Passenger findNearest(Queue<Passenger> q)
        {
            Passenger target = q.Dequeue();

            while (q.Count != 0)
            {
                Passenger compare = q.Dequeue();

                if (compare.sx < target.sx)
                {
                    target = compare;
                }
                else if (compare.sx == target.sx && compare.sy < target.sy)
                {
                    target = compare;
                }
            }

            return target;
        }
    }
}

//         public static int n, m, oil, people;
//         public static int[,] map;
//         public static int[,] cus_map;
//         public static int[] dx = {-1, 1, 0, 0};
//         public static int[] dy = {0, 0, -1, 1};
//         public static bool[] check;
//         public static int t_x, t_y;
//         public static int[,] cus;
//         
//         static Queue<Node> queue = null;
//         static Queue<Passenger> psnQueue = null;
//
//         static StringBuilder sb = new StringBuilder();
//         static StreamWriter sw = new StreamWriter(new BufferedStream(Console.OpenStandardOutput()));
//         static StreamReader sr = new StreamReader(new BufferedStream(Console.OpenStandardInput()));
//
//         public class Node
//         {
//             public int x;
//             public int y;
//             public int dis;
//
//             public Node(int x, int y, int dis)
//             {
//                 this.x = x;
//                 this.y = y;
//                 this.dis = dis;
//             }
//         }
//
//
//         public static void Main()
//         {
//             sw.AutoFlush = true;
//
//             int no, sec;
//             sec = 0;
//             string[] tmp = sr.ReadLine().Split(' ');
//             n = Convert.ToInt32(tmp[0]);
//             m = Convert.ToInt32(tmp[1]);
//             oil = Convert.ToInt32(tmp[2]);
//             map = new int[n, n];
//             cus = new int[m + 1, 4];
//             cus_map = new int[n, n];
//
//             check = new bool[m + 1];
//             for (int i = 0; i < n; i++)
//             {
//                 tmp = sr.ReadLine().Split(' ');
//                 for (int j = 0; j < n; j++)
//                 {
//                     map[i, j] = Convert.ToInt32(tmp[j]);
//                 }
//             }
//
//             tmp = sr.ReadLine().Split(' ');
//             t_x = Convert.ToInt32(tmp[0]) - 1;
//             t_y = Convert.ToInt32(tmp[1]) - 1;
//             for (int i = 1; i <= m; i++)
//             {
//                 tmp = sr.ReadLine().Split(' ');
//                 for (int j = 0; j < 4; j++)
//                 {
//                     int num = Convert.ToInt32(tmp[j]) - 1;
//                     cus[i, j] = num;
//                 }
//
//                 cus_map[cus[i, 0], cus[i, 1]] = i;
//             }
//
//             people = m;
//
//             bool end = false;
//             while (true)
//             {
//                 if (people == 0)
//                 {
//                     break;
//                 }
//
//                 // 손님 고르기
//                 Node node = bfs1();
//                 if (node == null)
//                 {
//                     end = true;
//                     break;
//                 }
//
//                 int cus_num = cus_map[node.x, node.y];
//                 // 손님 데려다 주기
//
//                 int cal1 = node.dis;
//                 int cal2 = bfs2(node.x, node.y, cus[cus_num, 2], cus[cus_num, 3]);
//
//
//                 if (oil < cal1 + cal2 || cal2 == -1)
//                 {
//                     end = true;
//                     break;
//                 }
//
//                 oil -= (cal1 + cal2);
//                 oil += (cal2 * 2);
//                 check[cus_num] = true;
//                 cus_map[node.x, node.y] = 0;
//                 t_x = cus[cus_num, 2];
//                 t_y = cus[cus_num, 3];
//                 people--;
//             }
//
//             if (end) Console.WriteLine(-1);
//             else Console.WriteLine(oil);
//         }
//
//
//         public static Node bfs1()
//         {
//             bool f = false;
//             bool[,] visited =new bool[n,n];
//             int short_dist = int.MaxValue;
//             Queue<Node> q =  new Queue<Node>();
//             
//             q.Enqueue(new Node(t_x, t_y, 0));
//             visited[t_x, t_y]=true;
//             while(q.Count != 0) {
//                 Node tmp = q.Dequeue();
// 			
// //			System.out.println(tmp);
//                 if(tmp.dis >short_dist) break;
//                 for(int k=0;k<4;k++) {
//                     if(tmp.x+dx[k] <0 || tmp.x+dx[k]>=n || tmp.y+dy[k] <0 || tmp.y+dy[k]>=n) continue;
//                     else if( map[tmp.x+dx[k],tmp.y+dy[k]]==1 || visited[tmp.x+dx[k],tmp.y+dy[k]]==true) continue;
//                     else {
//                         if(tmp.dis<oil) {
//                             for(int i=0;i<m;i++) {
//                                 if(p[i].isEnd==true) continue;
//                                 if(p[i].start[0] == tmp.x+dx[k] && p[i].start[1] == tmp.y+dy[k]) {
//                                     p[i].dist = tmp.cnt+1;
//                                     short_dist = (short_dist>p[i].dist)? p[i].dist:short_dist;
//                                     f=true;
//                                 }
//                             }
//                             visited[tmp.x+dx[k],tmp.y+dy[k]]=true;
//                             q.Enqueue(new Loc(tmp.x+dx[k],tmp.y+dy[k],tmp.dis+1));
//                         }
//                         else return f;
//                     }
//                 }
//             }
//             return f;	
//         }
//
//         public static int bfs2(int s_x, int s_y, int e_x, int e_y)
//         {
//             bool[,] visited = new bool[n, n];
//             Queue<Node> q = new LinkedList<>();
//
//             visited[s_x, s_y] = true;
//             q.offer(new Node(s_x, s_y, 0));
//             int dis = -1;
//             while (!q.isEmpty())
//             {
//                 Node node = q.poll();
//                 if (node.x == e_x && node.y == e_y)
//                 {
//                     return node.dis;
//                 }
//
//                 for (int d = 0; d < 4; d++)
//                 {
//                     int nx = node.x + dx[d];
//                     int ny = node.y + dy[d];
//                     if (0 <= nx && nx < n && 0 <= ny && ny < n)
//                     {
//                         if (!visited[nx, ny] && map[nx, ny] != 1)
//                         {
//                             visited[nx, ny] = true;
//                             q.add(new Node(nx, ny, node.dis + 1));
//                         }
//                     }
//                 }
//             }
//
//             return dis;
//         }
//         