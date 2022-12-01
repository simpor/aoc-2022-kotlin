import AoCUtils
import AoCUtils.test

fun main() {

    fun part1(input: String, debug: Boolean = false): Long {

        val elves = input.split("\n\n")

        return elves.map { s -> s.split("\n").sumOf { it.toInt() } }.max().toLong()
    }

    fun part2(input: String, debug: Boolean = false): Long {

        val elves = input.split("\n\n")

        return elves.map { s -> s.split("\n").sumOf { it.toInt() } }.sorted().asReversed().take(3).sum().toLong()

    }


    val testInput = "1000\n" +
            "2000\n" +
            "3000\n" +
            "\n" +
            "4000\n" +
            "\n" +
            "5000\n" +
            "6000\n" +
            "\n" +
            "7000\n" +
            "8000\n" +
            "9000\n" +
            "\n" +
            "10000"

    val input = AoCUtils.readText( "year2022/day01.txt")

    part1(testInput) test Pair(24000L, "test 1 part 1")
    part1(input) test Pair(68775L, "part 1")

    part2(testInput) test Pair(45000L, "test 2 part 2")
    part2(input) test Pair(202585L, "part 2")
}
