package fastcampus.aop.part5.chapter06.data.preference

interface PreferenceManager {

    fun getLong(key: String): Long?

    fun putLong(key: String, value: Long)
}
