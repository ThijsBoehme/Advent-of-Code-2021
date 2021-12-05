fun main() {
    fun part1(input: List<String>): Int {
        val grid = mutableMapOf<Coordinate, Int>()
        input.forEach { line ->
            val (start, end) = line.split(" -> ")
                .map { coordinates -> coordinates.split(",").map { it.toInt() } }
                .map { (x, y) -> Coordinate(x, y) }

            if (start.x != end.x && start.y != end.y) return@forEach

            when {
                start.x == end.x -> {
                    yRange(start, end).forEach { i ->
                        grid[Coordinate(start.x, i)] = grid.getOrPut(Coordinate(start.x, i)) { 0 } + 1
                    }
                }
                start.y == end.y -> {
                    xRange(start, end).forEach { i ->
                        grid[Coordinate(i, start.y)] = grid.getOrPut(Coordinate(i, start.y)) { 0 } + 1
                    }
                }
                else -> return@forEach
            }
        }
        return grid.filter { it.value >= 2 }
            .count()
    }

    fun part2(input: List<String>): Int {
        val grid = mutableMapOf<Coordinate, Int>()
        input.forEach { line ->
            val (start, end) = line.split(" -> ")
                .map { coordinates -> coordinates.split(",").map { it.toInt() } }
                .map { (x, y) -> Coordinate(x, y) }

            when {
                start.x == end.x -> {
                    yRange(start, end).forEach { i ->
                        grid[Coordinate(start.x, i)] = grid.getOrPut(Coordinate(start.x, i)) { 0 } + 1
                    }
                }
                start.y == end.y -> {
                    xRange(start, end).forEach { i ->
                        grid[Coordinate(i, start.y)] = grid.getOrPut(Coordinate(i, start.y)) { 0 } + 1
                    }
                }
                else -> {
                    val xRange = xRange(start, end)
                    val yRange = yRange(start, end)

                    xRange.zip(yRange).forEach { (x, y) ->
                        grid[Coordinate(x, y)] = grid.getOrPut(Coordinate(x, y)) { 0 } + 1
                    }
                }
            }
        }
        return grid.filter { it.value >= 2 }
            .count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private data class Coordinate(
    val x: Int,
    val y: Int,
)

private fun xRange(start: Coordinate, end: Coordinate): IntProgression =
    when {
        start.x <= end.x -> start.x..end.x
        else -> start.x downTo end.x
    }

private fun yRange(start: Coordinate, end: Coordinate): IntProgression =
    when {
        start.y <= end.y -> start.y..end.y
        else -> start.y downTo end.y
    }
