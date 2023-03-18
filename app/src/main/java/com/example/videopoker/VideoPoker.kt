package com.example.videopoker

class VideoPoker() {
    var m_pChecker : CheckCard = CheckCard()
    var m_pHand : MutableList<CARD> = MutableList<CARD>(NUM_HAND) { CARD(-1, TYPE.UNKNOWN) }

    var m_pAvail : MutableList<CARD> = MutableList<CARD>(NUM_HAND) { CARD(-1, TYPE.UNKNOWN) }
    var m_pRemain : MutableList<CARD> = MutableList<CARD>(NUM_TOTAL - NUM_HAND) { CARD(-1, TYPE.UNKNOWN) }

    fun ConValToType(value : Int) : TYPE {
        when(value) {
            0 -> return TYPE.CARD_SPADE
            1 -> return TYPE.CARD_CLOVER
            2 -> return TYPE.CARD_HEART
            3 -> return TYPE.CARD_DIAMOND
            else -> return TYPE.UNKNOWN
        }

    }
    fun ConTypeToVal(value : TYPE) : Int {
        when(value) {
            TYPE.CARD_SPADE -> return 0
            TYPE.CARD_CLOVER -> return 1
            TYPE.CARD_HEART -> return 2
            TYPE.CARD_DIAMOND -> return 3
            else -> return -1
        }

    }

    fun GenerateNCard(cardIdx : MutableList<Int>, numTotalCard : Int, numGenCard : Int) {
        var i = 0
        while (i < numGenCard) {
            cardIdx[i] = (0 until numTotalCard).random()
            for (j in 0..(i - 1)) {
                if (cardIdx[i] == cardIdx[j]) {
                    i -= 1
                    break
                }
            }
            i += 1
        }
    }

    fun ChangeOneCARD(card : MutableList<CARD>, handIdx : Int, remainIdx : Int) {
        card[handIdx].AssignFrom(m_pRemain[remainIdx])
    }
    fun ComputeAvgCreditForCardChange(card : MutableList<CARD>, handIdx : MutableList<Int>, numChangeCard : Int) : Float {
        val (credit, numComputeCredit) = ComputeTotalCreditForCardChange(card, handIdx, 0, numChangeCard)

        return (credit.toFloat()) / numComputeCredit.toFloat()
    }
    fun ComputeTotalCreditForCardChange(card : MutableList<CARD>, handIdx : MutableList<Int>,  startRemainIdx : Int, numChangeCard : Int) : Pair<Int, Int> {
        var numComputeCredit : Int = 0
        if(numChangeCard == 0) {
            numComputeCredit = 1
            return Pair(ComputeCredit(card), numComputeCredit)
        }

        var credit : Int = 0

        var tempCARD : MutableList<CARD> = MutableList<CARD>(NUM_HAND) { CARD(-1, TYPE.UNKNOWN) }
        var tempHandIdx : MutableList<Int> = mutableListOf<Int>()
        //tempCARD.addAll(card)
        for(i in 0..(NUM_HAND-1)){
            tempCARD[i].AssignFrom(card[i])
        }
        tempHandIdx.addAll(handIdx)
        tempHandIdx.removeAt(0)

        for(i in startRemainIdx..(NUM_TOTAL - NUM_HAND - 1)) {
            ChangeOneCARD(tempCARD, handIdx[0], i)

            val (tempCredit, tempNumComputeCredit) = ComputeTotalCreditForCardChange(tempCARD, tempHandIdx, i + 1, numChangeCard - 1)
            credit += tempCredit
            numComputeCredit += tempNumComputeCredit
        }

        return Pair(credit, numComputeCredit);
    }
    fun ComputeCredit(card : MutableList<CARD>) : Int {
        var tempCARD : MutableList<CARD> = MutableList<CARD>(NUM_HAND) { CARD(-1, TYPE.UNKNOWN) }
        for(i in 0..(NUM_HAND-1)){
            tempCARD[i].AssignFrom(card[i])
        }
        m_pChecker.Sorting(tempCARD)

        return m_pChecker.ReturnCredit(tempCARD, 1)
    }

