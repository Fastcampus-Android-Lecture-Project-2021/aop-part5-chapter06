package fastcampus.aop.part5.chapter06.extension

import android.widget.TextView
import androidx.annotation.ColorRes

fun TextView.setTextColorRes(@ColorRes colorResId: Int) {
    setTextColor(color(colorResId))
}
