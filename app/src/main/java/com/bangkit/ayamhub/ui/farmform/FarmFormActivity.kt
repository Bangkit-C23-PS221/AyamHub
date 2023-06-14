package com.bangkit.ayamhub.ui.farmform

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.drawToBitmap
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.data.network.response.MyFarmResponse
import com.bangkit.ayamhub.databinding.ActivityFarmFormBinding
import com.bangkit.ayamhub.helpers.*
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.ui.farmer.FarmerActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class FarmFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFarmFormBinding
    private var photoFile: File? = null
    private var caller = ""
    private val viewModel: FarmFormViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFarmFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewSetup()
        checkCaller()
        locationSetup()
        showImage()

        binding.gambarButton.setOnClickListener { startGallery() }
        binding.registerButton.setOnClickListener { validateForm() }
    }

    private fun viewSetup() {
        supportActionBar?.title = "Buat Peternakan";
        binding.spKabupaten.isEnabled = false
        binding.spKecamatan.isEnabled = false
    }

    private fun checkCaller() {
        caller = intent.getStringExtra(EXTRA_CALLER) ?: ""
        if (caller == EDIT) {
            supportActionBar?.title = "Edit Peternakan"
            getMyFarmData()
            binding.registerButton.text = "Simpan Data"
        }
    }

    private fun getMyFarmData() {
        viewModel.getMyFarm().observe(this@FarmFormActivity) { result ->
            when(result) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    Log.e("Called Again", "Test")
                    prePopulateData(result.data)
                }
                is Result.Error -> {
                    showLoading(false)
                    Reusable.showToast(this@FarmFormActivity, "Gagal mengambil data")
                }
            }
        }
    }

    private fun prePopulateData(data: MyFarmResponse) {
        with(binding) {
            usernameEditText.setText(data.nameFarm)
            chickenType.setText(data.typeChicken)
            hargaEditText.setText(data.priceChicken)
            umurEditText.setText(Reusable.getChickenAge(data.ageChicken))
            beratEditText.setText(data.weightChicken)
            stockEditText.setText(data.stockChicken)
            catatan.setText(data.descFarm)
            alamatLengkap.setText(Reusable.getSpecificAddress(data.addressFarm))
            toggleButton.isChecked = data.status == READY
            urlToBitmap(this@FarmFormActivity, data.photoUrl){
                viewModel.saveImage(it as Bitmap)
            }
            locationSetup(data.addressFarm)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@FarmFormActivity)
                val reducedFile = reduceFileImage(myFile)
                viewModel.saveImage(BitmapFactory.decodeFile(reducedFile.path))
            }
        }
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun showImage() {
        viewModel.detectionImage.observe(this@FarmFormActivity) {
            binding.gambarPeternakan.setImageBitmap(it)
            photoFile = bitmapToFile(this@FarmFormActivity, it)
        }
    }

    private fun validateForm()  {
        with(binding) {
            val name = usernameEditText.text.toString()
            val price = hargaEditText.text.toString()
            val age = umurEditText.text.toString()
            val type = chickenType.text.toString()
            val weight = beratEditText.text.toString()
            val stock = stockEditText.text.toString()
            val address = alamatLengkap.text.toString()
            val note = catatan.text.toString()
            val status = toggleButton.text.toString()
            val province = getSelectedItem(spProvince)
            val city = getSelectedItem(spKabupaten)
            val district = getSelectedItem(spKecamatan)

            when {
                name.isEmpty() -> {
                    usernameEditText.error = "Mohon diisi dulu namanya!"
                }
                type.isEmpty() -> {
                    chickenType.error = "Mohon diisi dulu tipe ayamnya!"
                }
                price.isEmpty() -> {
                    hargaEditText.error = "Mohon diisi dulu harganya!"
                }
                age.isEmpty() -> {
                    umurEditText.error = "Mohon diisi dulu umur ayamnya!"
                }
                weight.isEmpty() -> {
                    beratEditText.error = "Mohon diisi dulu berat ayamnya!"
                }
                stock.isEmpty() -> {
                    stockEditText.error = "Mohon diisi dulu jumlah stock ayamnya!"
                }
                note.isEmpty() -> {
                    catatan.error = "Mohon diisi dulu catatannya!"
                }
                province.isEmpty() -> {
                    Reusable.showToast(this@FarmFormActivity, "Mohon diisi dulu provinsinya")
                }
                city.isEmpty() -> {
                    Reusable.showToast(this@FarmFormActivity, "Mohon diisi dulu kabupatennya")
                }
                district.isEmpty() -> {
                    Reusable.showToast(this@FarmFormActivity, "Mohon diisi dulu kecamatannya")
                }
                address.isEmpty() -> {
                    alamatLengkap.error = "Mohon diisi dulu alamat lengkapnya!"
                }
                photoFile == null -> {
                    Reusable.showToast(this@FarmFormActivity, "Mohon pilih dulu gambar peternakannya")
                }
                else -> {
                    val fullAddress = "$province, $city, $district, $address"
                    uploadFarm(name, type, price, age, weight, stock, note, fullAddress, status)
                }
            }
        }
    }

    private fun uploadFarm(
        name: String,
        type: String,
        price: String,
        age: String,
        weight: String,
        stock: String,
        note: String,
        address: String,
        status: String
    ) {
        val upName = toTextRequestBody(name)
        val upType = toTextRequestBody(type)
        val upPrice = toTextRequestBody(price)
        val upAge = toTextRequestBody(Reusable.ageToDate(age.toInt()))
        val upWeight = toTextRequestBody(weight)
        val upStock = toTextRequestBody(stock)
        val upNote = toTextRequestBody(note)
        val upAddress = toTextRequestBody(address)
        val upStatus = toTextRequestBody(status)
        val image = photoFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())

        val imageMultipart = MultipartBody.Part.createFormData(
            "file",
            "${generateUniqueCode()}.jpg",
            image as RequestBody
        )

        if(caller == EDIT) {
            viewModel.updateMyFarm(
                imageMultipart, upName, upType, upPrice, upAge, upWeight, upStock, upNote, upAddress, upStatus
            ).observe(this@FarmFormActivity) { result ->
                when(result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        finish()
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Reusable.showToast(this@FarmFormActivity, "Gagal memperbarui data anda")
                    }
                }
            }

        } else {
            viewModel.uploadFarm(
                imageMultipart, upName, upType, upPrice, upAge, upWeight, upStock, upNote, upAddress, upStatus
            ).observe(this@FarmFormActivity) { result ->
                when(result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        startActivity(Intent(this@FarmFormActivity, FarmerActivity::class.java))
                        viewModel.setLevel(FARMER)
                        finish()
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Reusable.showToast(this@FarmFormActivity, "Gagal membuat peternakan anda")
                    }
                }
            }
        }
    }

    private fun toTextRequestBody(data: String): RequestBody {
        return data.toRequestBody("text/plain".toMediaType())
    }

    private fun locationSetup(location: String = "") {
        val provinceAdapter = provideArrayAdapter(this)
        binding.spProvince.adapter = provinceAdapter

        val kabupatenAdapter = provideArrayAdapter(this)
        binding.spKabupaten.adapter = kabupatenAdapter

        val kecamatanAdapter = provideArrayAdapter(this)
        binding.spKecamatan.adapter = kecamatanAdapter

        viewModel.getProvinsi.observe(this) { listProvince ->
            val sortedProvince = setupDropdown(listProvince, provinceAdapter, PROVINCE)
            if (location.isNotEmpty()) {
                val index = provinceAdapter.getPosition(Reusable.getProvince(location))
                viewModel.provinceId.value = sortedProvince[index-1].id.toInt()
                binding.spProvince.setSelection(index)
            }

            binding.spProvince.onItemSelectedListener = onItemSelected { position ->
                if (position != 0) {
                    val selectedProvince = sortedProvince[position-1].id.toInt()
                    kabupatenAdapter.clear()
                    viewModel.provinceId.value = selectedProvince

                    binding.spKabupaten.isEnabled = true
                    binding.spKecamatan.isEnabled = false
                } else {
                    kecamatanAdapter.clear()
                    kabupatenAdapter.clear()
                    binding.spKabupaten.isEnabled = false
                    binding.spKecamatan.isEnabled = false
                }
            }
        }

        viewModel.getKabupaten.observe(this) { listKab ->
            val sortedKab = setupDropdown(listKab, kabupatenAdapter, CITY)
            if (location.isNotEmpty()) {
                val index = kabupatenAdapter.getPosition(Reusable.getCity(location))
                viewModel.kabupatenId.value = sortedKab[index-1].id.toInt()
                binding.spKabupaten.setSelection(index)
            }

            binding.spKabupaten.onItemSelectedListener = onItemSelected { position ->
                if (position != 0) {
                    val selectedKabupaten = sortedKab[position-1].id.toInt()
                    kecamatanAdapter.clear()
                    viewModel.kabupatenId.value = selectedKabupaten

                    binding.spKecamatan.isEnabled = true
                } else {
                    kecamatanAdapter.clear()
                    binding.spKecamatan.isEnabled = false
                }
            }
        }

        viewModel.getKecamatan.observe(this) { listKec ->
            setupDropdown(listKec, kecamatanAdapter, DISTRICT)
            if (location.isNotEmpty()) {
                val index = kecamatanAdapter.getPosition(Reusable.getDistrict(location))
                binding.spKecamatan.setSelection(index)
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }

    companion object {
        const val EDIT = "edit"
        const val EXTRA_CALLER = "caller"
        const val READY = "Siap Panen"
        const val FARMER = "farm"
        const val PROVINCE = "Provinsi"
        const val CITY = "Kota"
        const val DISTRICT = "Kecamatan"
    }

}