    fun PrintResultForString() : String {
        var result : String = ""
        var credit : Int = 0
        var tempCARD : MutableList<CARD> = MutableList<CARD>(NUM_HAND) { CARD(-1, TYPE.UNKNOWN) }
        for(i in 0..(NUM_HAND-1)){
            tempCARD[i].AssignFrom(m_pHand[i])
        }

        m_pChecker.Sorting(tempCARD)
        credit = m_pChecker.ReturnCredit(tempCARD, 1)
        result = m_pChecker.PrintHandCheckForString(tempCARD) + ", Credit : " + credit.toString()
        return result
    }

    fun PrintCard(card : MutableList<CARD>, numCard : Int)  {
        for(i in 0..(numCard-1)) {
            card[i].Print()
        }
    }
    fun GetHand() : MutableList<CARD> {
        return m_pHand
    } // test
    fun PrintHand() {
        PrintCard(m_pHand, NUM_HAND)
    } // test
    fun PrintHandForString(index : Int) : String {
        return m_pHand[index].PrintForString()
    }
    fun GenerateCARD(cards : MutableList<CARD>) {
        var isHand : MutableList<Boolean> = mutableListOf<Boolean>()
        isHand.addAll(Array<Boolean>(NUM_TOTAL, {false}))

        for(i in 0..(NUM_HAND-1)){
            m_pHand[i].m_number = cards[i].m_number
            m_pHand[i].m_type = cards[i].m_type
            isHand[ConTypeToVal(cards[i].m_type) + (cards[i].m_number - 1) * 4] = true
        }
        var cntRemain : Int = 0
        for(i in 0..(NUM_TOTAL-1)) {
            if(!isHand[i]) {
                m_pRemain[cntRemain].m_number =  i / 4 + 1
                m_pRemain[cntRemain].m_type = ConValToType(i % 4)
                cntRemain += 1
            }
        }
    }
    fun GenerateCARD() {
        var isHand : MutableList<Boolean> = mutableListOf<Boolean>()
        isHand.addAll(Array<Boolean>(NUM_TOTAL, {false}))

        var handIdx : MutableList<Int> = mutableListOf<Int>()
        handIdx.addAll(Array<Int>(NUM_HAND, {-1}))

        GenerateNCard(handIdx,  NUM_TOTAL,  NUM_HAND);

        for(i in 0..(NUM_HAND-1)) {
            m_pHand[i].m_number = handIdx[i] / 4 + 1
            m_pHand[i].m_type = ConValToType(handIdx[i] % 4)
            isHand[handIdx[i]] = true
        }

        var cntRemain : Int = 0
        for(i in 0..(NUM_TOTAL-1)){
            if(!isHand[i]) {
                m_pRemain[cntRemain].m_number = i / 4 + 1
                m_pRemain[cntRemain].m_type = ConValToType(i % 4)
                cntRemain += 1
            }
        }
    } // test
    fun GenerateCARDForTest() {
        var isHand: MutableList<Boolean> = mutableListOf<Boolean>()
        isHand.addAll(Array<Boolean>(NUM_TOTAL, { false }))
        var handIdx: MutableList<Int> = mutableListOf<Int>(26, 44, 8, 19, 14)

        for (i in 0..(NUM_HAND - 1)) {
            m_pHand[i].m_number = handIdx[i] / 4 + 1
            m_pHand[i].m_type = ConValToType(handIdx[i] % 4)
            isHand[handIdx[i]] = true
        }

        var cntRemain: Int = 0
        for (i in 0..(NUM_TOTAL - 1)) {
            if (!isHand[i]) {
                m_pRemain[cntRemain].m_number = i / 4 + 1
                m_pRemain[cntRemain].m_type = ConValToType(i % 4)
                cntRemain += 1
            }
        }

    }

