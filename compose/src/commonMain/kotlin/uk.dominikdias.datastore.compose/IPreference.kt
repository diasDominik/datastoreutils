package uk.dominikdias.datastore.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uk.dominikdias.datastore.IPreference
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Collects values from this [Flow] and represents its latest value via [State] in a
 * composable.
 *
 * The [State] will be updated when a new value is emitted by the [Flow].
 *
 * This function is a convenience wrapper around [androidx.compose.runtime.collectAsState].
 *
 * @param initial The initial value to be used for the [State] until the [Flow] emits its
 * first value.
 * @param coroutineContext The [CoroutineContext] to be used for collecting the [Flow].
 * @return A [State] that represents the latest value of the [Flow].
 */
@Composable
fun <T> IPreference<T>.collectAsState(initial: T, coroutineContext: CoroutineContext = EmptyCoroutineContext): State<T> {
    return this.get().collectAsState(initial = initial, context = coroutineContext)
}

/**
 * Collects values from this [Flow] and represents its latest value via [State] in a
 * composable.
 *
 * The [State] will be updated when a new value is emitted by the [Flow].
 *
 * This function is a convenience wrapper around [androidx.lifecycle.compose.collectAsStateWithLifecycle].
 *
 * It uses the provided [lifecycleOwner] and [minActiveState] to control the collection of
 * values from the [Flow]. The collection will start when the lifecycle is at least in the
 * [minActiveState] and stop when it falls below that state.
 *
 * @param initial The initial value to be used for the [State] before any value is emitted by the [Flow].
 * @param lifecycleOwner The [LifecycleOwner] to observe for lifecycle state changes. Defaults to [LocalLifecycleOwner.current].
 * @param minActiveState The minimum [Lifecycle.State] in which the collection should be active. Defaults to [Lifecycle.State.STARTED].
 * @param context The [CoroutineContext] to be used for collecting the [Flow]. Defaults to [EmptyCoroutineContext].
 * @return A [State] that represents the latest value of the [Flow].
 */
@Composable
fun <T> IPreference<T>.collectAsStateWithLifecycle(
    initial: T,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): State<T> {
    return this.get().collectAsStateWithLifecycle(
        initialValue = initial,
        lifecycleOwner = lifecycleOwner,
        minActiveState = minActiveState,
        context = context,
    )
}