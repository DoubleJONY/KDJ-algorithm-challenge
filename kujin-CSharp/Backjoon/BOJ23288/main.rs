#![allow(non_snake_case)]

use std::io::{self, Read};
use std::num::FpCategory::Nan;
use std::collections::HashMap;
use std::collections::VecDeque;

//동쪽은 1, 서쪽은 2, 북쪽은 3, 남쪽은 4
enum Direction
{
    EAST,
    WEST,
    NORTH,
    SOUTH
}

struct Dice {
    moveDir: Direction,
    posX: usize,
    posY: usize,
    bottom: usize,
    north: usize,
    south: usize,
    east: usize,
    west: usize,
    top: usize,
}

impl Dice {
    fn new() -> Dice {
        Dice {
            moveDir: Direction::EAST,
            posX : 0,
            posY : 0,//[posY, posX]
            bottom: 6,
            top: 1,
            east: 3,
            west: 4,
            north: 2,
            south: 5,
        }
    }
    // {-1, 0},no
    // {0, 1},ea
    // {1, 0},so
    // {0, -1}we
    fn MoveDice(&mut self, mapRowSize : usize, mapColumnSize : usize) {
        let temp = self.top;
        let dir = &self.moveDir;
        match dir {
            Direction::EAST => {
                if self.posX < mapColumnSize -1
                {

                    self.posY += 0;
                    self.posX += 1;

                    self.top = self.west;
                    self.west = self.bottom;
                    self.bottom = self.east;
                    self.east = temp;
                }
                else
                {
                    self.posY += 0;
                    self.posX = ( self.posX as i32 - 1) as usize;

                    self.moveDir = Direction::WEST;

                    self.top = self.east;
                    self.east = self.bottom;
                    self.bottom = self.west;
                    self.west = temp;
                }




            }
            Direction::WEST => {
                if self.posX > 0
                {
                    self.posY += 0;
                    self.posX = ( self.posX as i32 - 1) as usize;

                    self.top = self.east;
                    self.east = self.bottom;
                    self.bottom = self.west;
                    self.west = temp;
                }
                else
                {
                    self.posY += 0;
                    self.posX += 1;

                    self.moveDir = Direction::EAST;

                    self.top = self.west;
                    self.west = self.bottom;
                    self.bottom = self.east;
                    self.east = temp;

                }



            }
            Direction::NORTH => {
                if self.posY > 0
                {
                    self.posY = ( self.posY as i32 - 1) as usize;
                    self.posX += 0;

                    self.top = self.south;
                    self.south = self.bottom;
                    self.bottom = self.north;
                    self.north = temp;
                }
                else
                {
                    self.posY += 1;
                    self.posX += 0;

                    self.moveDir = Direction::SOUTH;

                    self.top = self.north;
                    self.north = self.bottom;
                    self.bottom = self.south;
                    self.south = temp;
                }


            }
            Direction::SOUTH => {
                if self.posY < mapRowSize -1
                {
                    self.posY += 1;
                    self.posX += 0;

                    self.top = self.north;
                    self.north = self.bottom;
                    self.bottom = self.south;
                    self.south = temp;
                }
                else
                {
                    self.posY = ( self.posY as i32 - 1) as usize;
                    self.posX += 0;

                    self.moveDir = Direction::NORTH;

                    self.top = self.south;
                    self.south = self.bottom;
                    self.bottom = self.north;
                    self.north = temp;
                }



            }
            _ => {
                println!("Errorrrr1!");
            }
        }
    }

    fn Roll(&mut self, isClockwise: bool)
    {
        let temp = self.north;
        if isClockwise {
            // self.north = self.west;
            // self.west = self.south;
            // self.south = self.east;
            // self.east = temp;

            match self.moveDir {
                Direction::EAST => {
                    self.moveDir = Direction::SOUTH;
                }
                Direction::WEST => {
                    self.moveDir = Direction::NORTH;
                }
                Direction::NORTH => {
                    self.moveDir = Direction::EAST;
                }
                Direction::SOUTH => {
                    self.moveDir = Direction::WEST;
                }
            }
        }
        else {
            // self.north = self.east;
            // self.east = self.south;
            // self.south = self.west;
            // self.west = temp;

            match self.moveDir {
                Direction::EAST => {
                    self.moveDir = Direction::NORTH;
                }
                Direction::WEST => {
                    self.moveDir = Direction::SOUTH;
                }
                Direction::NORTH => {
                    self.moveDir = Direction::WEST;
                }
                Direction::SOUTH => {
                    self.moveDir = Direction::EAST;
                }
            }
        }
    }
}

