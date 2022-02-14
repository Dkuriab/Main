package com.onlineshop.networking

/**
 * Sealed class for response statuses, have three data classes:
 *  - [Success] - result available
 *  - [Error] - the request ended with an error
 *  - [Loading] - the result has not been received yet
 */
sealed class Resource<out T>(open val data: T?, open val message: String?)

data class Success<T>(override val data: T?) : Resource<T>(data, null)
data class Error<T>(override val data: T?, override val message: String) : Resource<T>(data, message)
data class Loading<T>(override val data: T?) : Resource<T>(data, null)
