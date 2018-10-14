package com.trayis.simplikmvvm.utils

import android.util.Log

class Logging private constructor() {

    interface Logger {
        fun log(level: Int, tag: String, msg: String)
        fun log(level: Int, tag: String, msg: String, e: Throwable?)
    }

    companion object {
        var logger: Logger? = null

        fun initLogging() {
            logger = object : Logger {
                override fun log(level: Int, tag: String, msg: String, e: Throwable?) {
                    Log.println(level, tag, msg)
                    e?.let {
                        var stackTraceString = Log.getStackTraceString(e)
                        Log.println(level, tag, stackTraceString)
                    }
                }

                override fun log(level: Int, tag: String, msg: String) {
                    Log.println(level, tag, msg);
                }
            }
        }

        fun d(tag: String, msg: String) {
            logger?.log(Log.DEBUG, tag, msg)
        }

        fun d(tag: String, msg: String?, e: Throwable?) {
            logger?.log(Log.DEBUG, tag, msg ?: "", e)
        }

        fun e(tag: String, msg: String) {
            logger?.log(Log.ERROR, tag, msg)
        }

        fun e(tag: String, msg: String?, e: Throwable?) {
            logger?.log(Log.ERROR, tag, msg ?: "", e)
        }

        fun i(tag: String, msg: String) {
            logger?.log(Log.INFO, tag, msg)
        }

        fun i(tag: String, msg: String?, e: Throwable?) {
            logger?.log(Log.INFO, tag, msg ?: "", e)
        }

        fun v(tag: String, msg: String) {
            logger?.log(Log.VERBOSE, tag, msg)
        }

        fun v(tag: String, msg: String?, e: Throwable?) {
            logger?.log(Log.VERBOSE, tag, msg ?: "", e)
        }

        fun w(tag: String, msg: String) {
            logger?.log(Log.WARN, tag, msg)
        }

        fun w(tag: String, msg: String?, e: Throwable?) {
            logger?.log(Log.WARN, tag, msg ?: "", e)
        }
    }

}