#![allow(non_snake_case)]

use std::io::{self, Read};

fn main() {
    ///


    // let dr: [i32; 4] = [-1, 0, 1, -0];
    // let dc: [i32; 4] = [0, 1, 0, -1];

    let mut input = String::new();

    io::stdin().read_line(&mut input).unwrap();

    let line = input.trim().split(' ')
        .map(|x| x.parse().unwrap()).collect::<Vec<i32>>();

    let N: usize = line[0] as usize;
    let Q: usize = line[1] as usize;
    let mapSize = 1 << N;

    let mut ans: usize = 0;

    let mut map: Vec<Vec<usize>> = vec![vec![0; mapSize]; mapSize];
    let mut tempMap: Vec<Vec<usize>> = vec![vec![0; mapSize]; mapSize];
    let mut vi: Vec<Vec<bool>> = vec![vec![false; mapSize]; mapSize];
    let mut l = vec![0; Q];

    for i in 0..mapSize
    {
        input.clear();
        io::stdin().read_line(&mut input).unwrap();
        let line = input.trim().split(' ')
            .map(|x| x.parse().unwrap()).collect::<Vec<usize>>();
        for j in 0..mapSize
        {
            (map[i])[j] = line[j];
        }
    }

    input.clear();
    io::stdin().read_line(&mut input).unwrap();
    let line = input.trim().split(' ')
        .map(|x| x.parse().unwrap()).collect::<Vec<usize>>();
    for i in 0..Q
    {
        l[i] = line[i];
    }

    // //파이어스톰!!
    for i in 0..Q
    {
        if l[i] != 0
        {
            rotate(&mut map, &mut tempMap, mapSize, l[i]);
        }

        updateIce(&mut map, mapSize);
    }

    //
    // // 남아있는 얼음의 합

    let mut remains = 0;
    for i in 0..mapSize {
        for j in 0..mapSize {
            remains += map[i][j];
        }
    }
    println!("{}", remains);
    //
    // // 남아있는 얼음 중 가장 큰 덩어리가 차지하는 칸의 개수

    let mut ans = 0;
    for i in 0..mapSize {
        for j in 0..mapSize {
            if vi[i][j] == false && map[i][j] > 0
            {
                let solution = bfs(&mut map, &mut vi, i,j, mapSize);
                if ans < solution
                {
                    ans = solution;
                }
            }
        }
    }

    match ans < 2 {
        true => { println!("{}", '0'); }
        false => { println!("{}", ans); }
    }
}


fn bfs(map: &mut Vec<Vec<usize>>, vi: &mut Vec<Vec<bool>>, x: usize, y: usize, mapSize : usize) -> usize
{
    let mut ret  = 0;
    let mut q: Vec<(usize, usize)> = Vec::new();
    let dr: [i32; 4] = [-1, 0, 1, -0];
    let dc: [i32; 4] = [0, 1, 0, -1];

    q.push((x,y));
    vi[x][y] = false;
    while (q.len() != 0)
    {
        let item = q[0];


        q.remove(0);
        let cy = item.0;
        let cx = item.1;

        for k in 0..4 {
            let ny = ((cy as i32) + dr[k]) as usize;
            let nx = ((cx as i32) + dc[k]) as usize;

            if ny < 0 || nx < 0 || ny >= mapSize || nx >= mapSize
            {
                continue;
            }
            if vi[ny][nx] == false && map[ny][nx] > 0
            {
                q.push((ny, nx));
                vi[ny][nx] = true;
                ret+=1;
            }
        }
    }

    ret
}
//&로 넘기기
fn updateIce(map: &mut Vec<Vec<usize>>, mapSize: usize)
{
    let mut v: Vec<(usize, usize)> = Vec::new();

    let dr: [i32; 4] = [-1, 0, 1, -0];
    let dc: [i32; 4] = [0, 1, 0, -1];

    for i in 0..mapSize {
        for j in 0..mapSize {
            if map[i][j] == 0
            {
                continue;
            }
            let mut adjIce = 0;
            for k in 0..4 {
                let ny: usize = ((i as i32) + dr[k]) as usize;
                let nx: usize = ((j as i32) + dc[k]) as usize;

                if (ny < 0 || nx < 0 || ny >= mapSize || nx >= mapSize || map[ny][nx] == 0)
                {
                    continue;
                }

                adjIce += 1;

            }
            if (adjIce < 3)
            {
                v.push((i, j));
            }
        }
    }

    for tuple in v
    {
        map[tuple.0][tuple.1] -= 1;
    }
}

fn rotate(map: &mut Vec<Vec<usize>>, tempMap: &mut Vec<Vec<usize>>, mapSize: usize, L: usize)
{
    let n = 1 << L;

    let mut mapPosY: usize = 0;
    let mut mapPosX: usize = 0;
    while mapPosY < mapSize
    {
        mapPosX = 0;
        while mapPosX < mapSize
        {
            let mut ii = 0;
            let mut jj = 0;
            for j in mapPosX..(mapPosX + n)
            {
                jj = 0;
                for i in (mapPosY..(mapPosY + n)).rev()
                {
                    (tempMap[ii])[jj] = map[i][j];
                    jj += 1;
                }
                ii += 1;
            }

            ii = 0;
            jj = 0;

            for i in mapPosY..(mapPosY + n)
            {
                jj = 0;
                for j in (mapPosX..(mapPosX + n))
                {
                    (map[i])[j] = tempMap[ii][jj];
                    jj += 1;
                }
                ii += 1;
            }

            mapPosX += n;
        }
        mapPosY += n;
    }

}
