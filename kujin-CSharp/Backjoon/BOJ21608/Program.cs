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
        public static int N;
        public static int studentNum;
        public static int[,] seat = null;
        public static int[,] studentData = null;
        public static List<int> studentOrder = null;
        
        public static int[] dr = new int[] {-1, 0, 1, -0}; //1
        public static int[] dc = new int[] {0, 1, 0, -1}; //0

        public static StringBuilder sb = new StringBuilder();
        public static StreamWriter sw = new StreamWriter(new BufferedStream(Console.OpenStandardOutput()));
        public static StreamReader sr = new StreamReader(new BufferedStream(Console.OpenStandardInput()));

        public static void Main()
        {
            string[] tmp = sr.ReadLine().Split(' ');
            N = Convert.ToInt32(tmp[0]);
            int studentNum = N * N;

            seat = new int[N + 1, N + 1];
            studentData = new int[studentNum + 1, 4];
            studentOrder = new List<int>();

            int currentStudent = 0;
            for (int i = 0; i < studentNum; i++)
            {
                currentStudent = 0;
                tmp = sr.ReadLine().Split(' ');
                currentStudent = Convert.ToInt32(tmp[0]);
                studentOrder.Add(currentStudent);
                for (int j = 0; j < 4; j++) {
                    studentData[currentStudent,j] = Convert.ToInt32(tmp[j + 1]);
                }
            }

            foreach (var i in studentOrder)
            {
                SitDownOneByOne(i);
            }
            
            Console.WriteLine(CalcAns());
           
        }
        
        public static int CalcAns() {
            int ans = 0;
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {

                    int myFriends = 0;
                    for (int k = 0; k < 4; k++) {
                        int nx = i + dc[k];
                        int ny = j + dr[k];

                        if (nx <= 0 || nx > N || ny <= 0 || ny > N)
                            continue;
                        if (IsInLikeStudent(seat[i,j], nx, ny)) {
                            myFriends++;
                        }
                    }
                    if (myFriends == 1) {
                        ans += 1;
                    } else if (myFriends == 2) {
                        ans += 10;
                    } else if (myFriends == 3) {
                        ans += 100;
                    } else if (myFriends == 4) {
                        ans += 1000;
                    }
                }
            }

            return ans;
        }
        
        public static void SitDownOneByOne(int cur) {
            int resX = -1, resY = -1;
            //여기가 중요한 포인트! 0 / 0인 경우가 정답이 될 수 있다
            int maxLikeStudents = -1, maxEmpties = -1;

            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    int likeStudents = 0;
                    int empty = 0;

                    if (seat[i,j] > 0)
                        continue;
                    for (int k = 0; k < 4; k++) {
                        int nx = i + dc[k];
                        int ny = j + dr[k];
                        if (nx <= 0 || nx > N || ny <= 0 || ny > N)
                            continue;
                        if (seat[nx,ny] == 0) {
                            empty++;
                        } else if (IsInLikeStudent(cur, nx, ny)) {
                            likeStudents++;
                        }
                    }

                    if (likeStudents > maxLikeStudents) {
                        maxLikeStudents = likeStudents;
                        maxEmpties = empty;
                        resX = i;
                        resY = j;
                    } else if (likeStudents == maxLikeStudents) {
                        if (empty > maxEmpties) {
                            maxEmpties = empty;
                            resX = i;
                            resY = j;

                        } else if (empty == maxEmpties) {
                            if (resX == i && resY > j) {
                                resY = j;
                            } else if (resX > i) {
                                resX = i;
                                resY = j;
                            }
                        }
                    }
                }
            }

            seat[resX,resY] = cur;
        }
        
        public static bool IsInLikeStudent(int cur, int x, int y) {
            for (int i = 0; i < 4; i++) {
                if (studentData[cur,i] == seat[x,y])
                    return true;
            }
            return false;
        }

    }
   
}