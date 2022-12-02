import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day02Test : FunSpec({
    val day02 = Day02()

    test("part1 example") {
        day02.part1(loadResource("example")) shouldBe 15
    }

    test("part1 input") {
        day02.part1(loadResource("input")) shouldBe 8890
    }

    test("part2 example") {
        day02.part2(loadResource("example")) shouldBe 12
    }

    test("part2 input") {
        day02.part2(loadResource("input")) shouldBe 10238
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


