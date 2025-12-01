#include <filesystem>
#include <fstream>
#include <iostream>
#include <vector>

std::filesystem::path get_input_path() {
    std::filesystem::path source_path = std::filesystem::path(__FILE__);
    std::filesystem::path dir = source_path.parent_path();
    std::filesystem::path input_path = dir / "input.txt";
    return input_path;
}

int main() {
    std::filesystem::path input_path = get_input_path();
    std::ifstream infile(input_path);

    if (!infile.is_open()) {
        std::cerr << "Error: could not open " << input_path << "\n";
        return 1;
    }

    std::vector<std::string> lines;
    for (std::string line; getline(infile, line);) {
        lines.push_back(line);
    }

    int dial = 50;
    int count = 0;
    for (const std::string &line: lines) {
        const char dir = line[0];
        int amount = 0;
        if (line.length() == 2) {
            amount = line[1] - '0';
        } else if (line.length() == 3) {
            amount = (line[1] - '0') * 10 + (line[2] - '0');
        } else {
            amount = (line[1] - '0') * 100 + (line[2] - '0') * 10 + (line[3] - '0');
        }
        if (dir == 'L') {
            dial = (dial - amount) % 100;
        } else {
            dial = (dial + amount) % 100;
        }
        if (dial < 0) {
            dial = 100 + dial;
        }
        if (dial == 0) {
            count++;
        }
    }

    std::cout << count << std::endl;
}
