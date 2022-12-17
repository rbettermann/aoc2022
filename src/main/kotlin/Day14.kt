class Day14 {

    class Pos(var x: Int, var y: Int)

    private fun extractXValues(input: String): List<Int> {
        return input.split("\n")
            .flatMap { it.split("->") }
            .map { it.split(",")[0].trim().toInt() }.distinct()
    }

    private fun extractYValues(input: String) = input.split("\n")
        .flatMap { it.split("->") }
        .map { it.split(",")[1].trim().toInt() }.distinct()

    private fun prepareMap(
        map: MutableList<MutableList<Char>>,
        sandPosition: Pair<Int, Int>,
        input: String,
        xValues: List<Int>
    ) {
        map[sandPosition.second][sandPosition.first] = '+'
        val paths = input.split("\n").map {
            it.split("->").map {
                Pair(
                    it.split(",")[0].trim().toInt() - xValues.min(),
                    it.split(",")[1].trim().toInt()
                )
            }
        }
        paths.forEach { path ->
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
    }

    private fun simulateStep(
        map: MutableList<MutableList<Char>>,
        simulatedSandPosition: Pos
    ): Pos? {
        val downPosition = Pos(simulatedSandPosition.x, simulatedSandPosition.y + 1)
        val diagLeftPosition = Pos(simulatedSandPosition.x - 1, simulatedSandPosition.y + 1)
        val diagRightPosition = Pos(simulatedSandPosition.x + 1, simulatedSandPosition.y + 1)

        //move down
        if (!map.isBlocked(downPosition)) {
            return downPosition
        }
        //move diagonally left
        if (!map.isBlocked(diagLeftPosition)) {
            return diagLeftPosition
        }
        //move diagonally right
        if (!map.isBlocked(diagRightPosition)) {
            return diagRightPosition
        }
        return null
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


    fun part1(input: String): Int {
        val xValues = extractXValues(input)
        val yValues = extractYValues(input)

        val width = xValues.max() - xValues.min() + 1
        val height = yValues.max() + 1
        val map = generateMap(width, height)

        val sandPosition = Pair(500 - xValues.min(), 0)

        prepareMap(map, sandPosition, input, xValues)
        //printMap(map)

        var numberOfSand = 0
        while (true) {
            var simulatedSandPosition = Pos(sandPosition.first, sandPosition.second)
            while (true) {
                if (simulatedSandPosition.x < 0 || simulatedSandPosition.x > width) {
                    return numberOfSand
                }
                val newPosition = simulateStep(map, simulatedSandPosition)
                if (newPosition == null) {
                    break
                } else {
                    simulatedSandPosition = newPosition
                }
            }
            numberOfSand += 1
            map[simulatedSandPosition.y][simulatedSandPosition.x] = 'o'
            //printMap(map)
            //println()
        }
    }

    fun part2(input: String): Int {
        val xValues = extractXValues(input)
        val yValues = extractYValues(input)

        val padding = 1000

        val minCoordinate = xValues.min() - padding
        val maxCoordinate = xValues.max() + padding

        val width = maxCoordinate - minCoordinate + 1
        val height = yValues.max() + 1 + 2
        val map = generateMap(width, height)
        (0 until width).forEach { map[height - 1][it] = '#' }

        val sandSource = Pair(500 - minCoordinate, 0)
        map[sandSource.second][sandSource.first] = '+'

        prepareMap(map, sandSource, input, xValues + minCoordinate + maxCoordinate)
        //printMap(map)

        var numberOfSand = 0
        while (true) {
            var simulatedSandPosition = Pos(sandSource.first, sandSource.second)
            while (true) {

                if (simulatedSandPosition.x < 0 || simulatedSandPosition.x > width) {
                    throw NotImplementedError("overflow, add more padding")
                }

                val newPosition = simulateStep(map, simulatedSandPosition)
                if (newPosition == null) {
                    if (simulatedSandPosition.x == sandSource.first && simulatedSandPosition.y == sandSource.second) {
                        return numberOfSand + 1
                    }
                    break
                } else {
                    simulatedSandPosition = newPosition
                }
            }
            numberOfSand += 1
            map[simulatedSandPosition.y][simulatedSandPosition.x] = 'o'
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
    return this[pos.y][pos.x] != '.'
}
