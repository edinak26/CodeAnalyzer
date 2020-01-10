package main

class LineWidthHistogram {
    val linesWidth = mutableMapOf<Int, Int>()
    val lineCount: Int get() = linesWidth.size

    fun addLine(lineNumber: Int, line: String) =
        linesWidth.put(lineNumber, line.length)

    fun getLinesForWidth(width: Int): MutableList<Int> =
        linesWidth.filter { it.value == width }.map { it.key }.toMutableList()

    fun getWidths(): List<Int> = linesWidth.values.toList()

}