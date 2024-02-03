package com.prafull.contestifyme.commons

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.prafull.contestifyme.features.problemsFeature.problemsConstants.ProblemsConstants
import com.prafull.contestifyme.features.profileFeature.constants.ProfileConstants
import com.prafull.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity

object GetChartData {
    /**
     *    This function returns a PieChartConfig object for the pie chart
     * */
    fun pieChartConfig(): PieChartConfig {
        return PieChartConfig(
            labelType = PieChartConfig.LabelType.VALUE,
            isAnimationEnable = true,
            showSliceLabels = true,
            animationDuration = 1500,
            isClickOnSliceEnabled = false
        )
    }

    /**
     *    This function returns a list of slices for the pie chart (verdicts)
     * */
    @SuppressLint("MutableCollectionMutableState")
    fun getVerdicts(user: UserInfoEntity): HashMap<String, Int> {
        if (user.subMissionInfo.isEmpty()) {
            return hashMapOf()
        }
        val verdictsMap: HashMap<String, Int> by mutableStateOf(hashMapOf())
        user.subMissionInfo.forEach {
            if (verdictsMap.containsKey(it.verdict)) {
                verdictsMap[it.verdict] = verdictsMap[it.verdict]!! + 1
            } else {
                verdictsMap[it.verdict] = 1
            }
        }
        return verdictsMap
    }
    fun getVerdictsList(verdicts: HashMap<String, Int>): List<PieChartData.Slice> {
        val list: List<PieChartData.Slice> = verdicts.keys.map {
            PieChartData.Slice(
                value = verdicts[it]!!.toFloat(),
                color = ProfileConstants.verdictsColors[it.uppercase()]!!.first,
                label = verdicts[it].toString(),
            )
        }
        return list
    }
    /**
     *      This function returns a PieChartData object for the pie chart (verdicts)
     * */
    fun getVerdictsData(verdicts: HashMap<String, Int>): PieChartData {
        return PieChartData(
            slices = getVerdictsList(verdicts),
            plotType = PlotType.Pie,
        )
    }
    /**
     *      This function returns a HashMap of tags with value 0
     * */

    fun getMap(): HashMap<String, Int> {
        val hashMap = hashMapOf<String, Int>()
        ProblemsConstants.tags.forEach {
            hashMap[it.lowercase()] = 0
        }
        return hashMap
    }
    fun getQuestionSolvedByTags(user: UserInfoEntity): HashMap<String, Int> {
        if (user.subMissionInfo.isEmpty()) {
            return hashMapOf()
        }
        val questionSolvedByTags: HashMap<String, Int> = getMap()
        user.subMissionInfo.forEach {
            it.tags.forEach { tag ->
                if (questionSolvedByTags.containsKey(tag.lowercase())) {
                    questionSolvedByTags[tag] = questionSolvedByTags[tag]!! + 1
                }
            }
        }
        return questionSolvedByTags
    }
    /**
     *      This function returns a PieChartData object for the donut chart (tags)
     * */
    fun getQuestionSolvedByTagsData(solvedByTags: HashMap<String, Int>): PieChartData {
        return PieChartData(
            slices = getTagsList(solvedByTags),
            plotType = PlotType.Donut,
        )
    }
    /**
     *      This function returns a list of slices for the donut chart (tags)
     * */
    private fun getTagsList(verdicts: HashMap<String, Int>): List<PieChartData.Slice> {
        val list: List<PieChartData.Slice> = verdicts.keys.map {
            PieChartData.Slice(
                value = verdicts[it]!!.toFloat(),
                color = ProfileConstants.solvedByTagsColor[it.lowercase()]!!,
                label = verdicts[it].toString(),
            )
        }
        return list
    }

    @SuppressLint("MutableCollectionMutableState")
    fun getQuestionSolvedByIndexData(user: UserInfoEntity): HashMap<String, Int> {
        if (user.subMissionInfo.isEmpty()) {
            return hashMapOf()
        }
        val questionSolvedByIndex: HashMap<String, Int> by mutableStateOf(hashMapOf())
        user.subMissionInfo.forEach {
            if (questionSolvedByIndex.containsKey(it.index)) {
                questionSolvedByIndex[it.index] = questionSolvedByIndex[it.index]!! + 1
            } else {
                questionSolvedByIndex[it.index] = 1
            }
        }
        return questionSolvedByIndex
    }
}