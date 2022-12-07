import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day07Test : FunSpec({
    val day07 = Day07()

    test("part1 example") {
        day07.part1(loadResource("example")) shouldBe 95437
    }

    test("part1 input") {
        day07.part1(loadResource("input")) shouldBe 1243729
    }

    test("part2 example") {
        day07.part2(loadResource("example")) shouldBe 24933642
    }

    test("part2 input") {
        day07.part2(loadResource("input")) shouldBe 4443914
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


