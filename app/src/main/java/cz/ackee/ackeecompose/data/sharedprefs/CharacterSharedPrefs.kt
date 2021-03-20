package cz.ackee.ackeecompose.data.sharedprefs

import android.content.SharedPreferences
import androidx.core.content.edit

class CharacterSharedPrefs(
    private val characterPrefs: SharedPreferences
) {

    fun getMaxPages(): Int {
        return characterPrefs.getInt(MAX_PAGES_KEY, DEFAULT_MAX_PAGES)
    }

    fun setMaxPages(maxPages: Int) {
        characterPrefs.edit(commit = true) {
            putInt(MAX_PAGES_KEY, maxPages)
        }
    }

    companion object {

        private const val MAX_PAGES_KEY = "max_pages"
        const val DEFAULT_MAX_PAGES = -1
    }
}