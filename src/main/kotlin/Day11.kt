import java.math.BigInteger
import kotlin.math.floor

class Day11 {

    class Monkey(
        var worryLevels: MutableList<Long>,

        val operatorLeft: String,
        val operator: String,
        val operatorRight: String,

        val test: Int,
        val testTrueMonkey: Int,
        val testFalseMonkey: Int,

        var inspectedItems: Int = 0
    )

    private fun String.toMonkey(): Monkey {

        val startingItems = Regex("Starting items:.*\n").find(this)!!.value.replace("Starting items:","").split(",").map { it.trim().toLong() }.toMutableList()
        val operation = Regex("Operation:.*\n").find(this)!!.value.replace("Operation: new =","").split(" ")
        val test = Regex("Test:.*\n").find(this)!!.value.replace("Test: divisible by ","").trim().toInt()
        val testTrue = Regex("If true:.*\n").find(this)!!.value.replace("If true: throw to monkey", "").trim().toInt()
        val testFalse = Regex("If false:.*").find(this)!!.value.replace("If false: throw to monkey", "").trim().toInt()


        return Monkey(
            worryLevels = startingItems,
            operatorLeft = operation[1].trim(),
            operator = operation[2].trim(),
            operatorRight = operation[3].trim(),
            test = test,
            testTrueMonkey = testTrue,
            testFalseMonkey = testFalse
        )
    }

    fun part1(input: String): Int {
        val monkeys = input.split("\n\n").map { it.toMonkey() }

        val rounds = 20
        (0 until rounds).forEach {
            println("Starting round $it")

            monkeys.forEachIndexed { index, monkey ->
                println("  Monkey $index:")
                monkey.worryLevels.forEach { level ->
                    monkey.inspectedItems++
                    println("    Inspecting an item with worry level $level")
                    val calculated = calculate(level, monkey)
                    println("        New worry level $calculated")
                    val bored = floor(calculated.toDouble() / 3.0).toLong()
                    println("        Bored level $bored")
                    val isDivisible = bored % monkey.test == 0L
                    println("        Bored level devisable by ${monkey.test}? $isDivisible")
                    if (isDivisible) {
                        monkeys[monkey.testTrueMonkey].worryLevels.add(bored)
                    } else {
                        monkeys[monkey.testFalseMonkey].worryLevels.add(bored)
                    }
                }
                monkey.worryLevels = mutableListOf()
            }

            monkeys.forEachIndexed { index, monkey ->
                println("Monkey $index: ${monkey.worryLevels.joinToString(", ")}")
            }
            println()
        }

        monkeys.forEachIndexed { index, monkey ->
            println("Monkey $index: ${monkey.inspectedItems}")
        }

        val top2 = monkeys.map { it.inspectedItems }.sortedDescending().take(2)
        return top2[0] * top2[1]
    }

    private fun calculate(level: Long, monkey: Monkey): Long {
        val a: Long
        val b: Long

        if (monkey.operatorLeft == "old")
            a = level
        else
            a = monkey.operatorLeft.toLong()

        if (monkey.operatorRight == "old")
            b = level
        else
            b = monkey.operatorRight.toLong()

        return when(monkey.operator) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            else -> throw NotImplementedError()
        }
    }

    fun part2(input: String): Long {
        val monkeys = input.split("\n\n").map { it.toMonkey() }

        val allDivisors = monkeys.map { it.test }.distinct().reduceRight{i, acc -> i * acc }

        val rounds = 10000
        (1 ..  rounds).forEach {
            //println("Starting round $it")

            monkeys.forEachIndexed { index, monkey ->
                monkey.worryLevels.forEach { level ->
                    monkey.inspectedItems++
                    val calculated = calculate(level, monkey) % allDivisors
                    val isDivisible = calculated % monkey.test == 0L
                    if (isDivisible) {
                        monkeys[monkey.testTrueMonkey].worryLevels.add(calculated)
                    } else {
                        monkeys[monkey.testFalseMonkey].worryLevels.add(calculated)
                    }
                }
                monkey.worryLevels = mutableListOf()
            }

            val rounds = listOf(1, 20, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000)
            if (rounds.contains(it)) {
//                monkeys.forEachIndexed { index, monkey ->
//                    println("Monkey $index: ${monkey.worryLevels.joinToString(", ")}")
//                }
//                println("Round $it")
//                monkeys.forEachIndexed { index, monkey ->
//                    println("Monkey $index: ${monkey.inspectedItems}")
//                }
//                println()
            }
        }

        monkeys.forEachIndexed { index, monkey ->
            println("Monkey $index: ${monkey.inspectedItems}")
        }

        val top2 = monkeys.map { it.inspectedItems.toLong() }.sortedDescending().take(2)
        return top2[0] * top2[1]
    }
}
