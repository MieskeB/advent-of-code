#include <algorithm>
#include <filesystem>
#include <fstream>
#include <iostream>
#include <map>
#include <ranges>
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

    std::map<int, long long> current_beam_indices;
    int amount_of_splits = 0;
    for (const std::string &line: lines) {
        if (current_beam_indices.empty()) {
            for (int i = 0; i < line.length(); i++) {
                if (line[i] == 'S') {
                    current_beam_indices[i] = 1;
                    break;
                }
            }
        }
        else {
            std::map<int, long long> new_beam_indices;
            for (const int i: current_beam_indices | std::views::keys) {
                if (line[i] == '^') {
                    new_beam_indices[i - 1] = current_beam_indices[i] + new_beam_indices[i - 1];
                    new_beam_indices[i + 1] = current_beam_indices[i] + new_beam_indices[i + 1];
                    amount_of_splits++;
                } else {
                    new_beam_indices[i] = current_beam_indices[i] + new_beam_indices[i];
                }
            }
            current_beam_indices = new_beam_indices;
        }
    }

    std::cout << "res a: " << amount_of_splits << std::endl;

    long long timelines = 0;
    for (auto [i, count] : current_beam_indices) {
        timelines += count;
    }

    std::cout << "res b: " << timelines << std::endl;
}