    fun ComputeOptimumChange(handIdx : MutableList<Int>) : Pair<Int, Float> {
        var tempHandIdx : MutableList<Int> = mutableListOf<Int>()
        tempHandIdx.addAll(handIdx)

        var numChangeCard : Int = 0
        var credit : Float = 0.toFloat()

        var optimumCredit : Float = ComputeAvgCreditForCardChange(m_pHand, handIdx, 0)

        for(i in 0..(NUM_HAND-1)) {
            tempHandIdx[0] = i
            credit = ComputeAvgCreditForCardChange(m_pHand, tempHandIdx, 1)
            if(credit > optimumCredit) {
                optimumCredit = credit
                numChangeCard = 1
                for(idx in 0..(numChangeCard-1)) {
                    handIdx[idx] = tempHandIdx[idx]
                }
            }
        }
        //print(numChangeCard, optimumCredit)

        for(i in 0..(NUM_HAND-1)) {
            for(j in (i + 1)..(NUM_HAND-1)) {
                tempHandIdx[0] = i;
                tempHandIdx[1] = j;
                credit = ComputeAvgCreditForCardChange(m_pHand, tempHandIdx, 2)

                if(credit > optimumCredit) {
                    optimumCredit = credit
                    numChangeCard = 2
                    for(idx in 0..(numChangeCard-1)) {
                        handIdx[idx] = tempHandIdx[idx]
                    }
                }

            }
        }
        //print(numChangeCard, optimumCredit)

        for(i in 0..(NUM_HAND-1)) {
            for(j in (i + 1)..(NUM_HAND-1)) {
                for(k in (j+1)..(NUM_HAND-1)) {
                    tempHandIdx[0] = i;
                    tempHandIdx[1] = j;
                    tempHandIdx[2] = k;
                    credit = ComputeAvgCreditForCardChange(m_pHand, tempHandIdx, 3)

                    if(credit > optimumCredit) {
                        optimumCredit = credit
                        numChangeCard = 3
                        for(idx in 0..(numChangeCard-1)) {
                            handIdx[idx] = tempHandIdx[idx]
                        }
                    }
                }

            }
        }
        //print(numChangeCard, optimumCredit)

        for(i in 0..(NUM_HAND-1)) {
            for(j in (i + 1)..(NUM_HAND-1)) {
                for(k in (j+1)..(NUM_HAND-1)) {
                    for(l in (k+1)..(NUM_HAND-1)) {
                        tempHandIdx[0] = i;
                        tempHandIdx[1] = j;
                        tempHandIdx[2] = k;
                        tempHandIdx[3] = l;
                        credit = ComputeAvgCreditForCardChange(m_pHand, tempHandIdx, 4)

                        if(credit > optimumCredit) {
                            optimumCredit = credit
                            numChangeCard = 4
                            for(idx in 0..(numChangeCard-1)) {
                                handIdx[idx] = tempHandIdx[idx]
                            }
                        }
                    }
                }

            }
        }
        //print(numChangeCard, optimumCredit)

        for(i in 0..(NUM_HAND-1)) {
            for(j in (i + 1)..(NUM_HAND-1)) {
                for(k in (j+1)..(NUM_HAND-1)) {
                    for(l in (k+1)..(NUM_HAND-1)) {
                        for(m in (l+1)..(NUM_HAND-1)) {
                            tempHandIdx[0] = i;
                            tempHandIdx[1] = j;
                            tempHandIdx[2] = k;
                            tempHandIdx[3] = l;
                            tempHandIdx[4] = m;
                            credit = ComputeAvgCreditForCardChange(m_pHand, tempHandIdx, 5)

                            if(credit > optimumCredit) {
                                optimumCredit = credit
                                numChangeCard = 5
                                for(idx in 0..(numChangeCard-1)) {
                                    handIdx[idx] = tempHandIdx[idx]
                                }
                            }
                        }
                    }
                }

            }
        }
        //print(numChangeCard, optimumCredit)

        return Pair(numChangeCard, optimumCredit)
    }
    fun ChangeHandIdx() : Pair<MutableList<Int>, Int> {
        var handIdx : MutableList<Int> = mutableListOf<Int>()
        handIdx.addAll(Array<Int>(NUM_HAND, {-1}))

        val (numChangeCard, optimumCredit) = ComputeOptimumChange(handIdx)

        return Pair(handIdx, numChangeCard)
    }
    fun ReplaceChangeHandIdx(handIdx : MutableList<Int>, numChangeCard : Int) {
        var changeIdx : MutableList<Int> = mutableListOf<Int>()
        changeIdx.addAll(Array<Int>(NUM_HAND, {-1}))

        GenerateNCard(changeIdx, NUM_TOTAL - NUM_HAND, numChangeCard)

        for(i in 0..(numChangeCard-1)) {
            m_pHand[handIdx[i]].AssignFrom(m_pRemain[changeIdx[i]])
        }
    }
    fun ChangeHand()  {
        var handIdx : MutableList<Int> = mutableListOf<Int>()
        handIdx.addAll(Array<Int>(NUM_HAND, {-1}))

        val (numChangeCard, optimumCredit) = ComputeOptimumChange(handIdx)
        //print(optimumCredit)

        var changeIdx : MutableList<Int> = mutableListOf<Int>()
        changeIdx.addAll(Array<Int>(numChangeCard, {-1}))
        GenerateNCard(changeIdx, NUM_TOTAL - NUM_HAND, numChangeCard)

        for(i in 0..(numChangeCard-1)) {
            m_pHand[handIdx[i]].AssignFrom(m_pRemain[changeIdx[i]])
        }
    }

