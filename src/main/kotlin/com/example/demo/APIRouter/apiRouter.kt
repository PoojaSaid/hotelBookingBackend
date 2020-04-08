package com.example.demo.APIRouter

import com.example.demo.Tables.customerWS
import com.example.demo.Tables.roomsWS
import org.springframework.web.bind.annotation.*

@CrossOrigin()
@RestController
@RequestMapping("/api")
class apiRouter {

    @PostMapping("/room/create")
    fun create_Room(
            @RequestParam("r_capacity", required = true) r_capacity:Int,
            @RequestParam("r_type", required = true) r_type: String

    ) = roomsWS().addRoom(r_capacity,r_type)

    @PostMapping("/room/getAll")
    fun get_Room(
            @RequestParam("date", required = false,defaultValue = "") date: String
    ) = roomsWS().getRoom(date)

    @PostMapping("/customer/create")
    fun create_Customer(
            @RequestParam("cust_firstname", required = true) cust_firstname:String,
            @RequestParam("cust_lastname", required = true) cust_lastname: String,
            @RequestParam("cust_email", required = true) cust_email: String,
            @RequestParam("cust_mobile", required = true) cust_mobile: String
    ) = customerWS().customerRegister(cust_firstname,cust_lastname,cust_email,cust_mobile)

    @PostMapping("/customer/login")
    fun login_Customer(
            @RequestParam("cust_email", required = true) cust_email: String,
            @RequestParam("cust_mobile", required = true) cust_mobile: String
    ) = customerWS().login(cust_email,cust_mobile)

    @PostMapping("/customer/Booking")
    fun Booking_Customer(
            @RequestParam("r_capacity", required = true) r_capacity: Int,
            @RequestParam("r_type", required = true) r_type: String,
            @RequestParam("r_startdate", required = true) r_startdate: String,
            @RequestParam("r_enddate", required = true) r_enddate: String

    ) = customerWS().createBooking(r_capacity,r_type,r_startdate, r_enddate)


    @PostMapping("/admin/login")
    fun login_Admin(
            @RequestParam("cust_email", required = true) cust_email: String,
            @RequestParam("cust_mobile", required = true) cust_mobile: String
    ) = customerWS().login(cust_email,cust_mobile)

}