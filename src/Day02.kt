fun main() {
    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0

        input.map { it.split(" ".toRegex(), 2) }
            .map { Pair(it[0], it[1].toInt()) }
            .forEach { (command, x) ->
                when (command) {
                    "forward" -> horizontal += x
                    "down" -> depth += x
                    "up" -> depth -= x
                    else -> {}
                }
            }
        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0

        input.map { it.split(" ".toRegex(), 2) }
            .map { Pair(it[0], it[1].toInt()) }
            .forEach { (command, x) ->
                when (command) {
                    "forward" -> {
                        horizontal += x
                        depth += aim * x
                    }
                    "down" -> aim += x
                    "up" -> aim -= x
                    else -> {}
                }
            }
        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
