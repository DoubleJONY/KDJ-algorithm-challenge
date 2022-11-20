#![allow(non_snake_case)]

use std::io::{self, Read};
use std::num::FpCategory::Nan;
use std::collections::HashMap;


fn main() {

    // let dc: [i32; 4] = [-1, 1, 0, 0];
    // let dr: [i32; 4] = [0, 0, -1, 1];

    let mut input = String::new();

    io::stdin().read_line(&mut input).unwrap();

    let line = input.trim().split(' ')
        .map(|x| x.parse().unwrap()).collect::<Vec<usize>>();

    let N: usize = line[0] as usize;//Map Size
    let M: usize = line[1] as usize;//try
    let mut marbleCount: usize = 0;
    let mut score: usize = 0;

    let mut map: Vec<Vec<usize>> = vec![vec![0; N]; N];
    let mut marbleVec: Vec<usize> = Vec::new();


    for i in 0..N
    {
        input.clear();
        io::stdin().read_line(&mut input).unwrap();
        let line = input.trim().split(' ')
            .map(|x| x.parse().unwrap()).collect::<Vec<usize>>();

        for j in 0..N {
            map[i][j] = line[j];
            if map[i][j] != 0
            {
                marbleCount += 1;
            }
        }
    }


    //simultor
    for i in 0..M
    {
        input.clear();
        io::stdin().read_line(&mut input).unwrap();
        let line = input.trim().split(' ')
            .map(|x| x.parse().unwrap()).collect::<Vec<usize>>();


        let count = Blizzard(&mut map, line[0], line[1], N);
        // for i in 0..N {
        //     println!("{0} {1} {2}",map[i][0], map[i][1], map[i][2]);
        // }
        // println!("bli");
        marbleCount -= count;
        InsertMarbleVec(&mut map, &mut marbleVec, N, marbleCount);

        score += MarbleExplode(&mut marbleVec, N, marbleCount);

        marbleCount = MarbleSplit(&mut marbleVec, N);

        UpdateMap(&mut map, &mut marbleVec, N);

        // for i in 0..N {
        //     println!("{0} {1} {2}",map[i][0], map[i][1], map[i][2]);
        // }
        // println!("fsdfs");
    }


    println!("{}", score);
}

//magic
fn Blizzard(map: &mut Vec<Vec<usize>>, magicDir: usize, magicDistance: usize, N: usize) -> usize
{
    let dc: [i32; 4] = [-1, 1, 0, 0];
    let dr: [i32; 4] = [0, 0, -1, 1];
    let mut count: usize = 0;


    for i in 0..magicDistance {
        let ny = (((N / 2) as i32) + ((i + 1) as i32) * dr[magicDir - 1]) as usize;
        let nx = (((N / 2) as i32) + ((i + 1) as i32) * dc[magicDir - 1]) as usize;

        if ny >= N || nx >= N || ny < 0 || nx < 0 || map[nx][ny] == 0
        {
            continue;
        }
        map[nx][ny] = 0;
        count += 1;
    }

    count
}

//일단 일차 배열로 바꾸고 생각하자
fn InsertMarbleVec(map: &mut Vec<Vec<usize>>, marbleVec: &mut Vec<usize>, N: usize, marbleCount: usize)
{
    let dr: [i32; 4] = [-1, 0, 1, -0];
    let dc: [i32; 4] = [0, 1, 0, -1];

    marbleVec.clear();

    let mut dir: usize = 0;
    let mut len: usize = 1;

    let mut curX: i32 = (N / 2) as i32;
    let mut curY: i32 = curX;

    let mut idx: usize = 0;

    while idx != marbleCount
    {
        //저번 토네이도 처럼 두번씩 이동한뒤 이동거리 증가
        for notMean in 0..2 {
            for i in 0..len {
                curX += dr[dir];
                curY += dc[dir];
                if curY >= N as i32 || curX >= N as i32 || curY < 0 || curX < 0
                {
                    // curX -= dr[dir];
                    // curY -= dc[dir];
                    break;
                }
                //0을 제외하고 넣으면 자동으로 채워지는 효과를 본다
                if map[curY as usize][curX as usize] != 0 {

                    marbleVec.push(map[curY as usize][curX as usize]);
                    idx += 1;
                }

                if idx == marbleCount
                {
                    return;
                }
            }
            // 방향 바꾸기
            dir = (dir + 1) % 4;
        }
        len += 1;
    }
}

