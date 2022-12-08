import AoCUtils.test

fun main() {
    data class Tree(val x: Int, val y: Int, val height: Int, var visible: Boolean = false, var scenic: Int = 0)

    fun part1(input: String, debug: Boolean = false): Long {
        val treeMap = input.lines().mapIndexed { y, row ->
            row.mapIndexed { x, c ->
                Point(x, y) to Tree(x, y, "$c".toInt())
            }
        }.flatten().toMap().toMutableMap()

        val maxX = treeMap.keys.map { it.x }.max()
        val maxY = treeMap.keys.map { it.y }.max()

        for (x in 0..maxX) {
            for (y in 0..maxY) {
                if (x == 0) treeMap[Point(x, y)]?.visible = true
                if (x == maxX) treeMap[Point(x, y)]?.visible = true
                if (y == 0) treeMap[Point(x, y)]?.visible = true
                if (y == maxY) treeMap[Point(x, y)]?.visible = true
            }
        }

        for (x in 0..maxX) {
            var edge = treeMap[Point(x, 0)]!!
            for (y in 0..maxY) {
                val current = treeMap[Point(x, y)]!!
                if (current.height > edge.height) {
                    current.visible = true
                    edge = current
                }
            }
        }

        for (x in 0..maxX) {
            var edge = treeMap[Point(x, maxY)]!!
            for (y in maxY downTo 0) {
                val current = treeMap[Point(x, y)]!!
                if (current.height > edge.height) {
                    current.visible = true
                    edge = current
                }
            }
        }

        for (y in 0..maxY) {
            var edge = treeMap[Point(0, y)]!!
            for (x in 0..maxX) {
                val current = treeMap[Point(x, y)]!!
                if (current.height > edge.height) {
                    current.visible = true
                    edge = current
                }
            }
        }

        for (y in 0..maxY) {
            var edge = treeMap[Point(maxX, y)]!!
            for (x in maxX downTo 0) {
                val current = treeMap[Point(x, y)]!!
                if (current.height > edge.height) {
                    current.visible = true
                    edge = current
                }
            }
        }

        return treeMap.values.filter { it.visible }.count().toLong()
    }

    fun calcScenic(treeMap: MutableMap<Point, Tree>, tree: Tree, maxY: Int, maxX: Int) {
        var prev = 0
        var yInc = 0
        for (y in (tree.y + 1)..maxY) {
            val current = treeMap[Point(tree.x, y)]!!
            if (prev != 0 && current.height > prev) {
                yInc++
                prev = current.height
            } else if (prev == 0 && current.height >= tree.height) {
                yInc = 1
                break
            } else if (prev == 0) {
                yInc++
                prev = current.height
            }
        }
        var yDesc = 0
        prev = 0
        for (y in (tree.y - 1) downTo 0) {
            val current = treeMap[Point(tree.x, y)]!!
            if (prev != 0 && current.height > prev) {
                yDesc++
                prev = current.height
            } else if (prev == 0 && current.height >= tree.height) {
                yDesc = 1
                break
            } else if (prev == 0) {
                yDesc++
                prev = current.height
            }
        }

        prev = 0
        var xInc = 0
        for (x in (tree.x + 1)..maxX) {
            val current = treeMap[Point(x, tree.y)]!!
            if (prev != 0 && current.height > prev) {
                xInc++
                prev = current.height
            } else if (prev == 0 && current.height == tree.height) {
                xInc = 1
                break
            } else if (prev == 0 && current.height >= tree.height) {
                xInc++
                prev = current.height
            }
        }
        var xDesc = 0
        prev = 0
        for (x in (tree.x - 1) downTo 0) {
            val current = treeMap[Point(x, tree.y)]!!
            if (prev != 0 && current.height > prev) {
                xDesc++
                prev = current.height
            } else if (prev == 0 && current.height >= tree.height) {
                xDesc = 1
                break
            } else if (prev == 0) {
                xDesc++
                prev = current.height
            }
        }

        tree.scenic = xInc * xDesc * yDesc * yInc
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val treeMap = input.lines().mapIndexed { y, row ->
            row.mapIndexed { x, c ->
                Point(x, y) to Tree(x, y, "$c".toInt())
            }
        }.flatten().toMap().toMutableMap()

        val maxX = treeMap.keys.map { it.x }.max()
        val maxY = treeMap.keys.map { it.y }.max()

        treeMap.values
            .filter { (it.y in 1 until maxY) && (it.x in 1 until maxX) }
            .forEach { tree ->
                calcScenic(treeMap, tree, maxY, maxX)
            }

        val first = treeMap.values.sortedBy { it.scenic }.last()
        calcScenic(treeMap, first, maxY, maxX)

        return first.scenic.toLong()
    }

    val testInput =
        "30373\n" +
                "25512\n" +
                "65332\n" +
                "33549\n" +
                "35390"

    val input = AoCUtils.readText("year2022/day08.txt")

    part1(testInput, false) test Pair(21L, "test 1 part 1")
    part1(input, false) test Pair(1672L, "part 1")

    part2(testInput, false) test Pair(8L, "test 2 part 2")
    part2(input) test Pair(921L, "part 2")
}
