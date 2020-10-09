package com.example.ks.activities.webview

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.ks.R
import com.example.ks.common.BaseActivity
import com.example.ks.constants.UserConstants
import com.example.ks.databinding.WebviewLayoutBinding


class WebViewActivity : BaseActivity() {
    lateinit var binding:WebviewLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.webview_layout)
       initView();
    }

    private fun initView() {

    loadWebView()
    clickEvent()

    }

    private fun clickEvent() {

        binding.back.setOnClickListener {
            finish()
        }
    }


    private fun loadWebView() {

        binding.webview.settings.javaScriptEnabled=true
        binding.webview.loadUrl(UserConstants.signWebUrl)
        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

                val url = request?.url.toString()
               /* val url = request?.url.toString()
                if (url.startsWith("mailto:")) {
                    val to = url.replace("mailto:","")
                    val subject = "Help"

                    val mailTo = "mailto:" + to +
                            "?&subject=" + Uri.encode(subject)
                    val emailIntent =
                        Intent(Intent.ACTION_VIEW)
                    emailIntent.data = Uri.parse(mailTo)
                    startActivity(emailIntent)
                        view?.reload()
                        return true

                } else {
                    view?.loadUrl(url)
                }
*/
                view?.loadUrl(url)
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
              // showProgressDialog()
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {

               // hideProgressDialog()
                super.onPageFinished(view, url)
            }
            override fun onLoadResource(view: WebView?, url: String) {
               /* Log.e("click url ", url)
                if (url.equals("mailto:support@xchange.black")){

                    val to = AppConstant.contactUsEmail
                    val subject = "Help"

                    val mailTo = "mailto:" + to +
                            "?&subject=" + Uri.encode(subject)
                    val emailIntent =
                        Intent(Intent.ACTION_VIEW)
                    emailIntent.data = Uri.parse(mailTo)
                    startActivity(emailIntent)
                }*/

            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
              //  hideProgressDialog()
                val errorMessage = "Got Error! $error"
                Toast.makeText(this@WebViewActivity, errorMessage, Toast.LENGTH_SHORT).show()
                super.onReceivedError(view, request, error)
            }
        }
    }


}
