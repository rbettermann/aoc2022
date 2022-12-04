class Day04 {

    private fun split(input: String): Pair<Int,Int> {
        val s = input.split("-")
        return Pair(s[0].toInt(), s[1].toInt())
    }

    fun part1(input: String): Int {
        val parsed = parseInput(input)

        return parsed.count { isOverlapping(it[0], it[1]) }
    }

    private fun parseInput(input: String) =
        input.split("\n").map { it.split(",") }.map { listOf(split(it[0]), split(it[1])) }

    private fun isOverlapping(a: Pair<Int,Int>, b: Pair<Int,Int>): Boolean {
        val r1 = a.first until a.second + 1
        val r2 = b.first until b.second + 1


        var result = false
        if (r1.contains(r2.first) && r1.contains(r2.last)) {
            result =  true
        } else if (r2.contains(r1.first) && r2.contains(r1.last)) {
            result = true
        } else
            result = false

       // println("$r1 $r2 $result")

        return result
    }

    fun part2(input: String): Int {
        val parsed = parseInput(input)
        return parsed.count { isOverlappingPartial(it[0], it[1]) }
    }

    private fun isOverlappingPartial(a: Pair<Int, Int>, b: Pair<Int, Int>): Boolean {
        val r1 = a.first until a.second + 1
        val r2 = b.first until b.second + 1

        if (r1.intersect(r2).isNotEmpty()) {
            return true
        }
        if (r2.intersect(r1).isNotEmpty()) {
            return true
        }
        return false

    }
}