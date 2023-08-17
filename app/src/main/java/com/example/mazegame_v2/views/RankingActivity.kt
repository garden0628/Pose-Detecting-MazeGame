package com.example.mazegame_v2.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mazegame_v2.R
import com.example.mazegame_v2.databinding.ActivityRankingBinding
import com.example.mazegame_v2.viewModels.RankingViewModel

class RankingActivity : AppCompatActivity() {

    private lateinit var rankingViewModel: RankingViewModel
    private lateinit var rankingBinding: ActivityRankingBinding
    private lateinit var record: String
    private lateinit var mode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rankingBinding = DataBindingUtil.setContentView(this, R.layout.activity_ranking)
        rankingViewModel = ViewModelProvider(this)[RankingViewModel::class.java]

        record = "100"
        mode = "easy"
        rankingBinding.modeAndSeconds.text = mode + " mode record : " + record

        rankingViewModel.generateRanking(record.toInt())
        rankingBinding.personalRank.text = rankingViewModel.recordModel.p_ranking.toString() + " 등"
        rankingBinding.monthRank.text = rankingViewModel.recordModel.m_ranking.toString() + " 등"
    }
}