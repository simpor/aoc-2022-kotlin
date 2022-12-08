import AoCUtils
import AoCUtils.test

fun main() {
    data class File(val name: String, val size: Long)
    data class Folder(
        val name: String,
        val parent: Folder?,
        val files: MutableList<File> = mutableListOf(),
        val folders: MutableList<Folder> = mutableListOf(),
        var totalSize: Long = 0
    ) {
        val level: Int
        init {
            level = if (parent != null) parent.level + 1 else 0
        }
    }

    fun isCommand(line: String) = line.startsWith("$")
    fun isCd(line: String) = line.contains("cd")
    fun isLs(line: String) = line.contains("ls")

    fun printFileSystem(folder: Folder) {
        println(" ".repeat(folder.level) + folder.name + " (dir, totalSize: ${folder.totalSize})")
        folder.files.forEach { println(" ".repeat(folder.level + 1) + it.name + " (file, size: ${it.size})") }
        folder.folders.forEach { printFileSystem(it) }
    }

    fun isFolder(line: String) = line.contains("dir")

    fun countTotalSize(folder: Folder): Long {
        val fileSize = folder.files.sumOf { it.size }
        folder.totalSize = folder.folders.sumOf { countTotalSize(it) } + fileSize
        return folder.totalSize
    }

    fun sumOfFoldersWithSizeLessThen(folder: Folder, totalSize: Long): Long {
        if (folder.totalSize < totalSize) {
            return folder.totalSize + folder.folders.sumOf { sumOfFoldersWithSizeLessThen(it, totalSize) }
        } else {
            return folder.folders.sumOf { sumOfFoldersWithSizeLessThen(it, totalSize) }
        }
    }

    fun getFileSystem(input: String): Folder {
        val root = Folder("/", null)
        var currentDir = root
        var listingMode = false
        input.lines().forEach { line ->
            if (isCommand(line)) {
                if (isCd(line)) {
                    listingMode = false
                    val dir = line.split(" ").last()
                    currentDir = if (dir == "/") {
                        root
                    } else if (dir == "..") {
                        currentDir.parent!!
                    } else {
                        currentDir.folders.first { it.name == dir }
                    }
                } else if (isLs(line)) {
                    listingMode = true
                } else {
                    throw Exception("Unknown command: $line")
                }
            } else if (listingMode) {
                if (isFolder(line)) {
                    val dir = line.split(" ").last().trim()
                    if (!currentDir.folders.any { it.name == dir }) {
                        currentDir.folders.add(Folder(dir, currentDir))
                    }
                } else {
                    val split = line.split(" ")
                    val size = split[0].trim().toLong()
                    val name = split[1].trim()
                    currentDir.files.add(File(name, size))
                }
            }
        }
        countTotalSize(root)
        return root
    }

    fun part1(input: String, debug: Boolean = false): Long {
        val root = getFileSystem(input)
        printFileSystem(root)
        return sumOfFoldersWithSizeLessThen(root, 100000L)
    }

    fun listOfDirs(folder: Folder): List<Folder> {
        val folders = folder.folders
        return folders + folder.folders.map { listOfDirs(it) }.flatten()
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val root = getFileSystem(input)
        val neededSize =  30000000L - (70000000L - root.totalSize)
        val listOfDirs = listOfDirs(root)
        val size = listOfDirs.map { it.totalSize - neededSize }.filter { it > 0 }.minOf { it }
        return size + neededSize
    }

    val testInput =
        "\$ cd /\n" +
                "\$ ls\n" +
                "dir a\n" +
                "14848514 b.txt\n" +
                "8504156 c.dat\n" +
                "dir d\n" +
                "\$ cd a\n" +
                "\$ ls\n" +
                "dir e\n" +
                "29116 f\n" +
                "2557 g\n" +
                "62596 h.lst\n" +
                "\$ cd e\n" +
                "\$ ls\n" +
                "584 i\n" +
                "\$ cd ..\n" +
                "\$ cd ..\n" +
                "\$ cd d\n" +
                "\$ ls\n" +
                "4060174 j\n" +
                "8033020 d.log\n" +
                "5626152 d.ext\n" +
                "7214296 k"

    val input = AoCUtils.readText("year2022/day07.txt")

    part1(testInput, false) test Pair(95437L, "test 1 part 1")
    part1(input, false) test Pair(1443806L, "part 1")

    part2(testInput, false) test Pair(24933642L, "test 2 part 2")
    part2(input) test Pair(921L, "part 2")
}
