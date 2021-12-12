fun main() {
    fun parseInput(input: List<String>): MutableMap<String, MutableList<String>> {
        val connections = mutableMapOf<String, MutableList<String>>()

        input.forEach { line ->
            val split = line.split("-")
            connections.computeIfAbsent(split[0]) { mutableListOf() }.add(split[1])
            connections.computeIfAbsent(split[1]) { mutableListOf() }.add(split[0])
        }
        return connections
    }

    fun countPaths(
        paths: MutableList<String> = mutableListOf("start"),
        connections: Map<String, List<String>>,
        allowTwice: Boolean
    ): Int {
        if (paths.last() == "end") return 1

        return connections[paths.last()]
            ?.filter { it != "start" }
            ?.sumOf { neighbour ->
                if (neighbour == neighbour.uppercase() || !paths.contains(neighbour)) {
                    paths.add(neighbour)
                    val count = countPaths(paths, connections, allowTwice)
                    paths.removeLast()
                    count
                } else if (allowTwice && neighbour != "end") {
                    paths.add(neighbour)
                    val count = countPaths(paths, connections, false)
                    paths.removeLast()
                    count
                } else 0
            } ?: 0
    }

    fun part1(input: List<String>): Int {
        val connections = parseInput(input)
        return countPaths(connections = connections, allowTwice = false)
    }

    fun part2(input: List<String>): Int {
        val connections = parseInput(input)
        return countPaths(connections = connections, allowTwice = true)
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day12_test1")
    val testInput2 = readInput("Day12_test2")
    val testInput3 = readInput("Day12_test3")
    check(part1(testInput1) == 10)
    check(part1(testInput2) == 19)
    check(part1(testInput3) == 226)
    check(part2(testInput1) == 36)
    check(part2(testInput2) == 103)
    check(part2(testInput3) == 3509)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
