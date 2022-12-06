class Day06 {
    private fun String.hasDistinctChars() = this.toCharArray().toSet().size == this.length

    private fun findDistinct(input: String, window: Int): Int {
        input.windowed(size = window, step = 1).forEachIndexed { index, s ->
            if (s.hasDistinctChars())
                return index + window
        }
        throw NotImplementedError()
    }

    fun part1(input: String) = findDistinct(input, 4)
    fun part2(input: String) = findDistinct(input, 14)
}