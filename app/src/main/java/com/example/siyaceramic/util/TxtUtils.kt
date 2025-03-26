package com.example.siyaceramic.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.siyaceramic.custom.Validator
import com.google.android.material.textfield.TextInputEditText
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class TxtUtils {

    companion object {
        var DO_SOP = true
        @JvmStatic
        fun isEmptyText(view: View?): Boolean {
            return if (view == null) true else getTextValue(view).equals("", ignoreCase = true)
        }


        @JvmStatic
        fun getTextPhoneValue(view: View?): String {
            var data: String = ""
            if (view is EditText) {
                data = view.text.toString()
                val re = Regex("[^A-Za-z0-9]")
                data = re.replace(data, "")
            }
            return data
        }

        @JvmStatic
        fun getTextValue(view: View?): String {
            return when (view) {
                is EditText -> view.text.toString().trim { it <= ' ' }
                is TextView -> view.text.toString().trim { it <= ' ' }
                is AutoCompleteTextView -> view.text.toString().trim { it <= ' ' }
                else -> ""
            }
        }
        fun printHashKey(ctx: Context) {
            try {
                val info: PackageInfo = ctx.packageManager.getPackageInfo(
                    "com.siyana.mavec", PackageManager.GET_SIGNATURES
                )
                for (signature in info.signatures) {
                    val md: MessageDigest = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    Log.e(
                        "Utils :: KEY HASH", Base64.encodeToString(md.digest(), android.util.Base64.DEFAULT)
                    )
                }
            } catch (e: PackageManager.NameNotFoundException) {
                Log.e("Utils :: ", "" + e)
            } catch (e: NoSuchAlgorithmException) {
                Log.e("Utils :: ", "" + e)
            }
        }

        @JvmStatic
        fun hideKeyBoard(context: Context?, view: View?) {

            if (context == null) return
            val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }

        }
        /**Validation For Email Only For Done Icon*/
        fun isValidEmail(edtText: TextInputEditText): Boolean {

            val validator = Validator()
            if (isEmptyText(edtText)) {
                return false
            }
            if (!validator.validateEmail(getTextValue(edtText))) {
                return false
            }
            return true

        }

        fun isValidateName(edtText: TextInputEditText): Boolean {
            /**Validation For Email And Phone For Done Icon*/
            val validator = Validator()
            if (isEmptyText(edtText)) {
                return false
            }
            if (!validator.validateName(
                    getTextValue(edtText)
                )
            ) {
                return false
            }

            return true

        }

        fun isValidatePhone(edtText: TextInputEditText): Boolean {
            /**Validation For Email And Phone For Done Icon*/
            val validator = Validator()
            if (TextUtils.isDigitsOnly(edtText.text?.trim()) && edtText.filters.size<10){
                val maxLength = 10
                edtText.filters = arrayOf<InputFilter>(LengthFilter(maxLength))
            }else if (edtText.filters.size<10){
                val maxLength = 10
                edtText.filters = arrayOf<InputFilter>(LengthFilter(maxLength))
            }


            return if (isEmptyText(edtText)) {
                false
            } else if (TextUtils.isDigitsOnly(edtText.text?.trim()) && !validator.validatePhone(
                    getTextValue(edtText)
                )
            ) {
                false
            } else !(!TextUtils.isDigitsOnly(edtText.text?.trim()) && !validator.validateEmail(
                getTextValue(edtText)
            ))

        }

        fun isValidateEmailOrPhone(edtText: TextInputEditText): Boolean {
            /**Validation For Email And Phone For Done Icon*/
            val validator = Validator()
            if (TextUtils.isDigitsOnly(edtText.text?.trim()) && edtText.filters.size<100){
                val maxLength = 100
                edtText.filters = arrayOf<InputFilter>(LengthFilter(maxLength))
            }else if (edtText.filters.size<100){
                val maxLength = 100
                edtText.filters = arrayOf<InputFilter>(LengthFilter(maxLength))
            }


            return if (isEmptyText(edtText)) {
                false
            } else if (TextUtils.isDigitsOnly(edtText.text?.trim()) && !validator.validatePhone(
                    getTextValue(edtText)
                )
            ) {
                false
            } else !(!TextUtils.isDigitsOnly(edtText.text?.trim()) && !validator.validateEmail(
                getTextValue(edtText)
            ))

        }

        /**Validation For Password For Done Icon*/
        fun isValidatePassWordSignIn(edtText: TextInputEditText): Boolean {

            val validator = Validator()
            if (isEmptyText(edtText)) {
                return false
            }
            if (!validator.validatePassword(getTextValue(edtText))) {
                return false
            }
            return true

        }
        /**
         * Show Toast MSG
         */
        @JvmStatic
        fun showToastMessage(context: Context?, mgs: String?, isShort: Boolean) {
            Toast.makeText(context, mgs, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
        }

        fun loadTransformationImage(
            context: Context,
            publicID: String?,
            imageView: View,
            drawable: Int
        ) {
            var imgView = imageView as ImageView
            if (imageView is AppCompatImageView) {
                imgView = imageView as AppCompatImageView
            } else if (imageView is ImageView) {
                imgView = imageView as ImageView
            }

           /* val generatedUrlFromPublicId2 =
                MediaManager.get().cloudinary.url().signed(true).type("upload").apply {
                    transformation().gravity("face").height(150).width(150).crop("thumb").zoom(0.3)
                        .generate()
                }.generate("$publicID.jpg")

            Glide.with(context)
                .load(generatedUrlFromPublicId2)
                .placeholder(drawable)
                .into(imgView)*/
        }
        /**
         * Print Lof
         */
        fun print(mesg: String) {
            if (DO_SOP) {
                if (mesg.length > 1000) {
                    val maxLogSize = 1000
                    for (i in 0..mesg.length / maxLogSize) {
                        val start = i * maxLogSize
                        var end = (i + 1) * maxLogSize
                        end = if (end > mesg.length) mesg.length else end

                    }
                }
            }
        }



        @JvmStatic
        fun setEditTextMaxLength(editText: EditText, length: Int) {
            val filterArray = arrayOfNulls<InputFilter>(1)
            filterArray[0] = LengthFilter(length)
            editText.filters = filterArray
        }
        /**
         * Here the Expand View Animation Just pass View ID
         */
        fun expand(v: View) {
            val matchParentMeasureSpec =
                View.MeasureSpec.makeMeasureSpec((v.parent as View).width, View.MeasureSpec.EXACTLY)
            val wrapContentMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            v.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
            val targetHeight = v.measuredHeight
            // Older versions of android (pre API 21) cancel animations for views with a height of 0.
            v.layoutParams.height = 1
            v.visibility = View.VISIBLE
            val a: Animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    v.layoutParams.height =
                        if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
                override fun willChangeBounds(): Boolean {
                    return true
                }
            }
            // Expansion speed of 1dp/ms
            a.duration = ((targetHeight / v.context.resources.displayMetrics.density).toInt()).toLong()
            v.startAnimation(a)
        }
        /**
         * Here the collapse View Animation Just pass View ID
         */
         fun collapse(v: View) {
            val initialHeight = v.measuredHeight
            val a: Animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                    if (interpolatedTime == 1f) {
                        v.visibility = View.GONE
                    } else {
                        v.layoutParams.height =
                            initialHeight - (initialHeight * interpolatedTime).toInt()
                        v.requestLayout()
                    }
                }
                override fun willChangeBounds(): Boolean {
                    return true
                }
            }
            // Collapse speed of 1dp/ms
            a.duration = ((initialHeight / v.context.resources.displayMetrics.density).toInt()).toLong()
            v.startAnimation(a)
        }
        //Check All Permission When APp Open
        val PERMISSION_REQUEST_CODE = 1
        fun checkAllpermission(activity: Activity?): Boolean {
            if (ActivityCompat.shouldShowRequestPermissionRationale((activity)!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if ((ContextCompat.checkSelfPermission((activity), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (
                            ContextCompat.checkSelfPermission((activity), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) || (
                            ContextCompat.checkSelfPermission((activity), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions((activity), arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
                    return true
                } else {
                    return false
                }
            } else {
                ActivityCompat.requestPermissions((activity), arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
                return false
            }
        }


    }
}