import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day05Test : FunSpec({
    val day05 = Day05()

    test("part1 example") {
        day05.part1(loadResource("example")) shouldBe "CMZ"
    }

    test("part1 input") {
        day05.part1(loadResource("input")) shouldBe "PSNRGBTFT"
    }

    test("part2 example") {
        day05.part2(loadResource("example")) shouldBe "MCD"
    }

    test("part2 input") {
        day05.part2(loadResource("input")) shouldBe "BNTZFPMMW"
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


