package com.example.demo.Tables

import com.example.demo.Extension.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.sql.PreparedStatement
import java.util.*

class Admin {

    fun adminRegister(
            firstName: String,
            lastName: String,
            email: String,
            mobile: String
    ): String {

        var conn = getDBConnection()
        conn!!.autoCommit = false

        //Validation of enter details
        var firstNameValidation = Validation().maxRangeValidation(firstName)
        var lastNameValidation = Validation().maxRangeValidation(lastName)
        var emailValidation = Validation().maxRangeValidation(email)
        var mobileValidation = Validation().mobileNoValidation(mobile)

        var validationDone = true

        var error = ""

        if (!firstNameValidation) {
            error = Validation().genMsg("first name should be less than 255 character")
            validationDone = false
        }
        if (!lastNameValidation) {
            error = Validation().genMsg("last name should be less than 255 character")
            validationDone = false
        }
        if (!emailValidation) {
            error = Validation().genMsg("email should be less than 255 character")
            validationDone = false
        }

        if (!mobileValidation) {
            error = Validation().genMsg("email should be less than 255 character")
            validationDone = false
        }

        if (!validationDone && error.length > 0) {
            var ErroRes = Validation().validationMsg(error)

            return ErroRes.toString()
        }

        lateinit var preparedStatement: PreparedStatement
        var sqlStatements = ArrayList<SQLModel>()
        var lsAdminId = UUID.randomUUID().toString()

        var jsonObject = JSONObject()
        var jsonResponse = JSONObject()

        jsonObject.put(customer.CUST_ID,lsAdminId)
        jsonObject.put(customer.CUST_FIRSTNAME, firstName)
        jsonObject.put(customer.CUST_LASTNAME, lastName)
        jsonObject.put(customer.CUST_EMAIL, email)
        jsonObject.put(customer.CUST_MOBILE, mobile)
        jsonObject.put(customer.CUST_TYPE, "A")

        try {
            var jsonArray = JSONArray()
            jsonArray.put(jsonObject)

            var insert = jsonInsert(jsonArray, customer.TABLE_NAME, conn)
            for (x in 0 until insert.size) {
                sqlStatements.add(insert[x])
            }
            for (i in 0 until sqlStatements.size) {
                var e = sqlStatements[i]
                preparedStatement = conn!!.prepareStatement(e.statement)
                preparedStatement.executeUpdate(e.statement)
            }
            conn!!.commit()
        } catch (e: Exception) {
            conn!!.rollback();
            jsonResponse.put(DBFunctions.STATUS, DBFunctions.FAILED)
            jsonResponse.put(DBFunctions.EXCEPTION, e.toString())
            return jsonResponse.toString()
        } finally {
            conn.close()
        }
        jsonResponse.put(DBFunctions.STATUS, DBFunctions.SUCCESS)
        jsonResponse.put(DBFunctions.RESPONSE, "Thank you , for registering on our hotel.")

        return jsonResponse.toString()

    }

}