import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val points = input.toPoints()
        val lowPoints = points.lowPoints()
        return lowPoints.sumOf { it.height + 1 }
    }

    fun part2(input: List<String>): Int {
        val points = input.toPoints()
        val lowPoints = points.lowPoints()
        val basins = mutableListOf<List<Point>>()

        lowPoints.forEach { lowPoint ->
            val basin = mutableListOf(lowPoint)
            var start = listOf(lowPoint)
            var neighbours: List<Point>
            do {
                neighbours = points.adjacentTo(start).filter { it.height != 9 }.filter { !basin.contains(it) }
                basin.addAll(neighbours)
                start = neighbours
            } while (neighbours.isNotEmpty())
            basins.add(basin)
        }

        return basins.map { basin ->
            basin.size
        }.sortedDescending()
            .take(3)
            .reduce { acc, i -> acc * i }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.toPoints(): List<Point> {
    val points = mutableListOf<Point>()
    forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            points.add(Point(x, y, char.digitToInt()))
        }
    }
    return points
}

private fun List<Point>.lowPoints(): List<Point> {
    return filter { point ->
        val left = firstOrNull { left -> (left.x == point.x - 1) && (left.y == point.y) }
        val right = firstOrNull { right -> (right.x == point.x + 1) && (right.y == point.y) }
        val up = firstOrNull { up -> (up.x == point.x) && (up.y == point.y - 1) }
        val down = firstOrNull { down -> (down.x == point.x) && (down.y == point.y + 1) }

        (left?.height?.compareTo(point.height) ?: 1) > 0
                && (right?.height?.compareTo(point.height) ?: 1) > 0
                && (up?.height?.compareTo(point.height) ?: 1) > 0
                && (down?.height?.compareTo(point.height) ?: 1) > 0
    }
}

private fun List<Point>.adjacentTo(targets: List<Point>) =
    targets.flatMap { target ->
        filter { point ->
            (abs(point.x - target.x) == 1 && point.y == target.y)
                    || (abs(point.y - target.y) == 1 && point.x == target.x)
        }
    }.distinct()


private data class Point(
    val x: Int,
    val y: Int,
    val height: Int,
)