fn MarbleExplode(mut marbleVec: &mut Vec<usize>, N: usize, marbleCount: usize) -> usize
{
    let mut check: bool = true;
    let mut score: usize = 0;

    //
    let mut tempMarbleVec: Vec<usize> = Vec::new();
    let mut marblePosition: usize = 0;
    let aa = marbleVec.len();
    while check
    {
        tempMarbleVec.clear();
        marblePosition = 0;
        //계속하는지 여부 false이면 폭파없이 쭉온거다
        check = false;
        let mut i = 0;

        while i < marbleVec.len() {
            if i >= marbleVec.len()
            {
                i = 0;
                check = true;
                break;
            }

            marblePosition = i;

            //일단 같은 구슬이 앞으로 몇개있는지 확인하기위해서
            while marblePosition < marbleVec.len() && marbleVec[marblePosition] == marbleVec[i] {
                marblePosition += 1;
            }

            if marblePosition - i < 4
            {
                //안터트리고 임시 벡터에 넣자
                for j in i..marblePosition {
                    tempMarbleVec.push(marbleVec[i]);
                }
            } else {
                score += marbleVec[i] * (marblePosition - i); // 폭발한 구슬 점수 업데이트
                //폭파하고 계속하기
                check = true;
            }


            i = marblePosition - 1;
            i +=1;
        }
        marbleVec.clear();
        //배열 업데이트 러스트에선 임시 배열이 여기서 수명이 죽기때문에  매개변수로 가져온 배열에 넣자
        if(tempMarbleVec.len() > 0)
        {
            for itemIndex in 0..tempMarbleVec.len() {
                marbleVec.push(tempMarbleVec[itemIndex]);
            }
        }
    }

    score
}

fn MarbleSplit(mut marbleVec: &mut Vec<usize>, N: usize) -> usize
{

    //
    let mut tempMarbleVec: Vec<usize> = Vec::new();
    let mut marblePosition: usize = 0;

    let mut i : usize = 0;
    while i < marbleVec.len() {
        if i  >= marbleVec.len()
        {
          //  i = 0;
            break;
        }
        if tempMarbleVec.len() >= N * N
        {
            break;
        }
        marblePosition = i;
        //일단 같은 구슬이 앞으로 몇개있는지 확인하기위해서
        while  marblePosition < marbleVec.len() && marbleVec[marblePosition] == marbleVec[i] {
            marblePosition += 1;
        }
        //갯수가 앞에 뒤에 어떤구슬인지 확인
        let A = marblePosition - i;
        let B = marbleVec[i];
        tempMarbleVec.push(A);
        tempMarbleVec.push(B);

        i = marblePosition - 1;
        i +=1;
    }
    //TODo
    if tempMarbleVec.len() >= N * N
    {
        // 구슬개수가 범위를 초과하면 개수를 맞춰 줌
        while tempMarbleVec.len() >= N * N
        {
            tempMarbleVec.pop();
        }
    }
    marbleVec.clear();
    for item in tempMarbleVec {
        marbleVec.push(item);
    }
    let returnValue = marbleVec.len();
    returnValue
}

fn UpdateMap(map: &mut Vec<Vec<usize>>, marbleVec: &mut Vec<usize>, N: usize)
{
    let dr: [i32; 4] = [-1, 0, 1, -0];
    let dc: [i32; 4] = [0, 1, 0, -1];

    let mut dir: usize = 0;
    let mut len: usize = 1;

    let mut curX: i32 = (N / 2) as i32;
    let mut curY: i32 = curX;

    let mut idx: usize = 0;
    let mut idxasd = marbleVec.len();

    for i in 0..N
    {
        for j in 0..N {
            map[i][j] = 0;
        }
    }

    while idx < marbleVec.len() {
        for notMean in 0..2 {
            for i in 0..len {
                let nx = curX + dr[dir];
                let ny = curY + dc[dir];
                map[ny as usize][nx as usize] = marbleVec[idx];
               // println!("{0} {1} {2} {3} : {4} {5}",marbleVec[idx], nx, ny, dir, dr[dir], dc[dir]);
                idx += 1;
                curX = nx;
                curY = ny;

                if idx == marbleVec.len()
                {
                    return;
                }
            }

            dir = (dir + 1) % 4;
        }
        len += 1;
    }
}
