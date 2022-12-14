import AoCUtils
import AoCUtils.test

fun main() {


    fun part1(input: String, debug: Boolean = false): Long {
        return 0L
    }

    fun part2(input: String, debug: Boolean = false): Long {
        return 0L
    }

    val testInput =
        "498,4 -> 498,6 -> 496,6\n" +
                "503,4 -> 502,4 -> 502,9 -> 494,9"

    val input = AoCUtils.readText("year2022/day14.txt")

    part1(testInput, false) test Pair(24L, "test 1 part 1")
    part1(input, false) test Pair(0L, "part 1")

    part2(testInput, false) test Pair(0L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")
}
