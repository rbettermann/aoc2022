class Day02 {
    companion object {
        val ScoreLookup = mapOf(
            "A" to 1, //Schere
            "B" to 2, //Stein
            "C" to 3,  //Papier
        )

        val LooseLookup = mapOf(
            "B" to "A",
            "C" to "B",
            "A" to "C",
        )
        val WinLookup = mapOf(
            "A" to "B",
            "B" to "C",
            "C" to "A",
        )
    }

    fun part1(input: String): Int {
        val lookup = mapOf(
            "X" to "A",
            "Y" to "B",
            "Z" to "C"
        )
        val parsed = input.split("\n").map { it.trim().split(" ") }
            .map { listOf(it[0], lookup[it[1]]!!) }

        val points = parsed.map {
            val points = calculatePoints(it[0], it[1])
            println(points)
            points
        }.sum()

        return points
    }

    private fun calculatePoints(a: Any, b: Any): Int {
        if (a == b) {
            return 3 + ScoreLookup[b]!!
        } else {
            if (a == "A") {
                if (b == "B") {
                    return 6 + ScoreLookup[b]!!
                } else {
                    return ScoreLookup[b]!!
                }

            } else if (a == "B") {
                if (b == "C") {
                    return 6 + ScoreLookup[b]!!
                } else {
                    return ScoreLookup[b]!!
                }
            } else if (a == "C") {
                if (b == "A") {
                    return 6 + ScoreLookup[b]!!
                } else {
                    return ScoreLookup[b]!!
                }
            }
        }
        throw NotImplementedError()
    }

    fun part2(input: String): Int {
        val parsed = input.split("\n").map { it.trim().split(" ") }
        return parsed.map { listOf(it[0], calculateStep(it[0], it[1])) }.map { calculatePoints(it[0], it[1]) }.sum()
    }

    private fun calculateStep(oponent: String, me: String): Any {
        if (me == "X") { //LOOSE
            return LooseLookup[oponent]!!
        }
        if (me == "Y") { //DRAW
            return oponent
        }
        if (me == "Z") { //WIN
            return WinLookup[oponent]!!
        }

        throw NotImplementedError()
    }
}