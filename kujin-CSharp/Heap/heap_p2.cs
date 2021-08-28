using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using NUnit.Framework;

namespace Algo
{
    [TestFixture]
    public class Tests
    {
        [Test]
        public void Test1()
        {
            // {{0, 3}, {1, 9}, {2, 6}, };
            int[,] input_1 = new int[,] { { 0, 3 }, { 1, 9 }, { 2, 6 }, { 20, 2 }, { 21, 2 }, { 24, 5 }, { 25, 10 } };
            //3, 7, 17, 2, 3, 5, 14
            int[,] input_2 = new int[,] { { 0, 10 }, { 4, 10 }, { 15, 2 }, { 5, 11 } };
            //10, 16, 7, 
            int[,] input_3 = new int[,]
            {
                { 24, 10 }, { 18, 39 }, { 34, 20 }, { 37, 5 }, { 47, 22 }, { 20, 47 }, { 15, 34 }, { 15, 2 },
                { 35, 43 }, { 26, 1 }
            };
            int[,] input_3_1 = new int[,]
            {
                { 24, 10 }, { 18, 39 }, { 34, 20 }, { 37, 5 }, { 47, 22 }, { 20, 47 }, { 15, 2 }, { 15, 34 },
                { 35, 43 }, { 26, 1 }
            };


            int[,] input_4 = new int[,] { { 0, 1 }, { 1, 2 }, { 500, 6 } };
            int[,] input_5 = new int[,] { { 0, 10 } };
            int[,] input_6 = new int[,] { { 1, 9 }, { 1, 4 }, { 1, 5 }, { 1, 7 }, { 1, 3 } };
            int[,] input_7 = new int[,] { { 0, 5 }, { 1, 4 }, { 6, 1 }, { 7, 1 } };
            int[,] input_8 = new int[,] { { 0, 3 }, { 4, 4 }, { 5, 3 }, { 4, 1 } };
            int[,] input_9 = new int[,] { { 0, 10 }, { 2, 10 }, { 9, 10 }, { 15, 2 } };
            int[,] input_10 = new int[,] { { 0, 10 }, { 2, 12 }, { 9, 19 }, { 15, 17 } };
            int[,] input_11 = new int[,] { { 0, 9 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 } };
            int[,] input_12 = new int[,] { { 10, 10 }, { 30, 10 }, { 50, 2 }, { 51, 2 } };
            int[,] input_13 = new int[,] { { 0, 3 }, { 1, 9 }, { 2, 6 }, { 30, 3 } };
            int[,] input_14 = new int[,] { { 1, 9 }, { 0, 11 } };
            int[,] input_15 = new int[,]
            {
                { 24, 10 }, { 28, 39 }, { 43, 20 }, { 37, 5 }, { 47, 22 }, { 20, 47 }, { 15, 2 }, { 15, 34 },
                { 35, 43 }, { 26, 1 }
            };
            int[,] input_16 = new int[,] { { 0, 1 }, { 1, 1 }, { 50, 7 } };
            int[,] input_17 = new int[,] { { 10000, 17 }, { 0, 10000 } };
            int[,] input_18 = new int[,] { { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 } };
            int[,] input_19 = new int[,] { { 0, 0 }, { 0, 100 } };
            int[,] input_20 = new int[,] { { 1, 9 }, { 0, 11 }, { 3, 0 } };
            int[,] input_21 = new int[,] { { 0, 3 }, { 1, 9 }, { 2, 6 } };
            int[,] input_22 = new int[,] { { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 } };
            int[,] input_23 = new int[,] { { 10, 10 }, { 30, 10 }, { 50, 2 }, { 51, 2 } };
            int[,] input_24 = new int[,] { { 0, 20 }, { 3, 4 }, { 3, 5 }, { 17, 2 } };
            int[,] input_25 = new int[,] { { 100, 1 }, { 101, 100 } };
            int[,] input_26 = new int[,] { { 0, 3 }, { 1, 9 }, { 2, 6 }, { 30, 3 } };
            int[,] input_27 = new int[,] { { 100, 0 } };
            int[,] input_28 = new int[,] { { 0, 100 }, { 1, 1 } };
            int[,] input_29 = new int[,]
            {
                { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 },
                { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }
            };
            int[,] input_30 = new int[,]
            {
                { 0, 1000 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 },
                { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 }
            };

            int[,] input_31 = new int[,]
            {
                { 0, 10 }, { 2, 12 }, { 9, 19 }, { 15, 17 },
                { 0, 9 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 },
                { 10, 10 }, { 30, 10 }, { 50, 2 }, { 51, 2 },
                { 0, 3 }, { 1, 9 }, { 2, 6 }, { 30, 3 },
                { 1, 9 }, { 0, 11 },
                { 24, 10 }, { 28, 39 }, { 43, 20 }, { 37, 5 }, { 47, 22 }, { 20, 47 }, { 15, 2 }, { 15, 34 },
                { 24, 10 }, { 28, 39 }, { 43, 20 }, { 37, 5 }, { 47, 22 }, { 20, 47 }, { 15, 2 }, { 15, 34 },
                { 35, 43 }, { 26, 1 }, { 100, 1 }, { 101, 100 }, { 10000, 17 }, { 0, 10000 },
                { 0, 1000 }, { 563, 51 }, { 986, 761 }, { 21, 16 }, { 65, 1 }, { 100, 311 }, { 0, 1 }, { 0, 1 },
                { 0, 1 }, { 0, 1 },
                { 0, 1 }, { 0, 1 }, { 0, 1 }, { 2000, 0 }, { 20, 1 }, { 0, 1 }, { 6, 1 }, { 0, 112 }, { 0, 1 }, { 0, 1 }
            };


          

             Assert.True(solution(input_1) == 7);
            Assert.True(solution(input_2) == 15);
            Assert.True(solution(input_3) == 74);
             Assert.True(solution(input_3) == solution(input_3_1));
             Assert.True(solution(input_4) == 3);
             Assert.True(solution(input_5) == 10);
             Assert.True(solution(input_6) == 13);
             Assert.True(solution(input_7) == 5);
             Assert.True(solution(input_8) == 3);
             Assert.True(solution(input_9) == 14);
             Assert.True(solution(input_10) == 25);
            Assert.True(solution(input_11) == 10);
            Assert.True(solution(input_12) == 6);
            Assert.True(solution(input_13) == 7);
            Assert.True(solution(input_14) == 15);
            Assert.True(solution(input_15) == 72);
            Assert.True(solution(input_16) == 3);
            Assert.True(solution(input_17) == 5008);
            Assert.True(solution(input_18) == 1);
            Assert.True(solution(input_19) == 50);
            Assert.True(solution(input_20) == 12);
            Assert.True(solution(input_21) == 9);
            Assert.True(solution(input_22) == 2);
            Assert.True(solution(input_23) == 6);
            Assert.True(solution(input_24) == 19);
            Assert.True(solution(input_25) == 50);
            Assert.True(solution(input_26) == 7);
            Assert.True(solution(input_27) == 0);
            Assert.True(solution(input_28) == 100);
            Assert.True(solution(input_29) == 10);
            Assert.True(solution(input_30) == 60);
            //
            //

        }

