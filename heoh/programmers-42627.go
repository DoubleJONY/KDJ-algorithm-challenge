import (
    "container/heap"
    "sort"
)

type Job = []int
const START = 0
const END = 1

type ByStart []Job
func (c ByStart) Len() int { return len(c) }
func (c ByStart) Swap(i, j int) { c[i], c[j] = c[j], c[i] }
func (c ByStart) Less(i, j int) bool { return c[i][START] < c[j][START] }

type JobQueue []Job
func (q JobQueue) Len() int { return len(q) }
func (q JobQueue) Peek() Job { return q[0] }
func (q *JobQueue) Add(j Job) { *q = append(*q, j) }
func (q *JobQueue) Pop() Job {
    element := (*q)[0]
    *q = (*q)[1:]
    return element
}

type JobHeap []Job
func (h JobHeap) Len() int { return len(h) }
func (h JobHeap) Swap(i, j int) { h[i], h[j] = h[j], h[i] }
func (h JobHeap) Less(i, j int) bool { return h[i][END] < h[j][END] }
func (h *JobHeap) Push(element interface{}) {
    *h = append(*h, element.(Job))
}
func (h *JobHeap) Pop() interface{} {
    old := *h
    n := len(old)
    element := old[n-1]
    *h = old[0 : n-1]
    return element
}

func Max(x, y int) int {
    if x < y {
        return y
    }
    return x
}

func solution(jobs [][]int) int {
    sort.Sort(ByStart(jobs))

	totalDelay := 0
    t := 0
    q := JobQueue(jobs)
	pq := make(JobHeap, 0)
    for q.Len() > 0 || pq.Len() > 0 {
        if q.Len() > 0 {
            job := q.Peek()
    
            if job[START] <= t {
                q.Pop()
                heap.Push(&pq, job)
                continue
            }
            if pq.Len() == 0 {
                t = Max(t, job[0])
                continue
            }
        }

        if pq.Len() > 0 {
            job := heap.Pop(&pq).(Job)
            t = (Max(t, job[0]) + job[1])
            totalDelay += (t - job[0])
        }
    }

    return totalDelay / len(jobs)
}
