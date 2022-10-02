#![allow(non_snake_case)]

use std::io::{self, Read};

fn main() {
    // use std::collections::VecDeque;
// use std::io::{stdin, self, Read, Write};
    // let stdout = io::stdout();
    // let mut output = io::BufWriter::new(stdout.lock());
    let mut input = String::new();
    // let stdin = io::stdin();
    // stdin.lock().read_to_string(&mut input).unwrap();

    io::stdin().read_line(&mut input).unwrap();

    let line = input.trim().split(' ')
        .map(|x| x.parse().unwrap()).collect::<Vec<i32>>();
    //
    // let lines = &mut input.lines();

    let n: i32 = line[0];
    let k: i32 = line[1];
    let n_size: usize = n as usize;
    let mut belt = vec![0; n_size * 2];

    input.clear();
    io::stdin().read_line(&mut input).unwrap();
    let line = input.trim().split(' ')
        .map(|x| x.parse().unwrap()).collect::<Vec<usize>>();

    for i in 0..(n_size * 2)
    {
        belt[i] = line[i];
    }


    let mut robot = vec![false; n_size * 2];
    let mut zero_cnt = 0;
    let mut time = 0;

    loop {
        time = time + 1;

        // 벨트 회전 + 로봇 이동
        let last_idx: usize = 2 * n_size - 1;
        let last_belt: usize = belt[last_idx];
        for i in (1..=last_idx).rev()
        {
            belt[i] = belt[i - 1];
        }

        belt[0] = last_belt;

        let lastRobot : bool = robot[last_idx];
        for i in (1..=last_idx).rev()
        {
            robot[i] = robot[i - 1];
        }
        robot[0] = lastRobot;

        if true == robot[n_size - 1]
        {
            robot[n_size - 1] = false;
        }

        for i in (0..(n_size - 1)).rev() {

            if false == robot[i]
            {
                continue;
            }

            if 0 < belt[i + 1] && false == robot[i + 1]
            {
                robot[i + 1] = true;
                belt[i + 1] = belt[i + 1] - 1;
                robot[i] = false;
                if belt[i + 1] == 0
                {
                    zero_cnt = zero_cnt + 1;
                }
            }
        }

        if false == robot[0] && belt[0] > 0
        {
            robot[0] = true;
            belt[0] = belt[0] - 1;
            //if문 다른표현
            match belt[0] {
                0 => zero_cnt = zero_cnt + 1,
                _ => (),
            }
        }

        if true == robot[n_size - 1]
        {
            robot[n_size - 1] = false;
        }


        if zero_cnt >= k
        {
            break;
        }
    }

    println!("{}", time);
}

// using System;
// using System.Collections.Generic;
// using System.IO;
// using System.Linq;
// using System.Text;
// using System.Threading.Tasks;
//
// namespace TestApp1
// {
//     public class Program
//     {
//         static int N, K;
//         static int[] belt;
//
//         static StringBuilder sb = new StringBuilder();
//         static StreamWriter sw = new StreamWriter(new BufferedStream(Console.OpenStandardOutput()));
//         static StreamReader sr = new StreamReader(new BufferedStream(Console.OpenStandardInput()));
//
//         public static void Main()
//         {
//
//
//             string[] tmp = sr.ReadLine().Split(' ');
//             N = Convert.ToInt32(tmp[0]);
//             K = Convert.ToInt32(tmp[1]);
//             belt = new int[N * 2];
//
//             tmp = sr.ReadLine().Split(' ');
//             for (int i = 0; i < N * 2; i++)
//             {
//                 belt[i] = Convert.ToInt32(tmp[i]);
//             }
//             // for (int i = 0; i < N; i++)
//             // {
//             //     belt[i+N] = Convert.ToInt32(tmp[0]);
//             // }
//
//             bool[] robot = new bool[N * 2];
//             int zeroCnt = 0;
//             int time = 0;
//
//             while (true)
//             {
//                 time++;
//
//                 // 벨트 회전 + 로봇 이동
//                 int lastIdx = 2 * N - 1;
//                 int lastBelt = belt[lastIdx];
//                 for (int i = lastIdx; i > 0; i--)
//                     belt[i] = belt[i - 1];
//                 belt[0] = lastBelt;
//
//                 bool lastRobot = robot[lastIdx];
//                 for (int i = lastIdx; i > 0; i--) robot[i] = robot[i - 1];
//                 robot[0] = lastRobot;
//
//                 if (robot[N - 1]) robot[N - 1] = false;
//
//                 for (int i = N - 2; i >= 0; i--)
//                 {
//                     if (!robot[i]) continue;
//                     if (belt[i + 1] > 0 && !robot[i + 1])
//                     {
//                         robot[i + 1] = true;
//                         belt[i + 1]--;
//                         robot[i] = false;
//                         if (belt[i + 1] == 0) zeroCnt++;
//                     }
//                 }
//
//                 if (!robot[0] && belt[0] > 0)
//                 {
//                     robot[0] = true;
//                     belt[0]--;
//                     if (belt[0] == 0) zeroCnt++;
//                 }
//
//                 if (robot[N - 1]) robot[N - 1] = false;
//
//
//                 if (zeroCnt >= K)
//                     break;
//
//             }
//
//             Console.WriteLine(time);
//
//         }
//     }
// }