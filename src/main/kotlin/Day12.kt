class Day12 {
    class Pos(
        val x: Int,
        val y: Int,
    ) {
        override fun equals(other: Any?): Boolean {
            if (other is Pos) {
                return (this.x == other.x && this.y == other.y)
            } else {
                return super.equals(other)
            }
        }

        override fun toString(): String {
            return "($x, $y)"
        }

        override fun hashCode(): Int {
            return "$x,$y".hashCode()
        }
    }


    fun part1(input: String): Int {
        val (rawMap, from, to) = parseWholeMap(input)

        val edges = extractEdges(rawMap)
        val vertices = edges.values.flatten().flatMap { listOf(it.first, it.second) }.distinct()

        return dijkstra(vertices, edges, from, to)[to]!!.second
    }

    private fun parseWholeMap(input: String): Triple<List<MutableList<Char>>, Pos, Pos> {
        val rawMap = parseMap(input)
        val from = findPos(rawMap, 'S')
        val to = findPos(rawMap, 'E')

        rawMap[from.y][from.x] = 'a'
        rawMap[to.y][to.x] = 'z'
        return Triple(rawMap, from, to)
    }

    private fun dijkstra(
        vertices: List<Pos>,
        edges: Map<Pos, List<Pair<Pos, Pos>>>,
        from: Pos,
        to: Pos
    ): Map<Pos, Pair<List<Pos>, Int>> {
        val notSeenVertices = vertices.toMutableSet()
        val distances = vertices.map { it to Int.MAX_VALUE }.toMap().toMutableMap()
        val paths = mutableMapOf<Pos, List<Pos>>()
        distances[from] = 0

        var current = from

        while (notSeenVertices.isNotEmpty() && notSeenVertices.contains(to)) {
            edges[current]?.forEach { adjacentEdge ->
                val distance = 1
                val adjacent = adjacentEdge.second
                if (distances[current]!! + distance < distances[adjacent]!!) {
                    distances[adjacent] = distances[current]!! + distance
                    paths[adjacent] =
                        paths.getOrDefault(current, listOf(current)) + listOf(adjacent)
                }
            }
            notSeenVertices.remove(current)
            if (current == to || notSeenVertices.all { distances[it]!! == Int.MAX_VALUE }) {
                break
            }
            if (notSeenVertices.isNotEmpty()) {
                current = notSeenVertices.minBy { distances[it]!! }!!
            }
        }
        return paths.mapValues { entry -> entry.value to distances[entry.key]!! }
    }

    private fun extractEdges(map: List<MutableList<Char>>): Map<Pos, List<Pair<Pos, Pos>>> {
        val edges = mutableListOf<Pair<Pos, Pos>>()
        map.forEachIndexed { y, row ->
            row.forEachIndexed { x, value ->
                getAdjacentNodes(map, Pos(x, y)).map {
                    edges.add(Pair(Pos(x, y), it))
                }
            }
        }

        return edges.distinct().groupBy { pair: Pair<Pos, Pos> -> pair.first }
    }


    private fun getAdjacentNodes(map: List<MutableList<Char>>, pos: Pos): List<Pos> {
        val currentValue = map.atPos(pos)!!

        return listOf(
            Pos(pos.x, pos.y - 1), // top
            Pos(pos.x, pos.y + 1),  // bottom
            Pos(pos.x - 1, pos.y),  // left
            Pos(pos.x + 1, pos.y),  // right
        ).filter { it ->
            val valueAtPosition = map.atPos(it)
            valueAtPosition != null &&
                    (valueAtPosition == currentValue ||
                            (valueAtPosition - 1) == currentValue ||
                            valueAtPosition < currentValue)
        }.toList()
    }

    private fun printMap(m: List<List<Char>>, visited: List<Pos>) {
        m.forEachIndexed { y, it ->
            it.forEachIndexed { x, it ->
                if (visited.contains(Pos(x, y))) {
                    print('+')
                } else {
                    //print("$it")
                    print(".")
                }
            }
            println()
        }
    }

    private fun findPos(rawMap: List<List<Char>>, s: Char): Pos {
        rawMap.forEachIndexed { y, row ->
            row.forEachIndexed { x, posVal ->
                if (posVal == s) {
                    return Pos(x, y)
                }
            }
        }
        throw NotImplementedError()
    }

    fun part2(input: String): Int {
        val (rawMap, from, to) = parseWholeMap(input)
        val edges = extractEdges(rawMap)
        val vertices = edges.values.flatten().flatMap { listOf(it.first, it.second) }.distinct()

        //brute force, yay, it would be smarter to back-track from end?
        val potentialStarts = rawMap.flatMapIndexed { y, it ->
            it.mapIndexed { x, value ->
                if (value == 'a') {
                    Pos(x, y)
                } else {
                    null
                }

            }
        }.filterNotNull()

        val returns = potentialStarts.mapNotNull {
            val result = dijkstra(vertices, edges, it, to)
            if (result[to] != null) {
                result[to]!!.second
            } else {
                null
            }
        }
        return returns.min()
    }

    private fun parseMap(input: String) =
        input.split("\n").map { it.toCharArray().map { it }.toMutableList() }
}

private fun <E> List<List<E>>.atPos(start: Day12.Pos): Char? {
    if (!this.indices.contains(start.y) || !this[0].indices.contains(start.x)) {
        return null
    }
    return this[start.y][start.x] as Char
}
