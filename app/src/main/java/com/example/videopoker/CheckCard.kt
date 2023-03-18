package com.example.videopoker

val NUM_HAND : Int = 5
val NUM_TOTAL : Int = 52
class CheckCard() {
    var m_creditJackBetter : Int = 1
    var m_creditTwoPair : Int = 2
    var m_creditTriple : Int = 3
    var m_creditStraight : Int = 4
    var m_creditFlush : Int = 6
    var m_creditFullHouse : Int = 9
    var m_creditFourCARD : Int = 25
    var m_creditStraightFlush : Int = 50
    var m_creditRoyalFlush : Int = 800

    fun IsOnePair(card : MutableList<CARD>) : Boolean {
        if(card[0].IsSameNumber(card[1])
                || card[1].IsSameNumber(card[2])
                || card[2].IsSameNumber(card[3])
                || card[3].IsSameNumber(card[4])) {
            return true
        }
        else {
            return false
        }
    }

    fun IsJackBetter(card : MutableList<CARD>) : Boolean {
        if ((card[0].IsSameNumber(card[1]) && (card[0].m_number >= 11 || card[0].m_number == 1)) ||
            (card[1].IsSameNumber(card[2]) && (card[1].m_number >= 11 || card[1].m_number == 1)) ||
            (card[2].IsSameNumber(card[3]) && (card[2].m_number >= 11 || card[2].m_number == 1)) ||
            (card[3].IsSameNumber(card[4]) && (card[3].m_number >= 11 || card[3].m_number == 1))) {
            return true;
        }
        else {
            return false
        }
    }
    fun IsTwoPair(card : MutableList<CARD>) : Boolean {
        if ((card[0].IsSameNumber(card[1]) && card[2].IsSameNumber(card[3])) ||
            (card[0].IsSameNumber(card[1]) && card[3].IsSameNumber(card[4])) ||
            (card[1].IsSameNumber(card[2]) && card[3].IsSameNumber(card[4]))){
            return true;
        }
        else {
            return false
        }
    }
    fun IsTriple(card : MutableList<CARD>) : Boolean {
        if (card[0].IsSameNumber(card[2]) ||
            card[1].IsSameNumber(card[3]) ||
            card[2].IsSameNumber(card[4])) {
            return true;
        }
        else {
            return false
        }
    }
    fun IsFlush(card : MutableList<CARD>) : Boolean {
        if (card[0].IsSameType(card[1]) && card[0].IsSameType(card[2]) && card[0].IsSameType(card[3]) && card[0].IsSameType(card[4])) {
            return true;
        }
        else {
            return false
        }
    }
    fun IsStraight(card : MutableList<CARD>) : Boolean {
        if (card[0].m_number == card[1].m_number - 1 &&
            card[1].m_number == card[2].m_number - 1 &&
            card[2].m_number == card[3].m_number - 1 &&
            card[3].m_number == card[4].m_number - 1) {
            return true;
        }
        else if (card[0].m_number == 1 &&
            card[1].m_number == 10 &&
            card[2].m_number == 11 &&
            card[3].m_number == 12 &&
            card[4].m_number == 13) {
            return true;
        }
        else {
            return false
        }
    }
    fun IsFullHouse(card : MutableList<CARD>) : Boolean {
        if ((card[0].IsSameNumber(card[2]) && card[3].IsSameNumber(card[4])) ||
            (card[0].IsSameNumber(card[1]) && card[2].IsSameNumber(card[4]))) {
            return true;
        }
        else {
            return false
        }
    }
    fun IsFourCARD(card : MutableList<CARD>) : Boolean {
        if (card[0].IsSameNumber(card[3]) ||
            card[1].IsSameNumber(card[4])) {
            return true;
        }
        else {
            return false
        }
    }
    fun IsStraightFlush(card : MutableList<CARD>) : Boolean {
        if (IsFlush(card) && IsStraight(card)) {
            return true;
        }
        else {
            return false
        }
    }
    fun IsRoyalFlush(card : MutableList<CARD>) : Boolean {
        if (IsFlush(card)) {
            if (card[0].m_number == 1 &&
                card[1].m_number == 10 &&
                card[2].m_number == 11 &&
                card[3].m_number == 12 &&
                card[4].m_number == 13) {
                return true;
            }
            else {
                return false
            }
        }
        else {
            return false
        }
    }
    fun SetCredit(cdJackBetter : Int, cdTwoPair : Int, cdTriple : Int, cdStraight : Int, cdFlush : Int, cdFullHouse : Int, cdFourCARD : Int, cdStrightFlush : Int, cdRoyalFlush : Int) {
        m_creditJackBetter = cdJackBetter;
        m_creditTwoPair = cdTwoPair;
        m_creditTriple = cdTriple;
        m_creditStraight = cdStraight;
        m_creditFlush = cdFlush;
        m_creditFullHouse = cdFullHouse;
        m_creditFourCARD = cdFourCARD;
        m_creditStraightFlush = cdStrightFlush;
        m_creditRoyalFlush = cdRoyalFlush;
    }
    fun ReturnCredit(card : MutableList<CARD>, bet : Int) : Int {
        if (IsRoyalFlush(card)) {
            return bet * m_creditRoyalFlush;
        }
        else if (IsStraightFlush(card)) {
            return bet * m_creditStraightFlush;
        }
        else if (IsFourCARD(card)) {
            return bet * m_creditFourCARD;
        }
        else if (IsFullHouse(card)) {
            return bet * m_creditFullHouse;
        }
        else if (IsFlush(card)) {
            return bet * m_creditFlush;
        }
        else if (IsStraight(card)) {
            return bet * m_creditStraight;
        }
        else if (IsTriple(card)) {
            return bet * m_creditTriple;
        }
        else if (IsTwoPair(card)) {
            return bet * m_creditTwoPair;
        }
        else if (IsJackBetter(card)) {
            return bet * m_creditJackBetter;
        }
        else {
            return 0
        }
    }
    fun PrintHandCheckForString(card : MutableList<CARD>) : String {
        if (IsRoyalFlush(card)) {
            return "Royal Flush";
        }
        else if (IsStraightFlush(card)) {
            return "Straight Flush";
        }
        else if (IsFourCARD(card)) {
            return "Four CARD";
        }
        else if (IsFullHouse(card)) {
            return "Full House";
        }
        else if (IsFlush(card)) {
            return "Flush";
        }
        else if (IsStraight(card)) {
            return "Straight";
        }
        else if (IsTriple(card)) {
            return "Triple";
        }
        else if (IsTwoPair(card)) {
            return "Two Pair";
        }
        else if (IsJackBetter(card)) {
            return "Jack Better";
        }
        else if (IsOnePair(card)) {
            return "One Pair";
        }
        else {
            return "Nothing";
        }
    }
    fun PrintHandCheck(card : MutableList<CARD>) {
        if (IsRoyalFlush(card)) {
            println("Royal Flush");
        }
        else if (IsStraightFlush(card)) {
            println("Straight Flush");
        }
        else if (IsFourCARD(card)) {
            println("Four CARD");
        }
        else if (IsFullHouse(card)) {
            println("Full House");
        }
        else if (IsFlush(card)) {
            println("Flush");
        }
        else if (IsStraight(card)) {
            println("Straight");
        }
        else if (IsTriple(card)) {
            println("Triple");
        }
        else if (IsTwoPair(card)) {
            println("Two Pair");
        }
        else if (IsJackBetter(card)) {
            println("Jack Better");
        }
        else if (IsOnePair(card)) {
            println("One Pair");
        }
        else {
            println("Nothing");
        }
    } // test
    fun Sorting(card : MutableList<CARD>)  {
        /*var tempCARD : CARD = CARD(-1, TYPE.UNKNOWN)

        for(i in 0..(NUM_HAND-1)) {
            for(j in (i+1)..(NUM_HAND-1)) {
                if (card[i].m_number > card[j].m_number) {
                    tempCARD.AssignFrom(card[i]);
                    card[i].AssignFrom(card[j]);
                    card[j].AssignFrom(tempCARD);
                }
            }
        }*/
        card.sortBy { it.m_number }

    }// test
}