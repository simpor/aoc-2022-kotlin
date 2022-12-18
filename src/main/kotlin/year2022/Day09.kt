import AoCUtils.test
import java.security.Principal
import kotlin.math.absoluteValue

fun main() {

    fun parse(input: String): List<Pair<String, Int>> {
        val moves = input.lines().map { l ->
            val s = l.split(" ")
            Pair(s[0], s[1].toInt())
        }
        return moves
    }

    fun moveFunction(move: Pair<String, Int>): (Point) -> Point {
        val updatePos = when (move.first) {
            "U" -> { pos: Point -> pos.copy(y = pos.y + 1) }
            "D" -> { pos: Point -> pos.copy(y = pos.y - 1) }
            "R" -> { pos: Point -> pos.copy(x = pos.x + 1) }
            "L" -> { pos: Point -> pos.copy(x = pos.x - 1) }
            else -> throw Exception("Unknown move: $move")
        }
        return updatePos
    }


    fun Point.isAround(other: Point): Boolean {
        if ((this.x - other.x).absoluteValue >= 2) return false
        if ((this.y - other.y).absoluteValue >= 2) return false
        return true
    }

    fun moveSnake(snake: Map<Int, Point>, i: Int, move: (Point) -> Point): Pair<Int, Point> {
        val current = snake[i]!!
        val pair = if (i == 0) {
            Pair(0, move.invoke(current))
        } else {
            val prev = snake[i - 1]!!
            val newPrev = move.invoke(prev)
            if (newPrev.isAround(current)) {
                Pair(i, current)
            } else {
                Pair(i, prev)
            }
        }
        return pair
    }

    fun part1(input: String, debug: Boolean = false): Long {
        val moves = parse(input)
        var snake = (0..1).map { Pair(it, Point(0, 0)) }.toMap()
        val map = mutableMapOf<Point, Boolean>()

        moves.forEach { move ->
            val updatePos = moveFunction(move)
            repeat(move.second) {
                val newSnake = snake.keys.sorted().associate { moveSnake(snake, it, updatePos) }
                snake = newSnake
                map[snake[1]!!] = true
            }
        }

        return map.values.count { it }.toLong()
    }

    fun printSnake(moveCommand: Pair<String, Int>, snake: Map<Int, Point>) {
        val xMax = snake.values.map { it.x }.max()
        val xMin = snake.values.map { it.x }.min()
        val yMax = snake.values.map { it.y }.max()
        val yMin = snake.values.map { it.y }.min()

        val points = snake.map { Pair(it.value, it.key) }.toMap()
        println("Move command: ${moveCommand.first} ${moveCommand.second}")
        for (y in yMin..yMax) {
            for (x in xMin..xMax) {
                if(points.contains(Point(x, y))) print(points[Point(x, y)])
                else print(".")
            }
            println()
        }
        println()

    }

    fun part2(input: String, debug: Boolean = false): Long {
        val moves = parse(input)

        var snake = (0..9).map { Pair(it, Point(0, 0)) }.toMap()
        val map = mutableMapOf<Point, Boolean>()

        moves.forEach { move ->
            val updatePos = moveFunction(move)
            repeat(move.second) {
                val newSnake = snake.keys.sorted().associate { moveSnake(snake, it, updatePos) }
                snake = newSnake
                map[snake[9]!!] = true

                printSnake(move, snake)
            }
        }



        return map.values.count { it }.toLong()
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

    part2(testInput, false) test Pair(1L, "test 1 part 2")
//    part2(
//        "R 5\n" +
//                "U 8\n" +
//                "L 8\n" +
//                "D 3\n" +
//                "R 17\n" +
//                "D 10\n" +
//                "L 25\n" +
//                "U 20", false
//    ) test Pair(36L, "test 2 part 2")
//    part2(input) test Pair(2602L, "part 2")
}
