using NUnit.Framework;

namespace Algo
{
    [TestFixture]
    public class Graph_3
    {
        [Test]
        public void Test1()
        {
            // 5	[2, 4]	[1, 3, 5] 5
            int n_1 = 5;
            int[] a1 = new int[] { 6, 5, 2, 7, 1, 4, 2, 4, 6 };
            int[] a2 = new int[] { 6, 2, 4, 0, 5, 0, 6, 4, 2, 4, 2, 0 };
            int[] a3 = new int[] {5, 2, 7, 1, 6, 3};
            int[] a4 = new int[] {6, 6, 6, 4, 4, 4, 2, 2, 2, 0, 0, 0, 1, 6, 5, 5, 3, 6, 0};

            Assert.True(solution(a4) == 3);
            Assert.True(solution(a2) == 3);
            Assert.True(solution(a3) == 3);
            Assert.True(solution(a1) == 3);

        }
        public int solution(int[] arrows)
        {
            int answer = 0;
            int[,] room = new int[500, 500];
            int directX = 0;
            int directY = 0;
            
            int coordinateX, coordinateY;
            int prevCoordinateX = 0, prevCoordinateY = 0;
            coordinateX = coordinateY = (500 / 2) - 1;
            
            room[coordinateX, coordinateY] = 1;

            bool makeRoom = true;
            
            for (int i = 0; i < arrows.Length; i++)
            {
                directX = 0;
                directY = 0;
                switch (arrows[i])
                {
                    //상
                    case 0:
                        directX = 0;
                        directY = 1;
                        break;
                    //하
                    case 4:
                        directX = 0;
                        directY = -1;
                        break;
                    //좌
                    case 6:
                        directX = -1;
                        directY = 0;
                        break;
                    //우
                    case 2:
                        directX = 1;
                        directY = 0;
                        break;
                    //우상
                    case 1:
                        directX = 1;
                        directY = 1;
                        break;
                    //우하
                    case 3:
                        directX = 1;
                        directY = -1;
                        break;
                    //좌하
                    case 5:
                        directX = -1;
                        directY = -1;
                        break;
                    //좌상
                    case 7:
                        directX = -1;
                        directY = 1;
                        break;
                }
                
                
                coordinateX += directX;
                coordinateY += directY;
                
                if (room[coordinateX, coordinateY] == 0)
                {
                   
                    room[coordinateX, coordinateY] = 1;
                    makeRoom = true;
                }
                else
                {
                    
                    if (makeRoom == true && (prevCoordinateX != coordinateX || prevCoordinateY != coordinateY))
                    {
                        answer++;
                        makeRoom = false;
                    }
                }
                
                prevCoordinateX = coordinateX;
                prevCoordinateY = coordinateY;
                
            }

            if (makeRoom == true)
                answer++;
            
           
            return answer;
        }
    }
}