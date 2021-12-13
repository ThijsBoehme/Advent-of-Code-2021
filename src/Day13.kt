fun main() {
    fun parse(input: List<String>): Pair<MutableSet<Pair<Int, Int>>, List<Pair<String, Int>>> {
        val coordinates = input.takeWhile { it.isNotBlank() }
        val set = coordinates.map { it.split(",") }
            .map { (x, y) -> x.toInt() to y.toInt() }
            .toMutableSet()

        val instructions = input.filter { it.startsWith("fold along") }
            .map {
                it.removePrefix("fold along ")
                    .split("=")
            }
            .map { (a, b) -> a to b.toInt() }

        return set to instructions
    }

    fun MutableSet<Pair<Int, Int>>.doFold(direction: String, offset: Int) {
        when (direction) {
            "x" -> {
                addAll(
                    filter { it.first > offset }
                        .map { 2 * offset - it.first to it.second }
                )
                removeIf { it.first > offset }
            }
            "y" -> {
                addAll(
                    filter { it.second > offset }
                        .map { it.first to 2 * offset - it.second }
                )
                removeIf { it.second > offset }
            }
            else -> throw IllegalStateException()
        }
    }

    fun part1(input: List<String>): Int {
        val (set, instructions) = parse(input)
        val (direction, offset) = instructions.first()

        set.doFold(direction, offset)
        return set.size
    }

    fun part2(input: List<String>) {
        val (set, instructions) = parse(input)

        instructions.forEach { (direction, offset) ->
            set.doFold(direction, offset)
        }

        (0..set.maxOf { it.second }).forEach { y ->
            (0..set.maxOf { it.first }).forEach { x ->
                if (set.contains(x to y)) print("#") else print(" ")
            }
            println()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    part2(testInput)

    val input = readInput("Day13")
    println(part1(input))
    part2(input)
}
