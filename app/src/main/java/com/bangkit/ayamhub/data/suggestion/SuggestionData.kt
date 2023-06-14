package com.bangkit.ayamhub.data.suggestion

import com.bangkit.ayamhub.R

class DataPenyakit {
    companion object {
        val daftarPenyakit = listOf(
            SuggestionData(
                "NewCastle Disease (NCD)",
                "NewCastle Disease atau penyakit Ranikhet adalah penyakit virus yang sangat menular dan dapat menyebabkan kematian pada ayam. Penyakit ini disebabkan oleh virus NewCastle Disease (NCD), virus yang tergolong genus Avian Paramyxovirus serotipe 1 (AMPV-1) dan famili Paramyxoviridae yang dapat mempengaruhi sistem pernapasan, pencernaan, dan saraf ayam.",
                listOf("Depresi dan kelesuan.", "Kehilangan nafsu makan dan penurunan produksi telur.", "Gangguan pernapasan seperti batuk, bersin, dan pilek.", "Diare dan tinja berwarna hijau.", "Kerontokan bulu dan pembengkakan pada kepala, leher, dan mata.", "Gangguan saraf seperti kejang, putus-putus, dan gangguan koordinasi."),
                listOf("Isolasi ayam yang terinfeksi segera dari ayam yang sehat untuk mencegah penyebaran penyaki", "Lakukan vaksinasi rutin sesuai jadwal yang direkomendasikan oleh dokter hewan.", "Pastikan kebersihan dan sanitasi yang baik di kandang, termasuk pembersihan rutin dan penggunaan desinfektan yang efektif.", "Hindari kontak dengan unggas liar dan serangga vektor yang dapat membawa virus.", "Jaga kekebalan tubuh ayam dengan memberikan pakan yang seimbang dan nutrisi yang cukup.", "Jika terdapat kasus NCD, segera konsultasikan dengan dokter hewan untuk diagnosis yang akurat dan penanganan yang tepat, termasuk pemberian terapi suportif dan pengendalian penyebaran penyakit."),
                R.drawable.img_ncd
            ),
            SuggestionData(
                "Salmonella",
                "Salmonella adalah jenis bakteri yang dapat menginfeksi ayam dan menyebabkan penyakit yang dikenal sebagai salmonellosis. Bakteri salmonella dapat menyebar melalui kontak dengan ayam yang terinfeksi, kotoran ayam, atau makanan yang terkontaminasi. Penyakit salmonella pada ayam juga dapat menular ke manusia melalui konsumsi produk ayam yang terinfeksi.",
                listOf("Diare berair atau berdarah.", "Nafsu makan menurun.", "Kehilangan berat badan.", "Kelesuan atau kelesuan yang berkepanjangan.", "Bulu kusam atau tidak terawat.", "Dehidrasi."),
                listOf("Isolasi ayam yang terinfeksi dari ayam yang sehat untuk mencegah penyebaran penyakit.", "Bersihkan dan sanitasi kandang secara rutin untuk mengurangi kontaminasi bakteri", "Berikan pakan yang bersih dan aman, hindari memberikan pakan yang terkontaminasi.", "Pastikan akses terhadap air minum yang bersih dan segar.", "Konsultasikan dengan dokter hewan untuk pengobatan yang sesuai, seperti pemberian antibiotik yang diresepkan."),
                R.drawable.img_salmo
            ),
            SuggestionData(
                "Coccidiosis",
                "Coccidiosis adalah infeksi protozoa parasit Genus Eimeria dari famili Eimeriidae yang mempengaruhi saluran pencernaan ayam. Penyakit ini dapat ditularkan melalui anak kandang, peralatan kandang, ransum, air minum atau litter yang tercemar.",
                listOf("Diare berdarah atau berwarna gelap.", "Kehilangan nafsu makan dan penurunan berat badan.", "Kehilangan bulu, kelemahan, dan penurunan produksi telur.", "Perubahan perilaku seperti kelesuan dan keengganan bergerak." , "Ayam tampak dehidrasi dan terlihat tidak sehat secara umum."),
                listOf("Isolasi ayam yang terinfeksi segera dari ayam yang sehat untuk mencegah penyebaran penyakit." , "Membersihkan dan mendesinfeksi kandang secara menyeluruh untuk menghilangkan oocyst yang ada di lingkungan." , "Gunakan pakan yang bersih dan bebas dari kontaminasi oocyst." , "Berikan suplemen pakan yang mengandung antioksidan dan vitamin untuk membantu meningkatkan kekebalan tubuh ayam." , "Terapkan program vaksinasi yang sesuai sesuai anjuran dokter hewan." , "Konsultasikan dengan dokter hewan untuk mendapatkan obat antikoksidiosis yang tepat dan dosis yang diperlukan." , "Jaga kebersihan dan kebersihan kandang dengan rutin membersihkan kotoran dan mempertahankan kelembaban yang sesuai."),
                R.drawable.img_cocci
            )
        )
    }
}

data class SuggestionData(
    val nama: String,
    val deskripsi: String,
    val gejala: List<String>,
    val saran: List<String>,
    val gambar: Int
)