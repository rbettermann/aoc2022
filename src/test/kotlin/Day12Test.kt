import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day12Test : FunSpec({
    val day12 = Day12()

    test("part1 example") {
        day12.part1(loadResource("example")) shouldBe 31
    }

    test("part1 input") {
        day12.part1(loadResource("input")) shouldBe 330
    }

    test("part2 example") {
        day12.part2(loadResource("example")) shouldBe 29
    }

    test("part2 input") {
        day12.part2(loadResource("input")) shouldBe 321
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


