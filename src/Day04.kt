fun main() {
    fun parseBoards(input: List<String>): List<Board> {
        val numberOfBoards = (input.size - 1) / 6
        val boards = mutableListOf<Board>()

        (0 until numberOfBoards).forEach { row ->
            val cells = mutableListOf<Cell>()
            for (i in 0 until 5) {
                input[row * 6 + i + 2].split(" ").filter { it.isNotEmpty() }
                    .forEachIndexed { index, s -> cells.add(Cell(index, i, s.toInt())) }
            }
            boards.add(Board(cells))
        }
        return boards
    }

    fun part1(input: List<String>): Int {
        val numbers = input[0].split(",").map { it.toInt() }
        val boards = parseBoards(input)

        numbers.forEach { number ->
            boards.forEach { board -> board.mark(number) }
            return boards.firstOrNull { it.hasWon() }
                ?.cells
                ?.filter { !it.marked }
                ?.sumOf { it.number }
                ?.times(number)
                ?: return@forEach
        }
        throw IllegalStateException("Not a single board won")
    }

    fun part2(input: List<String>): Int {
        val numbers = input[0].split(",").map { it.toInt() }
        val boards = parseBoards(input).toMutableList()

        var winningBoard: Board = boards[0]
        var lastNumber = 0

        run searching@{
            numbers.forEach { number ->
                lastNumber = number
                boards.forEach { board -> board.mark(number) }
                val winningBoards = boards.filter { it.hasWon() }
                winningBoards.forEach {
                    winningBoard = it
                    boards.remove(it)
                }
                if (boards.size == 0) {
                    return@searching
                }
            }
        }

        return winningBoard.cells
            .filter { !it.marked }
            .sumOf { it.number }
            .times(lastNumber)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

private data class Board(
    val cells: List<Cell>
) {
    fun mark(number: Int) {
        cells.filter { it.number == number }
            .forEach { it.marked = true }
    }

    fun hasWon(): Boolean {
        val winningRow = cells.groupBy { it.y }
            .any { it.value.all { cell -> cell.marked } }

        val winningColumn = cells.groupBy { it.x }
            .any { it.value.all { cell -> cell.marked } }

        return winningRow || winningColumn
    }
}

private data class Cell(
    val x: Int,
    val y: Int,
    val number: Int,
    var marked: Boolean = false
)
