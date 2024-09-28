package com.prafull.contestifyme.app.contestsFeature.domain.model.particularContest

import com.google.gson.annotations.SerializedName

data class ParticularContestDto(
    @SerializedName("result")
    val result: Result,
    @SerializedName("status")
    val status: String
)

data class Row(
    @SerializedName("party")
    val party: Party,
    @SerializedName("penalty")
    val penalty: Int,
    @SerializedName("points")
    val points: Int,
    @SerializedName("problemResults")
    val problemResults: List<ProblemResult>,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("successfulHackCount")
    val successfulHackCount: Int,
    @SerializedName("unsuccessfulHackCount")
    val unsuccessfulHackCount: Int
)

data class Result(
    @SerializedName("contest")
    val contest: Contest,
    @SerializedName("problems")
    val problems: List<Problem>,
    @SerializedName("rows")
    val rows: List<Row>
)

data class ProblemResult(
    @SerializedName("bestSubmissionTimeSeconds")
    val bestSubmissionTimeSeconds: Int,
    @SerializedName("points")
    val points: Int,
    @SerializedName("rejectedAttemptCount")
    val rejectedAttemptCount: Int,
    @SerializedName("type")
    val type: String
)

data class Problem(
    @SerializedName("contestId")
    val contestId: Int,
    @SerializedName("index")
    val index: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("points")
    val points: Int,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("type")
    val type: String
)

data class Party(
    @SerializedName("contestId")
    val contestId: Int,
    @SerializedName("ghost")
    val ghost: Boolean,
    @SerializedName("members")
    val members: List<Member>,
    @SerializedName("participantType")
    val participantType: String,
    @SerializedName("room")
    val room: Int,
    @SerializedName("startTimeSeconds")
    val startTimeSeconds: Int
)

data class Member(
    @SerializedName("handle")
    val handle: String
)

data class Contest(
    @SerializedName("durationSeconds")
    val durationSeconds: Int,
    @SerializedName("frozen")
    val frozen: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phase")
    val phase: String,
    @SerializedName("relativeTimeSeconds")
    val relativeTimeSeconds: Int,
    @SerializedName("startTimeSeconds")
    val startTimeSeconds: Int,
    @SerializedName("type")
    val type: String
)