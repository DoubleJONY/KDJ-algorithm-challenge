#![allow(non_snake_case)]

use std::io::{self, Read};
use std::num::FpCategory::Nan;
use std::collections::HashMap;
use std::collections::VecDeque;
use std::future::pending;
use std::slice::Windows;

#[derive(Copy, Clone)]
struct Fish {
    dir: usize,
    rowPos : usize,
    columnPos : usize
}

impl Fish {
    fn new(dirNum: usize, _rowPos: usize, _columnPos: usize) -> Fish {
        Fish
        {
            dir : dirNum,
            rowPos : _rowPos,
            columnPos : _columnPos
        }
    }

}





fn main() {


    let mut input = String::new();

    io::stdin().read_line(&mut input).unwrap();

    let line = input.trim().split(' ')
        .map(|x| x.parse().unwrap()).collect::<Vec<usize>>();

    let N: usize = line[0] as usize;
    let K: usize = line[1] as usize;

    let mut fishBowls : Vec<Vec<usize>> = vec![Vec::new(); N];

    input.clear();
    io::stdin().read_line(&mut input).unwrap();
    let line = input.trim().split(' ')
        .map(|x| x.parse().unwrap()).collect::<Vec<usize>>();

    for i in 0..N
    {


        fishBowls[i].push(line[i]);
    }


    let mut cnt = 0;

    while true
    {
        // 최대값과 최소값을 찾는다
        let mut min = 10001;
        let mut max = 0;
        let resultMinMax = FindMinMax(&mut fishBowls);
        min  = resultMinMax.0;
        max = resultMinMax.1;
        if (max-min <= K) {
            break;
        }



        // 최소값인 곳에 1을 더함
        MinAddOne(&mut fishBowls, min);
        // println!("step 1");
        // for i in 0..N {
        //     for j in 0..fishBowls[i].len(){
        //         print!("{0} ", fishBowls[i][j]);
        //     }
        //     println!("");
        // }

        // 맨 왼쪽에 있는 어항을 그 다음 어항 위에 올린다.
        FirstOnSecond(&mut fishBowls);

        // println!("step 2");
        // for i in 0..N {
        //     for j in 0..fishBowls[i].len(){
        //         print!("{0} ", fishBowls[i][j]);
        //     }
        //     println!("");
        // }
        // 되는 만큼 굴리기
        while (true) {
            if (!Roll(N,&mut fishBowls)) { // 굴릴 수 없으면 break;
                break;
            }
        }
        // println!("step 3");
        // for i in 0..N {
        //     for j in 0..fishBowls[i].len(){
        //         print!("{0} ", fishBowls[i][j]);
        //     }
        //     println!("");
        // }
        // 물고기 조절 작업
        AdjustFishNum(&mut fishBowls);
        // println!("step 4");
        // for i in 0..N {
        //     for j in 0..fishBowls[i].len(){
        //         print!("{0} ", fishBowls[i][j]);
        //     }
        //     println!("");
        // }
        // 펼치기
        Open(N, &mut fishBowls);
        // println!("step 5");
        // for i in 0..N {
        //     for j in 0..fishBowls[i].len(){
        //         print!("{0} ", fishBowls[i][j]);
        //     }
        //     println!("");
        // }
        // 공중부양 2번
        GongjungFirst(N, &mut fishBowls);
        // println!("step 6");
        // for i in 0..N {
        //     for j in 0..fishBowls[i].len(){
        //         print!("{0} ", fishBowls[i][j]);
        //     }
        //     println!("");
        // }
        GongjungSecond(N, &mut fishBowls);
        //
        // println!("step 6-2");
        // for i in 0..N {
        //     for j in 0..fishBowls[i].len(){
        //         print!("{0} ", fishBowls[i][j]);
        //     }
        //     println!("");
        // }
        // 물고기 조절 작업
        AdjustFishNum(&mut fishBowls);
        //
        // println!("step 7");
        // for i in 0..N {
        //     for j in 0..fishBowls[i].len(){
        //         print!("{0} ", fishBowls[i][j]);
        //     }
        //     println!("");
        // }
        // 펼치기
        Open(N, &mut fishBowls);

        cnt += 1;
    }



        println!("{}", cnt);


}

fn FindMinMax(fishBowls : &mut Vec<Vec<usize>>) -> (usize, usize)
{
    // 최대값과 최소값을 찾는다
    let mut min = 10001;
    let mut max = 0;

    for i in 0..fishBowls.len() {
        if min > fishBowls[i][0]
        {
            min = fishBowls[i][0];
        }
        if max < fishBowls[i][0]
        {
            max = fishBowls[i][0]
        }

    }

    (min, max)
}

