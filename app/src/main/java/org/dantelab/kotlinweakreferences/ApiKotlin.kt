package org.dantelab.kotlinweakreferences

import java.util.ArrayList

/**
 * Created by ivanbrazhnikov on 07/04/2017.
 */

internal class ApiKotlin {
    fun getComments(handler: (list: List<Comment>?, exception: Exception?) -> Unit) {
        Thread(Runnable {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            handler.invoke(ArrayList<Comment>(), null)
        }).start()
    }

    internal class Comment
}
