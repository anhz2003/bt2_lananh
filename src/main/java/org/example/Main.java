package org.example;

import boofcv.abst.filter.derivative.ImageGradient;
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.alg.filter.derivative.GImageDerivativeOps;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.border.BorderType;
import boofcv.struct.image.GrayF32;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Đọc ảnh từ đường dẫn
        String imageFilePath = "E:/xulyanh/hinhanh/pikvn002694-tranh-phong-canh-tuyen-tap-dep-file-psd.jpg";
        BufferedImage inputImage = ImageIO.read(new File(imageFilePath));

        // Chuyển đổi sang ảnh grayscale
        GrayF32 grayImage = ConvertBufferedImage.convertFromSingle(inputImage, null, GrayF32.class);

        // Áp dụng Laplacian Gaussian
        GrayF32 laplacianImage = new GrayF32(grayImage.width, grayImage.height);
        applyLaplacian(grayImage, laplacianImage);

        // Lưu ảnh kết quả Laplacian vào thư mục dự án
        BufferedImage laplacianOutput = ConvertBufferedImage.convertTo(laplacianImage, null);
        ImageIO.write(laplacianOutput, "png", new File("laplacian_output.png"));

        // Khởi tạo ảnh gradient cho x và y
        GrayF32 gradientXImage = new GrayF32(grayImage.width, grayImage.height);
        GrayF32 gradientYImage = new GrayF32(grayImage.width, grayImage.height);

        // Áp dụng toán tử Sobel
        applySobel(grayImage, gradientXImage, gradientYImage);

        // Lưu ảnh kết quả Sobel vào thư mục dự án
        BufferedImage sobelOutputX = ConvertBufferedImage.convertTo(gradientXImage, null);
        BufferedImage sobelOutputY = ConvertBufferedImage.convertTo(gradientYImage, null);
        ImageIO.write(sobelOutputX, "png", new File("sobel_gradient_x.png"));
        ImageIO.write(sobelOutputY, "png", new File("sobel_gradient_y.png"));
    }

    private static void applyLaplacian(GrayF32 inputGray, GrayF32 outputLaplacian) {
        GImageDerivativeOps.laplace(inputGray, outputLaplacian, BorderType.EXTENDED);
    }

    private static void applySobel(GrayF32 inputGray, GrayF32 gradientX, GrayF32 gradientY) {
        ImageGradient<GrayF32, GrayF32> sobel = FactoryDerivative.sobel(GrayF32.class, null);
        sobel.process(inputGray, gradientX, gradientY);
    }
}
