package com.bangkit.ayamhub.ui.homepage.ui.detection

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.ayamhub.databinding.FragmentDetectionBinding
import com.bangkit.ayamhub.helpers.uriToFile
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.channels.FileChannel

class DetectionFragment : Fragment() {
    private var imageSize = 224

    private var _binding: FragmentDetectionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetectionViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var interpreter: Interpreter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupInterpreter()

        showImage()
        binding.btnCamera.setOnClickListener { startCamera() }
        binding.btnGalery.setOnClickListener { startGallery() }
        binding.btnClassify.setOnClickListener { classifyImage() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupInterpreter() {
        try {
            val options = Interpreter.Options()
            interpreter = Interpreter(loadModelFile(), options)
        } catch (e: IOException) {
            // Handle the exception
        }
    }

    private fun loadModelFile(): ByteBuffer {
        val fileDescriptor = requireContext().assets.openFd("newmodel.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        launcherIntentCamera.launch(intent)
    }

    private fun showImage() {
        viewModel.detectionImage.observe(viewLifecycleOwner) {
            binding.pvImage.setImageBitmap(it)
        }
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun classifyImage() {
        val imageBitmap = binding.pvImage.drawable.toBitmap()
        val resizedBitmap = ThumbnailUtils.extractThumbnail(imageBitmap, imageSize, imageSize)

        val inputBuffer = preprocessImage(resizedBitmap)
        val outputBuffer = Array(1) { FloatArray(4) }

        interpreter.run(inputBuffer, outputBuffer)

        val maxIndex = outputBuffer[0].indices.maxByOrNull { outputBuffer[0][it] } ?: -1
        val classes = arrayOf("coccidiosis", "healthy", "ncd", "salmonella")
        val className = if (maxIndex != -1) classes[maxIndex] else "Unknown"
        binding.result.text = className
    }

    private fun preprocessImage(bitmap: Bitmap): ByteBuffer {
        val inputShape = interpreter.getInputTensor(0).shape()
        val inputSize = inputShape[1]

        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)

        val inputBuffer = ByteBuffer.allocateDirect(1 * inputSize * inputSize * 3 * 4)
        inputBuffer.order(ByteOrder.nativeOrder())

        val pixels = IntArray(inputSize * inputSize)
        resizedBitmap.getPixels(pixels, 0, inputSize, 0, 0, inputSize, inputSize)

        for (pixelValue in pixels) {
            val r = (pixelValue shr 16 and 0xFF).toFloat() / 255.0f
            val g = (pixelValue shr 8 and 0xFF).toFloat() / 255.0f
            val b = (pixelValue and 0xFF).toFloat() / 255.0f

            inputBuffer.putFloat(r)
            inputBuffer.putFloat(g)
            inputBuffer.putFloat(b)
        }

        return inputBuffer
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            viewModel.saveImage(imageBitmap)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, requireContext())
                viewModel.saveImage(BitmapFactory.decodeFile(myFile.path))
            }
        }
    }

    companion object {
        private const val YOUR_MODEL_PATH = "your_model.tflite"
    }
}