        public int solution(int[,] jobs)
        {
            //시간 값
            int time = 0;
            int arrayIndex = 0;

            KeyValuePair<int, int> temp;
            List<KeyValuePair<int, int>> insertJobList = new List<KeyValuePair<int, int>>();
            List<KeyValuePair<int, int>> jobList = new List<KeyValuePair<int, int>>();
            List<int> timeList = new List<int>();


            for (int i = 0; i < jobs.GetLength(0); i++)
            {
                insertJobList.Add(new KeyValuePair<int, int>(jobs[i, 0], jobs[i, 1]));
            }

            //시간순으로 정렬
            insertJobList = insertJobList.OrderBy(x => x.Key).ToList();


            while (jobs.GetLength(0) > arrayIndex || jobList.Count != 0)
            {
                if (jobList.Count == 0 && jobs[arrayIndex, 0] > time)
                {
                    //지금까지 넣어진 일이 끝나는 시점보다 뒤에 일이 올 때 그쪽으로 시간을 떙김

                    time = insertJobList[arrayIndex].Key;
                }

                //시간이되면 다 넣음
                while (jobs.GetLength(0) > arrayIndex && time >= insertJobList[arrayIndex].Key)
                {
                    jobList.Add(insertJobList[arrayIndex]);
                    arrayIndex++;


                    //삽입정렬 방금 넣은거만 정렬함
                    for (int i = jobList.Count - 1; i > 0; i--)
                    {
                        if (jobList[i].Value < jobList[i - 1].Value)
                        {
                            temp = jobList[i];
                            jobList[i] = jobList[i - 1];
                            jobList[i - 1] = temp;
                        }
                        else
                        {
                            break;
                        }
                    }
                }

                //수식계산
                //SJF 스케줄링
                if (jobList.Count > 0)
                {
                    {
                        //한방에 계산하기
                        time = jobList[0].Value + time;
                        timeList.Add(time - jobList[0].Key);
                        jobList.RemoveAt(0);
                    }
                }
            }


            return (int)(Math.Floor((float)timeList.Sum() / (float)jobs.GetLength(0)));
        }



    }
}

    
    