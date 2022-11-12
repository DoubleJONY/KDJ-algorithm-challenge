#![allow(non_snake_case)]

use std::io::{self, Read};
use std::num::FpCategory::Nan;
use std::collections::HashMap;


fn main() {

    let mut input = String::new();

    io::stdin().read_line(&mut input).unwrap();

    let line = input.trim().split(' ')
        .map(|x| x.parse().unwrap()).collect::<Vec<usize>>();

    let N: usize = line[0] as usize;//Map Size
    let M: usize = line[1] as usize;//try

    let mut map: Vec<Vec<usize>> = vec![vec![0; N]; N];
    let mut cloudCheck: Vec<Vec<bool>> = vec![vec![false; N]; N];

    let mut cloudPosition : Vec<(usize,usize)> = Vec::new();

    for i in 0..N
    {
        input.clear();
        io::stdin().read_line(&mut input).unwrap();
        let line = input.trim().split(' ')
            .map(|x| x.parse().unwrap()).collect::<Vec<usize>>();

        for j in 0..N {
            map[i][j] = line[j];

        }

    }


    //init Cloud
    cloudPosition.push((N -1, 0));
    cloudPosition.push((N -1, 1));
    cloudPosition.push((N - 2, 0));
    cloudPosition.push((N - 2, 1));

    let mut d : usize = 0;
    let mut s : usize = 0;
    for i in 0..M{
        input.clear();
        io::stdin().read_line(&mut input).unwrap();
        let line = input.trim().split(' ')
            .map(|x| x.parse().unwrap()).collect::<Vec<usize>>();

        d = line[0];
        s = line[1];
        CloudMove(&mut map,&mut cloudPosition,&mut cloudCheck,d - 1, s, N);
        WaterCopyBug(&mut map,&mut cloudPosition, N);
        cloudPosition.clear();

        MakeCloud(&mut map,&mut cloudPosition,&mut cloudCheck, N);

    }

    let mut ans : usize = 0;
    for i in 0..N {
        for j in 0..N {
            ans += map[i][j];
        }
    }


    println!("{}", ans);
}

fn CloudMove(map: &mut Vec<Vec<usize>>,cloudPosition: &mut Vec<(usize,usize)> ,cloudCheck: &mut  Vec<Vec<bool>>, d : usize, s : usize, N : usize)
{
    let mut x : usize = 0;
    let mut y : usize = 0;
    let mut nx : i32 = 0;
    let mut ny : i32 = 0;

    let dc: [i32; 8] = [0, -1, -1, -1, 0, 1, 1, 1];
    let dr: [i32; 8] = [-1, -1, 0, 1, 1, 1, 0, -1];

    for i in 0..cloudPosition.len() {
        x = cloudPosition[i].0;
        y = cloudPosition[i].1;

        nx = (((x as i32) + (dc[d] * s as i32)) % N as i32) as i32;
        if nx < 0
        {
            nx += N as i32;
        }

        ny = (((y as i32) + (dr[d] * s as i32)) % N as i32) as i32;

        if ny < 0
        {
            ny += N as i32;
        }

        cloudCheck[nx as usize ][ny as usize] = true;
        map[nx as usize ][ny as usize ] += 1;

        cloudPosition[i].0 = nx as usize;
        cloudPosition[i].1 = ny as usize;
    }
}
fn WaterCopyBug(map: &mut Vec<Vec<usize>>,cloudPosition: &mut Vec<(usize,usize)> , N : usize)
{
    let mut x : usize = 0;
    let mut y : usize = 0;

    let mut count :usize = 0;

    let dc: [i32; 8] = [0, -1, -1, -1, 0, 1, 1, 1];
    let dr: [i32; 8] = [-1, -1, 0, 1, 1, 1, 0, -1];

    for i in 0..cloudPosition.len() {
        count = 0;
        x = cloudPosition[i].0;
        y = cloudPosition[i].1;
        //대각선 계산
        for j in 0..4 {
            let mut nx : i32 = (((x as i32) + dc[j * 2 + 1])) as i32;
            let mut ny : i32 = (((y as i32) + dr[j * 2 + 1])) as i32;


            if nx < 0 || nx >= (N  as i32) || ny < 0 || ny >= (N as i32)
            {

                continue;
            }

            if map[nx as usize][ny as usize] > 0
            {
                count+=1;
            }
        }
        map[x][y] += count;
    }
}


fn MakeCloud(map: &mut Vec<Vec<usize>>,cloudPosition: &mut Vec<(usize,usize)>, cloudCheck: &mut  Vec<Vec<bool>>, N : usize)
{

    for i in 0..N {
        for j in 0..N {
            if cloudCheck[i][j] == false && map[i][j] >= (2 as usize) {
                cloudPosition.push((i, j));
                map[i][j] -= 2;
            }
        }
    }

    for i in 0..N {
        for j in 0..N {
            cloudCheck[i][j] = false;
        }

    }
}