    fun TotalProbability()  {
        var isHand : MutableList<Boolean> = mutableListOf<Boolean>()
        var handIdx : MutableList<Int> = mutableListOf<Int>()
        handIdx.addAll(Array<Int>(NUM_HAND, {-1}))
        isHand.addAll(Array<Boolean>(NUM_TOTAL, {false}))

        var numChangeCard : Int = 0
        var cntRemain : Int
        var optimumCredit : Float
        var numGame : Int = 0
        var totalCredit : Float = 0.toFloat()

        for(c1 in 0..(NUM_TOTAL-1)) {
            for(c2 in (c1+1)..(NUM_TOTAL-1)) {
                for(c3 in (c2+1)..(NUM_TOTAL-1)) {
                    for(c4 in (c3+1)..(NUM_TOTAL-1)){
                        for(c5 in (c4+1)..(NUM_TOTAL-1)) {
                            for(i in 0..(NUM_TOTAL - 1)){
                                isHand[i] = false
                            }

                            m_pHand[0].m_number = c1 / 4+1
                            m_pHand[0].m_type = ConValToType(c1 % 4)
                            m_pHand[1].m_number = c2 / 4+1
                            m_pHand[1].m_type = ConValToType(c2 % 4)
                            m_pHand[2].m_number = c3 / 4+1
                            m_pHand[2].m_type = ConValToType(c3 % 4)
                            m_pHand[3].m_number = c4 / 4+1
                            m_pHand[3].m_type = ConValToType(c4 % 4)
                            m_pHand[4].m_number = c5 / 4+1
                            m_pHand[4].m_type = ConValToType(c5 % 4)

                            isHand[c1] = true
                            isHand[c2] = true
                            isHand[c3] = true
                            isHand[c4] = true
                            isHand[c5] = true

                            cntRemain = 0
                            for(i in 0..(NUM_TOTAL-1)) {
                                if(!isHand[i]) {
                                    m_pRemain[cntRemain].m_number = i / 4 + 1
                                    m_pRemain[cntRemain].m_type = ConValToType(i % 4)
                                    cntRemain += 1;
                                }
                            }

                            val (numChangeCard, optimumCredit) = ComputeOptimumChange(handIdx)
                            totalCredit += optimumCredit
                            numGame += 1
                            //print("Probability", numGame, totalCredit, Float(totalCredit) / Float(numGame))
                            println("Probability " + numGame.toString() + " " + optimumCredit.toString() + " " + totalCredit.toString())
                            /*if(numGame % 10 == 0) {
                                println("Probability" + numGame.toString() + "," + totalCredit.toString() + "," + ((totalCredit.toFloat()) / (numGame.toFloat())).toString())
                            }*/
                        }
                    }
                }
            }
        }
        println("Total Probability" + numGame.toString() + "," + totalCredit.toString() + "," + ((totalCredit.toFloat()) / (numGame.toFloat())).toString())
    }
}