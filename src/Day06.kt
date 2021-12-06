fun main() {
    /**
     * Brute-force method, works for part 1
     */
    fun bruteForce(initialState: String, days: Int): Long {
        val fish = initialState.split(",").map { it.toInt() }.toMutableList()

        repeat(days) { _ ->
            fish.replaceAll { it - 1 }
            fish.filter { it == -1 }
                .forEach { _ -> fish.add(8) }
            fish.replaceAll { if (it == -1) 6 else it }
        }
        return fish.count().toLong()
    }

    /**
     * Recursive implementation, works for part 1 + testcase of part 2
     */
    fun fishPlusOffspring(days: Int): Long {
        val numberOfOwnChildren = days / 7
        var count = 1L
        repeat(numberOfOwnChildren) {
            count += fishPlusOffspring(days - (it + 1) * 7 - 2)
        }
        return count
    }

    /**
     * Smart and performant implementation after asking for a hint
     */
    fun smartCalculate(start: List<Int>, days: Int): Long {
        var countByAge = start.groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }
            .toMutableMap()
        repeat(days) {
            countByAge = countByAge.mapKeys { it.key - 1 }.toMutableMap()
            countByAge[8] = countByAge.getOrPut(8) { 0 } + (countByAge[-1] ?: return@repeat)
            countByAge[6] = countByAge.getOrPut(6) { 0 } + (countByAge[-1] ?: return@repeat)
            countByAge.remove(-1)
        }
        return countByAge.values.sum()
    }

    fun part1(input: String): Long {
        val fish = input.split(",").map { it.toInt() }
        return smartCalculate(fish, 80)
    }

    fun part2(input: String): Long {
        val fish = input.split(",").map { it.toInt() }
        return smartCalculate(fish, 256)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test").first()
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06").first()
    println(part1(input))
    println(part2(input))
}
