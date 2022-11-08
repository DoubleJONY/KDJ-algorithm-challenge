#![allow(non_snake_case)]

use std::io::{self, Read};
use std::num::FpCategory::Nan;
use std::collections::HashMap;

fn main() {
    ///
    // let dr: [i32; 4] = [-1, 0, 1, -0];
    // let dc: [i32; 4] = [0, 1, 0, -1];

    let mut input = String::new();

    io::stdin().read_line(&mut input).unwrap();

    let line = input.trim().split(' ')
        .map(|x| x.parse().unwrap()).collect::<Vec<i32>>();

    let N: usize = line[0] as usize;
    let M: usize = line[1] as usize;//Color Count

    let mut map: Vec<Vec<i32>> = vec![vec![0; N]; N];
    let mut tempMap: Vec<Vec<i32>> = vec![vec![0; N]; N];
    let mut vi: Vec<Vec<bool>> = vec![vec![false; N]; N];
    let mut rainbowVec : Vec<(usize,usize)> = Vec::new();

    for i in 0..N
    {
        input.clear();
        io::stdin().read_line(&mut input).unwrap();
        let line = input.trim().split(' ')
            .map(|x| x.parse().unwrap()).collect::<Vec<i32>>();

        for j in 0..N {
            map[i][j] = line[j];

        }

    }



    println!("{}", AutoPlay(&mut map, &mut tempMap, &mut vi, &mut rainbowVec, N))
}

fn AutoPlay(map: &mut Vec<Vec<i32>>, tempMap: &mut Vec<Vec<i32>>, vi: &mut Vec<Vec<bool>>, rainbowVec: &mut Vec<(usize, usize)>, N: usize) -> usize
{
    let mut ans = 0;

    while (true)
    {
//         for i in 0..N
//         {
//             println!("{0} {1} {2} {3} {4} {5} ", map[i][0],map[i][1],map[i][2],map[i][3],map[i][4],map[i][5]);
//
//         }
// println!("Aaa");
        let bestSize = BFS(map, vi, rainbowVec, N);

        if bestSize < 2
        {
            break;
        } else {
            ans += bestSize
        }

        Gravity(map, N);

        Rotate(map, tempMap, N);

        Gravity(map, N);
    }
    ans
}

