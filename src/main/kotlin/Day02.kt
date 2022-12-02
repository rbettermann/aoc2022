class Day02 {
    enum class Draws(val points: Int, val win: String, val loose: String) {
        SCISSORS(1, "PAPER", "ROCK"),
        ROCK(2, "SCISSORS", "PAPER"),
        PAPER(3, "ROCK", "SCISSORS")
    }

    enum class Outcome {
        LOOSE,
        DRAW,
        WIN
    }

    private fun String.toDraw(): Draws {
        return when (this) {
            "A" -> Draws.SCISSORS
            "B" -> Draws.ROCK
            "C" -> Draws.PAPER
            else -> throw AssertionError()
        }
    }

    private fun String.toOutcome(): Outcome {
        return when (this) {
            "X" -> Outcome.LOOSE
            "Y" -> Outcome.DRAW
            "Z" -> Outcome.WIN
            else -> throw AssertionError()
        }
    }

    private fun parseInput(input: String) = input.split("\n").map { it.trim().split(" ") }
    private fun calculatePoints(opponentDraw: Draws, myDraw: Draws): Int {
        return when (opponentDraw) {
            myDraw -> 3 + myDraw.points
            Draws.valueOf(myDraw.win) -> 6 + myDraw.points
            else -> myDraw.points
        }
    }

    private fun calculateDraw(opponent: Draws, me: Outcome): Draws {
        return when (me) {
            Outcome.LOOSE -> Draws.valueOf(opponent.win)
            Outcome.DRAW -> opponent
            Outcome.WIN -> Draws.valueOf(opponent.loose)
        }
    }

    fun part1(input: String): Int {
        val translateToDraw = mapOf("X" to "A", "Y" to "B", "Z" to "C")
        val parsed = parseInput(input).map { listOf(it[0], translateToDraw[it[1]]!!) }
        return parsed.sumOf { calculatePoints(it[0].toDraw(), it[1].toDraw()) }
    }

    fun part2(input: String): Int {
        return parseInput(input)
            .map { listOf(it[0].toDraw(), calculateDraw(it[0].toDraw(), it[1].toOutcome())) }
            .sumOf { calculatePoints(it[0], it[1]) }
    }
}