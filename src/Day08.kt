fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            line.split("|")[1]
                .split(" ")
                .map { it.trim() }
                .count {
                    it.length == 2
                            || it.length == 3
                            || it.length == 4
                            || it.length == 7
                }
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val signals = line.split("|")
                .map { it.trim() }
                .first()
                .split(" ")
                .map { it.trim() }
            val mapping = mutableMapOf<Char, Char>()

            // Find segment a
            val twoLetters = signals.first { it.length == 2 }
            val threeLetters = signals.first { it.length == 3 }
            mapping[(twoLetters - threeLetters).first()] = 'a'

            check(mapping.size == 1)

            // Find segment c and f
            val sixLetters = signals.filter { it.length == 6 }
            val six = sixLetters.first { (twoLetters - it).length != 4 }
            mapping[twoLetters.commonWith(six)] = 'f'
            mapping[twoLetters.notCommonWith(six)] = 'c'

            check(mapping.size == 3)

            // Find segment b
            val fourLetters = signals.first { it.length == 4 }
            val threeSixDifference = sixLetters.map { (threeLetters - it) }
            val beg = threeSixDifference.filter { it.length == 3 }
                .first { threes -> threes.allCommonWith(fourLetters).length == 1 }
//            val beg = sixLetters.first { (threeLetters - it).length == 3 }
            val b = fourLetters.commonWith(beg)
            mapping[b] = 'b'

            check(mapping.size == 4)

            // Find segment g
            val fiveLetters = signals.filter { it.length == 5 }
            val three = fiveLetters.first { (threeLetters - it).length == 2 }
            val g = three.commonWith(beg)
            mapping[g] = 'g'

            check(mapping.size == 5)

            // Final segments
            mapping[("$g" - (threeLetters - three)).first()] = 'd'
            mapping[("$b" - ("$g" - beg)).first()] = 'e'

            check(mapping.size == 7)

            // Do mapping
            val numbers = line.split("|")
                .map { it.trim() }[1]
                .split(" ")
                .map { it.trim() }
            val mapToNumbers = mapOf(
                "abcefg" to 0,
                "cf" to 1,
                "acdeg" to 2,
                "acdfg" to 3,
                "bcdf" to 4,
                "abdfg" to 5,
                "abdefg" to 6,
                "acf" to 7,
                "abcdefg" to 8,
                "abcdfg" to 9,
            )
            check(mapToNumbers.size == 10)
            numbers.map { number ->
                val map = number.map { mapping[it] }
                    .sortedBy { it }
                    .joinToString("")
                mapToNumbers[map]
            }.joinToString("").toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

private operator fun String.minus(longer: String): String {
    var difference = longer
    forEach { character ->
        difference = difference.replace("$character", "")
    }
    return difference
}

private fun String.commonWith(other: String): Char = first { other.contains(it) }
private fun String.notCommonWith(other: String): Char = first { !other.contains(it) }
private fun String.allCommonWith(other: String): String = filter { other.contains(it) }
