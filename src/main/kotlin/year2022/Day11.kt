import AoCUtils
import AoCUtils.test


enum class OperationValue { PLUS, TIMES, SQUARE }

fun main() {
    data class Monkey(
        val id: Long,
        val operation: OperationValue,
        val operationValue: Long,
        val testValue: Long,
        val testTrue: Long,
        val testFalse: Long,
        var count: Long = 0,
        val items: MutableList<Long> = mutableListOf(),
    )

    fun monkeyMap(input: String): Map<Long, Monkey> {
        val monkeys = input.split("\n\n").map { monkeyLines ->
            val lines = monkeyLines.lines()
            val id = lines[0].replace("Monkey ", "").replace(":", "").toLong()
            val items = lines[1].trim().replace("Starting items: ", "").split(",").map { it.trim().toLong() }
            val operationValue =
                if (!lines[2].contains("old * old")) lines[2].trim().replace("Operation: new = old * ", "")
                    .replace("Operation: new = old + ", "").toLong() else 0
            val operationPlus = if (lines[2].contains("old * old")) OperationValue.SQUARE else if (lines[2].trim()
                    .contains("+")
            ) OperationValue.PLUS else OperationValue.TIMES
            val testValue = lines[3].trim().replace("Test: divisible by ", "").toLong()
            val testTrue = lines[4].trim().replace("If true: throw to monkey ", "").toLong()
            val testfalse = lines[5].trim().replace("If false: throw to monkey ", "").toLong()

            Pair(
                id,
                Monkey(id, operationPlus, operationValue, testValue, testTrue, testfalse, items = items.toMutableList())
            )
        }.toMap()
        return monkeys
    }

    fun part1(input: String, debug: Boolean = false): Long {
        val monkeys = monkeyMap(input)

        repeat(20) {
            monkeys.values.forEach { monkey ->
                monkey.items.forEach { item ->
                    monkey.count += 1
                    val worrieLevel = when (monkey.operation) {
                        OperationValue.PLUS -> item + monkey.operationValue
                        OperationValue.TIMES -> item * monkey.operationValue
                        OperationValue.SQUARE -> item * item
                    } / 3
                    val test = worrieLevel % monkey.testValue == 0L
                    if (test) {
                        monkeys[monkey.testTrue]!!.items.add(worrieLevel)
                    } else {
                        monkeys[monkey.testFalse]!!.items.add(worrieLevel)
                    }

                }
                monkey.items.clear()
            }
        }
        return monkeys.values.map { it.count }.sortedDescending().take(2).fold(1) { acc, i -> acc * i }
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val monkeys = monkeyMap(input)
        val lcd = monkeys.values.map { it.testValue }.fold(1L) {acc, l -> acc * l }

        repeat(10000) {
            monkeys.values.forEach { monkey ->
                monkey.items.forEach { item ->
                    monkey.count += 1
                    val worrieLevel = when (monkey.operation) {
                        OperationValue.PLUS -> item + monkey.operationValue
                        OperationValue.TIMES -> item * monkey.operationValue
                        OperationValue.SQUARE -> item * item
                    } % lcd
                    val test = worrieLevel % monkey.testValue == 0L

                    if (test) {
                        monkeys[monkey.testTrue]!!.items.add(worrieLevel)
                    } else {
                        monkeys[monkey.testFalse]!!.items.add(worrieLevel)
                    }

                }
                monkey.items.clear()
            }
        }
        return monkeys.values.map { it.count }.sortedDescending().take(2).fold(1) { acc, i -> acc * i }
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
    part1(input, false) test Pair(56350L, "part 1")

    part2(testInput, false) test Pair(2713310158L, "test 2 part 2")
    part2(input) test Pair(13954061248L, "part 2")
}
