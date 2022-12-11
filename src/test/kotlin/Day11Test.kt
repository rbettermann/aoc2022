import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day11Test : FunSpec({
    val day11 = Day11()

    test("part1 example") {
        day11.part1(loadResource("example")) shouldBe 10605
    }

    test("part1 input") {
        day11.part1(loadResource("input")) shouldBe 50830
    }

    test("part2 example") {
        day11.part2(loadResource("example")) shouldBe 2713310158
    }

    test("part2 input") {
        day11.part2(loadResource("input")) shouldBe 14399640002
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


