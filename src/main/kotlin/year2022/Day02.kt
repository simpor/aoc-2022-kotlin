import AoCUtils
import AoCUtils.test
import Type.*

enum class Type(val score: Long) { ROCK(1L), PAPER(2L), SCISSOR(3L) }

fun main() {

    val lost = 0L
    val draw = 3L
    val win = 6L
    fun isRock(a: String) = a == "A" || a == "X"
    fun isPaper(a: String) = a == "B" || a == "Y"
    fun win(a: String) = a == "Z"
    fun draw(a: String) = a == "Y"


    fun toType(a: String) = if (isRock(a)) ROCK else if (isPaper(a)) PAPER else SCISSOR

    fun part1(input: String, debug: Boolean = false): Long {
        return input.lines().filter { it.isNotEmpty() }.sumOf { game ->
            val a = game.split(" ")
            val first = toType(a[0])
            val second = toType(a[1])

            when (first) {
                ROCK -> when (second) {
                    ROCK -> second.score + draw
                    PAPER -> second.score + win
                    SCISSOR -> second.score + lost
                }

                PAPER -> when (second) {
                    ROCK -> second.score + lost
                    PAPER -> second.score + draw
                    SCISSOR -> second.score + win
                }

                else -> when (second) {
                    ROCK -> second.score + win
                    PAPER -> second.score + lost
                    SCISSOR -> second.score + draw
                }
            }
        }


    }

    fun part2(input: String, debug: Boolean = false): Long {
        return input.lines().filter { it.isNotEmpty() }.sumOf { game ->
            val a = game.split(" ")
            val first = toType(a[0])
            val second = a[1]
            when (first) {
                ROCK -> when {
                    win(second) -> PAPER.score + win
                    draw(second) -> ROCK.score + draw
                    else -> SCISSOR.score + lost
                }

                PAPER -> when {
                    win(second) -> SCISSOR.score + win
                    draw(second) -> PAPER.score + draw
                    else -> ROCK.score + lost
                }

                SCISSOR -> when {
                    win(second) -> ROCK.score + win
                    draw(second) -> SCISSOR.score + draw
                    else -> PAPER.score + lost
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
