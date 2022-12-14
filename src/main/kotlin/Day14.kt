class Day14 {

    class Pos(var x: Int, var y: Int)

    fun part1(input: String): Int {

        val coordinates =
            input.split("\n").flatMap { it.split("->") }.map { it.split(",")[0].trim().toInt() }
                .distinct()

        val depth =
            input.split("\n").flatMap { it.split("->") }.map { it.split(",")[1].trim().toInt() }
                .distinct()

        val minCoordinate = coordinates.min()
        val maxCoordinate = coordinates.max()

        val width = maxCoordinate - minCoordinate + 1
        val height = depth.max() + 1
        var map = generateMap(width, height)

        var sandPosition = Pair(500 - minCoordinate, 0)
        map[sandPosition.second][sandPosition.first] = '+'

        val parsed = input.split("\n").map {
            it.split("->").map {
                Pair(
                    it.split(",")[0].trim().toInt() - minCoordinate,
                    it.split(",")[1].trim().toInt()
                )
            }
        }
        parsed.forEach { path ->
            var lastNode = path[0]
            path.forEach { edge ->
                if (lastNode == edge) {
                    map[lastNode.second][lastNode.first] = '#'
                } else if (lastNode.first != edge.first && lastNode.second == edge.second) {
                    //horizontal
                    if (lastNode.first <= edge.first)
                        (lastNode.first..edge.first).forEach {
                            map[lastNode.second][it] = '#'
                        }
                    else {
                        (edge.first..lastNode.first).forEach {
                            map[lastNode.second][it] = '#'
                        }
                    }

                } else if (lastNode.first == edge.first && lastNode.second != edge.second) {
                    //vertical
                    if (lastNode.second <= edge.second)
                        (lastNode.second..edge.second).forEach {
                            map[it][lastNode.first] = '#'
                        }
                    else
                        (edge.second..lastNode.second).forEach {
                            map[it][lastNode.first] = '#'
                        }
                } else {
                    throw NotImplementedError()
                }
                lastNode = edge
            }
        }
        printMap(map)

        var numberOfSand = 0
        var overflow = false
        while (!overflow) {
            var sandPosition = Pos(sandPosition.first, sandPosition.second)
            var blocked = false
            while (!blocked) {

                if (sandPosition.x < 0 || sandPosition.x > width) {
                    return numberOfSand
                }

                val downPosition = Pos(sandPosition.x, sandPosition.y + 1)
                val diagLeftPosition = Pos(sandPosition.x-1, sandPosition.y + 1)
                val diagRightPosition = Pos(sandPosition.x+1, sandPosition.y + 1)

                //move down
                if (!map.isBlocked(downPosition)) {
                    sandPosition = downPosition
                    continue
                }
                //move diagonally left
                if (!map.isBlocked(diagLeftPosition)) {
                    sandPosition = diagLeftPosition
                    continue
                }
                //move diagonnaly right
                if (!map.isBlocked(diagRightPosition)) {
                    sandPosition = diagRightPosition
                    continue
                }
                blocked = true
            }
            numberOfSand += 1
            map[sandPosition.y][sandPosition.x] = 'o'
            printMap(map)
            println()
        }
        throw NotImplementedError()
    }

    private fun printMap(map: List<List<Char>>) {
        map.forEachIndexed { index, it ->
            //print("$index ")
            it.forEach { print(it) }
            println()
        }
    }

    private fun generateMap(width: Int, height: Int): MutableList<MutableList<Char>> {
        return (0 until height).map {
            (0 until width).map {
                '.'
            }.toMutableList()
        }.toMutableList()
    }

    fun part2(input: String): Int {
        val coordinates =
            input.split("\n").flatMap { it.split("->") }.map { it.split(",")[0].trim().toInt() }
                .distinct()

        val depth =
            input.split("\n").flatMap { it.split("->") }.map { it.split(",")[1].trim().toInt() }
                .distinct()

        val padding = 1000

        val minCoordinate = coordinates.min() - padding
        val maxCoordinate = coordinates.max() + padding

        val width = maxCoordinate - minCoordinate + 1
        val height = depth.max() + 1 + 2
        var map = generateMap(width, height)
        (0 until width).forEach { map[height-1][it]='#' }

        printMap(map)

        var sandSource = Pair(500 - minCoordinate, 0)
        map[sandSource.second][sandSource.first] = '+'

        val parsed = input.split("\n").map {
            it.split("->").map {
                Pair(
                    it.split(",")[0].trim().toInt() - minCoordinate,
                    it.split(",")[1].trim().toInt()
                )
            }
        }
        parsed.forEach { path ->
            var lastNode = path[0]
            path.forEach { edge ->
                if (lastNode == edge) {
                    map[lastNode.second][lastNode.first] = '#'
                } else if (lastNode.first != edge.first && lastNode.second == edge.second) {
                    //horizontal
                    if (lastNode.first <= edge.first)
                        (lastNode.first..edge.first).forEach {
                            map[lastNode.second][it] = '#'
                        }
                    else {
                        (edge.first..lastNode.first).forEach {
                            map[lastNode.second][it] = '#'
                        }
                    }

                } else if (lastNode.first == edge.first && lastNode.second != edge.second) {
                    //vertical
                    if (lastNode.second <= edge.second)
                        (lastNode.second..edge.second).forEach {
                            map[it][lastNode.first] = '#'
                        }
                    else
                        (edge.second..lastNode.second).forEach {
                            map[it][lastNode.first] = '#'
                        }
                } else {
                    throw NotImplementedError()
                }
                lastNode = edge
            }
        }
        printMap(map)

        var numberOfSand = 0
        var overflow = false
        while (!overflow) {
            var sandPos = Pos(sandSource.first, sandSource.second)
            var blocked = false
            while (!blocked) {

                if (sandPos.x < 0 || sandPos.x > width) {
                    throw NotImplementedError("overflow, add more padding")
                }

                val downPosition = Pos(sandPos.x, sandPos.y + 1)
                val diagLeftPosition = Pos(sandPos.x-1, sandPos.y + 1)
                val diagRightPosition = Pos(sandPos.x+1, sandPos.y + 1)

                //move down
                if (!map.isBlocked(downPosition)) {
                    sandPos = downPosition
                    continue
                }
                //move diagonally left
                if (!map.isBlocked(diagLeftPosition)) {
                    sandPos = diagLeftPosition
                    continue
                }
                //move diagonnaly right
                if (!map.isBlocked(diagRightPosition)) {
                    sandPos = diagRightPosition
                    continue
                }
                if (sandPos.x == sandSource.first && sandPos.y == sandSource.second){
                    return numberOfSand + 1
                }
                blocked = true
            }
            numberOfSand += 1
            map[sandPos.y][sandPos.x] = 'o'
            //printMap(map)
            //println()
        }
        throw NotImplementedError()
    }
}

private fun MutableList<MutableList<Char>>.isBlocked(pos: Day14.Pos): Boolean {
    if (pos.x < 0 || pos.x > this[0].size) {
        return false
    }

    if (this[pos.y][pos.x] == '.') {
        return false
    } else {
        return true
    }
}
