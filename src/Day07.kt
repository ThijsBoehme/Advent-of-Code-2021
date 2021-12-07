import kotlin.math.abs

fun main() {
    fun part1(input: String): Int {
        val positions = input.split(",").map { it.toInt() }
        return (positions.minOrNull()!!..positions.maxOrNull()!!)
            .minOf { i ->
                positions.sumOf { abs(it - i) }
            }
    }

    fun part2(input: String): Int {
        val positions = input.split(",").map { it.toInt() }
        return (positions.minOrNull()!!..positions.maxOrNull()!!)
            .minOf { i ->
                positions.sumOf { (0..abs(it - i)).sum() }
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test").first()
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07").first()
    println(part1(input))
    println(part2(input))
}
