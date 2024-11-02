package kr.kro.jayden_bin.greenmate.service

import kr.kro.jayden_bin.greenmate.util.MimeUtils
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class ImageService(
    private val storageService: StorageService,
) {
    fun upload(
        name: String,
        file: MultipartFile,
    ) {
        storageService.upload(name, file.inputStream)
    }

    fun getDownloadUrl(name: String): String = storageService.downloadUrl(name)

    fun generateImageName(file: MultipartFile): String {
        val extension = MimeUtils.getFileExtension(file)
        return "${UUID.randomUUID()}$extension"
    }

    fun remove(name: String) {
        storageService.delete(name)
    }
}
