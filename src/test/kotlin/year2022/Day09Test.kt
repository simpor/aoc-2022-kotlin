package year2022

import Day09
import Point
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day09Test : FunSpec({
    val day = Day09()
    context("Part 1") {
        test("Test case") {
            day.part1(day.testInput()) shouldBe 13L
        }
        test("Input") {
            day.part1(day.input()) shouldBe 5858L
        }
    }
    context("Part 2") {
        test("Test case 1") {
            day.part2(day.testInput()) shouldBe 1L
        }
        test("Test case 2") {
            day.part2(
                "R 5\n" +
                        "U 8\n" +
                        "L 8\n" +
                        "D 3\n" +
                        "R 17\n" +
                        "D 10\n" +
                        "L 25\n" +
                        "U 20"
            ) shouldBe 36L
        }
        test("Input") {
            day.part2(day.input()) shouldBe 2602L
        }
    }
    context("move snake tests") {
        val moveU = day.moveFunction("U")
        val moveD = day.moveFunction("D")
        val moveL = day.moveFunction("L")
        val moveR = day.moveFunction("R")
        test("U2") {
            val range = 0..1
            val snake = range.associateWith { Point(0, 0) }.toMutableMap()
            doMovement(range, day, snake, moveU)
            snake[0] shouldBe Point(0, 1)
            snake[1] shouldBe Point(0, 0)
            doMovement(range, day, snake, moveU)
            snake[0] shouldBe Point(0, 2)
            snake[1] shouldBe Point(0, 1)
        }
        test("D2") {
            val range = 0..1
            val snake = range.associateWith { Point(0, 0) }.toMutableMap()
            doMovement(range, day, snake, moveD)
            snake[0] shouldBe Point(0, -1)
            snake[1] shouldBe Point(0, 0)
            doMovement(range, day, snake, moveD)
            snake[0] shouldBe Point(0, -2)
            snake[1] shouldBe Point(0, -1)
        }
        test("R2") {
            val range = 0..1
            val snake = range.associateWith { Point(0, 0) }.toMutableMap()
            doMovement(range, day, snake, moveR)
            snake[0] shouldBe Point(1, 0)
            snake[1] shouldBe Point(0, 0)
            doMovement(range, day, snake, moveR)
            snake[0] shouldBe Point(2, 0)
            snake[1] shouldBe Point(1, 0)
        }
        test("L2") {
            val range = 0..1
            val snake = range.associateWith { Point(0, 0) }.toMutableMap()
            doMovement(range, day, snake, moveL)
            snake[0] shouldBe Point(-1, 0)
            snake[1] shouldBe Point(0, 0)
            doMovement(range, day, snake, moveL)
            snake[0] shouldBe Point(-2, 0)
            snake[1] shouldBe Point(-1, 0)
        }
        test("R1-U2") {
            val range = 0..1
            val snake = range.associateWith { Point(0, 0) }.toMutableMap()
            doMovement(range, day, snake, moveR)
            doMovement(range, day, snake, moveU)
            snake[0] shouldBe Point(1, 1)
            snake[1] shouldBe Point(0, 0)
            doMovement(range, day, snake, moveU)
            snake[0] shouldBe Point(1, 2)
            snake[1] shouldBe Point(1, 1)
        }
        test("L1-U2") {
            val range = 0..1
            val snake = range.associateWith { Point(0, 0) }.toMutableMap()
            doMovement(range, day, snake, moveL)
            doMovement(range, day, snake, moveU)
            snake[0] shouldBe Point(-1, 1)
            snake[1] shouldBe Point(0, 0)
            doMovement(range, day, snake, moveU)
            snake[0] shouldBe Point(-1, 2)
            snake[1] shouldBe Point(-1, 1)
        }
        test("U1-R2") {
            val range = 0..1
            val snake = range.associateWith { Point(0, 0) }.toMutableMap()
            doMovement(range, day, snake, moveU)
            doMovement(range, day, snake, moveR)
            snake[0] shouldBe Point(1, 1)
            snake[1] shouldBe Point(0, 0)
            doMovement(range, day, snake, moveR)
            snake[0] shouldBe Point(2, 1)
            snake[1] shouldBe Point(1, 1)
        }
        test("U1-L2") {
            val range = 0..1
            val snake = range.associateWith { Point(0, 0) }.toMutableMap()
            doMovement(range, day, snake, moveU)
            doMovement(range, day, snake, moveL)
            snake[0] shouldBe Point(-1, 1)
            snake[1] shouldBe Point(0, 0)
            doMovement(range, day, snake, moveL)
            snake[0] shouldBe Point(-2, 1)
            snake[1] shouldBe Point(-1, 1)
        }
    }
})

private fun doMovement(
    range: IntRange,
    day: Day09,
    snake: MutableMap<Int, Point>,
    moveU: (Point) -> Point
) {
    range.forEach { key ->
        val newPos = day.moveSnake(snake, key, moveU).second
        snake[key] = newPos
    }
}