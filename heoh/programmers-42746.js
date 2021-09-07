function solution(numbers) {
    numbers = numbers.map(String);
    numbers.sort((a, b) => Number(b+a) - Number(a+b));
    var answer = numbers.join('');
    return Number(answer) === 0 ? '0' : answer;
}
