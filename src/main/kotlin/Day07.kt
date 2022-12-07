class Day07 {
    enum class NodeType {
        DIR,
        FILE
    }

    class Node(
        var name: String,
        var childs: MutableList<Node>,
        var type: NodeType,
        var predecessor: Node?,
        var size: Int = -1
    )

    private fun createFileTree(input: String): Node {
        val parsed = input.split("\n")

        val root = Node(
            name = "/",
            childs = mutableListOf(),
            type = NodeType.DIR,
            predecessor = null,
        )
        var fp = root

        parsed.drop(1).forEach {
            if (it.startsWith("$ cd ..")) {
                fp = fp.predecessor!!
            } else if (it.startsWith("$ cd")) {
                val newDir = it.replace("$ cd", "").trim()
                fp = fp.childs.filter { it.type == NodeType.DIR }
                    .filter { it.name == newDir }.first()
            } else if (it.startsWith("$ ls")) {
                // do nothing
            } else if (it.startsWith("dir")) {
                fp.childs.add(
                    Node(
                        name = it.replace("dir", "").trim(),
                        childs = mutableListOf(),
                        type = NodeType.DIR,
                        predecessor = fp
                    )
                )
            } else {
                fp.childs.add(
                    Node(
                        name = it,
                        childs = mutableListOf(),
                        type = NodeType.FILE,
                        predecessor = fp
                    )
                )
            }
        }
        root.size = calculateSize(root.childs)
        return root
    }

    private fun calculateSize(childs: MutableList<Node>): Int = childs.sumOf {
        if (it.type == NodeType.FILE) {
            it.size = it.name.split(" ")[0].toInt()
        } else {
            it.size = calculateSize(it.childs)
        }
        it.size
    }

    private fun iterateWithCondition(
        root: Node,
        border: Int,
        condition: (Int, Int) -> Boolean
    ): List<Node> {
        val res = mutableListOf<Node>()
        iterateOverAllDirs(root, border, res, condition)
        return res
    }

    private fun iterateOverAllDirs(
        fp: Node,
        border: Int,
        res: MutableList<Node>,
        condition: (Int, Int) -> Boolean
    ) {
        if (fp.type == NodeType.DIR && condition(fp.size, border)) {
            res.add(fp)
        }
        fp.childs.filter { it.type == NodeType.DIR }.forEach {
            iterateOverAllDirs(it, border, res, condition)
        }
    }

    fun part1(input: String): Int {
        val root = createFileTree(input)
        return iterateWithCondition(root, 100000)
        { a, b -> a < b }.sumOf { it.size }
    }

    fun part2(input: String): Int {
        val root = createFileTree(input)
        return iterateWithCondition(root, 30000000 - (70000000 - root.size))
        { a, b -> a >= b }.minOf { it.size }
    }
}