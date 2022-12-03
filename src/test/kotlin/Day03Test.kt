import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day03Test : FunSpec({
    val day03 = Day03()

    test("part1 example") {
        day03.part1(loadResource("example")) shouldBe 157
    }

    test("part1 input") {
        day03.part1(loadResource("input")) shouldBe 7817
    }

    test("part2 example") {
        day03.part2(loadResource("example")) shouldBe 70
    }

    test("part2 input") {
        day03.part2(loadResource("input")) shouldBe 2444
    }

    test("priority") {
        day03.characterToPriority('a') shouldBe 1
        day03.characterToPriority('A') shouldBe 27
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


