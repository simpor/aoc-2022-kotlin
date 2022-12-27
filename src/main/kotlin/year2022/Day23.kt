import AoCUtils
import AoCUtils.test

enum class MapType { SPACE, ELF }

fun main() {

    data class MovementLogic(val moveDirection: Map2dDirection, val checks: List<Map2dDirection>) {
        fun checkIfElf(map: Map2d<MapType>, elf: Point): Boolean {
            val toCheck = map.around(elf, checks).values.any { it == MapType.ELF }
            return toCheck
        }

        fun checkIfSpace(map: Map2d<MapType>, elf: Point): Boolean {
            val toCheck = map.around(elf, checks).values.any { it == MapType.SPACE }
            return toCheck
        }

        fun pointToMoveTo(point: Point) = when (moveDirection) {
            Map2dDirection.N -> Point(point.x, point.y - 1)
            Map2dDirection.NE -> Point(point.x + 1, point.y - 1)
            Map2dDirection.E -> Point(point.x + 1, point.y)
            Map2dDirection.SE -> Point(point.x + 1, point.y + 1)
            Map2dDirection.S -> Point(point.x, point.y + 1)
            Map2dDirection.SW -> Point(point.x - 1, point.y + 1)
            Map2dDirection.W -> Point(point.x - 1, point.y)
            Map2dDirection.NW -> Point(point.x - 1, point.y - 1)
            Map2dDirection.CENTER -> point
        }
    }

    fun part1(input: String, debug: Boolean = false): Long {
        val map = parseMap(input) { c ->
            when (c) {
                '.' -> MapType.SPACE
                '#' -> MapType.ELF
                else -> throw Exception("Unknown Type")
            }
        }

        val movementLogics = mutableListOf<MovementLogic>()
        movementLogics.add(
            MovementLogic(
                Map2dDirection.N,
                listOf(Map2dDirection.N, Map2dDirection.NE, Map2dDirection.NW)
            )
        )
        movementLogics.add(
            MovementLogic(
                Map2dDirection.S,
                listOf(Map2dDirection.S, Map2dDirection.SE, Map2dDirection.SW)
            )
        )
        movementLogics.add(
            MovementLogic(
                Map2dDirection.W,
                listOf(Map2dDirection.W, Map2dDirection.NW, Map2dDirection.SW)
            )
        )
        movementLogics.add(
            MovementLogic(
                Map2dDirection.E,
                listOf(Map2dDirection.E, Map2dDirection.NE, Map2dDirection.SE)
            )
        )

        data class ElfToMove(val from: Point, val to: Point)

        while (true) {
            val elfes = map.filter { it.value == MapType.ELF }.map { it.key }

//            val movements = elfes.filter { e -> movementLogic.checkIfSpace(map, e) }.map { ElfToMove(it, movementLogic.pointToMoveTo(it))}


            val movementLogic = movementLogics.removeAt(0)
            movementLogics.add(movementLogic)
        }




        return 0L
    }

    fun part2(input: String, debug: Boolean = false): Long {
        return 0L
    }

    val testInput =
        "....#..\n" +
                "..###.#\n" +
                "#...#.#\n" +
                ".#...##\n" +
                "#.###..\n" +
                "##.#.##\n" +
                ".#..#.."

    val input = AoCUtils.readText("year2022/day23.txt")

    part1(testInput, false) test Pair(24L, "test 1 part 1")
    part1(input, false) test Pair(0L, "part 1")

    part2(testInput, false) test Pair(0L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")
}
