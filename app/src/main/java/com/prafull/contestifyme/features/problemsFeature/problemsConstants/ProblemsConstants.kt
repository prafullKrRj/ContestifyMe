package com.prafull.contestifyme.features.problemsFeature.problemsConstants

object ProblemsConstants {

    fun getProblems(): String {
        return "https://codeforces.com/api/problemset.problems"
    }
    fun getProblemsByTags(tags: List<String>) : String {
        var tagsString = ""
        tags.forEach {
            tagsString += "$it;"
        }
        return "https://codeforces.com/api/problemset.problems?tags=$tagsString"
    }
    fun getProblemsByRating(rating: Int) : String {
        return "https://codeforces.com/api/problemset.problems?minRating=$rating"
    }
    val tags: List<String> = listOf<String>(
        "2-sat",
        "binary search",
        "bitmasks",
        "brute force",
        "chinese remainder theorem",
        "combinatorics",
        "constructive algorithms",
        "data structures",
        "dfs and similar",
        "divide and conquer",
        "dp",
        "dsu",
        "expression parsing",
        "fft",
        "flows",
        "games",
        "geometry",
        "graph matchings",
        "graphs",
        "greedy",
        "hashing",
        "implementation",
        "interactive",
        "math",
        "matrices",
        "meet-in-the-middle",
        "number theory",
        "probabilities",
        "schedules",
        "shortest paths",
        "sortings",
        "string suffix structures",
        "strings",
        "ternary search",
        "trees",
        "two pointers"
    )
    val array = intArrayOf(
        800,
        900,
        1000,
        1100,
        1200,
        1300,
        1400,
        1500,
        1600,
        1700,
        1800,
        1900,
        2000,
        2100,
        2200,
        2300,
        2400,
        2500,
        2600,
        2700,
        2800,
        2900,
        3000,
        3100,
        3200,
        3300,
        3400,
        3500
    )
}