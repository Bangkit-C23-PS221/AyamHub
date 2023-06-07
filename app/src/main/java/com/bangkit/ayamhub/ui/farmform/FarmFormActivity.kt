package com.bangkit.ayamhub.ui.farmform

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.data.network.response.DetailFarmResponse
import com.bangkit.ayamhub.data.network.response.MyFarmResponse
import com.bangkit.ayamhub.databinding.ActivityFarmFormBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bangkit.ayamhub.helpers.uriToFile
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.ui.farmer.FarmerActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FarmFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFarmFormBinding
    private var provinsi = ""
    private var kabupaten = ""
    private var kecamatan = ""
    private var photoFile: File? = null
    private var caller = ""
    private val viewModel: FarmFormViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFarmFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkCaller()
        viewSetup()
        locationSetup()
        showImage()

        binding.gambarButton.setOnClickListener { startGallery() }
        binding.registerButton.setOnClickListener { validateForm() }
    }

    private fun checkCaller() {
        caller = intent.getStringExtra(EXTRA_CALLER) ?: ""
        if (caller == EDIT) {
            getMyFarmData()
        }
    }

    private fun getMyFarmData() {
        viewModel.getMyFarm.observe(this@FarmFormActivity) { result ->
            when(result) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
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
            Reusable.urlToBitmap(this@FarmFormActivity, data.photoUrl){
                viewModel.saveImage(it!!)
            }
            toggleButton.isChecked = data.status == READY
        }
    }

    private fun viewSetup() {
        supportActionBar?.title = "Buat Peternakan";
        binding.spKabupaten.isEnabled = false
        binding.spKecamatan.isEnabled = false
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@FarmFormActivity)
                photoFile = myFile
                viewModel.saveImage(BitmapFactory.decodeFile(myFile.path))
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
            photoFile = Reusable.bitmapToFile(this@FarmFormActivity, it)
        }
    }

    private fun validateForm() {
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
            val image = gambarPeternakan.drawable

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
                provinsi.isEmpty() -> {
                    Reusable.showToast(this@FarmFormActivity, "Mohon diisi dulu provinsinya")
                }
                kabupaten.isEmpty() -> {
                    Reusable.showToast(this@FarmFormActivity, "Mohon diisi dulu kabupatennya")
                }
                kecamatan.isEmpty() -> {
                    Reusable.showToast(this@FarmFormActivity, "Mohon diisi dulu kecamatannya")
                }
                address.isEmpty() -> {
                    alamatLengkap.error = "Mohon diisi dulu alamat lengkapnya!"
                }
                image == null -> {
                    Reusable.showToast(this@FarmFormActivity, "Mohon pilih dulu gambar peternakannya")
                }
                else -> {
                    val fullAddress = "$provinsi, $kabupaten, $kecamatan, $address"
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
        val upAge = toTextRequestBody(getAge(age.toInt()))
        val upWeight = toTextRequestBody(weight)
        val upStock = toTextRequestBody(stock)
        val upNote = toTextRequestBody(note)
        val upAddress = toTextRequestBody(address)
        val upStatus = toTextRequestBody(status)
        val image = photoFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())

        val imageMultipart = MultipartBody.Part.createFormData(
            "file",
            photoFile?.name as String,
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
                        Log.e("FarmForm", result.error)
                    }
                }
            }
        }
    }

    private fun toTextRequestBody(data: String): RequestBody {
        return data.toRequestBody("text/plain".toMediaType())
    }

    private fun getAge(days: Int): String {
        val currentDate = LocalDate.now()
        val birthDate = currentDate.minusDays(days.toLong())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Define your desired date format here
        return birthDate.format(formatter)
    }

    private fun locationSetup() {

        val provinceAdapter = provideArrayAdapter()
        binding.spProvince.adapter = provinceAdapter

        val kabupatenAdapter = provideArrayAdapter()
        binding.spKabupaten.adapter = kabupatenAdapter

        val kecamatanAdapter = provideArrayAdapter()
        binding.spKecamatan.adapter = kecamatanAdapter

        viewModel.getProvinsi.observe(this) { listProvince ->
            val sortedProvince = listProvince.sortedBy { it.nama }
            provinceAdapter.add("Pilih Provinsi")
            provinceAdapter.addAll(sortedProvince.map { it.nama })
            provinceAdapter.notifyDataSetChanged()

            binding.spProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        val selectedProvince = sortedProvince[position-1].id.toInt()
                        kabupatenAdapter.clear()
                        viewModel.provinceId.value = selectedProvince

                        binding.spKabupaten.isEnabled = true
                        binding.spKecamatan.isEnabled = false

                        provinsi = sortedProvince[position-1].nama
                        kabupaten = ""
                        kecamatan = ""
                    } else {
                        provinsi = ""
                        kabupaten = ""
                        kecamatan = ""
                        kecamatanAdapter.clear()
                        kabupatenAdapter.clear()
                        binding.spKabupaten.isEnabled = false
                        binding.spKecamatan.isEnabled = false
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        viewModel.getKabupaten.observe(this) { listKab ->
            val sortedKab = listKab.sortedBy { it.nama }
            kabupatenAdapter.add("Pilih Kabupaten")
            kabupatenAdapter.addAll(sortedKab.map { it.nama })
            kabupatenAdapter.notifyDataSetChanged()

            binding.spKabupaten.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        val selectedKabupaten = sortedKab[position-1].id.toInt()
                        kecamatanAdapter.clear()
                        viewModel.kabupatenId.value = selectedKabupaten

                        binding.spKecamatan.isEnabled = true

                        kabupaten = sortedKab[position-1].nama
                        kecamatan = ""
                    } else {
                        kabupaten = ""
                        kecamatan = ""
                        kecamatanAdapter.clear()
                        binding.spKecamatan.isEnabled = false
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        viewModel.getKecamatan.observe(this) { listKec ->
            val sortedKec = listKec.sortedBy { it.nama }
            kecamatanAdapter.add("Pilih Kecamatan")
            kecamatanAdapter.addAll(sortedKec.map { it.nama })
            kecamatanAdapter.notifyDataSetChanged()

            binding.spKecamatan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    kecamatan = if (position != 0) {
                        sortedKec[position-1].nama
                    } else {
                        ""
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    private fun provideArrayAdapter(): ArrayAdapter<String> {
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }

    private fun showLoading(loading: Boolean) {
        //TODO Uncomment this
//        if (loading) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
    }

    companion object {
        const val EDIT = "edit"
        const val EXTRA_CALLER = "caller"
        const val READY = "Siap Panen"
        const val FARMER = "farm"
    }

}