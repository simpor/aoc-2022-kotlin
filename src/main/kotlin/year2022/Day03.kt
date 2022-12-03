import AoCUtils
import AoCUtils.test

fun main() {


    fun part1(input: String, debug: Boolean = false): Long {

        val answer = input.lines().map { line ->
            val firstItem = line.substring(0, line.length / 2)
            val secondItem = line.substring(line.length / 2)
            firstItem.map { c -> if (secondItem.contains(c)) c else null }
                .filterNotNull()
                .distinct()
                .map { c ->
                    val prio = if (c.isLowerCase()) c.code - 'a'.code + 1
                    else c.code - 'A'.code + 27
                    prio
                }
        }.flatten()
            .sum()

        return answer.toLong()
    }

    fun part2(input: String, debug: Boolean = false): Long {

        val x = input.lines()
            .chunked(3)
            .map { list ->
                val first = list[0]
                val second = list[1]
                val third = list[2]
                first.map { c ->
                    if (second.contains(c) && third.contains(c)) c else null
                }.distinct()
            }.flatten().filterNotNull().map { c ->
                val prio = if (c.isLowerCase()) c.code - 'a'.code + 1
                else c.code - 'A'.code + 27
                prio
            }.sum()

        return x.toLong()
    }

    val testInput = "vJrwpWtwJgWrhcsFMMfFFhFp\n" +
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\n" +
            "PmmdzqPrVvPwwTWBwg\n" +
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn\n" +
            "ttgJtRGJQctTZtZT\n" +
            "CrZsJsPPZsGzwwsLwLmpwMDw"

    val input = AoCUtils.readText("year2022/day03.txt")

    part1(testInput, false) test Pair(157L, "test 1 part 1")
    part1(input, false) test Pair(7850L, "part 1")

    part2(testInput, false) test Pair(70L, "test 2 part 2")
    part2(input) test Pair(2581L, "part 2")
}
