#![allow(non_snake_case)]

use std::io::{self, Read};

fn main() {
    ///

    let dr: [i32; 4] = [-1, 0, 1, -0];
    let dc: [i32; 4] = [0, 1, 0, -1];

    let mut input = String::new();
    // let stdin = io::stdin();
    // stdin.lock().read_to_string(&mut input).unwrap();

    io::stdin().read_line(&mut input).unwrap();

    let line = input.trim().split(' ')
        .map(|x| x.parse().unwrap()).collect::<Vec<i32>>();
    //
    // let lines = &mut input.lines();

    let n: usize = line[0] as usize;
    let mut x: usize;
    let mut y: usize;
    let mut ans: usize = 0;

    let mut map: Vec<Vec<usize>> = vec![vec![0; n]; n];

    for i in 0..n
    {
        input.clear();
        io::stdin().read_line(&mut input).unwrap();
        let line = input.trim().split(' ')
            .map(|x| x.parse().unwrap()).collect::<Vec<usize>>();
        for j in 0..n
        {
            (map[i])[j] = line[j];
        }
    }

    let mut x = (n / 2);
    let mut y = (n / 2);

    let mut d: usize = 0;
    let mut temp: usize = 1;

    let mut isEnd: bool = false;

    loop {

        for i in 0..temp {
            x = ((x as i32) + dc[d]) as usize;
            y = ((y as i32) + dr[d]) as usize;
            ans += Tornado(x, y, d, n, &mut map);

            if (x == 0 && y == 0)
            {
                isEnd = true;
                break;
            }
        }

        if (isEnd)
        {
            break;
        }

        d += 1;
        if (d == 4)
        {
            d = 0;
        }

        for i in 0..temp {
            x = ((x as i32) + dc[d]) as usize;
            y = ((y as i32) + dr[d]) as usize;
            ans += Tornado(x, y, d, n, &mut map);

            if (x == 0 && y == 0)
            {
                isEnd = true;
                break;
            }
        }

        if (isEnd)
        {
            break;
        }

        d += 1;
        if (d == 4)
        {
            d = 0;
        }

        temp += 1;
    }
    println!("{}", ans);

}

fn Tornado(r: usize, c: usize, _direct: usize, n: usize, map: &mut Vec<Vec<usize>>) -> usize
{
    let dia: [[i32; 2]; 4] = [[1, -1], [1, 1], [-1, 1], [-1, -1]];
    let dr: [i32; 4] = [-1, 0, 1, -0];
    let dc: [i32; 4] = [0, 1, 0, -1];

    let mut direct : usize = _direct;
    let mut value = (map[r])[c];
    let mut nx: usize;
    let mut ny: usize;
    let mut per1: usize = ((value as f64) * 0.01) as usize;
    let mut per2: usize = ((value as f64) * 0.02) as usize;
    let mut per5: usize = ((value as f64) * 0.05) as usize;
    let mut per7: usize = ((value as f64) * 0.07) as usize;
    let mut per10: usize = ((value as f64) * 0.1) as usize;

    let mut ans = 0;

    for i in 0..8
    {
        if i == 4
        {
            continue;
        }
        if (i % 2 == 0)
        {
            if (i == 0)
            {
                // a
                nx = ((r as i32) + dc[direct]) as usize;
                ny = ((c as i32) + dr[direct]) as usize;

                let a: usize = value - (2 * (per1 + per2 + per7 + per10) + per5);
                if (IsInside(nx, ny, n))
                {
                    (map[nx])[ny] += a;
                } else {
                    ans += a;
                }

                // 5%
                nx = ((r as i32) + (dc[direct]) * 2) as usize;
                ny = ((c as i32) + (dr[direct]) * 2) as usize;

                if (IsInside(nx, ny, n))
                {
                    (map[nx])[ny] += per5;
                } else {
                    ans += per5;
                }
            } else {
                //7%
                nx = ((r as i32) + dc[direct]) as usize;
                ny = ((c as i32) + dr[direct]) as usize;

                if (IsInside(nx, ny, n))
                {
                    (map[nx])[ny] += per7;
                } else {
                    ans += per7
                };

                //2%
                nx = ((r as i32) + (dc[direct]) * 2) as usize;
                ny = ((c as i32) + (dr[direct]) * 2) as usize;

                if (IsInside(nx, ny, n))
                {
                    (map[nx])[ny] += per2;
                } else {
                    ans += per2
                };
            }
        }
        else {

            if (i == 1 || i == 7)
            {

                nx = ((r as i32) + dia[direct][0]) as usize;
                ny = ((c as i32) + dia[direct][1]) as usize;

                if (IsInside(nx, ny, n))
                {
                    (map[nx])[ny] += per10;
                }

                else
                {
                    ans += per10;
                }
            }
            else
            {
                // 1%
                nx = ((r as i32) + dia[direct][0]) as usize;
                ny = ((c as i32) + dia[direct][1]) as usize;

                if (IsInside(nx, ny, n))
                {
                    (map[nx])[ny] += per1;
                }

                else
                {
                    ans += per1;
                }
            }

            direct += 1;
            if (direct == 4)
            {
                direct = 0;
            }

        }
    }
    (map[r])[c] = 0;

    ans
}

fn IsInside(x: usize, y: usize, N: usize) -> bool
{
    x >= 0 && y >= 0 && x < N && y < N
}
