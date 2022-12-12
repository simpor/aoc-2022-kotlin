import AoCUtils
import AoCUtils.test


enum class OperationValue { PLUS, TIMES, SQUARE }

fun main() {
    data class Monkey(
        val id: Int,
        val operation: OperationValue,
        val operationValue: Int,
        val testValue: Int,
        val testTrue: Int,
        val testFalse: Int,
        var count: Int = 0,
        val items: MutableList<Int> = mutableListOf(),
    )

    fun part1(input: String, debug: Boolean = false): Long {
        val monkeys = input.split("\n\n").map { monkeyLines ->
            val lines = monkeyLines.lines()
            val id = lines[0].replace("Monkey ", "").replace(":", "").toInt()
            val items = lines[1].trim().replace("Starting items: ", "").split(",").map { it.trim().toInt() }
            val operationValue =
                if (!lines[2].contains("old * old")) lines[2].trim().replace("Operation: new = old * ", "")
                    .replace("Operation: new = old + ", "").toInt() else 0
            val operationPlus = if (lines[2].contains("old * old")) OperationValue.SQUARE else if (lines[2].trim()
                    .contains("+")
            ) OperationValue.PLUS else OperationValue.TIMES
            val testValue = lines[3].trim().replace("Test: divisible by ", "").toInt()
            val testTrue = lines[4].trim().replace("If true: throw to monkey ", "").toInt()
            val testfalse = lines[5].trim().replace("If false: throw to monkey ", "").toInt()

            Pair(
                id,
                Monkey(id, operationPlus, operationValue, testValue, testTrue, testfalse, items = items.toMutableList())
            )
        }.toMap()

        repeat(20) {
            monkeys.values.forEach { monkey ->
                monkey.items.forEach { item ->
                    monkey.count += 1
                    val worrieLevel = when (monkey.operation) {
                        OperationValue.PLUS -> item + monkey.operationValue
                        OperationValue.TIMES -> item * monkey.operationValue
                        OperationValue.SQUARE -> item * item
                    } / 3
                    val test = worrieLevel % monkey.testValue == 0
                    if (test) {
                        monkeys[monkey.testTrue]!!.items.add(worrieLevel)
                    } else {
                        monkeys[monkey.testFalse]!!.items.add(worrieLevel)
                    }

                }
                monkey.items.clear()
            }
        }
        monkeys.values.forEach { println(it) }

        return monkeys.values.map { it.count }.sortedDescending().take(2).fold(1) { acc, i -> acc * i }
    }

    fun part2(input: String, debug: Boolean = false): Long {
        return 0L
    }

    val testInput =
        "Monkey 0:\n" +
                "  Starting items: 79, 98\n" +
                "  Operation: new = old * 19\n" +
                "  Test: divisible by 23\n" +
                "    If true: throw to monkey 2\n" +
                "    If false: throw to monkey 3\n" +
                "\n" +
                "Monkey 1:\n" +
                "  Starting items: 54, 65, 75, 74\n" +
                "  Operation: new = old + 6\n" +
                "  Test: divisible by 19\n" +
                "    If true: throw to monkey 2\n" +
                "    If false: throw to monkey 0\n" +
                "\n" +
                "Monkey 2:\n" +
                "  Starting items: 79, 60, 97\n" +
                "  Operation: new = old * old\n" +
                "  Test: divisible by 13\n" +
                "    If true: throw to monkey 1\n" +
                "    If false: throw to monkey 3\n" +
                "\n" +
                "Monkey 3:\n" +
                "  Starting items: 74\n" +
                "  Operation: new = old + 3\n" +
                "  Test: divisible by 17\n" +
                "    If true: throw to monkey 0\n" +
                "    If false: throw to monkey 1"

    val input = AoCUtils.readText("year2022/day11.txt")

    part1(testInput, false) test Pair(10605L, "test 1 part 1")
    part1(input, false) test Pair(0L, "part 1")

    part2(testInput, false) test Pair(0L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")
}
