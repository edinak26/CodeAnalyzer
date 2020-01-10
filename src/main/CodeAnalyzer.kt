package main

import java.io.File
import java.util.*

class CodeAnalyzer {
    var maxLineWidth = 0
    var widestLineNumber = 0
    var totalChars = 0
    val lineWidthHistogram = LineWidthHistogram()
    val lineCount get() = lineWidthHistogram.lineCount
    val meanLineWidth get() = totalChars.toDouble() / lineCount

    private val sortedWidths get() = lineWidthHistogram.getWidths().sorted()

    fun findJavaFiles(parentDirectory: File): List<File> {
        val files = ArrayList<File>()
        findJavaFiles(parentDirectory, files)
        return files
    }

    private fun findJavaFiles(parentDirectory: File, files: MutableList<File>) {
        val directoryFiles = parentDirectory.listFiles() ?: emptyArray()
        for (file in directoryFiles) {
            if (file.isDirectory)
                findJavaFiles(file, files)
            else if (file.name.endsWith(".java"))
                files.add(file)
        }
    }

    fun analyzeFile(javaFile: File) =
        javaFile.readLines().forEachIndexed { lineCount, line -> analyzeLine(lineCount, line) }

    private fun analyzeLine(lineCount: Int, line: String) {
        totalChars += line.length
        lineWidthHistogram.addLine(lineCount, line)
        recordWidestLine(lineCount, line)
    }

    private fun recordWidestLine(lineCount: Int, line: String) {
        if (line.length > maxLineWidth) {
            maxLineWidth = line.length
            widestLineNumber = lineCount
        }
    }

    fun calcMedianLineWidth(): Int =
        if (sortedWidths.isNotEmpty())
            sortedWidths[lineCount / 2]
        else
            throw Error("Cannot cacl median without lines")
}