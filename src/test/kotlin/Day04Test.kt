import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day04Test : FunSpec({
    val day04 = Day04()

    test("part1 example") {
        day04.part1(loadResource("example")) shouldBe 2
    }

    test("part1 input") {
        day04.part1(loadResource("input")) shouldBe 515
    }

    test("part2 example") {
        day04.part2(loadResource("example")) shouldBe 4
    }

    test("part2 input") {
        day04.part2(loadResource("input")) shouldBe 883
    }

}) {
    companion object {
        fun loadResource(file: String): String {
            val filename = "/${this.javaClass.name.lowercase()
                .replace("test\$companion", "")}/$file.txt"
            return this::class.java.getResource(filename).readText()
        }
    }
}


