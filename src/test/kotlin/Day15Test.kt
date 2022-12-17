import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day15Test : FunSpec({
    val day15 = Day15()

    test("part1 example array based") {
        day15.part1ArraySolution(loadResource("example"), 10) shouldBe 26
    }

    test("part1 example") {
        day15.part1(loadResource("example"), 10) shouldBe 26
    }

    test("part1 input") {
        day15.part1(loadResource("input"), 2000000) shouldBe 4737443
    }

    test("test combine intervals method3") {
        val map = mapOf<Long, MutableList<Pair<Long,Long>>>(
            11L to mutableListOf<Pair<Long,Long>>(
                Pair(-496790L, 3358877L),
                Pair(3230947L, 4427873L),
            )
        )
        day15.combineIntervals(map[11]!!).size shouldBe 1
    }

    test("test combine intervals method") {
        val map = mapOf<Long, MutableList<Pair<Long,Long>>>(
            11L to mutableListOf<Pair<Long,Long>>(
                Pair(2L, 2L),
                Pair(11L, 13L),
                Pair(3L, 13L),
                Pair(-3L, 3L),
                Pair(15L, 25L),
                Pair(15L, 17L),
                )
        )
        day15.combineIntervals(map[11]!!).size shouldBe 2
    }

    test("test combine intervals method 2") {
        val map = mapOf<Long, MutableList<Pair<Long,Long>>>(
            11L to mutableListOf<Pair<Long,Long>>(
                Pair(-496790L, 1755208L),
                Pair(1689869L, 2744725L),
                Pair(3230947L, 3922655L),
                Pair(1038689L, 1099373L),
                Pair(1435991L, 1753657L),
                Pair(280784L, 1722120L),
                Pair(2072695L, 3200959L),
                Pair(3520555L, 4427873L),
                Pair(2740973L, 3358877L)
            )
        )
        day15.combineIntervals(day15.combineIntervals(day15.combineIntervals(map[11L]!!))).size shouldBe 1
    }

    test("part2 example") {
        day15.part2(loadResource("example"), 20) shouldBe 56000011
    }

    test("part2 input") {
        day15.part2(loadResource("input"), 4000000) shouldBe 11482462818989L
    }
}) {
    companion object {
        fun loadResource(file: String): String {
            val filename = "/${
                this.javaClass.name.lowercase()
                    .replace("test\$companion", "")
            }/$file.txt"
            return this::class.java.getResource(filename).readText()
        }
    }
}


