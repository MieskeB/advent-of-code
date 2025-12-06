#include <filesystem>
#include <fstream>
#include <iostream>
#include <vector>
#include <algorithm>
#include <cctype>

std::filesystem::path get_input_path() {
    std::filesystem::path source_path = std::filesystem::path(__FILE__);
    std::filesystem::path dir = source_path.parent_path();
    std::filesystem::path input_path = dir / "input.txt";
    return input_path;
}

int find_length_longest_word(const std::vector<std::string> &words) {
    int longest_word = 0;
    for (std::string word : words) {
        if (word.length() > longest_word) {
            longest_word = word.length();
        }
    }
    return longest_word;
}

int main() {
    std::filesystem::path input_path = get_input_path();
    std::ifstream infile(input_path);

    if (!infile.is_open()) {
        std::cerr << "Error: could not open " << input_path << "\n";
        return 1;
    }

    std::vector<std::string> lines;
    for (std::string line; getline(infile, line); ) {
        lines.push_back(line);
    }


    long long res_a = 0;
    for (int i = 0; i < lines.back().size(); i++) {
        if (lines.back()[i] == ' ') {
            continue;
        }
        long long temp_res = 1;
        for (int j = 0; j < lines.size() - 1; j++) {
            std::string number_word = "";
            bool has_started = false;
            for (int k = i; k < i + 20; k++) {
                auto digit = lines[j][k];
                if (!has_started && digit == ' ') {
                    continue;
                } else if (has_started && digit == ' ') {
                    break;
                }
                has_started = true;
                number_word += digit;
            }
            if (lines.back()[i] == '*') {
                temp_res *= std::stoll(number_word);
            } else if (lines.back()[i] == '+') {
                res_a += std::stoll(number_word);
            }
        }
        if (lines.back()[i] == '*') {
            res_a += temp_res;
        }
    }

    std::cout << "Res a: " << res_a << std::endl;

    long long res_b;
    int line_length = find_length_longest_word(lines);
    std::vector<long long> numbers;
    for (int i = line_length - 1; i >= 0; i--) {
        std::string number_word = "";
        for (int j = 0; j < lines.size() - 1; j++) {
            if (lines[j].length() > i) {
                number_word += lines[j][i];
            }
        }
        numbers.push_back(std::stoll(number_word));
        if (lines.back().length() > i) {
            if (lines.back()[i] == '*') {
                long long temp_res = 1;
                for (long long number : numbers) {
                    temp_res *= number;
                }
                res_b += temp_res;
                numbers.clear();
                i--;
            } else if (lines.back()[i] == '+') {
                for (long long number : numbers) {
                    res_b += number;
                }
                numbers.clear();
                i--;
            }
        }
    }

    std::cout << "Res b: " << res_b << std::endl;
}