fn MinAddOne(fishBowls : &mut Vec<Vec<usize>>, min : usize)
{

    for i in 0..fishBowls.len(){
        if fishBowls[i][0] == min {
            fishBowls[i][0] += 1;
        }
    }
}

fn CopyFish(map: &mut Vec<Vec<Vec<Fish>>>, fishList:  &mut Vec<Fish>)
{
    fishList.clear();
    for r in 0..4 {
        for c in 0..4 {
            for fish in &map[r][c] {
                fishList.push(*fish);
            }
        }
    }

}

fn FirstOnSecond(fishBowls : &mut Vec<Vec<usize>>)
{
    let aa = fishBowls[0][0];
    fishBowls[1].push(aa);
    fishBowls[0] = Vec::new();
}

fn Roll(N : usize ,fishBowls : &mut Vec<Vec<usize>>) -> bool {
    let mut startIdx: usize = 0; // 굴리기 시작할 인덱스
    let mut endIdx: usize = 0; // 굴리기 끝나는 인덱스
    let mut size: usize = 0;

    // 굴리기 시작할 인덱스 찾기
    while true {
        if fishBowls[startIdx].len() != 0 {
            size = fishBowls[startIdx].len();
            break;
        }
        startIdx += 1;
    }

    // 굴리기 끝낼 인덱스 찾기
    endIdx = startIdx;
    while true {
        if endIdx >= N || fishBowls[endIdx].len() != size {
            break;
        }
        endIdx += 1;
    }

    endIdx -= 1;
    // 못굴리는 경우
    if endIdx + size >= N {
        return false;
    }

    // 굴릴 수 있으면?
    for i in (startIdx..=endIdx).rev() {
        for j in 0..size {
            let aa =fishBowls[i][j];
            fishBowls[endIdx + 1 + j].push(aa);
        }
        fishBowls[i] = Vec::new();
    }

    true
}
fn AdjustFishNum(fishBowls : &mut Vec<Vec<usize>>)
{
    let dr: [i32; 4] = [-1, 0, 1, 0];
    let dc: [i32; 4] = [0, -1, 0, 1];

    let mut calc : Vec<Vec<i32>> = vec![Vec::new(); fishBowls.len()];
    for i in 0..calc.len(){
        calc[i] = Vec::new();
    }

     for i in 0..fishBowls.len(){
         for j in 0..fishBowls[i].len(){
             let mut sum : i32 = 0;
             for d in 0..4 {
                 let nx = (i as i32 + dr[d])as usize;//nx
                 let ny = (j as i32 + dc[d]) as usize;

                 if nx < 0 || nx >= fishBowls.len() || ny < 0 || ny >= fishBowls[nx].len() {
                     continue;
                 }

                 if i32::abs((fishBowls[i][j] as i32) - (fishBowls[nx][ny] as i32)) < 5 {
                     continue;
                 }
                // 자기 칸 위주로 생각하기
                // println!("{0}", ((fishBowls[nx][ny] as i32 - fishBowls[i][j] as i32) / 5));
                 sum += ((fishBowls[nx][ny] as i32 - fishBowls[i][j] as i32) / 5);
             }
             calc[i].push(sum);
         }
     }

    for i in 0..fishBowls.len(){
        for j in 0..fishBowls[i].len(){
            fishBowls[i][j] = (fishBowls[i][j] as i32 + calc[i][j]) as usize;
        }
    }

}

fn Open(N : usize,fishBowls : &mut Vec<Vec<usize>>){

    let mut openFishBowls : Vec<Vec<usize>> = vec![Vec::new(); fishBowls.len()];

    let mut idx: usize = 0;

    for i in 0..N{
        if fishBowls[i].len() == 0 {
            continue;
        }
        for j in 0..fishBowls[i].len(){
            openFishBowls[idx] =  Vec::new();
            openFishBowls[idx].push(fishBowls[i][j]);
            idx +=1;
        }
    }


    for i in 0..openFishBowls.len(){
        fishBowls[i].clear();
        for j in 0..openFishBowls[i].len(){
            fishBowls[i].push(openFishBowls[i][j]);
        }
    }

}

fn GongjungFirst(N : usize,fishBowls : &mut Vec<Vec<usize>>) {
    for i in 0..N/2 {
        let aa = fishBowls[i][0];
        fishBowls[N - i - 1].push(aa);
        fishBowls[i] = Vec::new();
    }
}
fn GongjungSecond(N : usize,fishBowls : &mut Vec<Vec<usize>>) {
    let mut refIndex = N / 2 + N / 4;

    for j in (0..=1).rev(){

        for i in 0..(N / 4){
            let aa = fishBowls[refIndex - 1 - i][j];
            fishBowls[refIndex + i].push(aa);
        }
    }

    for i in N/2..refIndex {
        fishBowls[i] = Vec::new();
    }
}
