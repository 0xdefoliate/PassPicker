package se.axelkarlsson.passpicker.ui.route.generator

import kotlinx.serialization.Serializable
import se.axelkarlsson.passpicker.R
import se.axelkarlsson.passpicker.util.Tab

@Serializable
object GeneratorRoute: Tab {
    override val label: String = "Generator"
    override val icon: Int = R.drawable.key_24px
}