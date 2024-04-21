package com.zoe.wan.android.example.activity.about_us

import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.common.ui.AppBar

class AboutUsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AboutUsScreen()
        }
    }

    @Composable
    fun AboutUsScreen() {
        val viewModel: AboutUsViewModel = viewModel()
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AppBar(onBackClick = {
                finish()
            }, title = "关于我们")

            AppInfo(viewModel)
        }
    }

    @Composable
    fun AppInfo(viewModel: AboutUsViewModel) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.dp_30))
        ) {
            Image(
                painter = painterResource(id = R.drawable.applogo), contentDescription
                = "", modifier = Modifier.size(dimensionResource(id = R.dimen.dp_80))
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_5)))
            val versionName = remember {
                viewModel.version.get()
            }
            Text(text = "v${versionName}")

        }

        AndroidView(factory = {context: Context ->
            TextView(context)
        }, update = {tv->

            tv.text  = HtmlCompat.fromHtml(getString(R.string.about_content),HtmlCompat.FROM_HTML_MODE_COMPACT)

            tv.movementMethod = LinkMovementMethod.getInstance()
        })
    }
}
