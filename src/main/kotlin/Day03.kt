class Day03 {

    fun characterToPriority(input: Char): Int {
        return if (input.code >= 'a'.code) {
            input.code - 'a'.code + 1
        } else {
            input.code - 'A'.code + 27
        }
    }

    private fun findSameChars(first: String, second: String): Int {
        return characterToPriority(
            first.chars().toArray()
                .intersect(second.chars().toArray().toSet())
                .first().toChar()
        )
    }

    private fun calculateForGroup(a: String, b: String, c: String): Int {
        return characterToPriority(
            a.chars().toArray()
                .intersect(b.chars().toArray().toSet())
                .intersect(c.chars().toArray().toSet())
                .first().toChar()
        )
    }

    fun part1(input: String): Int {
        val parsed = input.split("\n").map {
            listOf(
                it.substring(0 until it.length / 2),
                it.substring(it.length / 2 until it.length)
            )
        }

        return parsed.map { findSameChars(it[0], it[1]) }.sum()
    }

    fun part2(input: String): Int {
        val parsed = input.split("\n").chunked(3)
        return parsed.sumOf { calculateForGroup(it[0], it[1], it[2]) }
    }
}