package com.example.figmatest.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.figmatest.databinding.SecondScreenBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SecondScreenFragment : Fragment() {

    private var _binding: SecondScreenBinding? = null
    val binding get() = _binding!!
    private val viewModel: ItemViewModel by sharedViewModel<ItemViewModel>()

    private val adapter = RVAdapter()

    private var timer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewReitings.adapter = adapter
        binding.recyclerViewReitings.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        startProgressBad(binding.randomPB, binding.randomPBProgress, (5..25).random())
        startProgressBad(binding.randomPB2, binding.randomPB2Progress, (5..25).random())
        viewModel.listItemModel.observe(this.viewLifecycleOwner, { list ->
            list.let {
                adapter.setList(list)
                Log.w("wtf", "adapter set list  = ${list}")
            }
        })
//         задержка перед обновлением анимации
        binding.randomButton.setOnClickListener {
            binding.randomPB.clearAnimation()
            binding.randomPB2.clearAnimation()
            startProgressBad(binding.randomPB, binding.randomPBProgress, (5..25).random())
            startProgressBad(binding.randomPB2, binding.randomPB2Progress, (5..25).random())
        }
        startCountDounTimer(3600000)
    }

    private fun startProgressBad(
        pb: ProgressBar,
        tv: TextView,
        duration: Int
    ) {
        pb.max = 100
        pb.scaleY = 3f
        val anim = ProgressBarAnimation(pb, tv, 0f, 100f)
        anim.duration = duration.toLong() * 1000
        pb.animation = anim
    }

    private fun startCountDounTimer(timeMillis: Long) {
        timer?.cancel()
        timer = object : CountDownTimer(timeMillis, 1000) {
            override fun onTick(ms: Long) {
                val seconds = ((ms / 1000) % 60)
                val minutes = (ms / (1000 * 60) % 60)
                val hours = (ms / (1000 * 60 * 60) % 24)
                binding.textView8.text = String.format("%02d", seconds)
                binding.textView7.text = String.format("%02d", minutes)
                binding.textView6.text = String.format("%02d", hours)
            }

            override fun onFinish() {
                binding.textView6.text = "00"
                binding.textView7.text = "00"
                binding.textView8.text = "00"
            }
        }.start()
    }
}