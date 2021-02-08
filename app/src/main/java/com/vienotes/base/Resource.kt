package com.vienotes.base

open class Resource<out T> constructor(val status: Status, val data: T?, private val exception: Exception?) {

    enum class Status {
        SUCCESS, ERROR, LOADING, CANCEL, DEFAULT
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(exception: Exception? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                null,
                exception
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }

        fun <T> cancel(data: T? = null, exception: Exception? = null): Resource<T> {
            return Resource(
                Status.CANCEL,
                data,
                exception
            )
        }

        fun <T> default(data: T? = null, exception: Exception? = null): Resource<T> {
            return Resource(
                Status.DEFAULT,
                data,
                exception
            )
        }
    }

}