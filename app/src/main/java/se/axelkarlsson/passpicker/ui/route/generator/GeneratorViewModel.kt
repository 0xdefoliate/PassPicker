package se.axelkarlsson.passpicker.ui.route.generator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import se.axelkarlsson.passpicker.util.PasswordGenerator
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class GeneratorViewModel @Inject constructor() : ViewModel() {
    val minimum: Int = 6
    val maximum: Int = 64

    val generated = MutableStateFlow("")
    val length = MutableStateFlow(max(minimum.toFloat(), 32f))
    val options = MutableStateFlow(
        mapOf<String, PasswordGenerator.Option?>(
            "alpha" to PasswordGenerator.Alphabetic,
            "numeric" to PasswordGenerator.Numeric,
            "special" to PasswordGenerator.Special
        )
    )

    val useAlpha = options.map {
        it["alpha"] != null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    val useNumeric = options.map {
        it["numeric"] != null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    val useSpecial = options.map {
        it["special"] != null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    fun onChecked(key: String, checked: Boolean) {
        val copy = options.value.toMutableMap()

        copy[key] = if (checked) {
            when (key) {
                "alpha" -> PasswordGenerator.Alphabetic
                "numeric" -> PasswordGenerator.Numeric
                "special" -> PasswordGenerator.Special
                else -> null
            }
        } else {
            null
        }

        options.value = copy.toMap()

        generate()
    }

    fun onSliderValueChanged(value: Float) {
        this.length.value = value
        generate()
    }

    fun generate() {
        val generator = PasswordGenerator()
        val mapped = options.value.mapNotNull {
            return@mapNotNull it.value
        }

        if (mapped.isEmpty()) {
            return
        }

        generated.value =
            generator.generate(minimum, length.value.toInt(), mapped)
    }
}