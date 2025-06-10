package uk.dominikdias.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

/**
 * Represents a single preference that can be saved, deleted, and observed.
 *
 * This interface defines the contract for interacting with a specific preference
 * value stored using DataStore. It allows for asynchronous saving and deletion
 * of the preference, as well as obtaining a [Flow] to observe its changes.
 * Additionally, it provides a Composable function to collect the preference's
 * state directly in a Jetpack Compose UI.
 *
 * @param T The type of the preference value.
 */
interface IPreference<T> {
    /**
     * Saves the given value to the preferences.
     *
     * @param value The value to be saved.
     */
    suspend fun save(value: T)
    /**
     * Deletes the preference.
     */
    suspend fun delete()
    /**
     * Retrieves the value of the preference as a Flow.
     *
     * This function allows observing changes to the preference value reactively.
     * The Flow will emit the current value of the preference and then any subsequent
     * updates to it.
     *
     * @return A [Flow] that emits the value of the preference. If the preference
     *         has not been set, it will emit the `defaultValue` provided when the
     *         `IPreference` was created.
     */
    fun get(): Flow<T>
    /**
     * Atomically updates the preference value based on its current state.
     *
     * The [transform] function receives the current value of the preference (or the default value
     * if not set) and should return the new value to be saved.
     * This operation is performed asynchronously and ensures atomicity.
     *
     * @param transform A suspend function that takes the current value of type [T] and returns the new value of type [T].
     */
    suspend fun update(transform: suspend (currentValue: T) -> T)
    /**
     * Fetches the current value of the preference once.
     *
     * This is a suspend function that retrieves the latest saved value, or the default value
     * if the preference is not set. Unlike [get], this does not return a [Flow] and
     * only provides a snapshot of the value at the time of calling.
     *
     * @return The current value of type [T].
     */
    suspend fun currentValue(): T
    /**
     * Observes whether this preference key explicitly exists in the DataStore.
     *
     * Emits `true` if the key is present, `false` otherwise. This is different from
     * [get], which might return a default value making it seem like a key "exists"
     * from the perspective of having a value.
     *
     * @return A [Flow] emitting `true` if the key exists, `false` otherwise.
     */
    fun exists(): Flow<Boolean>
}

/**
 * An interface for managing preferences using Jetpack DataStore.
 * It provides a way to create, save, delete, and observe individual preferences.
 */
interface IPreferencesRepository {
    /**
     * Creates an [IPreference] instance for a given [Preferences.Key] and default value.
     *
     * This function provides a convenient way to create and manage individual preferences
     * stored in DataStore.
     *
     * @param T The type of the preference value.
     * @param key The [Preferences.Key] used to identify the preference in DataStore.
     * @param defaultValue The default value to be used if the preference is not yet set.
     * @return An [IPreference] instance that allows saving, deleting, and observing the preference value.
     */
    fun <T> createPreference(key: Preferences.Key<T>, defaultValue: T): IPreference<T>
    /**
     * Clears all preferences managed by this repository.
     *
     * This asynchronously removes all key-value pairs from the underlying DataStore.
     * After this operation, all [IPreference] instances obtained from this repository
     * will revert to their default values upon observation or retrieval.
     */
    suspend fun clearAllPreferences()
}