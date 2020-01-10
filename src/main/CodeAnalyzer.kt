package main

import java.io.File
import java.util.*

class CodeAnalyzer {
    var lineCount = 0
    var maxLineWidth = 0
    var widestLineNumber = 0
    var totalChars = 0
    val lineWidthHistogram = LineWidthHistogram()
    val meanLineWidth get() = totalChars.toDouble()/lineCount

    fun findJavaFiles(parentDirectory: File): List<File> {
        val files = ArrayList<File>()
        findJavaFiles(parentDirectory, files)
        return files
    }

    private fun findJavaFiles(parentDirectory: File, files: MutableList<File>) {
        for (file in parentDirectory.listFiles()!!) {
            if (file.name.endsWith(".java"))
                files.add(file)
            else if (file.isDirectory)
                findJavaFiles(file, files)
        }
    }

    fun analyzeFile(javaFile: File) =
        javaFile.forEachLine { measureLine(it) }

    private fun measureLine(line: String) {
        lineCount++
        val lineSize = line.length
        totalChars += lineSize
        lineWidthHistogram.addLine(lineSize, lineCount)
        recordWidestLine(lineSize)
    }

    private fun recordWidestLine(lineSize: Int) {
        if (lineSize > maxLineWidth) {
            maxLineWidth = lineSize
            widestLineNumber = lineCount
        }
    }

    fun getMedianLineWidth(): Int {
        val sortedWidths = getSortedWidths()
        var cumulativeLineCount = 0
        for (width in sortedWidths) {
            cumulativeLineCount += lineCountForWidth(width)
            if (cumulativeLineCount > lineCount / 2) return width
        }
        throw Error("Cannot get here")
    }

    private fun lineCountForWidth(width: Int) =
        lineWidthHistogram.getLinesForWidth(width).size

    private fun getSortedWidths(): List<Int> =
        lineWidthHistogram.getWidths().sorted()
}