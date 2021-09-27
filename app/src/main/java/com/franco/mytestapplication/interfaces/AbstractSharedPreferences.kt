package com.franco.mytestapplication.interfaces

/**
 * A wrapper for [SharedPreferences] to avoid passing a context directly to save/fetch arbitrary
 * data
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 7:19 p.m.
 */
interface AbstractSharedPreferences {
    /**
     * Saves a string
     * @param key the key to identify the data.
     * @param value the value to save
     * @param async flag to indicate if data must be saved in synchronous or asynchronous
     * way, for example, making use of another thread.
     */
    fun saveString(key: String, value: String)

    /**
     * Saves a integer
     * @param key the key to identify the data.
     * @param value the value to save
     * @param async flag to indicate if data must be saved in synchronous or asynchronous
     * way, for example, making use of another thread.
     */
    fun saveInt(key: String, value: Int)

    /**
     * Saves a float number
     * @param key the key to identify the data.
     * @param value the value to save
     * @param async flag to indicate if data must be saved in synchronous or asynchronous
     * way, for example, making use of another thread.
     */
    fun saveFloat(key: String, value: Float)

    /**
     * Saves a long number
     * @param key the key to identify the data.
     * @param value the value to save
     * @param async flag to indicate if data must be saved in synchronous or asynchronous
     * way, for example, making use of another thread.
     */
    fun saveLong(key: String, value: Long)

    /**
     * Saves a boolean
     * @param key the key to identify the data.
     * @param value the value to save
     * @param async flag to indicate if data must be saved in synchronous or asynchronous
     * way, for example, making use of another thread.
     */
    fun saveBoolean(key: String, value: Boolean)


    /**
     * Returns a string corresponding to [key].
     * @param key The key to retrieve its value. Could be null value.
     * @return string value or null
     */
    fun getString(key: String): String?

    /**
     * Returns a int corresponding to [key].
     * @param key The key to retrieve its value. Could be null value.
     * @return int value or null
     */
    fun getInt(key: String): Int

    /**
     * Returns a float corresponding to [key].
     * @param key The key to retrieve its value. Could be null value.
     * @return int value or null
     */
    fun getFloat(key: String): Float

    /**
     * Returns a long corresponding to [key].
     * @param key The key to retrieve its value. Could be null value.
     * @return long value or null
     */
    fun getLong(key: String): Long

    /**
     * Returns a boolean corresponding to [key].
     * @param key The key to retrieve its value. Could be null value.
     * @return boolean value or null
     */
    fun getBoolean(key: String): Boolean

    /**
     * Deletes the element with [key] key.
     * Default implementation does nothing.
     */
    fun delete(key: String) {}

    /**
     * Verifies the element under the [key] exist into storage.
     * @return true if element for such key exist, false otherwise.
     */
    fun contains(key: String): Boolean

    /**
     * Remove an existing key from storage.
     * @param key The key of the mapping to remove.
     * @return Returns the value that was stored under the key, or null if there
     * was no such key.
     */
    fun <T> remove(key: String): T?

    /**
     *  Removes all the elements from storage.
     */
    fun removeAll()
}