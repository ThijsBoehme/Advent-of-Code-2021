import kotlin.math.abs

fun main() {
    fun part1(input: List<String>, steps: Int): Int {
        val octopuses = input.flatMapIndexed { y: Int, line: String ->
            line.mapIndexed { x, c -> Octopus(x, y, c.digitToInt()) }
        }

        var flashes = 0
        repeat(steps) {
            octopuses.forEach { it.energy++ }
            while (octopuses.any { it.energy > 9 && !it.flashed }) {
                val flasher = octopuses.first { it.energy > 9 && !it.flashed }
                flasher.flashed = true
                flashes++
                octopuses.filter {
                    abs(it.x - flasher.x) <= 1 && abs(it.y - flasher.y) <= 1
                }.forEach { it.energy++ }
            }

            octopuses.filter { it.flashed }
                .forEach { it.energy = 0; it.flashed = false }
        }

        return flashes
    }

    fun part2(input: List<String>): Int {
        val octopuses = input.flatMapIndexed { y: Int, line: String ->
            line.mapIndexed { x, c -> Octopus(x, y, c.digitToInt()) }
        }

        var step = 1
        while (true) {
            octopuses.forEach { it.energy++ }
            while (octopuses.any { it.energy > 9 && !it.flashed }) {
                val flasher = octopuses.first { it.energy > 9 && !it.flashed }
                flasher.flashed = true
                octopuses.filter {
                    abs(it.x - flasher.x) <= 1 && abs(it.y - flasher.y) <= 1
                }.forEach { it.energy++ }
            }

            octopuses.filter { it.flashed }
                .forEach { it.energy = 0; it.flashed = false }

            if (octopuses.all { it.energy == 0 }) return step
            step++
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput, 100) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input, 100))
    println(part2(input))
}

private data class Octopus(
    val x: Int,
    val y: Int,
    var energy: Int,
    var flashed: Boolean = false,
)
