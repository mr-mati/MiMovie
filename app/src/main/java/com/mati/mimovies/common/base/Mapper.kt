package com.mati.mimovies.common.base

interface Mapper<F, T> {

    fun fromMap(from: F): T

}