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


    fun look(height: Int, trees: MutableList<Int>): Int {
        if (trees.size == 0) return 0
        var t = 0
        trees.forEach { h ->
            if (h >= height) {
                t++
                return t
            } else {
                t++
            }
        }
        return t
    }

    fun calcScenic(treeMap: MutableMap<Point, Tree>, tree: Tree, maxX: Int, maxY: Int) {
        val yInc = mutableListOf<Int>()
        val yDesc = mutableListOf<Int>()
        val xInc = mutableListOf<Int>()
        val xDesc = mutableListOf<Int>()

        for (y in (tree.y + 1)..maxY) {
            val current = treeMap[Point(tree.x, y)]!!
            yInc.add(current.height)
        }

        for (y in (tree.y - 1) downTo 0) {
            val current = treeMap[Point(tree.x, y)]!!
            yDesc.add(current.height)
        }

        for (x in (tree.x + 1)..maxX) {
            val current = treeMap[Point(x, tree.y)]!!
            xInc.add(current.height)
        }

        for (x in (tree.x - 1) downTo 0) {
            val current = treeMap[Point(x, tree.y)]!!
            xDesc.add(current.height)
        }


        val xPlus = look(tree.height, xInc)
        val xMinus = look(tree.height, xDesc)
        val yMinus = look(tree.height, yDesc)
        val yPlus = look(tree.height, yInc)


        tree.scenic = xPlus * xMinus * yMinus * yPlus

        if (tree.height == 9) {
            if (tree.y == 29) {
                // println("hej")
            }
        }
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
                calcScenic(treeMap, tree, maxX, maxY)
            }

        val first = treeMap.values.sortedBy { it.scenic }.last()
//        calcScenic(treeMap, first, maxX, maxY)

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
    part2(input) test Pair(327180L, "part 2") // no 350
}

