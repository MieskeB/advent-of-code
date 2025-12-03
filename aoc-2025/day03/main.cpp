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
    for (std::string line; getline(infile, line); ) {
        lines.push_back(line);
    }

    int res_a = 0;
    for (const std::string& line : lines) {
        int largest = 0;
        for (int i = 0; i < line.length() - 1; i++) {
            for (int j = i + 1; j < line.length(); j++) {
                std::string word = std::string(1, line[i]) + line[j];
                int number = std::stoi(word);
                if (number > largest) {
                    largest = number;
                }
            }
        }
        res_a += largest;
    }
    std::cout << "res a: " << res_a << std::endl;

    long long res_b = 0;
    for (const std::string& line : lines) {
        std::string result;
        int curr_index = 0;
        int curr_number = 9;
        for (int i = 11; i >= 0; i--) {
            while (true) {
                bool done = false;
                for (int j = curr_index; j < line.length() - i; j++) {
                    if (line[j] - '0' == curr_number) {
                        done = true;
                        result += line[j];
                        curr_index = j + 1;
                        curr_number = 9;
                        break;
                    }
                }
                if (done) {
                    break;
                }
                curr_number -= 1;
                if (curr_number < 0) {
                    std::cout << "Curr number became lower than 0" << std::endl;
                    exit(1);
                }
            }
        }
        res_b += std::stoll(result);
    }
    std::cout << "res b: " << res_b << std::endl;
}
