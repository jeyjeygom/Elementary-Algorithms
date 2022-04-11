package serie1.problema

import problema.createReader
import problema.createWriter
import java.util.*
import kotlin.system.measureTimeMillis


fun firstImpl(cmpWords: Array<String>, cmpFiles: Array<String>, outFile: String) {
    val comparedWords = MutableList(0) { Pair("", 0) }
    val leftWord = cmpWords[0]
    val rightWord = cmpWords[1]
    // creates readers and offers the first words from each file to priorityQueue with the index of each reader
    val readers = cmpFiles.map { createReader(it) }
    val priority = PriorityQueue<Pair<String, Int>>  { a, b -> a.first.compareTo(b.first)}
    readers.forEachIndexed { index, element -> priority.offer(Pair(element.readLine(), index)) }

    var prevWord = ""

    while (priority.isNotEmpty()){
        val currWord = priority.poll()
        if(currWord.first in leftWord..rightWord && currWord.first != prevWord){ // currWord between leftWord and rightWord and different from prevWord
            comparedWords.add(currWord)
            prevWord = currWord.first
        }
        val nextWord = readers[currWord.second].readLine()
        if( nextWord !=null) priority.offer(Pair(nextWord,currWord.second))
    }

    val finalWordsList = Array(comparedWords.size){ comparedWords[it]}
    val out = createWriter(outFile)
    finalWordsList.forEach { out.println(it.first) }
    out.close()
}

fun main(args : Array<String>) {
    val cmpWords = arrayOf(args[1],args[2])
    val outFile = args[3]
    val cmpFiles = Array(args.size - 4 ){ args[4 + it] }
    val time = measureTimeMillis {
        firstImpl(cmpWords, cmpFiles, outFile)
    }
    println("$time seconds")
}
