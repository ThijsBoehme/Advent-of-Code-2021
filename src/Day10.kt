fun main() {
    fun part1(input: List<String>): Int {
        val scores = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137,
        )

        return input.sumOf { line ->
            var stripped: String
            var result = line
            do {
                stripped = result
                result = stripped.removeChunks()
            } while (result != stripped)

            val firstIncorrectCharacter = result.firstOrNull {
                it == ')'
                        || it == ']'
                        || it == '}'
                        || it == '>'
            }
            firstIncorrectCharacter ?: return@sumOf 0
            scores[firstIncorrectCharacter] ?: 0
        }
    }

    fun part2(input: List<String>): Long {
        val scores = mapOf(
            ')' to 1,
            ']' to 2,
            '}' to 3,
            '>' to 4,
        )

        val notCorrupt = input.map { line ->
            var stripped: String
            var result = line
            do {
                stripped = result
                result = stripped.removeChunks()
            } while (result != stripped)
            result
        }.filter { !it.contains("""[)\]}>]""".toRegex()) }

        val points = notCorrupt.map { line ->
            line.reversed()
                .map {
                    when (it) {
                        '(' -> ')'
                        '[' -> ']'
                        '{' -> '}'
                        '<' -> '>'
                        else -> throw IllegalStateException()
                    }
                }
                .fold(0L) { acc, c -> acc * 5 + (scores[c] ?: throw IllegalStateException()) }
        }.sorted()
        return points[(points.size - 1) / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

private fun String.removeChunks(): String {
    return replace("()", "")
        .replace("[]", "")
        .replace("{}", "")
        .replace("<>", "")
}
