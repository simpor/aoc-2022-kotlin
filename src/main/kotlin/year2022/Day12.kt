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
        "Sabqponm\n" +
                "abcryxxl\n" +
                "accszExk\n" +
                "acctuvwj\n" +
                "abdefghi"

    val input = AoCUtils.readText("year2022/day12.txt")

    part1(testInput, false) test Pair(0L, "test 1 part 1")
    part1(input, false) test Pair(0L, "part 1")

    part2(testInput, false) test Pair(0L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")
}
