class Day08 {
    fun part1(input: String): Int {
        val map = parseMap(input)

        map.forEach {
            //   println(it)
        }

        var visCount = 0
        for (y in 0 until map.size) {
            for (x in 0 until map[0].size) {
                val tree = map[y][x]

                if (isVisable(map, x, y, tree)) {
                    visCount++
                }
                //println("$x, $y, ($tree): ${isVisable(map, x, y, tree)}")
            }
        }

        return visCount
    }

    private fun parseMap(input: String) = input.split("\n").map {
        it.toCharArray().map { it.digitToInt() }
    }.filter { it.isNotEmpty() }

    private fun isVisable(map: List<List<Int>>, x: Int, y: Int, treeVal: Int): Boolean {
        if (x == 0 || y == 0 || x == map[0].size - 1 || y == map.size - 1) {
            //border tree are always visable
            return true
        }

        //left
        if (((0 until x).map { map[y][it] < treeVal }.all { it })) return true
        //right
        if (((x + 1 until map[0].size).map { map[y][it] < treeVal }.all { it })) return true
        //top
        if (((0 until y).map { map[it][x] < treeVal }.all { it })) return true
        //bottom
        if (((y + 1 until map.size).map { map[it][x] < treeVal }.all { it })) return true

        return false
    }

    private fun calcScore(map: List<List<Int>>, x: Int, y: Int, treeVal: Int): Int {

        if (x==2 && y==1) {
            println("siii")
        }

        //left
        //var scoreLeft = if (x==0) 0 else 1
        var scoreLeft = 0
        for (it in (0 until x).reversed()) {
            if (map[y][it] < treeVal)
                scoreLeft++
            else {
                scoreLeft++
                break
            }
        }
        //right
        var scoreRight = 0
        for (it in x + 1 until map[0].size) {
            if (map[y][it] < treeVal)
                scoreRight++
            else {
                scoreRight++
                break
            }
        }
        //top
        var scoreTop = 0
        for (it in (0 until y).reversed()) {
            if (map[it][x] < treeVal)
                scoreTop++
            else {
                scoreTop++
                break
            }
        }
        //bottom
        var scoreBottom = 0
        for (it in (y + 1 until map.size)) {
            if (map[it][x] < treeVal)
                scoreBottom++
            else {
                scoreBottom++
                break
            }
        }
        return scoreLeft * scoreRight * scoreTop * scoreBottom
    }

    fun part2(input: String): Int {
        val map = parseMap(input)

        map.forEach {
            println(it)
        }

        val results = mutableListOf<Int>()
        for (y in 0 until map.size) {
            for (x in 0 until map[0].size) {
                val tree = map[y][x]
                results.add(calcScore(map, x, y, tree))

                println("$x, $y, ($tree): ${calcScore(map, x, y, tree)}")
            }
        }

        return results.max()
    }

}