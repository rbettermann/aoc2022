import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class Day13 {
    companion object {
        val mapper = jacksonObjectMapper()
    }

    fun part1(input: String): Int {
        val packets = input.split("\n\n").map {
            val rows = it.split("\n")
            val first: List<Any> = mapper.readValue(rows[0])
            val second: List<Any> = mapper.readValue(rows[1])
            Pair(first, second)
        }

        val correctIndexes = mutableListOf<Int>()
        val wrongIndexes = mutableListOf<Int>()
        packets.forEachIndexed { index, packet ->
            println("== Pair ${index + 1} ==")
            val a = packet.first
            val b = packet.second
            println("  - Compare $a vs $b")
            val compareResult = comparePackets(a, b)

            if (compareResult == -1) {
                correctIndexes.add(index + 1)
            } else if (compareResult == 1) {
                wrongIndexes.add(index + 1)
            } else {
                throw NotImplementedError("equal!")
            }
        }

        if (correctIndexes.size + wrongIndexes.size != packets.size) {
            throw NotImplementedError("fail.")
        }

        return correctIndexes.sum()
    }

    private fun comparePackets(
        left: List<Any>,
        right: List<Any>
    ): Int? {
        val zipped = left.zip(right)
        var newReturnVal: Int? = null
        for (it in zipped) {
            val first = it.first
            val second = it.second

            if (newReturnVal != null) {
                println("cancel execution...")
                return newReturnVal
            }

            println("    - Compare $first vs $second")

            if (first is Int && second is Int) {
                if (first == second) {
                    //continue
                } else if (first < second) {
                    println("      - Left side is smaller, so inputs are in the right order")
                    newReturnVal = -1
                    return newReturnVal
                } else {
                    println("      - Right side is smaller, so inputs are not in the right order")
                    //not right oder!
                    newReturnVal = 1
                    return newReturnVal
                }
            } else if (first is List<*> && second is List<*>) {
                newReturnVal = comparePackets(
                    first as List<Any>,
                    second as List<Any>
                )
            } else if (first is List<*> && second is Int) {
                newReturnVal = comparePackets(first as List<Any>, listOf(second))
            } else if (first is Int && second is List<*>) {
                newReturnVal = comparePackets(listOf(first), second as List<Any>)
            } else {
                throw NotImplementedError()
            }
        }
        if (newReturnVal == null) {
            val missing = left.size - right.size
            if (missing < 0) {
                println("     - Left side ran out of items, so inputs are in the right order")
                return -1
            }
            if (missing > 0) {
                println("      - Right side ran out of items, so inputs are not in the right order")
                return 1
            }
        }
        return newReturnVal
    }

    fun part2(input: String): Int {
        val packets = input.split("\n").filter { it.isNotEmpty() }.map {
            val first: List<Any> = mapper.readValue(it)
            first
        }
        val divider1: List<Any> = mapper.readValue("[[2]]")
        val divider2: List<Any> = mapper.readValue("[[6]]")

        val all = (packets + divider1 + divider2).toMutableList()
        all.sortWith { a, b ->
            comparePackets(
                a as List<Any>,
                b as List<Any>
            )!!
        }

        var pos1 = 0
        var pos2 = 0
        all.forEachIndexed {index, value ->
            println("${value.toString()} == ${divider1.toString()} --> ${value.toString() == divider1.toString()}")
            if (listOf(value).toString() == divider1.toString()) {
                pos1 = index + 1
            }
            if (listOf(value).toString() == divider2.toString()) {
                pos2 = index + 1
            }
        }
        return pos1 * pos2
    }
}
