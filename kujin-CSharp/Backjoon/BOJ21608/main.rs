#![allow(non_snake_case)]

use std::io::{self, Read};
use std::num::FpCategory::Nan;

fn main() {
    ///


    // let dr: [i32; 4] = [-1, 0, 1, -0];
    // let dc: [i32; 4] = [0, 1, 0, -1];

    let mut input = String::new();

    io::stdin().read_line(&mut input).unwrap();

    let line = input.trim().split(' ')
        .map(|x| x.parse().unwrap()).collect::<Vec<i32>>();

    let N: usize = line[0] as usize;
    let studentNum = N * N;

    let mut seat: Vec<Vec<usize>> =vec![vec![0; N + 1]; N + 1];
    let mut studentData: Vec<Vec<usize>> = vec![vec![0; 4]; studentNum + 1];
    let mut studentOrder : Vec<usize> = Vec::new();

    let mut currentStudent :usize = 0;

    for i in 0..studentNum
    {
        input.clear();
        io::stdin().read_line(&mut input).unwrap();
        let line = input.trim().split(' ')
            .map(|x| x.parse().unwrap()).collect::<Vec<usize>>();

        currentStudent = line[0];
        studentOrder.push(currentStudent);
        for j in 0..4 {
            studentData[currentStudent][j] = line[j + 1];
        }

    }

    for i in studentOrder
    {
        SitDownOneByOne(&mut studentData, &mut seat, i, N);
    }

    println!("{}", CalcAns(&mut studentData, &mut seat, N))

}

fn CalcAns(studentData: &mut Vec<Vec<usize>>, seat: &mut Vec<Vec<usize>>,  N : usize)-> usize
{
    let mut ans = 0;
    let mut myFriends = 0;

    let dr: [i32; 4] = [-1, 0, 1, -0];
    let dc: [i32; 4] = [0, 1, 0, -1];

    for i in 1..=N
    {
        for j in 1..=N
        {
            myFriends = 0;
            for k in 0..4 {
                let nx = ((i as i32) + dc[k]) as usize;
                let ny = ((j as i32) + dr[k]) as usize;

                if nx <= 0 || nx > N || ny <= 0 || ny > N
                {
                    continue;
                }
                if IsInLikeStudent(studentData, seat, seat[i][j], nx, ny)
                {
                    myFriends += 1;
                }
            }
            if myFriends == 1
            {
                ans += 1;
            }
            else if myFriends == 2
            {
                ans += 10;
            }
            else if myFriends == 3
            {
                ans += 100;
            }
            else if myFriends == 4
            {
                ans += 1000;
            }
        }
    }
    ans
}


fn IsInLikeStudent(studentData: &mut Vec<Vec<usize>>, seat: &mut Vec<Vec<usize>>,curStudent : usize , x : usize, y : usize ) -> bool
{
    for i in 0..4 {
        if studentData[curStudent][i] == seat[x][y]
        {
            return true;
        }
    }
    false
}

fn SitDownOneByOne(studentData: &mut Vec<Vec<usize>>, seat: &mut Vec<Vec<usize>>, curStudent : usize, N : usize)
{
    let mut resX : i32  = -1;
    let mut resY : i32  = -1;

    let mut maxLikeStudents : i32 = -1;
    let mut maxEmpties : i32  = -1;

    let mut likeStudents  : i32  = 0;
    let mut empty  : i32  = 0;

    let dr: [i32; 4] = [-1, 0, 1, -0];
    let dc: [i32; 4] = [0, 1, 0, -1];

    for i in 1..=N
    {
        for j in 1..=N {
            likeStudents = 0;
            empty = 0;
            if seat[i][j] > 0
            {
                continue;
            }
            for k in 0..4
            {
                let nx = ((i as i32) + dc[k]) as usize;
                let ny = ((j as i32) + dr[k]) as usize;
                if nx <= 0 || nx > N || ny <= 0 || ny > N
                {
                    continue;
                }
                if seat[nx][ny] == 0
                {
                    empty +=1;
                }
                else if IsInLikeStudent(studentData, seat,curStudent, nx, ny)
                {
                    likeStudents +=1;
                }
            }
            if likeStudents > maxLikeStudents
            {
                maxLikeStudents = likeStudents;
                maxEmpties = empty;
                resX = i as i32;
                resY = j as i32;
            }
            else if likeStudents == maxLikeStudents
            {
                if empty > maxEmpties
                {
                    maxEmpties = empty;
                    resX = i as i32;
                    resY = j as i32;

                }
                else if empty == maxEmpties
                {
                    if resX == i  as i32 && resY > j as i32
                    {
                        resY = j as i32;
                    }
                    else if resX > i as i32
                    {
                        resX = i as i32;
                        resY = j as i32;
                    }
                }
            }
        }
    }
    seat[resX as usize][resY as usize] = curStudent;
}
