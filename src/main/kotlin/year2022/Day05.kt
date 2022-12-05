import AoCUtils
import AoCUtils.test

fun main() {

    data class Crane(val move: Int, val from: Int, val to: Int)


    fun part1(input: String, debug: Boolean = false): String {
        val crates = mutableListOf<Int>()

        val towers = input.lines().filter { it.startsWith(" 1 ") }.map { it.replace(" ", "") }.last().toInt()

        input.lines().filter { it.contains("[") }
            .map {  }


        return ""
    }

    fun part2(input: String, debug: Boolean = false): Long {
        return 0L
    }

    val testInput =
        "    [D]    \n" +
                "[N] [C]    \n" +
                "[Z] [M] [P]\n" +
                " 1   2   3 \n" +
                "\n" +
                "move 1 from 2 to 1\n" +
                "move 3 from 1 to 3\n" +
                "move 2 from 2 to 1\n" +
                "move 1 from 1 to 2\n"

    val input = AoCUtils.readText("year2022/day05.txt")

    part1(testInput, false) test Pair("CMZ", "test 1 part 1")
    part1(input, false) test Pair("490L", "part 1")

    part2(testInput, false) test Pair(4L, "test 2 part 2")
    part2(input) test Pair(921L, "part 2")
}
