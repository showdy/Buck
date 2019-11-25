package com.buck.data.model


/**
 * 网络请求结果的基类
 */
//sealed class RequestResult<out T : Any> {
//
//    data class Success<out T : Any>(val data: T) : RequestResult<T>()
//
//    data class Error(val exception: Exception) : RequestResult<Nothing>()
//
//    override fun toString(): String {
//        return when (this) {
//            is Success<*> -> "Success[data=$data]"
//
//            is Error -> "Error[exception=$exception]"
//        }
//    }
//}

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}


data class RequestResult<out T>(

    val status: Status? = null,

//    val total: Int? = 0,

    val data: T? = null,

    val code: Int? = null,

    val message: String? = null
) {
    //三个静态对象
    companion object {
        //正在加载
        fun <T> loading(data: T? = null) = RequestResult(Status.LOADING, data)

        fun <T> success(data: T? = null) = RequestResult(Status.SUCCESS, data)

        fun <T> error(msg: String? = null, data: T? = null) = RequestResult(Status.ERROR, data, message = msg)

    }


    fun isLoading(): Boolean = status == Status.LOADING
    fun isSuccess(): Boolean = status == Status.SUCCESS
    fun isError(): Boolean = status == Status.ERROR

}