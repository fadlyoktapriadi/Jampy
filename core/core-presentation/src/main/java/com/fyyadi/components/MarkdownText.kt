package com.fyyadi.components

import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.fyyadi.core_presentation.R
import com.fyyadi.theme.Black600
import io.noties.markwon.Markwon
import io.noties.markwon.html.HtmlPlugin

@Composable
fun MarkdownText(
    markdown: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
) {
    val context = LocalContext.current
    val markwon = remember {
        Markwon.builder(context)
            .usePlugin(HtmlPlugin.create())
            .build()
    }
    val textColor = Black600.toArgb()

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            val tf = ResourcesCompat.getFont(ctx, R.font.rethinksans_regular)
            TextView(ctx).apply {
                setTextColor(textColor)
                textSize = fontSize.value
                setLineSpacing(0f, 1.4f)
                tf?.let { typeface = it }
            }
        },
        update = { textView ->
            markwon.setMarkdown(textView, markdown)
            textView.setTextColor(textColor)
        }
    )
}