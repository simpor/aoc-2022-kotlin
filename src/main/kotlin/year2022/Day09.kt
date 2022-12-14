import AoCUtils
import AoCUtils.test
import kotlin.math.absoluteValue

fun main() {

    fun Point.isAround(other: Point): Boolean {
        if ((this.x - other.x).absoluteValue >= 2) return false
        if ((this.y - other.y).absoluteValue >= 2) return false
        return true
    }

    fun part1(input: String, debug: Boolean = false): Long {
        val moves = input.lines().map { l ->
            val s = l.split(" ")
            Pair(s[0], s[1].toInt())
        }

        var pos = Point(0, 0)
        var prevPos = Point(0, 0)
        var tail = Point(0, 0)

        val map = mutableMapOf<Point, Boolean>()

        moves.forEach { move ->
            when (move.first) {
                "U" -> {
                    repeat(move.second) {
                        prevPos = pos
                        pos = pos.copy(y = pos.y + 1)
                        if (!tail.isAround(pos)) {
                            tail = prevPos
                        }
                        map[tail] = true
                    }
                }

                "D" -> {
                    repeat(move.second) {
                        prevPos = pos
                        pos = pos.copy(y = pos.y - 1)
                        if (!tail.isAround(pos)) {
                            tail = prevPos
                        }
                        map[tail] = true
                    }
                }

                "R" -> {
                    repeat(move.second) {
                        prevPos = pos
                        pos = pos.copy(x = pos.x + 1)
                        if (!tail.isAround(pos)) {
                            tail = prevPos
                        }
                        map[tail] = true
                    }
                }

                "L" -> {
                    repeat(move.second) {
                        prevPos = pos
                        pos = pos.copy(x = pos.x - 1)
                        if (!tail.isAround(pos)) {
                            tail = prevPos
                        }
                        map[tail] = true
                    }
                }

                else -> throw Exception("Unknown move: $move")
            }
        }



        return map.values.count { it }.toLong()
    }

    fun part2(input: String, debug: Boolean = false): Long {
        return 0L
    }

    val testInput =
        "R 4\n" +
                "U 4\n" +
                "L 3\n" +
                "D 1\n" +
                "R 4\n" +
                "D 1\n" +
                "L 5\n" +
                "R 2"

    val input = AoCUtils.readText("year2022/day09.txt")

    part1(testInput, false) test Pair(13L, "test 1 part 1")
    part1(input, false) test Pair(5858L, "part 1")

    part2(testInput, false) test Pair(1L, "test 2 part 2")
    part2("R 5\n" +
            "U 8\n" +
            "L 8\n" +
            "D 3\n" +
            "R 17\n" +
            "D 10\n" +
            "L 25\n" +
            "U 20", false) test Pair(36L, "test 3 part 2")
    part2(input) test Pair(0L, "part 2")
}
