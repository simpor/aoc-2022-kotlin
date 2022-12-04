import AoCUtils
import AoCUtils.test

fun main() {


    fun part1(input: String, debug: Boolean = false): Long {
        return input.lines().map { line ->
            line.split(",").map {
                val x = it.split("-")
                Pair(x[0].toInt(), x[1].toInt())
            }
        }.map { elves ->
            val e1 = elves[0]
            val e2 = elves[1]
            if (e1.first >= e2.first && e1.second <= e2.second) true
            else e2.first >= e1.first && e2.second <= e1.second
        }.count { it }.toLong()
    }

    fun part2(input: String, debug: Boolean = false): Long {
        return input.lines().map { line ->
            line.split(",").map {
                val x = it.split("-")
                Pair(x[0].toInt(), x[1].toInt())
            }
        }.map { elves ->
            val e1 = elves[0]
            val e2 = elves[1]
            if (e1.first >= e2.first && e1.first <= e2.second) true
            else if (e1.second >= e2.first && e1.second <= e2.second) true
            else if (e2.second >= e1.first && e2.second <= e1.second) true
            else if (e2.first >= e1.first && e2.first <= e1.second) true
            else false
        }.count { it }.toLong()
    }

    val testInput =
        "2-4,6-8\n" +
                "2-3,4-5\n" +
                "5-7,7-9\n" +
                "2-8,3-7\n" +
                "6-6,4-6\n" +
                "2-6,4-8"

    val input = AoCUtils.readText("year2022/day04.txt")

    part1(testInput, false) test Pair(2L, "test 1 part 1")
    part1(input, false) test Pair(490L, "part 1")

    part2(testInput, false) test Pair(4L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")
}
