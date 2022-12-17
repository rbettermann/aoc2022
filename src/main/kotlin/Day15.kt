import kotlin.math.abs

class Day15 {

    class Pos(var x: Long, var y: Long) {
        override fun toString(): String {
            return "($x,$y)"
        }
    }

    fun distance(a: Pos, b: Pos): Long {
        return abs(a.x - b.x) + abs(a.y - b.y)
    }

    private fun prepareMap(
        map: MutableList<MutableList<Char>>,
        xValues: List<Long>,
        positions: List<Pair<Pos, Pos>>
    ) {
        val xOffset = xValues.min()

        positions.forEach {
            val sender = it.first
            val beacon = it.second
            sender.x = sender.x - xOffset
            beacon.x = beacon.x - xOffset

            map[sender.y.toInt()][sender.x.toInt()] = 'S'
            map[beacon.y.toInt()][beacon.x.toInt()] = 'B'
        }
        println("starting simulation...")
        positions.forEach {
            println("sender: ${it.first.x} ${it.first.y}")
            val sender = it.first
            val beacon = it.second
            val d = distance(sender, beacon)
            map.forEachIndexed { y, chars ->
                chars.forEachIndexed { x, c ->
                    if (distance(sender, Pos(x.toLong(), y.toLong())) <= d) {
                        if (map[y][x] == '.') {
                            map[y][x] = '#'
                        }
                    }
                }
            }

        }
    }

    private fun generateMap(width: Long, height: Long): MutableList<MutableList<Char>> {
        return (0 until height).map {
            (0 until width).map {
                '.'
            }.toMutableList()
        }.toMutableList()
    }


    private fun printMap(map: List<List<Char>>) {
        map.forEachIndexed { index, it ->
            //print("$index ")
            it.forEach { print(it) }
            println()
        }
    }

    fun part1(input: String, interestingY: Int): Int {
        val intervals = calculateIntervals(input)
        return intervals[interestingY.toLong()]!!.map { it.first until it.second }.flatten().distinct()
            .count()
    }

    private fun calculateIntervals(input: String): MutableMap<Long, MutableList<Pair<Long, Long>>> {
        val parsed = input.split("\n").map {
            val p = it.replace("Sensor at x=", "").split(",")
            val p2 = p[1].replace(" y=", "").split(":")

            val x1 = p[0].trim().toLong()
            val y1 = p2[0].trim().toLong()
            val x2 = p2[1].replace("closest beacon is at x=", "").trim().toLong()
            val y2 = p[2].replace("y=", "").trim().toLong()

            Pair(Pos(x1, y1), Pos(x2, y2))
        }

        val intervals = mutableMapOf<Long, MutableList<Pair<Long, Long>>>()

        parsed.forEach {
            val sender = it.first
            val beacon = it.second
            val d = distance(sender, beacon)
            (sender.y - d..sender.y + d).forEach { yCoordinate ->
                val midDistance = distance(sender, Pos(sender.x, yCoordinate))
                if (intervals[yCoordinate] == null) {
                    intervals[yCoordinate] = mutableListOf()
                }
                intervals[yCoordinate]!!.add(
                    Pair(
                        sender.x - (d - midDistance),
                        sender.x + (d - midDistance)
                    )
                )
            }
        }

        intervals.forEach { xIntervals ->

            var newIntervals = mutableListOf<Pair<Long, Long>>()
            var combinedIntervals = combineIntervals(xIntervals.value)
            while (combinedIntervals.size != newIntervals.size) {
                newIntervals = combinedIntervals
                combinedIntervals = combineIntervals(newIntervals)
            }
            intervals[xIntervals.key] = combineIntervals(combinedIntervals)
        }


        return intervals
    }

    fun combineIntervals(
        xIntervals: MutableList<Pair<Long, Long>>
    ): MutableList<Pair<Long, Long>> {
        val newIntervals = mutableListOf<Pair<Long, Long>>()

        xIntervals.sortedBy { it.first }.forEach { intervalToMerge ->
            if (!isContainedInInterval(newIntervals, intervalToMerge)) {
                val combined = xIntervals.filter { candidate ->
                    candidate.first == intervalToMerge.second ||
                    candidate.first == intervalToMerge.second + 1 ||
                    candidate.first > intervalToMerge.first && candidate.first < intervalToMerge.second
                }.maxByOrNull { it.second }
                if (combined != null) {
                    val newInterval = Pair(intervalToMerge.first, combined.second)
                    if (!isContainedInInterval(newIntervals, newInterval)) {
                        newIntervals.add(newInterval)
                    }
                }
            }
        }

        xIntervals.forEach {
            if (!isContainedInInterval(newIntervals, it)) {
                newIntervals.add(it)
            }
        }

        newIntervals.groupBy { it.first }
            .filter { it.value.count() > 1 }
            .forEach {
                val newOne = it.value.maxBy { it.second }
                newIntervals.removeAll(it.value)
                newIntervals.add(newOne)
            }

        return newIntervals
    }

    private fun isContainedInInterval(
        intervals: MutableList<Pair<Long, Long>>,
        interval: Pair<Long, Long>
    ): Boolean {
        intervals.forEach {
            if (it.first <= interval.first && it.second >= interval.second) {
                return true
            }
        }
        return false
    }

    fun part1ArraySolution(input: String, interestingY: Int): Int {

        val parsed = input.split("\n").map {
            val p = it.replace("Sensor at x=", "").split(",")
            val p2 = p[1].replace(" y=", "").split(":")

            val x1 = p[0].trim().toLong()
            val y1 = p2[0].trim().toLong()
            val x2 = p2[1].replace("closest beacon is at x=", "").trim().toLong()
            val y2 = p[2].replace("y=", "").trim().toLong()

            Pair(Pos(x1, y1), Pos(x2, y2))
        }

        val xValues = parsed.flatMap { listOf(it.first.x, it.second.x) }
        val yValues = parsed.flatMap { listOf(it.first.y, it.second.y) }

        val paddingX = 5
        val xMin = xValues.min() - paddingX
        val xMax = xValues.max() + paddingX
        val width = xMax - xMin + 1

        val height = yValues.max() + 1
        val map = generateMap(width, height)
        prepareMap(map, xValues + xMin + xMax, parsed)

        return map[interestingY].count { it == '#' }
    }

    fun part2(input: String, maxRange: Int): Long {
        val intervals = calculateIntervals(input)
            .filter { it.key > 0 && it.key < maxRange }
            .filter { it.value.size == 2 }
            .filter { it.value[0].second + 2 == it.value[1].first }

        if (intervals.size != 1) {
            throw NotImplementedError()
        }

        intervals.forEach {
            val y = it.key
            val x = it.value[0].second + 1
            return x * 4000000L + y
        }
        throw NotImplementedError()
    }
}