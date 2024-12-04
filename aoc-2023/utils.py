def readFile(day, file_name):
    with open(f"resources/day{day}/{file_name}.txt", 'r') as file:
        lines = file.readlines()
    lines = [line[:-1] if i < len(lines) - 1 else line for i, line in enumerate(lines)]
    return lines
