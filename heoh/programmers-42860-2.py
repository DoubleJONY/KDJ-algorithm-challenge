MAX_INT = 0x7fffffff

LEFT = 0
RIGHT = 1

def solution(name: str):
    left_right_count = find_optimal_left_right_count(name)
    up_down_count = find_optimal_up_down_count(name)

    return left_right_count + up_down_count

def find_optimal_left_right_count(name: str):
    name = 'A' + name[1:]
    
    if name_is_all_a(name):
        return 0

    if name_has_no_a(name):
        return len(name) - 1

    n = len(name)
    sequences = find_all_name_sequences(name)

    min_count = n - 1

    for seq in sequences:
        left, right = seq
        seq_length = get_sequence_length(n, seq)

        case_a = get_min_move(n, 0, left) + (seq_length - 1)
        case_b = get_min_move(n, 0, right) + (seq_length - 1)
        count = min(case_a, case_b)

        min_count = min(min_count, count)

    return min_count

def name_is_all_a(name: str):
    for c in name:
        if c != 'A':
            return False
    return True

def name_has_no_a(name: str):
    for c in name:
        if c == 'A':
            return False
    return True

def find_all_name_sequences(name: str):
    n = len(name)
    a_sequences = find_all_a_sequences(name)
    name_sequences = []
    for a_seq in a_sequences:
        a_left, a_right = a_seq

        name_left = (a_right + 1) % n
        name_right = (a_left - 1) % n
        name_seq = (name_left, name_right)

        name_sequences.append(name_seq)

    return name_sequences

def find_all_a_sequences(name: str):
    n = len(name)
    a_sequences = []
    left = 0
    while left < n:
        if name[left] == 'A':
            right = left + 1
            while right < n and name[right] == 'A':
                right += 1
            right -= 1
            a_seq = (left, right)

            a_sequences.append(a_seq)

            left = right + 1
        else:
            left += 1

    first_seq = a_sequences[0]
    last_seq = a_sequences[-1]
    if first_seq != last_seq:
        if first_seq[LEFT] == 0 and last_seq[RIGHT] == (n - 1):
            merged_seq = (last_seq[LEFT], first_seq[RIGHT])
            a_sequences.pop()
            a_sequences[0] = merged_seq

    return a_sequences

def get_sequence_length(n, seq):
    left, right = seq
    if left <= right:
        return right - left + 1
    else:
        return (right + n) - left + 1

def get_min_move(n, start, end):
    left_point = min(start, end)
    right_point = max(start, end)

    right_move = right_point - left_point
    left_move = (left_point + n) - right_point
    return min(right_move, left_move)

def find_optimal_up_down_count(name: str):
    count = 0
    for c in name:
        up_count = ord(c) - ord('A')
        down_count = 1 + ord('Z') - ord(c)
        count += min(up_count, down_count)
    return count
