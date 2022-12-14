import kotlin.math.absoluteValue


class Day09 {

    fun parse(input: String): List<Pair<String, Int>> = input.lines().map { l ->
        val s = l.split(" ")
        Pair(s[0], s[1].toInt())
    }

    fun moveFunction(move: String): (Point) -> Point = when (move) {
        "U" -> { pos: Point -> pos.copy(y = pos.y + 1) }
        "D" -> { pos: Point -> pos.copy(y = pos.y - 1) }
        "R" -> { pos: Point -> pos.copy(x = pos.x + 1) }
        "L" -> { pos: Point -> pos.copy(x = pos.x - 1) }
        else -> throw Exception("Unknown move: $move")
    }

    fun Point.isAround(other: Point): Boolean =
        if ((this.x - other.x).absoluteValue >= 2) false else (this.y - other.y).absoluteValue < 2


    fun printSnake(moveCommand: Pair<String, Int>, snake: MutableMap<Int, Point>) {
        val xMax = snake.values.maxOfOrNull { it.x }!!
        val xMin = snake.values.minOfOrNull { it.x }!!
        val yMax = snake.values.maxOfOrNull { it.y }!!
        val yMin = snake.values.minOfOrNull { it.y }!!

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
        val xMax = map.keys.maxOfOrNull { it.x }!!
        val xMin = map.keys.minOfOrNull { it.x }!!
        val yMax = map.keys.maxOfOrNull { it.y }!!
        val yMin = map.keys.minOfOrNull { it.y }!!

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


    fun moveSnake(snake: MutableMap<Int, Point>, i: Int, move: (Point) -> Point): Pair<Int, Point> {
        val current = snake[i]!!
        val pair = if (i == 0) {
            Pair(0, move.invoke(current))
        } else {
            val prev = snake[i - 1]!!
            if (prev.isAround(current)) {
                Pair(i, current)
            } else {
                var x = if (current.x == prev.x) current.x else if (current.x < prev.x) current.x + 1 else current.x - 1
                var y = if (current.y == prev.y) current.y else if (current.y < prev.y) current.y + 1 else current.y - 1
                Pair(i, Point(x, y))
            }
        }
        return pair
    }

    fun part1(input: String, debug: Boolean = false): Long {
        val moves = parse(input)

        val range = 0..1
        val snake = range.associateWith { Point(0, 0) }.toMutableMap()
        val map = logic(moves, range, snake, debug)

        if (debug) printMap(map)

        return map.values.count { it }.toLong()
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val moves = parse(input)

        val range = 0..9
        val snake = range.associateWith { Point(0, 0) }.toMutableMap()
        val map = logic(moves, range, snake, debug)

        if (debug) printMap(map)

        return map.values.count { it }.toLong()
    }

    fun logic(
        moves: List<Pair<String, Int>>,
        range: IntRange,
        snake: MutableMap<Int, Point>,
        debug: Boolean
    ): MutableMap<Point, Boolean> {
        val map = mutableMapOf<Point, Boolean>()

        moves.forEach { move ->
            val updatePos = moveFunction(move.first)
            repeat(move.second) {
                range.forEach { key ->
                    snake[key] = moveSnake(snake, key, updatePos).second
                }
                map[snake[range.max()]!!] = true
                if (debug) printSnake(move, snake)
            }

        }
        return map
    }

    fun testInput() =
        "R 4\n" +
                "U 4\n" +
                "L 3\n" +
                "D 1\n" +
                "R 4\n" +
                "D 1\n" +
                "L 5\n" +
                "R 2"

    fun input() = AoCUtils.readText("year2022/day09.txt")
}