fn main() {


    let mut input = String::new();

    io::stdin().read_line(&mut input).unwrap();

    let line = input.trim().split(' ')
        .map(|x| x.parse().unwrap()).collect::<Vec<usize>>();

    let N: usize = line[0] as usize;//Map row
    let M: usize = line[1] as usize;//map Column
    let K: usize = line[2] as usize;// move count


    let mut score: usize = 0;

    let mut map: Vec<Vec<usize>> = vec![vec![0; M]; N]; //N, M
    let mut visit: Vec<Vec<bool>> = vec![vec![false; M]; N]; //N, M

    let mut dice : Dice = Dice::new();


    for i in 0..N
    {
        input.clear();
        io::stdin().read_line(&mut input).unwrap();
        let line = input.trim().split(' ')
            .map(|x| x.parse().unwrap()).collect::<Vec<usize>>();

        for j in 0..M {
            map[i][j] = line[j];
            visit[i][j] = false;
        }
    }



    for i in 0..K{
        dice.MoveDice(N,M);
        score += GetScore(&mut dice, &mut map, &mut visit, N,M);
        SetDirection(&mut dice, &mut map);
        // println!("Score");
        // println!("{}", score);
    }

    println!("{}", score);
}

fn  GetScore(dice : &mut Dice, map: &mut Vec<Vec<usize>>, visit: &mut Vec<Vec<bool>>, mapRowSize : usize, mapColumnSize : usize) -> usize
{
    let mut ret  = 0;
    let mut q: Vec<(usize, usize)> = Vec::new();
    let mut tempVisitPos:  Vec<(usize, usize)> = Vec::new();
    let dr: [i32; 4] = [-1, 0, 1, -0];
    let dc: [i32; 4] = [0, 1, 0, -1];

    q.push((dice.posY,dice.posX));
    tempVisitPos.push((dice.posY,dice.posX));
    visit[dice.posY][dice.posX] = false;

    // println!("{0} {1}",dice.posY,dice.posX);
    // println!("{}", map[dice.posY][dice.posX]);
    // match dice.moveDir {
    //     Direction::EAST => {
    //         println!("EAST");
    //     }
    //     Direction::WEST => {
    //         println!("WEST");
    //     }
    //     Direction::NORTH => {
    //         println!("NORTH");
    //     }
    //     Direction::SOUTH => {
    //         println!("SOUTH");
    //     }
    //     _=>
    //         {
    //             println!("ERROR");
    //         }
    // }

    while q.len() != 0
    {
        let item = q[0];


        q.remove(0);
        let cy = item.0;
        let cx = item.1;

        for k in 0..4 {
            let ny = ((cy as i32) + dr[k]) as usize;
            let nx = ((cx as i32) + dc[k]) as usize;

            if ny < 0 || nx < 0 || ny >= mapRowSize || nx >= mapColumnSize
            {
                continue;
            }
            if visit[ny][nx] == false && map[ny][nx] == map[dice.posY][dice.posX]
            {
                q.push((ny, nx));
                tempVisitPos.push((ny, nx));

                visit[ny][nx] = true;
                ret+=1;
            }
        }
    }

    for pos in tempVisitPos {
        visit[pos.0][pos.1] = false;
    }

    if ret == 0
    {
        ret = 1;
    }

    ret * map[dice.posY][dice.posX]
}

fn SetDirection(dice : &mut Dice, map: &mut Vec<Vec<usize>>)
{
    // println!("DICE");
    // println!("{}", dice.north);
    // println!("{0} {1} {2}", dice.west, dice.top, dice.east);
    // println!("{}", dice.south);
    // println!("{}", dice.bottom);
    // println!("map : {}", map[dice.posY][dice.posX]);

    if dice.bottom > map[dice.posY][dice.posX]
    {
        dice.Roll(true);
    }
    else if dice.bottom < map[dice.posY][dice.posX]
    {
        dice.Roll(false);
    }

}