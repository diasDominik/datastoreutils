package uk.dominikdias.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first

/**
 * A repository for managing preferences using Jetpack DataStore.
 *
 * This class provides a way to create and manage individual preferences,
 * allowing for saving, deleting, and observing preference values.
 *
 * @param dataStore The DataStore instance used for storing preferences.
 */
open class PreferencesRepository(private val dataStore: DataStore<Preferences>) : IPreferencesRepository {
    override fun <T> createPreference(
        key: Preferences.Key<T>,
        defaultValue: T
    ): IPreference<T> {
        return Preference(key, defaultValue)
    }

    override suspend fun clearAllPreferences() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    /**
     * Represents a single preference value stored in the DataStore.
     *
     * This class provides methods to save, delete, and retrieve a specific preference value.
     * It uses the provided `key` to identify the preference and `defaultValue` if the preference
     * is not found in the DataStore.
     *
     * @param T The type of the preference value.
     * @property key The [Preferences.Key] used to identify this preference in the DataStore.
     * @property defaultValue The default value to return if the preference is not found.
     * @constructor Creates a new Preference instance.
     */
    inner class Preference<T>(
        private val key: Preferences.Key<T>,
        private val defaultValue: T
    ): IPreference<T> {
        override suspend fun save(value: T) {
            dataStore.edit {
                it[this@Preference.key] = value
            }
        }

        override suspend fun delete() {
            dataStore.edit {
                it.remove(this@Preference.key)
            }
        }

        override fun get(): Flow<T> {
            return dataStore.data.map { it[this.key] ?: this.defaultValue }.distinctUntilChanged()
        }

        override suspend fun update(transform: suspend (T) -> T) {
            dataStore.edit {
                val currentValue = it[this.key] ?: this.defaultValue
                val newValue = transform(currentValue)
                it[this.key] = newValue
            }
        }

        override suspend fun currentValue(): T {
            return get().first()
        }

        override fun exists(): Flow<Boolean> {
            return dataStore.data.map { it.contains(this.key) }.distinctUntilChanged()
        }
    }
}