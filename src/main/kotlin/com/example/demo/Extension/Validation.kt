package com.example.demo.Extension

import org.json.JSONObject
import java.io.File

class Validation {

    fun convertToJavaDate(date: String): String {
        val lsDate = date.substring(6, 10) + "/" + date.substring(3, 5) + "/" + date.substring(0, 2);
        return lsDate
    }

    var lsMsg = ""
    fun maxRangeValidation(msg: String): Boolean {

        if (msg.length > 255) {

            return false
        }
        return true
    }


    fun mobileNoValidation(msg: String): Boolean {
        if (msg.length < 10 || msg.length > 10) {
            return false
        }
        return true
    }

    fun IDValidation(msg: Int): Boolean {
        if (msg < 0) {
            return false
        }
        return true
    }


    fun passwordValidation(msg: String): Boolean {
        if (msg.length < 6) {
            return false
        }
        return true
    }

    fun capacityValidation(msg: Int): Boolean {
        if (msg > 10) {
            return false
        }
        return true
    }

    fun minRangeValidation(msg: Int): Boolean {
        if (msg < 0) {
            return false
        }
        return true
    }
    fun genMsg(msg: String):String {

        lsMsg = lsMsg + msg

        return lsMsg
    }
    fun validationMsg(msg: String):String {
        var error= JSONObject().apply {
            put("status", "failed")
            put("message", Validation().genMsg(msg))
            //put("error", Response_Validate_Error)
        }
        return error.toString()
    }



    fun getStatusMessage(Std: String, Msg: String): String {
        var jsonResponse = JSONObject()
        jsonResponse.put("status", Std)
        jsonResponse.put("message", Msg)
        return jsonResponse.toString()
    }

    fun getPath(): String {
        var tomcatBase = System.getProperty("catalina.base");
        var pathString = "/webapps/storage/img";
        var filePath = tomcatBase + pathString

        if (!(File(filePath).exists())) {
            File(filePath).mkdirs();


            var lsLibFileForPermission = File(filePath)
            lsLibFileForPermission.setReadable(true, false)
            lsLibFileForPermission.setExecutable(true, false)
            lsLibFileForPermission.setWritable(true, false)
        }
        return filePath;
    }

    fun checkRowExist(IsSQL: String): Int {
        var conn = getDBConnection()

        conn!!.autoCommit = false

        var stmt = conn!!.createStatement()

        var rs = stmt.executeQuery(IsSQL)

        var totalRows = 0

        while (rs!!.next()) {
            totalRows = rs.getNullableInt("dc")!!
        }
        if (totalRows > 0) {
            return 1
        } else {
            return 0
        }
    }
}
