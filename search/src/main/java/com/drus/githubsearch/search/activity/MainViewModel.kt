package com.drus.githubsearch.search.activity

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.drus.githubsearch.core.presentation.BaseViewModel
import com.drus.githubsearch.search.Screens
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val router: Router,
    private val holder: NavigatorHolder,
    private val inputMethodManager: InputMethodManager
) : BaseViewModel() {

    private var keyboardHandler: Unregistrar? = null
    val keyboardVisibility = MutableLiveData<Boolean>()
    private var isFirstAttach = true

    private val isKeyboardVisible: Boolean
        get() = keyboardVisibility.value ?: false

    fun setNavigator(navigator: Navigator) {
        holder.removeNavigator()
        holder.setNavigator(navigator)
        if (isFirstAttach) {
            router.newRootChain(Screens.search())
            isFirstAttach = false
        }
    }

    fun closeKeyboard(view: View?) {
        if (isKeyboardVisible) {
            view?.apply {
                clearFocus()
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
            }
        }
    }

    fun initKeyboardHandler(activity: AppCompatActivity) {
        keyboardHandler = KeyboardVisibilityEvent.registerEventListener(activity) {
            keyboardVisibility.value = it
        }
    }

    fun detach() {
        keyboardHandler?.unregister()
        holder.removeNavigator()
    }

    fun showKeyboard(view: EditText) {
        view.requestFocus()
        if (!isKeyboardVisible) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }
}