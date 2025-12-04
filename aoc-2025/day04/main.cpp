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

std::vector<std::tuple<int, int>> directions_mult {
    {0, -1},
    {1, -1},
    {1, 0},
    {1, 1},
    {0, 1},
    {-1, 1},
    {-1, 0},
    {-1, -1}
};

std::vector<std::tuple<int, int>> get_positions(std::vector<std::string> lines) {
    std::vector<std::tuple<int, int>> positions;
    int x_bound = lines[0].size();
    int y_bound = lines.size();
    for (int y = 0; y < lines.size(); y++) {
        for (int x = 0; x < lines[0].size(); x++) {
            if (lines[y][x] != '@') {
                continue;
            }

            int count = 0;
            for (auto [dx, dy] : directions_mult) {
                if (x + dx < 0 || y + dy < 0 || x + dx >= x_bound || y + dy >= y_bound) {
                    continue;
                }
                if (lines[y + dy][x + dx] == '@') {
                    count++;
                }
            }

            if (count < 4) {
                positions.emplace_back(x, y);
            }
        }
    }
    return positions;
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

    std::vector<std::tuple<int, int>> positions = get_positions(lines);
    std::cout << "res a: " << positions.size() << std::endl;

    int count = 0;
    while (positions.size() != 0) {
        count += positions.size();
        for (auto [x, y] : positions) {
            lines[y][x] = 'x';
        }
        positions = get_positions(lines);
    }

    std::cout << "res b: " << count << std::endl;
}
