package serie1.problema

import problema.createReader
import problema.createWriter
import kotlin.system.measureTimeMillis

class MyPriority(aSize : Int, cmp: (Pair<String,Int>,Pair<String,Int>)-> Int){

    private var size = 0
    var heap = Array(aSize){Pair("",0)}

    fun isEmpty(): Boolean{
        return size == 0
    }
    private fun peek(): Pair<String,Int>{
        return heap[0]
    }

    val compare = cmp

    private fun exchange(array: Array<Pair<String, Int>>, left: Int, right: Int) {
        val tmp = array[left]
        array[left] = array[right]
        array[right] = tmp
    }

    private fun heapify(i: Int){
        val left = (2*i) + 1
        val right = (2*i) + 2
        var parent = i
        if(left < size && compare(heap[left], heap[parent]) < 0 ){
            parent = left
        }
        if(right < size && compare(heap[right], heap[parent]) < 0 ){
            parent = right
        }
        if(parent == i) return
        exchange(heap,i, parent)
        heapify(parent)
    }

    // gets the value at the head of heap
    fun poll(): Pair<String, Int>{
        val headValue = peek()
        heap[0] = heap[--size]
        heapify(0)
        return headValue
    }

    // maintains heap organized
    private fun decreaseKey(key: Int){
        var parent = (key - 1) / 2
        var currVal = key

        while ( parent != currVal && compare(heap[currVal], heap[parent]) < 0){
            exchange(heap, parent, currVal)
            currVal = parent
            parent = (currVal -1 ) / 2
        }

    }
    //adds value to last position of the heap
    fun offer(value : Pair<String,Int>){
        if(heap.isEmpty())
            return
        heap[size] = value
        size++
        decreaseKey(size - 1)
    }
}



fun cmpString(left : Pair<String,Int>, right : Pair<String,Int>) = left.first.compareTo(right.first)


fun secondImpl(cmpWords: Array<String>, cmpFiles: Array<String>, outFile: String) {

    val comparedWords = MutableList(0) { Pair("", 0) }
    val leftWord = cmpWords[0]
    val rightWord = cmpWords[1]

    // creates readers and offers the first words from each file to priorityQueue with the index of each reader
    val priority = MyPriority(cmpFiles.size, ::cmpString )
    val readers = cmpFiles.map { createReader(it) }
    readers.forEachIndexed { index, element -> priority.offer(Pair(element.readLine(), index)) }

    var prevWord = ""

    while(!priority.isEmpty()){
        val currWord = priority.poll()

        if(currWord.first in leftWord..rightWord && currWord.first != prevWord){ // currWord between leftWord and rightWord and different from prevWord
            comparedWords.add(currWord)
            prevWord = currWord.first
        }
        val nextString = readers[currWord.second].readLine()
        if (nextString != null)
            priority.offer(Pair(nextString, currWord.second))
    }

    val out = createWriter(outFile)
    comparedWords.forEach { out.println(it.first) }
    out.close()

}

fun main(args: Array<String>){
    val cmpWords = arrayOf(args[1],args[2])
    val outFile = args[3]
    val cmpFiles = Array(args.size - 4 ){ args[4 + it] }
    val time = measureTimeMillis {
        secondImpl(cmpWords, cmpFiles, outFile)
    }
    println("${time / 1000}.${time % 1000} seconds")
}
