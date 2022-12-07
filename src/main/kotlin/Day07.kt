class Day07 {
    enum class NodeType {
        DIR,
        FILE
    }

    class Node(
        var name: String,
        var ancestors: MutableList<Node>,
        var type: NodeType,
        var predecessor: Node?
    ) {
        fun calcSize(): Int {
            return if (this.type == NodeType.FILE) {
                this.name.split(" ")[0].toInt()
            } else {
                this.ancestors.sumOf { it.calcSize() }
            }
        }
    }

    private fun parseToFileTree(input: String): Node {
        val root = Node(
            name = "/",
            ancestors = mutableListOf(),
            type = NodeType.DIR,
            predecessor = null,
        )
        var fp = root

        input.split("\n").drop(1).forEach {
            if (it.startsWith("$ cd ..")) {
                fp = fp.predecessor!!
            } else if (it.startsWith("$ cd")) {
                val newDir = it.replace("$ cd", "").trim()
                fp = fp.ancestors.filter { it.type == NodeType.DIR }
                    .filter { it.name == newDir }.first()
            } else if (it.startsWith("$ ls")) {
                // do nothing
            } else if (it.startsWith("dir")) {
                fp.ancestors.add(
                    Node(
                        name = it.replace("dir", "").trim(),
                        ancestors = mutableListOf(),
                        type = NodeType.DIR,
                        predecessor = fp
                    )
                )
            } else {
                fp.ancestors.add(
                    Node(
                        name = it,
                        ancestors = mutableListOf(),
                        type = NodeType.FILE,
                        predecessor = fp
                    )
                )
            }
        }
        return root
    }

    private fun iterateOverAllDirs(
        fp: Node,
        filter: (Int) -> Boolean
    ): List<Node> {
        assert(fp.type == NodeType.DIR)
        return listOfNotNull(if (filter(fp.calcSize())) fp else null) +
                fp.ancestors
                    .filter { it.type == NodeType.DIR }
                    .flatMap { iterateOverAllDirs(it, filter) }
    }

    fun part1(input: String): Int {
        val root = parseToFileTree(input)
        val border = 100000
        return iterateOverAllDirs(root)
        { dirSize -> dirSize < border }.sumOf { it.calcSize() }
    }

    fun part2(input: String): Int {
        val root = parseToFileTree(input)
        val border = 30000000 - (70000000 - root.calcSize())
        return iterateOverAllDirs(root)
        { dirSize -> dirSize >= border }.minOf { it.calcSize() }
    }
}