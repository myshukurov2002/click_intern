fun sumOfOdds(nums: List<Int>): Int = nums.filter { it % 2 != 0 }.sum()

fun main() {
    println(sumOfOdds(listOf(1, 2, 3, 4, 5)))
    println(sumOfOdds(listOf(2, 4, 6)))
    println(sumOfOdds(emptyList()))
}
