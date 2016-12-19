package com.litaook.lottery.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.litaook.lottery.ResizeImage;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String imagePath;
	protected int width;
	protected int height;
	protected ImageIcon icon = null;

	public ImagePanel(String imagePath, int width, int height) {
		try {
			this.width = width;
			this.height = height;
			InputStream input = new FileInputStream(imagePath);
			BufferedImage bufferedImage = ImageIO.read(input);
			BufferedImage resizedImage = ResizeImage.convert(width, height, bufferedImage);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageIO.write(resizedImage, "jpg", output);
			icon = new ImageIcon(output.toByteArray());
			input.close();
			output.close();
		} catch(Exception e) {}
	}
	
	public void fireIconProperty() {
		firePropertyChange("icon", icon, icon);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if(icon != null) {
			Graphics scratchGraphics = (g == null) ? null : g.create();
			scratchGraphics.drawImage(icon.getImage(), 0, 0, width, height, this);
			scratchGraphics.dispose();
		} else {
			super.paintComponent(g);
		}
	}
}
