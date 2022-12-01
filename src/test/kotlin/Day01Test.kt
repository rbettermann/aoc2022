import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day01Test : FunSpec({
    val day01 = Day01()

    test("part1 example") {
        day01.part1(loadResource("example")) shouldBe 24000
    }

    test("part1 input") {
        day01.part1(loadResource("input")) shouldBe 66186
    }

    test("part2 example") {
        day01.part2(loadResource("example")) shouldBe 45000
    }

    test("part2 input") {
        day01.part2(loadResource("input")) shouldBe 196804
    }
}) {
    companion object {
        fun loadResource(file: String): String {
            val filename = "/${this.javaClass.name.lowercase().replace("test\$companion","")}/$file.txt"
            return this::class.java.getResource(filename).readText()
        }
    }
}


