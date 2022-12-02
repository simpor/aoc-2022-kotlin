import AoCUtils
import AoCUtils.test

fun main() {

    val rockX = 1L
    val paperY = 2L
    val scissorZ = 3L

    val firstRock = "A"
    val firstPaper = "B"
    val firstScissor = "C"

    val secondRock = "X"
    val secondPaper = "Y"
    val secondScissor = "Z"

    val lost = 0L
    val draw = 3L
    val win = 6L
    fun isRock(a: String) = a == "A" || a == "X"
    fun isPaper(a: String) = a == "B" || a == "Y"
    fun isScissor(a: String) = a == "C" || a == "Z"



    fun part1(input: String, debug: Boolean = false): Long {
        return input.lines().filter { it.isNotEmpty() }.map { game ->
            val a = game.split(" ")
            val first = a[0]
            val second = a[1]

            val score = when {
                isRock(first) -> {
                    when {
                        isRock(second) -> rockX + draw
                        isPaper(second) -> paperY + lost
                        else -> scissorZ+lost
                    }
                }
                else -> 0
            }


            when (first) {
                firstRock -> when (second) {
                    secondRock -> rockX + draw
                    secondPaper -> paperY + win
                    else -> scissorZ + lost
                }

                firstPaper -> when (second) {
                    secondRock -> rockX + lost
                    secondPaper -> paperY + draw
                    else -> scissorZ + win
                }

                else -> when (second) {
                    secondRock -> rockX + win
                    secondPaper -> paperY + lost
                    else -> scissorZ + draw
                }
            }

        }.sum()


    }

    fun part2(input: String, debug: Boolean = false): Long {
        return input.lines().filter { it.isNotEmpty() }.map { game ->
            val a = game.split(" ")
            val first = a[0]
            val second = a[1]
// A for Rock, B for Paper, and C for Scissors
            when (first) {
                firstRock -> when (second) {
                    secondRock -> scissorZ + lost
                    secondPaper -> rockX + draw
                    else -> paperY + win
                }

                firstPaper -> when (second) {
                    secondRock -> rockX + lost
                    secondPaper -> paperY + draw
                    else -> scissorZ + win
                }

                else -> when (second) {
                    secondRock -> paperY + lost
                    secondPaper -> scissorZ + draw
                    else -> rockX + win
                }
            }

        }.sum()
    }

    val testInput = "A Y\n" +
            "B X\n" +
            "C Z\n"

    val input = AoCUtils.readText("year2022/day02.txt")

    part1(testInput) test Pair(15L, "test 1 part 1")
    part1(input) test Pair(12535L, "part 1")

    part2(testInput) test Pair(12, "test 2 part 2")
    part2(input) test Pair(15457L, "part 2")
}
