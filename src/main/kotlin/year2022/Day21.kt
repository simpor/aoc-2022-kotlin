import AoCUtils.test
import java.lang.Exception

enum class Operation { PLUS, MINUS, TIMES, DIVIDE }

fun main() {
    open class Monkey
    data class MonkeyValue(val name: String, val value: Long) : Monkey()
    data class MonkeyOperation(val name: String, val first: String, val second: String, val operation: Operation) :
        Monkey()

    fun getValueFor(name: String, monkeyMap: Map<String, Monkey>): Long {
        val monkey = monkeyMap[name]
        return when (monkey) {
            is MonkeyValue -> monkey.value
            is MonkeyOperation -> when (monkey.operation) {
                Operation.PLUS -> getValueFor(monkey.first, monkeyMap) + getValueFor(monkey.second, monkeyMap)
                Operation.MINUS -> getValueFor(monkey.first, monkeyMap) - getValueFor(monkey.second, monkeyMap)
                Operation.TIMES -> getValueFor(monkey.first, monkeyMap) * getValueFor(monkey.second, monkeyMap)
                Operation.DIVIDE -> getValueFor(monkey.first, monkeyMap) / getValueFor(monkey.second, monkeyMap)
            }

            else -> throw Exception("nope")
        }
    }

    fun parse(input: String): Map<String, Monkey> {
        val monkeys = input.lines().map { line ->
            val split1 = line.split(":")
            val name = split1[0].trim()
            val num = split1[1].trim().toLongOrNull()
            if (num != null) {
                MonkeyValue(name, num)
            } else {
                val split2 = split1[1].trim().split(" ")
                MonkeyOperation(
                    name, split2[0], split2[2], when (split2[1]) {
                        "+" -> Operation.PLUS
                        "-" -> Operation.MINUS
                        "*" -> Operation.TIMES
                        "/" -> Operation.DIVIDE
                        else -> throw Exception("nope")
                    }
                )
            }
        }

        val monkeyMap = monkeys.map {
            when (it) {
                is MonkeyValue -> Pair(it.name, it)
                is MonkeyOperation -> Pair(it.name, it)
                else -> throw Exception("nope")
            }
        }.toMap()
        return monkeyMap
    }

    fun part1(input: String, debug: Boolean = false): Long {
        val monkeyMap = parse(input)
        val result = getValueFor("root", monkeyMap)
        return result
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val monkeyMap = parse(input)
        val human = "humn"
        return 0L
    }

    val testInput =
        "root: pppw + sjmn\n" +
                "dbpl: 5\n" +
                "cczh: sllz + lgvd\n" +
                "zczc: 2\n" +
                "ptdq: humn - dvpt\n" +
                "dvpt: 3\n" +
                "lfqf: 4\n" +
                "humn: 5\n" +
                "ljgn: 2\n" +
                "sjmn: drzm * dbpl\n" +
                "sllz: 4\n" +
                "pppw: cczh / lfqf\n" +
                "lgvd: ljgn * ptdq\n" +
                "drzm: hmdt - zczc\n" +
                "hmdt: 32"

    val input = AoCUtils.readText("year2022/day21.txt")

    part1(testInput, false) test Pair(152L, "test 1 part 1")
    part1(input, false) test Pair(118565889858886L, "part 1")

    part2(testInput, false) test Pair(301L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")
}
