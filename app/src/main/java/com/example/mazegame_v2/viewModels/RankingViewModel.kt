package com.example.mazegame_v2.viewModels

import androidx.lifecycle.ViewModel
import com.example.mazegame_v2.models.RecordModel

class RankingViewModel : ViewModel() {
    val recordModel: RecordModel = RecordModel()

    fun generateRanking(record: Int){
        recordModel.insertNewRecord(record)
        recordModel.getRanking(record)
    }
}