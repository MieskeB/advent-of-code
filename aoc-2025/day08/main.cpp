#include <algorithm>
#include <filesystem>
#include <fstream>
#include <iostream>
#include <vector>
#include <cmath>
#include <map>

std::filesystem::path get_input_path() {
    std::filesystem::path source_path = std::filesystem::path(__FILE__);
    std::filesystem::path dir = source_path.parent_path();
    std::filesystem::path input_path = dir / "test_input.txt";
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

double distance3D(const std::tuple<int, int, int> &a,
                  const std::tuple<int, int, int> &b) {
    double dx = static_cast<double>(std::get<0>(a) - std::get<0>(b));
    double dy = static_cast<double>(std::get<1>(a) - std::get<1>(b));
    double dz = static_cast<double>(std::get<2>(a) - std::get<2>(b));

    return std::sqrt(dx * dx + dy * dy + dz * dz);
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

    std::vector<std::tuple<int, int, int> > locations;
    for (const std::string &line: lines) {
        std::vector<std::string> splitted = split(line, ",");
        locations.emplace_back(std::stoi(splitted[0]), std::stoi(splitted[1]), std::stoi(splitted[2]));
    }

    std::vector<std::pair<std::tuple<int, int>, double> > index_distances;
    for (int i = 0; i < locations.size() - 1; i++) {
        for (int j = i + 1; j < locations.size(); j++) {
            double distance = distance3D(locations[i], locations[j]);
            std::tuple<int, int> indices = std::make_tuple(i, j);
            index_distances.push_back(std::make_pair(indices, distance));
        }
    }

    std::ranges::sort(index_distances,
                      [](const std::pair<std::tuple<int, int>, double> &a,
                         const std::pair<std::tuple<int, int>, double> &b) {
                          return a.second < b.second;
                      }
    );

    std::vector<std::pair<std::vector<int>, int> > circuits;
    for (int i = 0; i < 10; i++) {
        auto [location, distance] = index_distances[i];
        auto [from_i, to_i] = location;
        bool is_placed = false;
        for (auto &[indices, amount]: circuits) {
            bool from_belongs_to_circuit = false;
            bool to_belongs_to_circuit = false;
            for (int index: indices) {
                if (index == from_i) {
                    from_belongs_to_circuit = true;
                } else if (index == to_i) {
                    to_belongs_to_circuit = true;
                }
            }

            // TODO edge case: there are two circuits, 1 with from_i, and 1 with to_i
            if (from_belongs_to_circuit || to_belongs_to_circuit) {
                is_placed = true;
                if (from_belongs_to_circuit) {
                    amount++;
                    indices.push_back(to_i);
                } else {
                    amount++;
                    indices.push_back(from_i);
                }
                break;
            }
        }
        if (!is_placed) {
            std::vector<int> circuit_indices = {from_i, to_i};
            circuits.emplace_back(circuit_indices, 2);
        }
    }

    std::ranges::sort(circuits,
                      [](const std::pair<std::vector<int>, int> &a,
                         const std::pair<std::vector<int>, int> &b) {
                          return a.second > b.second;
                      });

    int res = 1;
    for (int i = 0; i < 3; i++) {
        res *= circuits[i].second;
    }

    std::cout << res << std::endl;
}
