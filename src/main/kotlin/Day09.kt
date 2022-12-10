import kotlin.math.sqrt

class Day09 {

    class Pos(
        var x: Int,
        var y: Int,
    ) {
        override fun toString(): String {
            return "($x, $y)"
        }
    }

    fun part1(input: String): Int {
        val size = 5
        val grid = buildGrid(size)

        var s = Pos(0, size - 1)
        var h = Pos(0, size - 1)
        var t = Pos(0, size - 1)
        //printGrid(grid, s, h, t)

        val parsed = input.lines().filter { it.isNotEmpty() }.map {
            Pair(it.split(" ")[0], it.split(" ")[1].toInt())
        }

        val tailPos = mutableListOf<Pair<Int, Int>>()
        tailPos.add(Pair(t.x, t.y))

        parsed.forEach {
            val direction = it.first
            val steps = it.second

            (0 until steps).forEachIndexed { index, it ->
                if (direction == "R") {
                    h.x = h.x + 1
                } else if (direction == "L") {
                    h.x = h.x + -1
                } else if (direction == "U") {
                    h.y = h.y - 1
                } else if (direction == "D") {
                    h.y = h.y + 1
                }

                followHead(h, t)
                tailPos.add(Pair(t.x, t.y))
                //printGrid(grid, s, h, t)
                //println()
            }
        }

        return tailPos.toSet().size
    }

    private fun followHead(h: Pos, t: Pos) {
        val distance = calculateDistance(h, t)
        if (distance == 1.0) {
            return //nothing todo (touching vertically / horizontally)
        }
        if (distance < 1.42) {
            return //nothing todo (diagonal touching)
        }
        if (distance == 2.0) { //move straight
            if (t.x == h.x) {
                if (t.y > h.y) {
                    t.y = t.y - 1
                } else {
                    t.y = t.y + 1
                }
            } else if (t.y == h.y) {
                if (t.x > h.x) {
                    t.x = t.x - 1
                } else {
                    t.x = t.x + 1
                }
            }
        } else { //move diagonally
            if (h.x > t.x) {
                t.x = t.x + 1
            } else {
                t.x = t.x - 1
            }

            if (h.y > t.y) {
                t.y = t.y + 1
            } else {
                t.y = t.y - 1
            }
        }
    }

    private fun calculateDistance(p2: Pos, p1: Pos) =
        sqrt((p2.y - p1.y) * (p2.y - p1.y) + (p2.x - p1.x) * (p2.x - p1.x).toDouble())

    private fun printGrid(
        grid: List<List<String>>,
        s: Pos,
        h: Pos,
        t: Pos,
        knots: List<Pos>
    ) {
        val gridToPrint = grid.map {
            it.toMutableList()
        }.toMutableList()

        gridToPrint[s.y][s.x] = "s"
        gridToPrint[t.y][t.x] = "T"
        knots.reversed().forEachIndexed { index, pos ->
            gridToPrint[pos.y][pos.x] = "${knots.size - index}"
        }
        gridToPrint[h.y][h.x] = "H"

        gridToPrint.forEach {
            println(it)
        }
    }

    private fun buildGrid(size: Int): MutableList<MutableList<String>> {
        val grid = (0 until size).map {
            (0 until size).map {
                "."
            }.toMutableList()
        }.toMutableList()
        return grid
    }

    fun part2(input: String): Int {
        val s = Pos(0, 0)
        val h = Pos(s.x, s.y)
        val knots = (0 until 9).map { Pos(s.x, s.y) }
        val t = Pos(s.x, s.y)

        val parsed = input.lines().filter { it.isNotEmpty() }.map {
            Pair(it.split(" ")[0], it.split(" ")[1].toInt())
        }

        val tailPos = mutableListOf<Pair<Int, Int>>()
        tailPos.add(Pair(t.x, t.y))

        parsed.forEach {
            val direction = it.first
            val steps = it.second
            (0 until steps).forEach { step ->
                if (direction == "R") {
                    h.x = h.x + 1
                } else if (direction == "L") {
                    h.x = h.x + -1
                } else if (direction == "U") {
                    h.y = h.y - 1
                } else if (direction == "D") {
                    h.y = h.y + 1
                }
                //move knots
                var currentKnot = h
                knots.forEachIndexed { index, it ->
                    followHead(currentKnot, it)
                    currentKnot = it
                }
                tailPos.add(Pair(currentKnot.x, currentKnot.y))
            }
        }

        return tailPos.toSet().size
    }
}