fn BFS(map: &mut Vec<Vec<i32>>, vi: &mut Vec<Vec<bool>>, rainbowVec: &mut Vec<(usize, usize)>, N: usize) -> usize
{
    let mut ret = 0;
    let mut q: Vec<(usize, usize)> = Vec::new();
    let dr: [i32; 4] = [-1, 0, 1, -0];
    let dc: [i32; 4] = [0, 1, 0, -1];

    //block size, start xy;
    let mut colorBlock: Vec<(i32,usize, usize, usize, usize)> = Vec::new();

    rainbowVec.clear();

    //init
    for i in 0..N {
        for j in 0..N {
            vi[i][j] = false;
            if map[i][j] == 0
            {
                rainbowVec.push((i,j));
            }
        }
    }

    //search
    for i in 0..N {
        for j in 0..N {
            if map[i][j] == -1 ||  map[i][j] == -2
            {
                vi[i][j] = true;
                continue;
            }
            else if map[i][j] == 0
            {
                continue;
            }
            if vi[i][j] == false {
                let mut rainbow = 0;
                ret = 1;
                q.push((i, j));
                vi[i][j] = true;
                //bfs
                while (q.len() != 0)
                {
                    let item = q[0];

                    q.remove(0);
                    let cy = item.0;
                    let cx = item.1;

                    for k in 0..4 {
                        let ny = ((cy as i32) + dr[k]) as usize;
                        let nx = ((cx as i32) + dc[k]) as usize;

                        //-1 is black, -2 is empty
                        if ny < 0 || nx < 0 || ny >= N || nx >= N || map[ny][nx] == -1 || map[ny][nx] == -2
                        {
                            continue;
                        }
                        //0 is rainbow
                        if vi[ny][nx] == false && (map[ny][nx] == map[i][j] || map[ny][nx] == 0)
                        {
                            q.push((ny, nx));
                            vi[ny][nx] = true;
                            if map[ny][nx] == 0 {
                                rainbow +=1;
                            }
                            ret += 1;
                        }
                    }
                }

                if ret > 1
                {
                    colorBlock.push((map[i][j],ret, i, j, rainbow));
                }

                for pos in &mut *rainbowVec {
                    vi[pos.0][pos.1] = false;
                }
            }
        }
    }


    //remove
    let mut bestSizeColor = 0;
    let mut bestSize: usize = 0;
    let mut bestRainbow: usize = 0;
    let mut x: usize = 0;
    let mut y: usize = 0;

    for i in 0..N {
        for j in 0..N {
            vi[i][j] = false;
        }
    }

    for block in colorBlock {
        // println!("{}", block.0);
        if (bestSize <= block.1)
        {
            if(bestSize == block.1)
            {
                //more rainbow?
                if(bestRainbow < block.4)
                {
                    bestSizeColor = block.0;
                    bestSize = block.1;
                    x = block.2;
                    y = block.3;
                    bestRainbow = block.4;
                }
                    //more col row?
                else if(bestRainbow == block.4)
                {
                    if(x  < block.2)
                    {
                        bestSizeColor = block.0;
                        bestSize = block.1;
                        x = block.2;
                        y = block.3;
                        bestRainbow = block.4;
                    }
                    else if(x == block.2 && y < block.3)
                    {
                        bestSizeColor = block.0;
                        bestSize = block.1;
                        x = block.2;
                        y = block.3;
                        bestRainbow = block.4;
                    }
                }
            }
            else
            {
                bestSizeColor = block.0;
                bestSize = block.1;
                x = block.2;
                y = block.3;
                bestRainbow = block.4;
            }

        }
    }
    //
    // if bestSize < 2
    // {
    //     return 0;
    // }
    //
    // let bestColorBlock : (usize, usize, usize) = match colorBlock.get(&bestSizeColor) {
    //     None => {(0,0,0)}
    //     Some(a) => {&a}
    // };
    //

    q.push((x, y));
    let mut viPos: Vec<(usize, usize)> = Vec::new();
    viPos.push((x,y));


    //bfs
    while (q.len() != 0)
    {
        let item = q[0];

        q.remove(0);
        let cy = item.0;
        let cx = item.1;

        for k in 0..4 {
            let ny = ((cy as i32) + dr[k]) as usize;
            let nx = ((cx as i32) + dc[k]) as usize;

            if ny < 0 || nx < 0 || ny >= N || nx >= N
            {
                continue;
            }
            //0 is rainbow
            if vi[ny][nx] == false && (map[ny][nx] == bestSizeColor || map[ny][nx] == 0)
            {
                q.push((ny, nx));
                vi[ny][nx] = true;
                viPos.push((ny,nx))
            }
        }
    }

    for viPo in &viPos {
        map[viPo.0][viPo.1] = -2;
    }

     // println!(" ab + {}", bestSize * bestSize);
     // println!("{}", bestSizeColor);
    bestSize * bestSize
}

fn Rotate(map: &mut Vec<Vec<i32>>, tempMap: &mut Vec<Vec<i32>>, N: usize)
{
    for i in 0..N
    {
        for j in 0..(N)
        {
            (tempMap[i])[j] = map[j][N - i - 1];
        }
    }


    for i in 0..N
    {
        for j in 0..N
        {
            (map[i])[j] = tempMap[i][j];
            tempMap[i][j] = 0;
        }
    }
}

fn Gravity(map: &mut Vec<Vec<i32>>, N: usize)
{
    for i in (0..N - 1).rev() {
        for j in (0..N) {
            if map[i][j] == -2 || map[i][j] == -1 {
                continue;
            }

            for k in i + 1..N {
                // (k, j) is empty
                if (map[k][j] == -2) {
                    map[k][j] = map[k - 1][j];
                    map[k - 1][j] = -2;
                } else {
                    // (k, j) is not empty
                    break;
                }
            }
        }
    }
}
