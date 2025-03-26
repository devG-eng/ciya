package com.example.siyaceramic.custom

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Used for validate different fields like email,phone
 */

private const val NAME_PATTERN = "^[A-Za-z0-9\\s]+[.]?[A-Za-z0-9\\s]{2,20}$"
private const val PHONE_PATTERN = "^[0-9]{10,12}$"
private const val USERNAME_PATTERN = "^[a-zA-Z ]+$"
private const val PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"
const val EMAIL_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{3,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" + ")+"
//    "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"
//"^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
//    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{3,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" + ")+"

class Validator {

    private val pattern: Pattern = Pattern.compile(USERNAME_PATTERN)
    private val patternPassword: Pattern = Pattern.compile(PASSWORD_PATTERN)
    private val patternEmail: Pattern = Pattern.compile(EMAIL_PATTERN)
    private val patternName: Pattern = Pattern.compile(USERNAME_PATTERN)
    private val patternPhone: Pattern = Pattern.compile(PHONE_PATTERN)
    private lateinit var matcher: Matcher

    //    /**
    //     * Validate username with regular expression
    //     * @param username username for validation
    //     * @return true valid username, false invalid username
    //     */
    /* fun validateUserName(username: String): Boolean {
         matcher = pattern.matcher(username)
         return matcher.matches()
     }*/

    /**
     * validate phone number
     */
    fun validatePhone(phone: String): Boolean {
        matcher = patternPhone.matcher(phone)
        return matcher.matches()
    }

    /**
     * validate password.
     */
    fun validatePassword(password: String): Boolean {
        matcher = patternPassword.matcher(password)
        return matcher.matches()
    }

    /**
     * validate email
     */
    fun validateEmail(email: String): Boolean {
        matcher = patternEmail.matcher(email)
        return matcher.matches()
    }

    /**
     * Validate name
     */
    fun validateName(Name: String): Boolean {
        matcher = patternName.matcher(Name)
        return matcher.matches()
    }
}