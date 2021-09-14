using NUnit.Framework;

namespace Algo.Brutal
{
    [TestFixture]
    public class Brutal_3
    {
        [Test]
        public void Test()
        {
            int brown_1 = 10;
            int yellow_1 = 2;
            int[] answer_1 = new int[] { 4, 3 };
        }

        public int[] solution(int brown, int yellow) {
            int[] answer = new int[2] { 0, 0};
            int x, y;
            int width = brown + yellow;

            for(int i=1; i<=width;i++)
            {
                x = i;
                y = width / x;
                if (x > y || x==y)
                {
                    if((width-((x*2)+(y*2)-4))==yellow)
                    {
                        answer[0] = x;
                        answer[1] = y;
                        break;
                    }
                }
            }




            return answer;
        
        }
    
        
        
    }
}