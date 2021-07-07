package com.example.app_tickets_firebase

class TicketsStock {
    companion object {
        var totalAmount = 0
        var price: Int = 0
        var discount: Double = 0.0
        fun subAmount(amount: Int) {
            totalAmount = totalAmount - amount
        }

    }
}