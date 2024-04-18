package com.zoe.wan.android.example.activity.search

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blankj.utilcode.util.KeyboardUtils
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.activity.webview.WebActivity
import com.zoe.wan.android.example.repository.data.SearchResultData

class SearchActivity : ComponentActivity() {
    companion object{
        const val Intent_Keyword = "Intent_Keyword"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val keyWord = intent.getStringExtra(Intent_Keyword)
            val viewModel: SearchViewModel = viewModel()
            viewModel.input.set(keyWord)
            viewModel.search()
            Column {
                SearchBar(viewModel)
                SearchResultList(viewModel)
            }

        }

    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchBar(viewModel: SearchViewModel) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.dp_50))
                .background(color = colorResource(id = R.color.teal_700))
        ) {
            //返回按钮
            IconButton(onClick = {
                finish()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back), contentDescription = "",
                    tint = Color.White, modifier = Modifier
                        .width(dimensionResource(id = R.dimen.dp_30))
                        .height(dimensionResource(id = R.dimen.dp_30))
                )
            }

            var inputValue by remember {
                mutableStateOf(viewModel.input.get())
            }

            //输入框
//            TextField(value = , onValueChange = )
            BasicTextField(
                value = inputValue ?: "", onValueChange = {
                    inputValue = it
                    viewModel.input.set(inputValue)

                },
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.dp_10),
                        vertical = dimensionResource(id = R.dimen.dp_10)
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp_10))
                    )
                    .height(dimensionResource(id = R.dimen.dp_40)),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    //点击搜索事件
                    viewModel.search()
                    //隐藏软键盘
                    KeyboardUtils.hideSoftInput(this@SearchActivity)
                }),
                decorationBox = { innerTextField ->
                    TextFieldDefaults.DecorationBox(
                        value = inputValue ?: "",
                        innerTextField = innerTextField,
                        enabled = true,
                        singleLine = true,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp_15)),
                        visualTransformation = VisualTransformation.None,
                        interactionSource = remember { MutableInteractionSource() },
                        contentPadding = PaddingValues(
                            start = dimensionResource(
                                id = R.dimen
                                    .dp_10
                            )
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )

                    )
                }
            )

            //清空按钮
            Text(text = "清空", color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.dp_10))
                    .clickable {
                        inputValue = ""
                        viewModel.input.set("")
                        viewModel.results.postValue(emptyList())
                        //隐藏软键盘
                        KeyboardUtils.hideSoftInput(this@SearchActivity)
                    })
        }
    }

    @Composable
    fun SearchResultList(viewModel: SearchViewModel) {

        val results = viewModel.results.observeAsState()

        LazyColumn {
            items(count = results.value?.size ?: 0) { index ->
                val item = results.value?.get(index)
                ListItem(item) {
                    //item点击事件
                    jumpToWeb(item?.title, item?.link)
                }
            }
        }
    }

    @Composable
    fun ListItem(item: SearchResultData?, onClick: () -> Unit) {
        //通过html转换标题内容过滤掉html标签
        Text(
            textAlign = TextAlign.Center,
            text = Html.fromHtml(item?.title).toString(), modifier = Modifier
                .height(dimensionResource(id = R.dimen.dp_45))
                .padding(start = dimensionResource(id = R.dimen.dp_15))
                .clickable {
                    onClick.invoke()
                }
                .wrapContentSize(Alignment.CenterStart)
        )
    }

    private fun jumpToWeb(title: String?, link: String?) {
        val intent = Intent(this@SearchActivity, WebActivity::class.java)
        intent.putExtra(WebActivity.INTENT_WEB_TITLE_KEY, title)
        intent.putExtra(WebActivity.INTENT_WEB_URL_KEY, link)
        startActivity(intent)
    }
}
