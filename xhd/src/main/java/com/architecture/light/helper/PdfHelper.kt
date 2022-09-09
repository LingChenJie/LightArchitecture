package com.architecture.light.helper

import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream


/**
 * File describe:
 * Author: SuQi
 * Create date: 9/9/22
 * Modify date: 9/9/22
 * Version: 1
 */
object PdfHelper {

    fun imgTransformPdf(imgPaths: Array<String>, pdf_save_address: String) {
        val doc = Document(PageSize.A4, 0F, 0F, 0F, 0F)
        try {
            //获取PDF书写器
            PdfWriter.getInstance(doc, FileOutputStream(pdf_save_address))
            //打开文档
            doc.open()
            //图片对象
            var img: Image?
            //遍历
            for (i in imgPaths.indices) {
                //获取图片
                img = Image.getInstance(imgPaths[i])
                //使图片与A4纸张大小自适应
                img.scaleToFit(Rectangle(PageSize.A4))
                //添加到PDF文档
                doc.add(img)
                //下一页，每张图片一页
                doc.newPage()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            //关闭文档
            doc.close()
        }
    }

}