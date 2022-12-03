class Day03 {

    fun characterToPriority(input: Char): Int {
        return if (input.code >= 'a'.code) {
            input.code - 'a'.code + 1
        } else {
            input.code - 'A'.code + 27
        }
    }

    private fun findSameChars(first: String, second: String): Int {
        val firstD = first.chars().distinct().toArray()
        val secondD = second.chars().distinct().toArray()
        val intersect = firstD.intersect(secondD.toSet()).first()
        return characterToPriority(intersect.toChar())
    }

    private fun calculateForGroup(a: String, b: String, c: String): Int {
        val aD = a.chars().distinct().toArray()
        val bD = b.chars().distinct().toArray()
        val cD = c.chars().distinct().toArray()

        return characterToPriority(aD.intersect(bD.toSet()).intersect(cD.toSet()).first().toChar())
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