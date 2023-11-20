package com.example.contestifyme.features.problemsFeature.problemsConstants

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
}