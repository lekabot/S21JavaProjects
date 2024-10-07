package ex03;

import java.io.*;
import java.net.*;
import java.nio.file.*;

class FileDownloader implements Runnable {
    private final String fileUrl;

    public FileDownloader(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public void run() {
        String fileName = getFileNameFromURL(fileUrl);
        System.out.println(Thread.currentThread().getName() + " start download " + fileName);
        try {
            downloadFile(fileUrl, fileName);
            System.out.println(Thread.currentThread().getName() + " finish download " + fileName);
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error downloading " + fileName + ": " + e.getMessage());
        }
    }

    private void downloadFile(String fileURL, String fileName) throws IOException, URISyntaxException {
        URI uri = new URI(fileURL);
        try (InputStream in = uri.toURL().openStream();
             OutputStream out = Files.newOutputStream(Paths.get(fileName))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    private String getFileNameFromURL(String fileURL) {
        try {
            URI uri = new URI(fileURL);
            String path = uri.getPath();
            return path.substring(path.lastIndexOf('/') + 1);
        } catch (URISyntaxException e) {
            return "file" + System.currentTimeMillis(); // Fallback in case of a malformed URL
        }
    }
}