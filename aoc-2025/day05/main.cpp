#include <algorithm>
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

    bool is_ranges = true;
    std::vector<std::pair<long long, long long> > ranges;
    int count_a = 0;
    for (const std::string &line: lines) {
        if (is_ranges) {
            if (line.size() == 0) {
                is_ranges = false;
                continue;
            }

            std::vector<std::string> range = split(line, "-");
            long long min = std::stoll(range[0]);
            long long max = std::stoll(range[1]);
            ranges.push_back(std::make_pair(min, max));
            continue;
        }

        long long number = std::stoll(line);
        for (std::pair<long long, long long> range: ranges) {
            if (number >= range.first && number <= range.second) {
                count_a++;
                break;
            }
        }
    }

    std::cout << "Count a: " << count_a << std::endl;

    std::ranges::sort(ranges,
                      [](const std::pair<long long, long long> &a, const std::pair<long long, long long> &b) {
                          return a.first < b.first;
                      });

    long long count_b = 0;
    long long current_start = ranges[0].first;
    long long current_end = ranges[0].second;
    for (int i = 1; i < ranges.size(); i++) {
        long long next_start = ranges[i].first;
        long long next_end = ranges[i].second;

        if (next_start <= current_end + 1) {
            if (next_end > current_end) {
                current_end = next_end;
            }
        } else {
            count_b += (current_end - current_start + 1);
            current_start = next_start;
            current_end = next_end;
        }
    }
    count_b += (current_end - current_start + 1);

    std::cout << "Count b: " << count_b << std::endl;
}
