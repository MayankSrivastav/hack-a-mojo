package com.util.services;


import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.util.pojo.PanDetails;

import net.coobird.thumbnailator.Thumbnails;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

public class Extractor {
	
	public static PanDetails ExtractData(byte[] imageInByte) {
		InputStream in = new ByteArrayInputStream(imageInByte);
		BufferedImage bImageFromConvert=null;
		try {
			bImageFromConvert = ImageIO.read(in);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		PanDetails panDetails=null;
		//File imageFile = new File("C:\\Users\\Ashwin\\Pictures\\pan.jpg");
		ITesseract instance = new Tesseract(); // JNA Interface Mapping
		BufferedImage img = null;
		// ITesseract instance = new Tesseract1(); // JNA Direct Mapping

		try {
			// img = ImageIO.read(imageFile);
			img = Thumbnails.of(bImageFromConvert).size(512, 512).outputFormat("png").asBufferedImage();
			System.out.println("Original Image Dimension: " + img.getWidth() + "x" + img.getHeight());

			BufferedImage SubImgage = img.getSubimage(0, 75, 250, 185);
			img = SubImgage;
			img = Thumbnails.of(img).size(512, 512).outputFormat("png").asBufferedImage();
			// ImageIO.write(SubImgage, "jpg",new
			// File("C:\\Users\\Ashwin\\Pictures\\out.jpg"));
			// int width = img.getWidth();
			// int height = img.getHeight();
			/*
			 * for(int y = 0; y < height; y++){ for(int x = 0; x < width; x++){
			 * int p = img.getRGB(x,y);
			 * 
			 * int a = (p>>24)&0xff; int r = (p>>16)&0xff; int g = (p>>8)&0xff;
			 * int b = p&0xff;
			 * 
			 * //calculate average int avg = (r+g+b)/3;
			 * 
			 * //replace RGB value with avg p = (a<<24) | (avg<<16) | (avg<<8) |
			 * avg;
			 * 
			 * img.setRGB(x, y, p); }}
			 */

			String result = instance.doOCR(thresholdImage(img, 100));
			// String result = instance.doOCR(img);
			// System.out.println(result);
			String[] arrayOfString = result.split("\n");
			List<String> tempList = new ArrayList<String>();
			// System.out.println(arrayOfString.length);
			 panDetails = new PanDetails();
			 
			 for (String string : arrayOfString) {
					if (!string.isEmpty() && !string.contains("'")
							&& !string.contains("INCOME")&& !string.contains("Permanent")) {
						//System.out.println(string);
						tempList.add(string);
					}

				}
				if (tempList.size() == 4) {
					panDetails.setName(tempList.get(0));
					panDetails.setFatherName(tempList.get(1));
					panDetails.setDob(tempList.get(2));
					panDetails.setPanCardNumber((tempList.get(3)).replaceAll("\\s+",""));
				}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		 
		return panDetails;
	}

	public static BufferedImage thresholdImage(BufferedImage image, int threshold) {
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		result.getGraphics().drawImage(image, 0, 0, null);
		WritableRaster raster = result.getRaster();
		int[] pixels = new int[image.getWidth()];
		for (int y = 0; y < image.getHeight(); y++) {
			raster.getPixels(0, y, image.getWidth(), 1, pixels);
			for (int i = 0; i < pixels.length; i++) {
				if (pixels[i] < threshold)
					pixels[i] = 0;
				else
					pixels[i] = 255;
			}
			raster.setPixels(0, y, image.getWidth(), 1, pixels);
		}
		return result;
	}
}
