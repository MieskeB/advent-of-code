#include <any>
#include <array>
#include <filesystem>
#include <fstream>
#include <iostream>
#include <ranges>
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

bool is_valid(std::vector<int> goal, std::vector<bool> states) {
    for (int g : goal) {
        if (!states[g]) {
            return false;
        }
        states[g] = false;
    }
    for (const bool state : states) {
        if (state) {
            return false;
        }
    }
    return true;
}

bool try_sequences(int depth, int max_depth, const std::vector<std::vector<int>> &buttons, std::vector<bool> &states, const std::vector<int> &goal) {
    if (depth == max_depth) {
        return is_valid(goal, states);
    }

    for (std::size_t i = 0; i < buttons.size(); i++) {
        for (int action : buttons[i]) {
            states[action] = !states[action];
        }

        if (try_sequences(depth + 1, max_depth, buttons, states, goal)) {
            return true;
        }

        for (int action : buttons[i]) {
            states[action] = !states[action];
        }
    }
    return false;
}

int fewest_presses(std::vector<int> goal, int goal_length, std::vector<std::vector<int>> buttons) {
    std::vector<bool> states(goal_length, false);
    for (int presses = 1; presses < 20; presses++) {
        std::vector<bool> tmp = states;

        if (try_sequences(0, presses, buttons, tmp, goal)) {
            return presses;
        }
    }
    return -1;
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

    int res = 0;
    for (const std::string& line : lines) {
        std::vector<std::string> buttons_s = split(line, " ");
        std::string goal_s = buttons_s[0];
        std::string idk_s = buttons_s.back();
        buttons_s.erase(buttons_s.begin());
        buttons_s.pop_back();

        int goal_length = goal_s.length() - 2;
        std::vector<int> goal;
        for (int i = 1; i < goal_length + 1; i++) {
            if (goal_s[i] == '#') {
                goal.push_back(i - 1);
            }
        }

        std::vector<std::vector<int>> buttons;
        for (std::string &button_s : buttons_s) {
            button_s = button_s.substr(1, button_s.size() - 2);
            std::vector<std::string> splitted = split(button_s, ",");
            std::vector<int> actions;
            for (const std::string& action_s : splitted) {
                actions.push_back(std::stoi(action_s));
            }
            buttons.push_back(actions);
        }

        res += fewest_presses(goal, goal_length, buttons);
    }

    std::cout << res << std::endl;
}
