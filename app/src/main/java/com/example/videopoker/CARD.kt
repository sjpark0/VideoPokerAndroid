package com.example.videopoker

enum class TYPE{
    UNKNOWN, CARD_SPADE, CARD_CLOVER, CARD_HEART, CARD_DIAMOND
}
class CARD(number : Int, type : TYPE) {
    var m_number : Int = number
    var m_type : TYPE = type

    fun IsSameNumber(card : CARD) : Boolean {
        if(m_number == card.m_number){
            return true
        }
        else{
            return false
        }
    }
    fun IsSameType(card : CARD) : Boolean {
        if(m_type == card.m_type){
            return true
        }
        else{
            return false
        }
    }
    fun IsSameCard(card : CARD) : Boolean {
        if(IsSameNumber(card) && IsSameType(card)){
            return true
        }
        else{
            return false
        }
    }
    fun PrintForString() : String {
        var result : String = ""
        when(m_number){
            1 -> result += "A,"
            11 -> result += "J,"
            12 -> result += "Q,"
            13 -> result += "K,"
            else -> result += (m_number.toString() + ",")
        }

        when(m_type){
            TYPE.CARD_SPADE -> result += "Spade"
            TYPE.CARD_HEART -> result += "Heart"
            TYPE.CARD_CLOVER -> result += "Clover"
            TYPE.CARD_DIAMOND -> result += "Diamond"
            TYPE.UNKNOWN -> result += "Unknown"
        }

        return result
    }

    fun Print() {
        when(m_number){
            1 -> println("A,")
            11 -> println("J,")
            12 -> println("Q,")
            13 -> println("K,")
            else -> println(m_number.toString())
        }

        when(m_type){
            TYPE.CARD_SPADE -> println("Spade")
            TYPE.CARD_HEART -> println("Heart")
            TYPE.CARD_CLOVER -> println("Clover")
            TYPE.CARD_DIAMOND -> println("Diamond")
            TYPE.UNKNOWN -> println("Unknown")
        }

    }
    fun AssignFrom(card : CARD){
        m_number = card.m_number
        m_type = card.m_type
    }
}