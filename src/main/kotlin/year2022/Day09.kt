import AoCUtils.test
import kotlin.math.absoluteValue

fun main() {

    fun parse(input: String): List<Pair<String, Int>> = input.lines().map { l ->
            val s = l.split(" ")
            Pair(s[0], s[1].toInt())
        }

    fun moveFunction(move: Pair<String, Int>): (Point) -> Point = when (move.first) {
        "U" -> { pos: Point -> pos.copy(y = pos.y + 1) }
        "D" -> { pos: Point -> pos.copy(y = pos.y - 1) }
        "R" -> { pos: Point -> pos.copy(x = pos.x + 1) }
        "L" -> { pos: Point -> pos.copy(x = pos.x - 1) }
        else -> throw Exception("Unknown move: $move")
    }

    fun Point.isAround(other: Point): Boolean = if ((this.x - other.x).absoluteValue >= 2) false else (this.y - other.y).absoluteValue < 2


    fun printSnake(moveCommand: Pair<String, Int>, snake: MutableMap<Int, Point>) {
        val xMax = snake.values.map { it.x }.max()
        val xMin = snake.values.map { it.x }.min()
        val yMax = snake.values.map { it.y }.max()
        val yMin = snake.values.map { it.y }.min()

        val points = snake.map { Pair(it.value, it.key) }.toMap()
        println("Move command: ${moveCommand.first} ${moveCommand.second}")
        for (y in yMax downTo yMin) {
            for (x in xMin..xMax) {
                if (x == 0 && y == 0) print("s")
                else if (points.contains(Point(x, y))) print(points[Point(x, y)])
                else print(".")

            }
            println()
        }
        println()

    }

    fun printMap(map: MutableMap<Point, Boolean>) {
        val xMax = map.keys.map { it.x }.max()
        val xMin = map.keys.map { it.x }.min()
        val yMax = map.keys.map { it.y }.max()
        val yMin = map.keys.map { it.y }.min()

        val points = map.map { it.key }.toSet()
        println("Map")
        for (y in yMax downTo yMin) {
            for (x in xMin..xMax) {
                if (points.contains(Point(x, y))) print("#")
                else print(".")

            }
            println()
        }
        println()

    }


    fun moveSnake2(snake: MutableMap<Int, Point>, i: Int, move: (Point) -> Point): Pair<Int, Point> {
        val current = snake[i]!!
        val pair = if (i == 0) {
            Pair(0, move.invoke(current))
        } else {
            val prev = snake[i - 1]!!
            if (prev.isAround(current)) {
                Pair(i, current)
            } else {
                if (prev.x == current.x || prev.y == current.y) {// straight move
                    if (prev.x == current.x) {
                        if (current.y < prev.y) Pair(i, current.copy(y = current.y + 1))
                        else Pair(i, current.copy(y = current.y - 1))
                    } else {
                        if (current.x < prev.x) Pair(i, current.copy(x = current.x + 1))
                        else Pair(i, current.copy(x = current.x - 1))
                    }
                } else { // diagonal move
                    if ((current.x - prev.x).absoluteValue > 1) {
                        if (current.x < prev.x) Pair(i, prev.copy(x = prev.x - 1))
                        else Pair(i, prev.copy(x = prev.x + 1))
                    } else {
                        if (current.y < prev.y) Pair(i, prev.copy(y = prev.y - 1))
                        else Pair(i, prev.copy(y = prev.y + 1))
                    }
                }
            }
        }
        return pair
    }

    fun part1(input: String, debug: Boolean = false): Long {
        val moves = parse(input)

        val snake = (0..1).map { Pair(it, Point(0, 0)) }.toMap().toMutableMap()
        val map = mutableMapOf<Point, Boolean>()

        moves.forEach { move ->
            val updatePos = moveFunction(move)
            repeat(move.second) {
                (0..1).forEach { key ->
                    val oldPos = snake[key]!!
                    val newPos = moveSnake2(snake, key, updatePos).second
                    snake[key] = newPos
                }
                map[snake[1]!!] = true
                if (debug) printSnake(move, snake)
            }

        }

        if (debug) printMap(map)

        return map.values.count { it }.toLong()
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val moves = parse(input)

        val snake = (0..9).map { Pair(it, Point(0, 0)) }.toMap().toMutableMap()
        val map = mutableMapOf<Point, Boolean>()

        moves.forEach { move ->
            val updatePos = moveFunction(move)
            repeat(move.second) {
                (0..9).forEach { key ->
                    val oldPos = snake[key]!!
                    val newPos = moveSnake2(snake, key, updatePos).second
                    snake[key] = newPos
                }
                map[snake[9]!!] = true
                if (debug) printSnake(move, snake)
            }

        }

        if (debug) printMap(map)

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

//    part2(testInput, false) test Pair(1L, "test 1 part 2")

    part2(
        "R 5\n" +
                "U 8\n" +
                "L 8\n" +
                "D 3\n" +
                "R 17\n" +
                "D 10\n" +
                "L 25\n" +
                "U 20", false
    ) test Pair(36L, "test 2 part 2")
    part2(input) test Pair(2602L, "part 2")
}
