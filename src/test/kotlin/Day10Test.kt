import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day10Test : FunSpec({
    val day10 = Day10()

    test("part1 example2") {
        day10.part1(loadResource("example2")) shouldBe 13140
    }

    test("part1 input") {
        day10.part1(loadResource("input")) shouldBe 12980
    }

    test("part2 example") {
        day10.part2(loadResource("example2")) shouldBe
                """
                    
                    ##..##..##..##..##..##..##..##..##..##..
                    ###...###...###...###...###...###...###.
                    ####....####....####....####....####....
                    #####.....#####.....#####.....#####.....
                    ######......######......######......####
                    #######.......#######.......#######.....
                """.trimIndent()
    }

    test("part2 input") {
        day10.part2(loadResource("input")) shouldBe
                """
                    
                    ###..###....##.#....####.#..#.#....###..
                    #..#.#..#....#.#....#....#..#.#....#..#.
                    ###..#..#....#.#....###..#..#.#....#..#.
                    #..#.###.....#.#....#....#..#.#....###..
                    #..#.#.#..#..#.#....#....#..#.#....#....
                    ###..#..#..##..####.#.....##..####.#....
                """.trimIndent()
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


