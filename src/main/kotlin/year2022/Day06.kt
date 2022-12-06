import AoCUtils
import AoCUtils.test

fun main() {


    fun part1(input: String, debug: Boolean = false): Long {
        for (a in 3..input.length) {
            val sub = input.subSequence(a-3, a+1)
            if (sub.toSet().count() == 4) {
                return a.toLong() + 1
            }
        }
        return 0L
    }

    fun part2(input: String, debug: Boolean = false): Long {
        for (a in 13..input.length) {
            val sub = input.subSequence(a-13, a+1)
            if (sub.toSet().count() == 14) {
                return a.toLong() + 1
            }
        }
        return 0L
    }

    val test1_1 = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
    val test1_2 = "bvwbjplbgvbhsrlpgdmjqwftvncz"
    val test1_3 = "nppdvjthqldpwncqszvftbrmjlhg"
    val test1_4 = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"
    val test1_5 = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"

    val input = AoCUtils.readText("year2022/day06.txt")

    part1(test1_1, false) test Pair(7L, "test 1 part 1")
    part1(test1_2, false) test Pair(5L, "test 2 part 1")
    part1(test1_3, false) test Pair(6L, "test 3 part 1")
    part1(test1_4, false) test Pair(10L, "test 4 part 1")
    part1(test1_5, false) test Pair(11L, "test 5 part 1")
    part1(input, false) test Pair(1093L, "part 1")

    part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb", false) test Pair(19L, "test 1 part 2")
    part2("bvwbjplbgvbhsrlpgdmjqwftvncz", false) test Pair(23L, "test 2 part 2")
    part2("nppdvjthqldpwncqszvftbrmjlhg", false) test Pair(23L, "test 3 part 2")
    part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", false) test Pair(29L, "test 4 part 2")
    part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", false) test Pair(26L, "test 5 part 2")
    part2(input) test Pair(3534L, "part 2")  // no 1833
}
