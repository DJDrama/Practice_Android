package com.dj.hero_datasource_test.network

sealed class HeroServiceResponseType{
    object EmptyList: HeroServiceResponseType()
    object MalformedData: HeroServiceResponseType()
    object GoodData: HeroServiceResponseType()
    object Http404: HeroServiceResponseType()
}
