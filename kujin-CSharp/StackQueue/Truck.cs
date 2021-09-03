using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using NUnit.Framework;

namespace PS
{
    [TestFixture]
    public class Truck
    {
        [Test]
        public void TruckPS()
        {
            int bridgeLength = 2;
            int weight = 10;
            int[] truck = { 7,4,5,6 };
            Assert.False(solution_Truck(bridgeLength, weight, truck) == 8);
        }
        
       
        public int solution_Truck(int bridge_length, int weight, int[] truck_weights) 
        {
            int answer = 0;

            Queue<int> truckWeights = new Queue<int>();
            List<int> arriverTruckWeights = new List<int>();
            Queue<KeyValuePair<int, int>> passBridgeTruck = new Queue<KeyValuePair<int, int>>();

            int currentLoadBridge = 0;

            foreach (var truck  in truck_weights)
            {
                truckWeights.Enqueue(truck);
            }
            
            
            while (true)
            {
                //트럭이 가는 거리 계산
                if (passBridgeTruck.Count != 0)
                {
                    for (int i = 0; i < passBridgeTruck.Count; i++)
                    {
                        var temp = passBridgeTruck.Dequeue();
                        passBridgeTruck.Enqueue(new KeyValuePair<int, int>(temp.Key, temp.Value + 1));

                    }
                    if (passBridgeTruck.Peek().Value == bridge_length)
                    {
                        passBridgeTruck.Dequeue();
                    }
                }
                answer++;
            
                if (truckWeights.Count== 0 && passBridgeTruck.Count == 0)
                {
                    break;
                    
                }
                
                //없으면 넣기
                if (passBridgeTruck.Count == 0)
                {
                   passBridgeTruck.Enqueue(new KeyValuePair<int, int>(truckWeights.Dequeue(),0));
                   
                }
                else if(truckWeights.Count != 0)
                {


                    currentLoadBridge = passBridgeTruck.Sum(x => x.Key);
                    if (currentLoadBridge <= weight)
                    {
                        if (currentLoadBridge + truckWeights.Peek() <= weight)
                        {
                            passBridgeTruck.Enqueue(new KeyValuePair<int, int>(truckWeights.Dequeue(), 0));
                        }
                    }
                }

                
            }
            return answer;
        }
    }
}