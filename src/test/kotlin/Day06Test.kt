import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day06Test : FunSpec({
    val day06 = Day06()

    test("part1 example") {
        day06.part1(loadResource("example")) shouldBe 7
    }
    test("part1 example2") {
        day06.part1("bvwbjplbgvbhsrlpgdmjqwftvncz") shouldBe 5
    }
    test("part1 example3") {
        day06.part1("nppdvjthqldpwncqszvftbrmjlhg") shouldBe 6
    }
    test("part1 example4") {
        day06.part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") shouldBe 10
    }
    test("part1 example5") {
        day06.part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") shouldBe 11
    }
    test("part1 input") {
        day06.part1(loadResource("input")) shouldBe 1651
    }

    test("part2 example") {
        day06.part2(loadResource("example")) shouldBe 19
    }

    test("part2 input") {
        day06.part2(loadResource("input")) shouldBe 3837
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


