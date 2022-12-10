import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day09Test : FunSpec({
    val day09 = Day09()

    test("part1 example") {
        day09.part1(loadResource("example")) shouldBe 13
    }

    test("part1 input") {
        day09.part1(loadResource("input")) shouldBe 6190
    }

    test("part2 example") {
        day09.part2(loadResource("example")) shouldBe 1
    }

    test("part2 example2") {
        day09.part2(loadResource("example2")) shouldBe 36
    }

    test("part2 input") {
        day09.part2(loadResource("input")) shouldBe 2516
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


