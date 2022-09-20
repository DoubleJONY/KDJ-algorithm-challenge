using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
//https://www.acmicpc.net/source/33380324

namespace TestApp1
{
   class Pos
    {
        public int smell { get; set; } // 냄새
        public int sharkNo { get; set; } // 냄새를 남긴 상어
        public Shark shrk { get; set; } // 현재 상어
        public Pos(int _smell, int _sharkNo, Shark _shrk)
        {
            smell = _smell; sharkNo = _sharkNo;
            shrk = _shrk;
        }
    }

    class Shark
    {
        public int no { get; set; }
        public int dir { get; set; }
        public int x { get; set;}
        public int y { get; set;}
        public Shark(int _no, int _dir, int _y, int _x)
        {
            no = _no; dir = _dir; y = _y; x = _x;
        }
    }
    
    class Program
    {
        static int N, M, k;
        static int[] xPos = { 0, 0, 0, -1, 1 }; // t, b, l, r
        static int[] yPos = { 0, -1, 1, 0, 0 };
        static int[,,] pri = null;
        static Pos[,] map;
        static List<Shark> lstShrk = new List<Shark>(); // 전체 상어 리스트
        static StringBuilder sb = new StringBuilder();
        static StreamWriter sw = new StreamWriter(new BufferedStream(Console.OpenStandardOutput()));
        static StreamReader sr = new StreamReader(new BufferedStream(Console.OpenStandardInput()));

        static void Main(string[] args)
        {
            sw.AutoFlush = true;

            int no, sec;
            sec = 0;
            string[] tmp = sr.ReadLine().Split(' ');
            N = Convert.ToInt32(tmp[0]);
            M = Convert.ToInt32(tmp[1]);
            k = Convert.ToInt32(tmp[2]);
            pri = new int[M + 1, 5, 4]; // 상어번호, 방향(1~4), 방향일 때 이동 우선순위
            map = new Pos[N, N];
            for (int i = 0; i < N; i++) 
            {
                tmp = sr.ReadLine().Split(' ');
                for (int j = 0; j < N; j++)
                {
                    no = Convert.ToInt32(tmp[j]);

                    Shark shrk = null;
                    if(no != 0)
                    {
                        shrk = new Shark(no, 0, i, j);
                        lstShrk.Add(shrk);
                    }
                    map[i, j] = new Pos(0, no, shrk);
                }
            }

            tmp = sr.ReadLine().Split(' ');
            for (int i = 0; i < M; i++)
            {
                foreach (var item in lstShrk.Where(x => x.no == (i + 1)))
                {
                    item.dir = Convert.ToInt32(tmp[i]);
                    map[item.y, item.x].smell = k;  // 초기 냄새값
                }
            }

            for(int i = 1; i <= M; i++)
            {
                for (int j = 1; j <= 4; j++)
                {
                    tmp = sr.ReadLine().Split(' ');
                    for (int k = 0; k < 4; k++)
                    {
                        pri[i, j, k] = Convert.ToInt32(tmp[k]);
                    }
                }
            }

            int nextY, nextX, myDir;
            bool flag;
            while(sec++ <= 1000)
            {
                for(int i = 0; i < lstShrk.Count; i++)
                {
                    Shark srk = lstShrk[i];

                    myDir = 0;
                    flag = false;
                    // 주변에 비어있는 칸이 있는지 확인
                    for(int j = 0; j < 4; j++)
                    {
                        nextY = srk.y + yPos[pri[srk.no, srk.dir, j]];
                        nextX = srk.x + xPos[pri[srk.no, srk.dir, j]];

                        if (nextY < 0 || nextX < 0 || nextY >= N || nextX >= N) continue;
                        if (map[nextY, nextX].smell > 0)
                        {
                            // 내가 남긴 냄새일 경우
                            if(map[nextY, nextX].sharkNo == srk.no && myDir == 0)
                            {
                                myDir = pri[srk.no, srk.dir, j];
                            }
                            continue;
                        }
                        else // 냄새가 없는 경우
                        {
                            flag = true;

                            // 다음 좌표에 상어 추가
                            if (map[nextY, nextX].shrk == null)
                            {
                                map[nextY, nextX].shrk = srk;
                            }
                            else
                            {
                                if (map[nextY, nextX].shrk.no > srk.no) // 번호가 작은 상어만 남긴다.
                                {
                                    //removeLst.Add(map[nextY, nextX].shrk.no);
                                    lstShrk.Remove(map[nextY, nextX].shrk); // 상어 리스트에서 원래 상어 제거
                                    map[nextY, nextX].shrk = srk;
                                }
                                else
                                {
                                    //removeLst.Add(map[nextY, nextX].shrk.no);
                                    lstShrk.Remove(srk); // 상어 리스트에서 현재 상어 제거
                                }
                                i--; // 삭제하고 난 이후에 인덱스가 밀려서 감소처리 *********************************************************
                            }

                            // 이전 상어 삭제
                            map[srk.y, srk.x].shrk = null;
                            srk.y = nextY;
                            srk.x = nextX;
                            srk.dir = pri[srk.no, srk.dir, j];
                            break;
                        }
                    }

                    // 위에서 이동하지 못한 경우 칸이 있는지 다시 확인 - 자신의 냄새가 있는 칸의 방향
                    if(flag == false)
                    {
                        nextY = srk.y + yPos[myDir];
                        nextX = srk.x + xPos[myDir];
                        // 다음 좌표에 상어 추가
                        if (map[nextY, nextX].shrk == null)
                        {
                            map[nextY, nextX].shrk = srk;
                        }
                        else
                        {
                            if (map[nextY, nextX].shrk.no > srk.no) // 번호가 작은 상어만 남긴다.
                            {
                                
                                lstShrk.Remove(map[nextY, nextX].shrk); // 상어 리스트에서 원래 상어 제거
                                map[nextY, nextX].shrk = srk;   // 
                            }
                            else
                            {
                                lstShrk.Remove(srk); // 상어 리스트에서 현재 상어 제거
                            }
                            i--; // 삭제하고 난 이후에 인덱스가 밀려서 감소처리
                        }

                        // 이전 상어 삭제
                        map[srk.y, srk.x].shrk = null;
                        srk.y = nextY;
                        srk.x = nextX;
                        srk.dir = myDir;
                    }
                }

                for(int i = 0; i < N; i++)
                {
                    for (int j = 0; j < N; j++)
                    {
                        // 전체 좌표 냄새 -1
                        if (map[i, j].smell > 0) 
                        {
                            map[i, j].smell--;
                        }

                        // 상어 count 가 0이면 shrk no = 0
                        // 상어 count 가 2이상이면 가장 작은 상어 + 나머지 상어 lst 에서 제외
                        // 상어 count 가 1이면 shrk no = 그 상어
                        if(map[i, j].shrk == null && map[i, j].smell == 0)
                        {
                            map[i, j].sharkNo = 0;
                        } 
                        else if(map[i, j].shrk != null)
                        {
                            map[i, j].sharkNo = map[i, j].shrk.no;
                        }
                    }
                }

                // 현재 상어 lst의 좌표에 k 세팅
                foreach (var item in lstShrk)
                {
                    map[item.y, item.x].smell = k;
                }

                if (lstShrk.Count == 1)
                {
                    break;
                }

             
                if(sec >= 1000)
                {
                    sec = -1;
                    break;
                }
            }

            // 1,000초가 넘어도 다른 상어가 격자에 남아 있으면 -1을 출력
            if (lstShrk != null && lstShrk.Count > 1)
                sec = -1;

            sw.WriteLine(sec.ToString());
        }
    }
}