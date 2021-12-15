import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>, repeats: Int = 1): List<Chiton> {
        val tile = input.flatMapIndexed { y, line ->
            line.mapIndexed { x, c ->
                Chiton(x, y, c.digitToInt())
            }
        }
        val sizeX = tile.maxOf { it.x } + 1
        val sizeY = tile.maxOf { it.y } + 1
        val grid = mutableListOf<Chiton>()
        repeat(repeats) { x ->
            repeat(repeats) { y ->
                grid.addAll(
                    tile.map {
                        Chiton(
                            it.x + x * sizeX,
                            it.y + y * sizeY,
                            (it.threat + x + y - 1) % 9 + 1
                        )
                    }
                )
            }
        }
        return grid
    }

    fun part1(input: List<String>): Int {
        val grid = parseInput(input).toMutableSet()
        return grid.leastThreateningPath()
    }

    fun part2(input: List<String>): Int {
        val grid = parseInput(input, 5).toMutableSet()
        return grid.leastThreateningPath()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}

private fun MutableSet<Chiton>.leastThreateningPath(): Int {
    val start = first { it.x == 0 && it.y == 0 }
    start.cumulativeThreat = 0
    val target = first { chiton -> chiton.x == maxOf { it.x } && chiton.y == maxOf { it.y } }

    val path = mutableListOf<Chiton>()
    while (isNotEmpty()) {
        val u = minByOrNull { it.cumulativeThreat }!!
        removeIf { it == u }
        if (u.x == target.x && u.y == target.y) {
            return target.cumulativeThreat
        }
        neighboursOf(u).forEach {
            val alt = u.cumulativeThreat + it.threat
            if (alt < it.cumulativeThreat) {
                it.cumulativeThreat = alt
                path.add(u)
            }
        }
    }
    throw IllegalStateException()
}

private fun Collection<Chiton>.neighboursOf(last: Chiton): List<Chiton> =
    filter {
        (abs(it.x - last.x) == 1 && it.y == last.y)
                || (it.x == last.x && abs(it.y - last.y) == 1)
    }

private data class Chiton(
    val x: Int,
    val y: Int,
    val threat: Int,
    var cumulativeThreat: Int = Int.MAX_VALUE,
)
