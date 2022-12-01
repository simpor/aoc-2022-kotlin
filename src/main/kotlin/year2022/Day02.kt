import AoCUtils
import AoCUtils.test

fun main() {

    fun part1(input: String, debug: Boolean = false): Long {
        return 0L
    }

    fun part2(input: String, debug: Boolean = false): Long {
        return 0L
    }
    
    val testInput = "add test example"

    val input = AoCUtils.readText( "year2022/day02.txt")

    part1(testInput) test Pair(0L, "test 1 part 1")
    part1(input) test Pair(0L, "part 1")

    part2(testInput) test Pair(0L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")
}
