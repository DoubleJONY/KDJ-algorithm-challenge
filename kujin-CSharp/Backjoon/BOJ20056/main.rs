#![allow(non_snake_case)]

use std::io::{self, Read};

#[derive(Clone, Copy)]
#[derive(Eq, PartialEq)]
struct FireBall
{
    r : i32,
    c : i32,
    m : i32,
    s : i32,
    d : i32,

}

impl FireBall {
    fn NewFireBall(_r: i32, _c: i32, _m: i32,_s: i32, _d: i32) -> FireBall {
        FireBall{r: _r, c : _c,  m: _m, s:_s, d:_d}
    }
}


fn main() {
    // use std::collections::VecDeque;
// use std::io::{stdin, self, Read, Write};
    // let stdout = io::stdout();
    // let mut output = io::BufWriter::new(stdout.lock());

    let dr: [i32; 8] = [-1,-1,0,1,1,1,0,-1];
    let dc: [i32; 8] = [0,1,1,1,0,-1,-1,-1];

    let mut input = String::new();
    // let stdin = io::stdin();
    // stdin.lock().read_to_string(&mut input).unwrap();

    io::stdin().read_line(&mut input).unwrap();

    let line = input.trim().split(' ')
        .map(|x| x.parse().unwrap()).collect::<Vec<i32>>();
    //
    // let lines = &mut input.lines();

    let n: i32 = line[0];
    let m: i32 = line[1];
    let k: i32 = line[2];
    let n_size: usize = n as usize;

    let mut map : Vec<Vec<Vec<FireBall>>> = vec![vec![Vec::new(); n_size]; n_size];
    let mut fireBallList = Vec::new();



    for i in 0..m
    {
        input.clear();
        io::stdin().read_line(&mut input).unwrap();
        let line = input.trim().split(' ')
            .map(|x| x.parse().unwrap()).collect::<Vec<i32>>();

        fireBallList.push(FireBall::NewFireBall(line[0] - 1,line[1] - 1, line[2], line[3], line[4]));
    }

    // 2개 이상이면
    // 1. 방향이 모두 짝수(홀수)인지 아닌지 확인
    let mut even : bool = false;
    let mut odd : bool = false;

    let mut massSum : i32 = 0;   // 질량 합
    let mut speedSum : i32 = 0;   // 속도 합

    for i in 0..k
    {
        for fBall in &mut fireBallList {
            let mut nr : i32 = fBall.r + dr[fBall.d as usize] *(fBall.s % n);
            let mut nc : i32 = fBall.c + dc[fBall.d as usize] *(fBall.s % n);

            if nr >= n
            {
                nr -= n;
            }
            else if nr < 0
            {
                nr += n;
            }
            if nc >= n
            {
                nc -= n;
            }
            else if nc < 0
            {
                nc += n;
            }

            fBall.r = nr;
            fBall.c = nc;

            (map[fBall.r as usize])[fBall.c as usize].push(*fBall);
        }

        for j in 0..n {
            for k in 0..n {
                even = false;
                odd = false;
                massSum = 0;
                speedSum = 0;
                // 2개 미만이면 ㅁㅜ시
                if (map[j as usize])[k as usize].len() < 2
                {
                    (map[j as usize])[k as usize].clear();
                    continue;
                }


                match (((map[j as usize])[k as usize])[0].d % 2) == 0 {
                    true => { even = true; }
                    false => { even = false; }
                }
                match (((map[j as usize])[k as usize])[0].d % 2) == 1 {
                    true => { odd = true; }
                    false => { odd = false; }
                }
                massSum = 0;
                speedSum = 0;

                for fBall in &mut ((map[j as usize])[k as usize])
                {
                    massSum += fBall.m;
                    speedSum += fBall.s;
                    match even && (fBall.d % 2 == 0) {
                        true => { even = true; }
                        false => { even = false; }
                    }
                    match odd && (fBall.d % 2 == 1) {
                        true => { odd = true; }
                        false => { odd = false; }
                    }

                    let vetCount : usize  = fireBallList.len();

                    for vecIndex in 0..vetCount {


                        match fireBallList[vecIndex] == *fBall {
                            true => {
                                fireBallList.remove(vecIndex);
                                break;
                            }
                            false => {}
                        }
                    }
                }

                let nMass: i32 = (massSum / 5) as i32;
                let nSpeed: i32 = (speedSum / (((map[j as usize])[k as usize]).len()) as i32) as i32;
                ((map[j as usize])[k as usize]).clear();

                if nMass == 0
                {
                    continue;
                }

                // 방향이 모두 홀수(짝수) 이면
                if even || odd
                {
                    for l in 0..8 {
                        match l % 2 == 0
                        {
                            true => {
                                fireBallList.push(FireBall::NewFireBall(j, k, nMass, nSpeed, l))
                            }
                            false => {}
                        }
                    }
                } else {
                    // 방향이 1, 3, 5, 7
                    for l in 0..8 {
                        match l % 2 == 1
                        {
                            true => {
                                fireBallList.push(FireBall::NewFireBall(j, k, nMass, nSpeed, l))
                            }
                            false => {}
                        }
                    }
                }
            }
        }

    }


    let mut ans : i32 = 0;
    for fBall in &mut fireBallList
    {
        ans += fBall.m;
    }

    println!("{}", ans);
}
