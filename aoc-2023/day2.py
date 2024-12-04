import re

import utils

data = utils.readFile(2, "input")

res = 0
power_res = 0
for d in data:
    game_number = int(d.split(": ")[0].split(" ")[1])
    games_log = d.split(": ")[1].strip().split("; ")
    games_log = [game_log.strip() for game_log in games_log]

    isValid = True
    highest_red = 0
    highest_green = 0
    highest_blue = 0
    for game_log in games_log:
        for log_item in game_log.split(", "):
            number = int(re.findall("\\d+", log_item)[0])
            if "red" in log_item:
                if number > 12:
                    isValid = False
                if highest_red < number:
                    highest_red = number
            elif "green" in log_item:
                if number > 13:
                    isValid = False
                if highest_green < number:
                    highest_green = number
            elif "blue" in log_item:
                if number > 14:
                    isValid = False
                if highest_blue < number:
                    highest_blue = number

    if isValid:
        res += game_number
    epic = highest_red * highest_green * highest_blue
    print(f"{game_number}: {epic}")
    power_res += epic

print(f"valid game IDs: {res}")
print(f"Power of games: {power_res}")
