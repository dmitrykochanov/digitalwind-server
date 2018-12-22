package com.dmko.iconf.base

data class BaseResponse<T>(val data: T?, val success: Boolean)