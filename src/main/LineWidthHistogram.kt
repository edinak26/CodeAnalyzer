package main

class LineWidthHistogram {
    val linesWidth = mutableMapOf<Int, Int>()

    fun addLine(lineNumber: Int, lineWidth: Int) =
        linesWidth.put(lineNumber, lineWidth)

    fun getLinesForWidth(width: Int): MutableList<Int> =
        linesWidth.filter { it.value == width }.map { it.key }.toMutableList()

    fun getWidths(): List<Int> = linesWidth.values.toList()

}