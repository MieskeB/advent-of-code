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

std::vector<std::string> split(const std::string &s, const std::string &delimiter) {
    size_t pos_start = 0, pos_end, delim_len = delimiter.length();
    std::vector<std::string> res;

    while ((pos_end = s.find(delimiter, pos_start)) != std::string::npos) {
        std::string token = s.substr(pos_start, pos_end - pos_start);
        pos_start = pos_end + delim_len;
        res.push_back(token);
    }

    res.push_back(s.substr(pos_start));
    return res;
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

    std::string line = lines[0];
    std::vector<std::string> words = split(line, ",");

    long long count_a = 0;
    long long count_b = 0;
    for (std::string w: words) {
        std::vector<std::string> splitted = split(w, "-");
        long long w0 = std::stoll(splitted[0]);
        long long w1 = std::stoll(splitted[1]);
        for (long long i = w0; i <= w1; i++) {
            std::string word = std::to_string(i);
            if (word.length() % 2 == 1) {
                continue;
            }
            if (int half = word.length() / 2; word.substr(0, half) == word.substr(half, half)) {
                count_a += i;
            }
        }
        for (long long i = w0; i <= w1; i++) {
            std::string word = std::to_string(i);
            int half = word.length() / 2;
            for (int j = 1; j <= half; j++) {
                if (word.length() % j != 0) {
                    continue;
                }
                std::string to_check = word.substr(0, j);
                bool all_same = true;
                for (int k = j; k < word.length(); k += j) {
                    if (word.substr(k, j) != to_check) {
                        all_same = false;
                        break;
                    }
                }
                if (all_same) {
                    count_b += i;
                    break;
                }
            }
        }
    }
    std::cout << count_a << std::endl;
    std::cout << count_b << std::endl;
}
