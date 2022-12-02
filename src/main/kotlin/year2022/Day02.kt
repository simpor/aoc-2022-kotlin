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
    fun win(a: String) = a == "Z"
    fun draw(a: String) = a == "Y"


    fun part1(input: String, debug: Boolean = false): Long {
        return input.lines().filter { it.isNotEmpty() }.sumOf { game ->
            val a = game.split(" ")
            val first = a[0]
            val second = a[1]

            when {
                isRock(first) -> when {
                    isRock(second) -> rockX + draw
                    isPaper(second) -> paperY + win
                    else -> scissorZ + lost
                }

                isPaper(first) -> when {
                    isRock(second) -> rockX + lost
                    isPaper(second) -> paperY + draw
                    else -> scissorZ + win
                }

                else -> when {
                    isRock(second) -> rockX + win
                    isPaper(second) -> paperY + lost
                    else -> scissorZ + draw
                }
            }
        }


    }

    fun part2(input: String, debug: Boolean = false): Long {
        return input.lines().filter { it.isNotEmpty() }.sumOf { game ->
            val a = game.split(" ")
            val first = a[0]
            val second = a[1]
            when {
                isRock(first) -> when {
                    win(second) -> paperY + win
                    draw(second) -> rockX + draw
                    else -> scissorZ + lost
                }

                isPaper(first) -> when {
                    win(second) -> scissorZ + win
                    draw(second) -> paperY + draw
                    else -> rockX + lost
                }

                else -> when {
                    win(second) -> rockX + win
                    draw(second) -> scissorZ + draw
                    else -> paperY + lost
                }
            }


        }
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
