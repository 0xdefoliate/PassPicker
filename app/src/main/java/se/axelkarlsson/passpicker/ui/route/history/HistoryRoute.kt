package se.axelkarlsson.passpicker.ui.route.history

import kotlinx.serialization.Serializable
import se.axelkarlsson.passpicker.R
import se.axelkarlsson.passpicker.util.Tab

@Serializable
object HistoryRoute: Tab {
    override val label: String = "History"
    override val icon: Int = R.drawable.history_24px
}