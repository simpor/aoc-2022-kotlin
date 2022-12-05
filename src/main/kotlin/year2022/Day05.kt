import AoCUtils.test

fun main() {

    data class Crane(val move: Int, val from: Int, val to: Int)


    fun part1(input: String, debug: Boolean = false): String {
        val towers = input.lines().filter { it.startsWith(" 1 ") }.map { it.replace(" ", "") }.last().length
        val crates = mutableListOf<MutableList<String>>()

        for (a in 1..towers) {
            crates.add(mutableListOf())
        }


        input.lines().filter { it.contains("[") }
            .map { line ->
                line.chunked(4)
                    .map { it.trim().replace("]", "").replace("[", "") }
                    .mapIndexed { index, s ->
                        if (s.isNotBlank()) crates[index].add(0, s)
//                        println("$index -> $s")
                    }
            }

        val craneMovements = input.lines().filter { it.contains("move") }.map { line ->
            val a = line.split("move|from|to".toRegex()).map { it.trim() }
            Crane(a[1].toInt(), a[2].toInt(), a[3].toInt())
        }

        craneMovements.forEach { movement ->
            for (crate in 1..movement.move) {
                crates[movement.to - 1].add(crates[movement.from - 1].removeLast())
            }
        }

        return crates.map { it.last() }.joinToString("")
    }

    fun part2(input: String, debug: Boolean = false): String {
        val towers = input.lines().filter { it.startsWith(" 1 ") }.map { it.replace(" ", "") }.last().length
        val crates = mutableListOf<MutableList<String>>()

        for (a in 1..towers) {
            crates.add(mutableListOf())
        }


        input.lines().filter { it.contains("[") }
            .map { line ->
                line.chunked(4)
                    .map { it.trim().replace("]", "").replace("[", "") }
                    .mapIndexed { index, s ->
                        if (s.isNotBlank()) crates[index].add(0, s)
//                        println("$index -> $s")
                    }
            }

        val craneMovements = input.lines().filter { it.contains("move") }.map { line ->
            val a = line.split("move|from|to".toRegex()).map { it.trim() }
            Crane(a[1].toInt(), a[2].toInt(), a[3].toInt())
        }

        craneMovements.forEach { movement ->
            val list = mutableListOf<String>()
            for (crate in 1..movement.move) {
                list.add(0, crates[movement.from - 1].removeLast())
            }
            crates[movement.to - 1].addAll(list)
        }

        return crates.map { it.last() }.joinToString("")
    }

    val testInput =
        "    [D]    \n" +
                "[N] [C]    \n" +
                "[Z] [M] [P]\n" +
                " 1   2   3 \n" +
                "\n" +
                "move 1 from 2 to 1\n" +
                "move 3 from 1 to 3\n" +
                "move 2 from 2 to 1\n" +
                "move 1 from 1 to 2\n"

    val input = AoCUtils.readText("year2022/day05.txt")

    part1(testInput, false) test Pair("CMZ", "test 1 part 1")
    part1(input, false) test Pair("SHQWSRBDL", "part 1")

    part2(testInput, false) test Pair("MCD", "test 2 part 2")
    part2(input) test Pair("921L", "part 2")
}
