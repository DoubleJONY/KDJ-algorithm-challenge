from collections import defaultdict


# defaultdict 재귀로 trie 쉽게 구현 및 사용 가능
# def create_trie():
#     return defaultdict(create_trie)
create_trie = lambda: defaultdict(create_trie)


def check_and_add_phone_number(trie, phone_number):
    if not phone_number:
        trie['is_terminal'] = True
    else:
        trie['is_branch'] = True

    if trie['is_terminal'] and trie['is_branch']:
        return False
    if trie['is_terminal']:
        return True

    child = trie[phone_number[0]]
    return check_and_add_phone_number(child, phone_number[1:])


def solution(phone_book):
    trie = create_trie()
    for s in phone_book:
        if not check_and_add_phone_number(trie, s):
            return False
    return True
