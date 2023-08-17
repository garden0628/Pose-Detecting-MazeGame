package com.example.mazegame_v2.models

class RecordModel() {
    val personalRecords = arrayListOf<Int>(50, 76, 87, 97, 108, 123, 133, 210, 360, 463, 510, 520)
    val monthRecords = arrayListOf<Int>(50, 76, 87, 97, 108, 111, 123, 133, 167, 197, 210, 232, 254, 278, 360, 383, 463, 510, 520, 694, 998, 1003)
    var p_ranking = 0
    var m_ranking = 0

    fun insertNewRecord(record: Int) {
        personalRecords.add(record)
        monthRecords.add(record)

        personalRecords.sort()
        monthRecords.sort()
    }

    fun getRanking(record: Int) {
        for (i: Int in 0 until personalRecords.size) {
            if(record == personalRecords[i]) {
                p_ranking = i+1
                break
            }
        }

        for (i: Int in 0 until monthRecords.size) {
            if(record == personalRecords[i]) {
                m_ranking = i+1
                break
            }
        }
    }

}