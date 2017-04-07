package org.dantelab.kotlinweakreferences

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import java.lang.ref.WeakReference

class MainActivityKotlin : AppCompatActivity() {

    internal var api = ApiKotlin()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    internal fun loadComments() {
        api.getComments { list, exception ->
            if (list != null) {
                updateUI(list)
            } else {
                displayError(exception!!)
            }
        }
    }

    internal fun loadCommentsWeak() {
        val thisRef = WeakReference(this)
        api.getComments { list, exception ->
            val `this` = thisRef.get()
            if (`this` != null)
                if (list != null) {
                    `this`.updateUI(list)
                } else {
                    `this`.displayError(exception!!)
                }
        }
    }

    internal fun loadCommentsWithMagic() {
        val weakThis by weak(this)
        api.getComments { list, exception ->
            val `this` = weakThis?.let { it } ?: return@getComments
            if (list != null)
                `this`.updateUI(list)
             else
                `this`.displayError(exception!!)
        }
    }

    internal fun loadCommentsWithSugar() {
        val weakThis by weak(this)
        api.getComments { list, exception -> weakThis?.run {
            if (list != null)
                updateUI(list)
            else
                displayError(exception!!)
        }}
    }

    internal fun loadCommentsWithDoubleSugar() = this.weaked().run { api.getComments { list, exception -> this.get()?.run {
            if (list != null)
                updateUI(list)
            else
                displayError(exception!!)
    }}}

    internal fun updateUI(comments: List<ApiKotlin.Comment>) {}

    internal fun displayError(error: Exception) {}

    fun onClick(view: View) {
        loadCommentsWithSugar()
    }
}

