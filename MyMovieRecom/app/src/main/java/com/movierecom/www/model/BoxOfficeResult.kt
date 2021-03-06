package com.movierecom.www.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class BoxOfficeResult(
    @Json(name = "boxofficeType")
    val boxOfficeType: String,
    val showRange: String,
    val dailyBoxOfficeList: List<DailyBoxOffice>
)

@Parcelize
data class DailyBoxOffice(
    @Json(name = "rnum")
    val rNum: String,
    val rank: String,
    @Json(name = "rankInten")
    val rankInTen: String,
    val rankOldAndNew: String,
    val movieCd: String,
    val movieNm: String,
    val openDt: String,
    val salesAmt: String,
    val salesShare: Float,
    @Json(name = "salesInten")
    val salesInTen: String,
    val salesChange: String,
    val salesAcc: String,
    val audiCnt: String,
    @Json(name = "audiInten")
    val audiInTen: String,
    val audiChange: String,
    val audiAcc: String,
    @Json(name = "scrnCnt")
    val screenCnt: String,
    val showCnt: String,
    var naverMovie: NaverMovie?=null
) : Parcelable