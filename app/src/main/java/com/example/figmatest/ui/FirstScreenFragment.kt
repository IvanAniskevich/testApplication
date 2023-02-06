package com.example.figmatest.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.figmatest.R
import com.example.figmatest.databinding.FirstScreenBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirstScreenFragment : Fragment() {

    private val viewModel: ItemViewModel by viewModel<ItemViewModel>()

    private var _binding: FirstScreenBinding? = null
    val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FirstScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var lottiProgress: Float? = viewModel.getLottiProgress()
        var pbProgress: Int? = viewModel.getPbProgress()
        viewModel.lottiProgress.observe(this.viewLifecycleOwner, {progress ->
            lottiProgress = progress
        })
        viewModel.pbPrgress.observe(this.viewLifecycleOwner, {progress ->
            pbProgress = progress
        })


        if (lottiProgress != null){
            Log.w("wtf", "lottiProgress !=null = $lottiProgress")
//            Происходит чертовщина!! вероятность успеха 1 к 30, что происходит??
//            хотя ниже тот же код работает правильно!
            binding.lottieAnimation.progress =lottiProgress!!
            Log.w("wtf", "lottiProgress on resume  = ${binding.lottieAnimation.progress}")
            binding.lottieAnimation.resumeAnimation()
        }else{
            Log.w("wtf", "lottiProgress = $lottiProgress")
            binding.lottieAnimation.playAnimation()
        }

        startProgressBad(binding.progressBar, binding.pBProgress, 14000, pbProgress?: 0)

        binding.startAnimButton.setOnClickListener {
            startProgressBad(binding.progressBar, binding.pBProgress, 14000, pbProgress?: 0)
            if (lottiProgress!= null){
                Log.w("wtf", "lottiProgress on startBtnClick  = $lottiProgress")
                binding.lottieAnimation.progress =lottiProgress!!
                binding.lottieAnimation.resumeAnimation()
            }
        }
        binding.stopAnimButton.setOnClickListener {
            Log.w("wtf", "lottiProgress on stopBtnClick  = ${binding.lottieAnimation.progress}")
            savePbState()
            saveAnimState()

        }
        binding.changeVisibilityButton.setOnClickListener {
            val v = binding.lottieAnimation
            if (v.visibility == View.VISIBLE){
                v.visibility = View.INVISIBLE
            }else {
                v.visibility = View.VISIBLE
            }

        }

        binding.showCustomAlertButton.setOnClickListener {
            val alertB = layoutInflater.inflate(R.layout.custom_alert, null)
            val dialog = Dialog(requireContext())
            dialog.setContentView(alertB)
            dialog.setCancelable(true)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            alertB.findViewById<Button>(R.id.alertButton).setOnClickListener {
                dialog.dismiss()
            }
        }
        binding.goToSecondScreenButton.setOnClickListener {
            findNavController().navigate(R.id.action_firstScreenFragment_to_secondScreenFragment)
        }
    }


    private fun saveAnimState() {
        val anim = binding.lottieAnimation
        viewModel.setLottiProgress(anim.progress)
        anim.pauseAnimation()

    }
    private fun savePbState(){
        viewModel.setPbProgress(binding.progressBar.progress)
        binding.progressBar.animation = null
    }

    private fun startProgressBad(
        pb: ProgressBar,
        tv: TextView,
        duration: Long,
        from: Int
    ) {
        pb.max = 100
        pb.scaleY = 3f
        val anim = ProgressBarAnimation(pb, tv, from.toFloat(), 100f)
        anim.duration = duration
        pb.animation = anim
    }

    override fun onStop() {
        super.onStop()
        savePbState()
        saveAnimState()

    }
}