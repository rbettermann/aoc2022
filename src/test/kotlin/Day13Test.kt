import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day13Test : FunSpec({
    val day13 = Day13()

    test("part1 example") {
        day13.part1(loadResource("example")) shouldBe 13
    }

    test("part1 input") {
        day13.part1(loadResource("input")) shouldBe 5675
    }

    test("part2 example") {
        day13.part2(loadResource("example")) shouldBe 140
    }

    test("part2 input") {
        day13.part2(loadResource("input")) shouldBe 20383
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


