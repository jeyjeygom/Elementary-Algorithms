package serie1
data class Point(var x:Int, var y:Int)

fun upperBound(a: IntArray, l: Int, r: Int, element: Int): Int {
    if(a.isEmpty()) return -1
    if(a.size == 1)
        return if(a[a.size-1] <= element) a.size-1
        else return -1
    var left = l
    var right = r
    while(left <= right){
        var mid = (left + right)/2
        if(mid == a.size - 1 && a[mid]<= element) return mid
        if(a[mid] == element && a[mid+1] != element) return mid
        if(a[mid] > element) right = mid -1
        else left = mid +1
    }
    return  -1
}
fun countIncreasingSubArrays(arr: IntArray): Int {
    var count = 0
    for (i in arr.indices) {
        for (j in i + 1 until arr.size) {
            var curr = arr[j]
            var prev = arr[j-1]
            if (curr > prev) count++
            else break
        }
    }
    return count
}
fun countEquals(points1: Array<Point>, points2: Array<Point>, cmp: (p1:Point, p2:Point)-> Int): Int {
    if(points1.isEmpty() || points2.isEmpty()) return 0
    var count = 0
    var ip1 = 0
    var ip2 = 0
    while(ip1<points1.size && ip2<points2.size){
        val p1 = points1[ip1]
        val p2 = points2[ip2]
        val cmp = cmp(p1,p2)
        if(cmp == 0) {
            count++
            ip1++
            ip2++
        }
        else if (cmp >0) ip2++
        else ip1++
    }
    return count
}
fun mostLonely(a: IntArray): Int {
    if (a.isEmpty()) return -1
    if (a.size==1) return a[0]
    mergeSort(a)
    if(a.size==2) return a[0]
    var mostLonely :Int
    var maxRight = a[1]-a[0]
    var maxLeft = a[a.size-1]-a[a.size-2]
    if (maxLeft<maxRight) mostLonely = a[0] else mostLonely = a[a.size-1]
    for (i in 1..a.size-2){
        val diffLeft = a[i]-a[i-1]
        val diffRight = a[i+1] - a[i]
        if(diffLeft>maxLeft && diffRight>maxRight) {
            maxRight = diffRight
            maxLeft = diffLeft
            mostLonely = a[i]
        }
    }
    return mostLonely
}
fun mergeSort(a: IntArray, l: Int = 0, r: Int = a.size - 1) {
    if (l < r) {
        val mid: Int = (l + r) / 2
        val aLeft = IntArray(mid - l + 1)
        val aRight = IntArray(r - mid)
        divide(a, aLeft, aRight, l, mid, r)
        mergeSort(aLeft, 0, mid)
        mergeSort(aRight, 0, r - mid - 1)
        merge(a, aLeft, aRight, l, mid, r)
    }
}
fun divide(a: IntArray, aLeft: IntArray, aRight: IntArray, l: Int, mid: Int, r: Int) {
    for (i in l..mid) {
        aLeft[i] = a[i]
    }
    for (i in mid + 1..r) {
        aRight[i - mid - 1] = a[i]
    }
}
fun merge(a: IntArray, aLeft: IntArray, aRight: IntArray, l: Int, mid: Int, r: Int) {
    var i: Int = 0
    var j: Int = 0
    var k: Int = l
    while (i < aLeft.size && j < aRight.size) {
        if (aLeft[i] < aRight[j]) {
            a[k++] = aLeft[i++]
        } else {
            a[k++] = aRight[j++]
        }
    }
    while (i < aLeft.size) {
        a[k++] = aLeft[i++]
    }
    while (j < aRight.size) {
        a[k++] = aRight[j++]
    }
}