import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day08Test : FunSpec({
    val day08 = Day08()

    test("part1 example") {
        day08.part1(loadResource("example")) shouldBe 21
    }

    test("part1 input") {
        day08.part1(loadResource("input")) shouldBe 1870
    }

    test("part2 example") {
        day08.part2(loadResource("example")) shouldBe 8
    }

    test("part2 input") {
        day08.part2(loadResource("input")) shouldBe -1
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


