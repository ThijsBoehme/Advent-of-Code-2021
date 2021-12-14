fun main() {
    fun polymerisation(input: List<String>, steps: Int): Long {
        val polymer = input.first()
        val substitutions = input.drop(2)
            .associate { line ->
                val split = line.split("->").map { it.trim() }
                split[0] to listOf(
                    "${split[0][0]}${split[1]}",
                    "${split[1]}${split[0][1]}",
                )
            }

        var pairOccurrences = polymer.zipWithNext()
            .groupingBy { "${it.first}${it.second}" }
            .eachCount()
            .mapValues { it.value.toLong() }

        repeat(steps) {
            val next = mutableMapOf<String, Long>()
            pairOccurrences.forEach { (pair, count) ->
                substitutions[pair]?.forEach {
                    next[it] = next.getOrDefault(it, 0L) + count
                }
            }
            pairOccurrences = next
        }

        val characterOccurrences = pairOccurrences.keys
            .flatMap { it.toList() }
            .toSet()
            .associateWith { character ->
                pairOccurrences.filterKeys { it.first() == character }
                    .values
                    .sum()
            }.toMutableMap()

        characterOccurrences[polymer.last()] = characterOccurrences.getOrDefault(polymer.last(), 0L) + 1

        return characterOccurrences.maxOf { it.value } - characterOccurrences.minOf { it.value }
    }

    fun part1(input: List<String>) = polymerisation(input, 10)

    fun part2(input: List<String>) = polymerisation(input, 40)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588L)
    check(part2(testInput) == 2188189693529)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
