import re

import utils

data = utils.readFile(1, "input")


# Returns the number if it is a word, or . if it is not a word
def isNumberWord(line, location):
    number_words = {
        "one": '1',
        "two": '2',
        "three": '3',
        "four": '4',
        "five": '5',
        "six": '6',
        "seven": '7',
        "eight": '8',
        "nine": '9',
    }

    for word, value in number_words.items():
        if line[location:].startswith(word):
            return value
    return None


res = 0
for d in data:
    first_digit_c = '.'
    last_digit_c = '.'
    for letter in list(d):
        if re.match("\\d", letter):
            if first_digit_c == '.':
                first_digit_c = letter
            last_digit_c = letter
    res += int(first_digit_c + last_digit_c)

print(f"Only numbers result: {res}")

res = 0
for d in data:
    first_digit_c = '.'
    last_digit_c = '.'
    for i, letter in enumerate(list(d)):
        if re.match("\\d", letter):
            if first_digit_c == '.':
                first_digit_c = letter
            last_digit_c = letter
        else:
            digit_c = isNumberWord(d, i)
            if digit_c is not None:
                if first_digit_c == '.':
                    first_digit_c = digit_c
                last_digit_c = digit_c
    res += int(first_digit_c + last_digit_c)

print(f"Numbers and letters result: {res}")
