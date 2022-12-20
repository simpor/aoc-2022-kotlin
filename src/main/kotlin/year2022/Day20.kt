import AoCUtils.test

fun main() {
    data class LinkedList(val value: Long) {
        lateinit var prev: LinkedList
        lateinit var next: LinkedList
    }

    fun debugPrintOrder(num1: LinkedList, c: String) {
        var toPrint = num1.next
        println("Looking at: " + c)
        print(num1.value)
        while (num1 != toPrint) {
            print(", " + toPrint.value)
            toPrint = toPrint.next
        }
        println()
    }

    fun part1(input: String, debug: Boolean = false): Long {
        val list = input.lines().map { it.toLong() }
            .map { LinkedList(it) }
        for (i in 1 until list.size) {
            val prev = list[i - 1]
            val current = list[i]
            prev.next = current
            current.prev = prev
        }
        list.first().prev = list.last()
        list.last().next = list.first()
        if (debug) debugPrintOrder(list.first(), "initial")

        list.forEach { c ->
            var value = c.value
            while (value != 0L) {
                if (value < 0) {
                    val a = c.prev.prev
                    val b = c.prev
                    val d = c.next

                    a.next = c
                    c.next = b
                    b.next = d

                    c.prev = a
                    b.prev = c
                    d.prev = b

                    value++
                } else {
                    val b = c.prev
                    val d = c.next
                    val e = c.next.next

                    b.next = d
                    d.next = c
                    c.next = e

                    d.prev = b
                    c.prev = d
                    e.prev = c

                    value--
                }
            }

            val num1 = list.first()
            if (debug) debugPrintOrder(num1, c.value.toString())


        }


        val num0 = list.filter { it.value == 0L }.first()

        var loopis = num0.next
        var counter = 1
        val resultList = mutableListOf<Long>()

        while (counter <= 3000) {
            counter++
            loopis = loopis.next
            if (counter % 1000 == 0) {
                resultList.add(loopis.value)
            }
        }

        return resultList.sum()
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val list = input.lines().map { it.toLong() * 811589153 }
            .map { LinkedList(it) }
        for (i in 1 until list.size) {
            val prev = list[i - 1]
            val current = list[i]
            prev.next = current
            current.prev = prev
        }
        list.first().prev = list.last()
        list.last().next = list.first()
        if (debug) debugPrintOrder(list.first(), "initial")

        repeat(10) { repeatNum ->
            list.forEach { c ->
                var value = c.value % list.size
                while (value != 0L) {
                    if (value < 0) {
                        val a = c.prev.prev
                        val b = c.prev
                        val d = c.next

                        a.next = c
                        c.next = b
                        b.next = d

                        c.prev = a
                        b.prev = c
                        d.prev = b

                        value++
                    } else {
                        val b = c.prev
                        val d = c.next
                        val e = c.next.next

                        b.next = d
                        d.next = c
                        c.next = e

                        d.prev = b
                        c.prev = d
                        e.prev = c

                        value--
                    }
                }

            }

            val num1 = list.first { it.value == 0L }
            if (debug) debugPrintOrder(num1, repeatNum.toString())
        }


        val num0 = list.first { it.value == 0L }

        var loopis = num0.next
        var counter = 1
        val resultList = mutableListOf<Long>()

        while (counter <= 3000) {
            counter++
            loopis = loopis.next
            if (counter % 1000 == 0) {
                resultList.add(loopis.value)
            }
        }

        return resultList.sum()
    }

    val testInput =
        "1\n" +
                "2\n" +
                "-3\n" +
                "3\n" +
                "-2\n" +
                "0\n" +
                "4"

    val input = AoCUtils.readText("year2022/day20.txt")

    part1(testInput, false) test Pair(3L, "test 1 part 1")
    part1(input, false) test Pair(4224L, "part 1")

    part2(testInput, true) test Pair(1623178306L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")
}
