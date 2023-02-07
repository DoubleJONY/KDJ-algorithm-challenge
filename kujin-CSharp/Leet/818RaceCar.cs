public class Solution {
    public int Racecar(int target) {
        Queue<(int move,int position, int speed)> queue = new Queue<(int, int, int)>();
        HashSet<(int position, int speed)> visited = new HashSet<(int, int)>();

        queue.Enqueue((0,0,1));
        while(queue.Any())
        {
            var current = queue.Dequeue();
            if(current.position == target) 
            {
                return current.move;
            }
            
            if(visited.Contains((current.position,current.speed)))
            {
                continue;
            }

            else
            {
                visited.Add((current.position, current.speed));
                queue.Enqueue((current.move+1, current.position+current.speed, current.speed*2));

                if((current.position + current.speed > target && current.speed > 0) ||
                   (current.position + current.speed < target && current.speed < 0)) 
                {
                    if(current.speed > 0)
                    {
                        current.speed = -1;
                    }
                    else
                    {
                        current.speed = 1;
                    }
                }
                queue.Enqueue((current.move + 1, current.position, current.speed));
            }
        }
        return 0;
    }
}