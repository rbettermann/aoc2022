import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day14Test : FunSpec({
    val day14 = Day14()

    test("part1 example") {
        day14.part1(loadResource("example")) shouldBe 24
    }

    test("part1 input") {
        day14.part1(loadResource("input")) shouldBe 578
    }

    test("part2 example") {
        day14.part2(loadResource("example")) shouldBe 93
    }

    test("part2 input") {
        day14.part2(loadResource("input")) shouldBe 24377
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


