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
        public class FireBall{
            public int r;  // row
            public int c;  // col
            public int m;  // mass
            public int s;  // speed
            public int d;  // direction

            public FireBall(int r, int c, int m, int s, int d){
                this.r = r;
                this.c = c;
                this.m = m;
                this.s = s;
                this.d = d;
            }
        }
        
        public static List<FireBall>[,] map;
        public static List<FireBall> fireBallList = new List<FireBall>();
        public static int[] dr = new int[]{-1,-1,0,1,1,1,0,-1};
        public static int[] dc = new int[]{0,1,1,1,0,-1,-1,-1};
        public static int N, M, K;

        static StringBuilder sb = new StringBuilder();
        static StreamWriter sw = new StreamWriter(new BufferedStream(Console.OpenStandardOutput()));
        static StreamReader sr = new StreamReader(new BufferedStream(Console.OpenStandardInput()));

        public static void Main()
        {
         

            string[] tmp = sr.ReadLine().Split(' ');
            N = Convert.ToInt32(tmp[0]);
            M = Convert.ToInt32(tmp[1]);
            K = Convert.ToInt32(tmp[2]);
           

            map = new List<FireBall>[N,N];
            for(int i = 0 ; i < N ;i++){
                for(int j = 0 ; j < N; j++){
                    map[i,j] = new List<FireBall>();
                }
            }
            
           
            for(int i = 0; i < M ;i++)
            {
                tmp = sr.ReadLine().Split(' ');
                fireBallList.Add(new FireBall(
                    Convert.ToInt32(tmp[0]) - 1,
                    Convert.ToInt32(tmp[1]) - 1,
                    Convert.ToInt32(tmp[2]),
                    Convert.ToInt32(tmp[3]),
                    Convert.ToInt32(tmp[4])));
             
                // fireBallList.Add(new fireBall(r-1, c-1, m, s, d));
            }

            // 2개 이상이면
            // 1. 방향이 모두 짝수(홀수)인지 아닌지 확인
            bool even = false ;
            bool odd = false;

            int massSum = 0;   // 질량 합 
            int speedSum = 0;   // 속도 합
            
            while (K-- > 0)
            {
                foreach (FireBall fBall in fireBallList)
                {
                    int nr = fBall.r + dr[fBall.d] *(fBall.s % N);
                    int nc = fBall.c + dc[fBall.d] *(fBall.s % N);
                    
                    if(nr >= N)
                        nr -= N;
                    else if(nr < 0)
                        nr += N;
                    if(nc >= N)
                        nc -= N;
                    else if(nc < 0)
                        nc += N;
                    
                    fBall.r = nr;
                    fBall.c = nc;

                    map[fBall.r,fBall.c].Add(fBall);
                }
              
                
                // 파이어볼이 두개 이상인곳 확인
                for(int i = 0 ; i < N; i++){
                    for(int j = 0 ; j < N; j++)
                    {
                        even = false;
                        odd = false;
                        massSum = 0;
                        speedSum = 0;
                        // 2개 미만이면 ㅁㅜ시
                        if(map[i,j].Count < 2){
                            map[i,j].Clear();
                            continue;
                        }


                        // 2개 이상이면
                        // 1. 방향이 모두 짝수(홀수)인지 아닌지 확인
                        even = (map[i,j][0].d % 2 == 0);
                        odd = (map[i,j][0].d % 2 == 1);

                        massSum = 0;   // 질량 합 
                        speedSum = 0;   // 속도 합
                        

                        foreach(FireBall fball in map[i,j]){
                            massSum += fball.m;
                            speedSum += fball.s;
                            // 방향이 짝수인지
                            even = even && (fball.d % 2 == 0);
                            // 방향이 홀수인지
                            odd = odd && (fball.d % 2 == 1);

                            fireBallList.Remove(fball);
                        }

                        int nMass = (int)(Math.Floor((double)(massSum/5)));
                        int nSpeed = (int)(Math.Floor((double)(speedSum/map[i,j].Count)));
                        map[i,j].Clear();

                        if(nMass == 0)
                            continue;

                        // 방향이 모두 홀수(짝수) 이면
                        if(even || odd){
                            // 방향이 0, 2, 4, 6
                            for(int k = 0 ; k < 7 ; k += 2)
                                fireBallList.Add(new FireBall(i,j,nMass, nSpeed, k));
                        }
                        else{
                            // 방향이 1, 3, 5, 7
                            for(int k = 1; k < 8 ; k+=2)
                                fireBallList.Add(new FireBall(i, j, nMass, nSpeed, k));
                        }
                    }
                }
            }
            
            int ans = 0;
            foreach(FireBall fball in fireBallList)
                ans += fball.m;

            
            
            Console.WriteLine(ans);

        }
    }
}