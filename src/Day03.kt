fun main() {
    fun mostCommonAtIndex(input: List<String>, index: Int, tieBreaker: Char): Char {
        val frequencies = input.map { it[index] }
            .groupingBy { it }
            .eachCount()
        if (frequencies['0'] == frequencies['1']) return tieBreaker
        return frequencies.maxByOrNull { it.value }!!.key
    }

    fun leastCommonAtIndex(input: List<String>, index: Int, tieBreaker: Char): Char {
        val frequencies = input.map { it[index] }
            .groupingBy { it }
            .eachCount()
        if (frequencies['0'] == frequencies['1']) return tieBreaker
        return frequencies.minByOrNull { it.value }!!.key
    }

    fun part1(input: List<String>): Int {
        var gammaString = ""
        for (i in 0 until input.first().length) {
            gammaString = gammaString.plus(mostCommonAtIndex(input, i, '0'))
        }

        val max = "1".repeat(input.first().length).toInt(2)
        val gamma = gammaString.toInt(2)
        val epsilon = max - gamma

        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        var filteredInput = input
        for (i in 0 until input.first().length) {
            filteredInput = filteredInput.filter { it[i] == mostCommonAtIndex(filteredInput, i, '1') }
        }
        val oxygen = filteredInput.first().toInt(2)

        filteredInput = input
        for (i in 0 until input.first().length) {
            filteredInput = filteredInput.filter { it[i] == leastCommonAtIndex(filteredInput, i, '0') }
        }
        val co2 = filteredInput.first().toInt(2)
        return oxygen * co2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
