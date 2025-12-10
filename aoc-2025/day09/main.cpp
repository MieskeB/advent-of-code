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
    for (std::string line; getline(infile, line); ) {
        lines.push_back(line);
    }

    long long largest_area = 0;
    for (int i = 0; i < lines.size() - 1; i++) {
        for (int j = i + 1; j < lines.size(); j++) {
            std::vector<std::string> point_a_v = split(lines[i], ",");
            std::vector<std::string> point_b_v = split(lines[j], ",");
            int point_a_x = std::stoi(point_a_v[0]);
            int point_a_y = std::stoi(point_a_v[1]);
            int point_b_x = std::stoi(point_b_v[0]);
            int point_b_y = std::stoi(point_b_v[1]);

            long long dx = std::abs(point_a_x - point_b_x) + 1;
            long long dy = std::abs(point_a_y - point_b_y) + 1;

            long long area = dx * dy;
            if (largest_area < area) {
                largest_area = area;
            }
        }
    }

    std::cout << "res a: " << largest_area << std::endl;

    // TODO I have no idea how to go about solving b
}
