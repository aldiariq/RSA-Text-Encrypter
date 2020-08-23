
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aldiariq
 */
public final class Dekripsi {
    private BigInteger n;
    private BigInteger d;
    private File LokasiFileDekripsi;
    
    Dekripsi(BigInteger d, BigInteger n, File LokasiFileDekripsi) throws FileNotFoundException, IOException{
        this.setD(d);
        this.setN(n);
        this.setLokasiFileDekripsi(LokasiFileDekripsi);
        
        //Proses Ambil Byte File yang Terenkripsi
        File fFileenkripsi = new File("FolderOperasi/HasilEnkripsi/" + this.getLokasiFileDekripsi().getName());
        FileInputStream fisFileenkripsi = new FileInputStream(fFileenkripsi);
        long panjangBytefileenkripsi = fFileenkripsi.length();
        
        byte[] byteFileenkripsi = new byte[(int) panjangBytefileenkripsi];
        fisFileenkripsi.read(byteFileenkripsi, 0, (int) panjangBytefileenkripsi);
        
        //Proses Dekripsi File yang Terenkripsi
        byte[] byteDekripsifile = prosesDekripsi(this.getD(), this.getN(), byteFileenkripsi);
        Path pathFiledekripsi = Paths.get("FolderOperasi/HasilDekripsi/" + fFileenkripsi.getName().replace(".enc", ""));
        Files.write(pathFiledekripsi, byteDekripsifile);
    }
    
    //Get Nilai D
    public BigInteger getD() {
        return d;
    }

    //Set Nilai D
    public void setD(BigInteger d) {
        this.d = d;
    }
    
    //Get Nilai N
    public BigInteger getN() {
        return n;
    }

    //Set Nilai N
    public void setN(BigInteger n) {
        this.n = n;
    }
    
    //Get Lokasi File Hasil Enkripsi
    public File getLokasiFileDekripsi() {
        return LokasiFileDekripsi;
    }

    //Set Lokasi File Hasil Enkripsi
    public void setLokasiFileDekripsi(File LokasiFileDekripsi) {
        this.LokasiFileDekripsi = LokasiFileDekripsi;
    }
    
    //Proses Dekripsi
    public static byte[] prosesDekripsi(BigInteger d, BigInteger n, byte[] hasilenkripsi) {
        return (new BigInteger(hasilenkripsi)).modPow(d, n).toByteArray();
    }
}
